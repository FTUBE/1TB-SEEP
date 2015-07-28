package onetb.commtest;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.sun.swing.internal.plaf.basic.resources.basic_zh_TW;

public class Tm {
	public static void main(String[] args) throws IOException{
		String path = "/Users/tianyang/EFile/t1.csv";
		File f = new File(path);
		BufferedWriter bw= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
		int line = 50000000;
		int range = 100;
		String[] u_set = new String[]{"1","0","0","0","0","0","0","0","0","0","0","5","0"};
		String[] p_set = new String[]{"taiyou","knife","jinsei","syougakusei","origami"};
		//bw.write("id,user,product");
		//bw.newLine();
		double start = System.currentTimeMillis();
		byte[] header = new byte[]{0};
		for(int n=0;n<range;n++){
			for(int i = 0;i<line/range;i++){
				int u_choose = (int) (Math.random()*(u_set.length-1));
				int p_choose = (int) (Math.random()*(p_set.length-1));
				String writeline = n+","+u_set[u_choose]+","+p_set[p_choose];
				byte count = (byte) writeline.length();
				header[0]=count;
				bw.write(writeline);
				bw.newLine();
				//os.write(header);
				//bw.newLine();
			}
		}
		bw.flush();
		bw.close();
		/*
		for(int i = 0;i<line;i++){
			int id = (int) (Math.random()*range);
			int u_choose = (int) (Math.random()*(u_set.length-1));
			int p_choose = (int) (Math.random()*(p_set.length-1));
			bw.write(id+","+u_set[u_choose]+","+p_set[p_choose]);
			bw.newLine();
		}*/
		double end = System.currentTimeMillis();
		long totalwrite = f.length();
		double time = (end-start)/1000;
		System.out.println("Finish.\nKouryutsu:"+(totalwrite/time)/1000000+"MB/s");
	}
}
