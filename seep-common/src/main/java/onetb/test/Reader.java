package onetb.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Reader {
public static void main(String[] args) throws IOException{
	String url_part = "/Users/tianyang/EFile/t1.csv";
	File f = new File(url_part);
	MappedByteBuffer in = new FileInputStream(url_part).getChannel().map(FileChannel.MapMode.READ_ONLY, 0, f.length());
	BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	String line = br.readLine();
	double start = System.currentTimeMillis();
	while(line!=null){
		line = br.readLine();
	}
	double end = System.currentTimeMillis();
	double time = (end-start)/1000;
	System.out.println(time+"s");
}
}
