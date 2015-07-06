package uk.ac.imperial.lsds.seep.api.data;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public abstract class Type {
	
	public final static int SIZE_OVERHEAD = Integer.BYTES;
	
	public abstract String toString();
	public abstract void write(ByteBuffer buffer, Object o);
	public abstract Object read(ByteBuffer buffer);
	public abstract int sizeOf(Object o);
	public abstract boolean isVariableSize();
	public abstract int write(byte[] buf, Object o,int pos);
	
	public enum JavaType{
		BYTE, SHORT, INT, LONG, STRING, BYTES
	}
	
	public static final Type BYTE = new Type() {
		
		public String toString(){
			return "BYTE";
		}

		@Override
		public void write(ByteBuffer buffer, Object o) {
			buffer.put((byte)o);
		}

		@Override
		public Object read(ByteBuffer buffer) {
			Object o = buffer.get();
			return o;
		}

		@Override
		public int sizeOf(Object o) {
			return Byte.BYTES;
		}

		@Override
		public boolean isVariableSize() {
			return false;
		}

		@Override
		public int write(byte[] buf, Object o, int pos) {
			// TODO Auto-generated method stub
			return 0;
		}
		
	};
	
	public static final Type SHORT = new Type() {
		
		public String toString(){
			return "SHORT";
		}

		@Override
		public void write(ByteBuffer buffer, Object o) {
			buffer.putShort((short)o);
		}

		@Override
		public Object read(ByteBuffer buffer) {
			Object o = buffer.getShort();
			return o;
		}

		@Override
		public int sizeOf(Object o) {
			return Short.BYTES;
		}

		@Override
		public boolean isVariableSize() {
			return false;
		}

		@Override
		public int write(byte[] buf, Object o, int pos) {
			// TODO Auto-generated method stub
			return 0;
		}
	};
	
	public static final Type INT = new Type() {
		
		public String toString(){
			return "INT";
		}

		@Override
		public void write(ByteBuffer buffer, Object o) {
			buffer.putInt((int)o);
		}

		@Override
		public Object read(ByteBuffer buffer) {
			return buffer.getInt();
			
		}

		@Override
		public int sizeOf(Object o) {
			return Integer.BYTES;
		}

		@Override
		public boolean isVariableSize() {
			return false;
		}

		@Override
		public int write(byte[] buf, Object o, int pos) {
			int number = (int)o;
			byte a = (byte)(number>>>24);
			byte b = (byte)(number>>>16);
			byte c = (byte)(number>>>8);
			byte d = (byte)(number);
			buf[pos] = a;
			buf[pos+1] = b;
			buf[pos+2] = c;
			buf[pos+3] = d;
			pos += 4;
			return pos;
		}
	};
	
	public static final Type LONG = new Type() {
		
		public String toString(){
			return "LONG";
		}


		@Override
		public Object read(ByteBuffer buffer) {
			return buffer.getLong();
		}

		@Override
		public void write(ByteBuffer buffer, Object o) {
			buffer.putLong((long)o);
		}

		@Override
		public int sizeOf(Object o) {
			return Long.BYTES;
		}


		@Override
		public boolean isVariableSize() {
			return false;
		}


		@Override
		public int write(byte[] buf, Object o, int pos) {
			// TODO Auto-generated method stub
			return 0;
		}
	};
	
	public static final Type STRING = new Type(){
		
		public String toString(){
			return "STRING";
		}

		@Override
		public Object read(ByteBuffer buffer) {
			int length = buffer.getInt();
            byte[] bytes = new byte[length];
            buffer.get(bytes);
            String str = null;
            try {
				str = new String(bytes, "UTF8");
			} 
            catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return str;
		}

		@Override
		public void write(ByteBuffer buffer, Object o) {
			byte[] bytes = null;
			try {
				bytes = ((String)o).getBytes("UTF8");
			} 
			catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if (bytes.length > Integer.MAX_VALUE)
                throw new SchemaException("A string or charsequence cannot be longer than Integer.MAX_VALUE");
            buffer.putInt((int) bytes.length);
            buffer.put(bytes);
		}
		@Override
		public int sizeOf(Object o) {
			return Integer.BYTES + uk.ac.imperial.lsds.seep.util.Utils.utf8Length((String)o);
		}

		@Override
		public boolean isVariableSize() {
			return true;
		}

		@Override
		public int write(byte[] buf, Object o, int pos) {
			byte[] bytes = null;
			try {
				bytes = ((String)o).getBytes("UTF8");
			} 
			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			int length = bytes.length;
			byte a = (byte)(length>>>24);
			byte b = (byte)(length>>>16);
			byte c = (byte)(length>>>8);
			byte d = (byte)(length);
			buf[pos] = a;
			buf[pos+1] = b;
			buf[pos+2] = c;
			buf[pos+3] = d;
			pos += 4;
			int i=0;
			while(true){
				buf[pos+i]= bytes[i];
				i++;
				if(!(i<length)){
					break;
				}
			}
			pos += length;
			return pos;
		}
	};
	
	public static final Type SHORTSTRING = new Type(){

		private final int maxSize = 64; // bytes
		
		@Override
		public String toString() {
			return "SHORTSTRING";
		}

		@Override
		public void write(ByteBuffer buffer, Object o) {
			// TODO;
			
		}

		@Override
		public Object read(ByteBuffer buffer) {
			// TODO:
			return null;
		}

		@Override
		public int sizeOf(Object o) {
			return maxSize;
		}

		@Override
		public boolean isVariableSize() {
			return false;
		}

		@Override
		public int write(byte[] buf, Object o, int pos) {
			// TODO Auto-generated method stub
			return 0;
		}
		
	};
	
	public static final Type BYTES = new Type() {
		
		public String toString(){
			return "BYTES";
		}

		@Override
		public Object read(ByteBuffer buffer) {
			int size = buffer.getInt();
            ByteBuffer val = buffer.slice();
            val.limit(size);
            buffer.position(buffer.position() + size);
            return val;
		}

		@Override
		public void write(ByteBuffer buffer, Object o) {
			ByteBuffer arg = (ByteBuffer) o;
            int pos = arg.position();
            buffer.putInt(arg.remaining());
            buffer.put(arg);
            arg.position(pos);
		}

		@Override
		public int sizeOf(Object o) {
			ByteBuffer buffer = (ByteBuffer) o;
            return Integer.BYTES + buffer.remaining();
		}

		@Override
		public boolean isVariableSize() {
			return true;
		}

		@Override
		public int write(byte[] buf, Object o, int pos) {
			// TODO Auto-generated method stub
			return 0;
		}
	};
	
}