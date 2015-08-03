package onetb.test;

import java.nio.ByteBuffer;

public class bufmain {
	public static void main(String[] args){
		ByteBuffer buf = ByteBuffer.allocate(10);
		buf.putInt(5);
		buf.put(new byte[]{0,1,1,1,1,1,1,1,1,1,2});
	}
}
