package onetb.test;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import uk.ac.imperial.lsds.seep.api.data.OTuple;
import uk.ac.imperial.lsds.seep.api.data.Schema;
import uk.ac.imperial.lsds.seep.api.data.Schema.SchemaBuilder;
import uk.ac.imperial.lsds.seep.api.data.Type;

public class LoopTest {
	public static void main(String[] args){
		int count = 0;
		Schema schema = SchemaBuilder.getInstance().newField(Type.INT, "id").newField(Type.STRING, "user").build();
		List<LoopTest> list = new ArrayList<LoopTest>();
		list.add(new LoopTest());
		list.add(new LoopTest());
		byte[] buf = new byte[10];
		double start = System.currentTimeMillis();
		String example = "user,raiko,tsukujiuribai";
		String e = "abcde";
		while(true){
			//String[] parts = example.split(",");
			//int i = example.indexOf(',');
			//int next = example.indexOf(',', i+1);
			//example.substring(0,i);
			//example.substring(i+1,next);
			//System.out.println(i+" "+next);
			//LoopTest a = list.get(1);
			//byte[] d = OTuple.create(schema, new String[]{"id", "user"}, new Object[]{1, "1000"});
			
			byte[] o = e.getBytes();
			/*
			System.out.println("Bytes length\n"+bytes.length);
			System.out.print("[ ");
			for(byte a:bytes){
				System.out.print(a+" ");
			}
			System.out.print("]");
			//byte[] data = new byte[4];
			//ByteBuffer wrapper = ByteBuffer.wrap(data);
			//int number = 123451;
			/*
			byte a = (byte)(number>>>24);
			byte b = (byte)(number>>>16);
			byte c = (byte)(number>>>8);
			byte d = (byte)(number);
			data[0] = a;
			data[1] = b;
			data[2] = c;
			data[3] = d;*/
			
			count++;
			if(count % 5000000 == 0){
				double end = System.currentTimeMillis();
				double time = (end-start)/1000;
				double kouryutsu = (count*16/time)/1000000;
				System.out.println(kouryutsu);
			}
			
			//wrapper.putInt(number);

		}
	}
	public static void serial(){
		
		//System.out.println(yourBytes.length);
	}
}
