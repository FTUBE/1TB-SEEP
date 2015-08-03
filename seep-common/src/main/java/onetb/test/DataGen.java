package onetb.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class DataGen {
	public static void main(String[] args) throws IOException{
		String path = "/Users/tianyang/EFile/Data";
		File f = new File(path);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
		int line = 5000;
		//int range = 100;
		//bw.write("id,user,product");
		//bw.newLine();
		double start = System.currentTimeMillis();
		for(int n=0;n<line;n++){
				int id = n;
				int x = (int) (Math.random()*1000);
				int y = (int) (Math.random()*1000);
				int z = (int) (Math.random()*1000);
				int x1 = (int) (Math.random()*1000);
				int y1 = (int) (Math.random()*1000);
				int z1 = (int) (Math.random()*1000);
				bw.write(n+","+Math.min(x, x1)+","+Math.min(y, y1)+","+Math.min(z, z1)+","+Math.max(x, x1)+","+Math.max(y, y1)+","+Math.max(z, z1));
				bw.newLine();
		}
		/*
		for(int i = 0;i<line;i++){
			int id = (int) (Math.random()*range);
			int u_choose = (int) (Math.random()*(u_set.length-1));
			int p_choose = (int) (Math.random()*(p_set.length-1));
			bw.write(id+","+u_set[u_choose]+","+p_set[p_choose]);
			bw.newLine();
		}*/

		bw.flush();
		bw.close();
		double end = System.currentTimeMillis();
		long totalwrite = f.length();
		double time = (end-start)/1000;
		System.out.println("Finish.\nKouryutsu:"+(totalwrite/time)/1000000+"MB/s");
	}
}
