package onetb.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LoadingMem {
public static void main(String[] args) throws IOException{
	String url_part = "/Users/tianyang/EFile/t1.csv";
	File f = new File(url_part);
	BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	ArrayList<String> mem = new ArrayList<String>();
	System.out.println("Start loading.....");
	System.gc();
	double start = System.currentTimeMillis();
	String line = br.readLine();
	int count = 0;
	while(line!=null){
		mem.add(line);
		line=br.readLine();
		if(count%500000==0){
		System.out.println(count);
			}
		count++;
	}
	System.out.println(count);
	double end = System.currentTimeMillis();
	float time = (float) ((end-start)/1000);
	System.out.println("Done.\nTime is : "+time+" s\n Now iterating...........");
	start = System.currentTimeMillis();
	int i = 0;
	int size = 10000000;
	String sample = null;
	while(i<size){
		sample = mem.get(i);
		i++;
	}
	end = System.currentTimeMillis();
	time = (float) ((end-start)/1000);
	System.out.println("Done.\nTime is : "+time+" s");
}
}
