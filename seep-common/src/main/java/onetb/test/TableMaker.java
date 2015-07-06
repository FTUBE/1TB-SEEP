package onetb.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class TableMaker {
	public static void main(String[] args) throws IOException{
		String path = "/Users/tianyang/EFile/t2.csv";
		File f = new File(path);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
		int line = 50000000;
		int range = 100;
		String[] u_set = new String[]{"1","0","0","0","0","0","0","0","0","0","0","5","0"};
		String[] p_set = new String[]{"taiyou","knife","jinsei","syougakusei","origami"};
		//bw.write("id,user,product");
		//bw.newLine();
		double start = System.currentTimeMillis();
		for(int n=0;n<range;n++){
			for(int i = 0;i<line/range;i++){
				int u_choose = (int) (Math.random()*(u_set.length-1));
				int p_choose = (int) (Math.random()*(p_set.length-1));
				bw.write(n+","+u_set[u_choose]+","+p_set[p_choose]);
				bw.newLine();
			}
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
