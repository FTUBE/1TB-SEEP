package onetb.commtest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
public class Memload {
	public static void main(String[] args) throws IOException{
	String url_part = "/Users/tianyang/EFile/t1.csv";
	File f = new File(url_part);
	InputStream in = new FileInputStream(f);
	//BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	System.out.println("Start loading.....");
	double start = System.currentTimeMillis();
	byte[] mem = IOUtils.toByteArray(in);
	System.out.println("Lenght is: "+mem.length);
	double end = System.currentTimeMillis();
	float time = (float) ((end-start)/1000);
	System.out.println("Done.\nTime is : "+time+" s\nNow iterating...........");
	start = System.currentTimeMillis();
	int pointer = 0;
	String s="";
	char c;
	byte count=0;
	while(pointer<mem.length){
		c = (char)mem[pointer];
		pointer++;
		if(c=='\n'){
			count=0;
			continue;
		}
		count++;
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
