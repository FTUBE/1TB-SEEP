package onetb.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class getridMB {
	public static void main(String[] args) throws IOException{
		String url_part = "/Users/tianyang/Downloads/Level3/c0/Leaf4";
		File f = new File(url_part);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		File fw = new File(url_part+"-processed");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fw)));
		String line = br.readLine();
		while(line!=null){
			String[] part = line.split("second");
			if(part.length==2){
				line = br.readLine();
				continue;				
			}
			bw.write(line+"\n");
			line = br.readLine();
		}
		bw.flush();
		br.close();
		bw.close();
		
	}
}
