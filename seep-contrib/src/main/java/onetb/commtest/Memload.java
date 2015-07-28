package onetb.commtest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.commons.io.IOUtils;
public class Memload {
	public static void main(String[] args) throws IOException{
		String url_part = "/Users/tianyang/EFile/t1.csv";
		File f = new File(url_part);
		long fileLength = f.length();
		MappedByteBuffer in = new FileInputStream(url_part).getChannel().map(FileChannel.MapMode.READ_ONLY, 0, fileLength);
	    int i = 0;
	    int k=0;
	    byte[] a = new byte[(int)f.length()];
	    while (i < fileLength){
	    	a[i]=in.get();
	    	i++;
	    }
	    String s = new String(a);
	    a=null;
		BufferedReader br = new BufferedReader(new InputStreamReader(IOUtils.toInputStream(s, "ASCII")),50909090);
		System.out.println("Completion.");
		double start = System.currentTimeMillis();
		String line = br.readLine();
		while(line!=null){
			line = br.readLine();
		}
		double end = System.currentTimeMillis();
		float time = (float) ((end-start)/1000);
		System.out.println("Completion."+time+"Read: "+i);
	/*String url_part = "/Users/tianyang/EFile/t1.csv";
	System.gc();
	File f = new File(url_part);
	InputStream in = new FileInputStream(f);
	//BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	System.out.println("Start loading.....");
	double start = System.currentTimeMillis();
	byte[] mem = IOUtils.toByteArray(in);
	String data = new String(mem);
	mem=null;
	System.gc();
	System.out.println("Length is: "+data.length());
	double end = System.currentTimeMillis();
	float time = (float) ((end-start)/1000);
	System.out.println("Done.\nTime is : "+time+" s\nNow iterating...........");
	start = System.currentTimeMillis();
	//int pointer = 0;
	//int size = mem.length;
	BufferedReader br = new BufferedReader(new InputStreamReader(IOUtils.toInputStream(data)),50205000);
	start = System.currentTimeMillis();
	String s = br.readLine();
	while(s!=null){
		s=br.readLine();
	}
	end = System.currentTimeMillis();
	time = (float) ((end-start)/1000);
	System.out.println("Done.\nTime is : "+time+" s");
	/*while(pointer<size){
		byte header = mem[pointer];
		pointer++;
		for(int i = 0;i<header;i++){
			byte a =mem[pointer];
			pointer++;
		}	
		//Arrays.fill(data, (byte)0);
	}
	end = System.currentTimeMillis();
	time = (float) ((end-start)/1000);
	System.out.println("Done.\nTime is : "+time+" s");
	/*String line = br.readLine();
	int count = 0;
	while(line!=null){
		mem.add(line);
		line=br.readLine();
		if(count%500000==0){
		System.out.println(count);
			}
		count++;
	}
	System.out.println(count);*/
	//start = System.currentTimeMillis();
	/*int i = 0;
	int size = 10000000;
	String sample = null;
	while(i<size){
		sample = mem.get(i);
		i++;
	}
	end = System.currentTimeMillis();
	time = (float) ((end-start)/1000);
	System.out.println("Done.\nTime is : "+time+" s");*/
	}
}
