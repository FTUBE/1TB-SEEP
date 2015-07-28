package onetb.test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class LoadingMem {
public static void main(String[] args) throws IOException{
	String url_part = "/Users/tianyang/EFile/t1.csv";
	File f = new File(url_part);
	long fileLength = f.length();
	MappedByteBuffer in = new FileInputStream(url_part).getChannel().map(FileChannel.MapMode.READ_ONLY, 0, fileLength);
    int i = 0;
    int k=0;
    System.out.println(in.capacity());
    //byte[] a = new byte[(int)f.length()];
   /* while (i < fileLength){
    	a[i]=in.get();
    	i++;
    }*/
	/*InputStream is = new ByteArrayInputStream(a);	
	BufferedReader br = new BufferedReader(new InputStreamReader(is),50909090);
	System.out.println("Completion.");
	double start = System.currentTimeMillis();
	String line = br.readLine();
	while(line!=null){
		line = br.readLine();
	}*/
	/*BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
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
	int size = mem.size();
	System.out.println("size:"+mem.size());
	String sample = null;
	while(i<size){
		sample = mem.get(i);
		i++;
	}
	end = System.currentTimeMillis();
	time = (float) ((end-start)/1000);
	System.out.println("Done.\nTime is : "+time+" s");*/

	/*double end = System.currentTimeMillis();
	float time = (float) ((end-start)/1000);
	System.out.println("Completion."+time+"Read: "+i);*/
}
}
