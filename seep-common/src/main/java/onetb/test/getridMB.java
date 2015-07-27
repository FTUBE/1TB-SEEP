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
		String url_part = "/Users/tianyang/Downloads/5.bin";
		File f = new File(url_part);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		//File fw = new File(url_part+"-processed");
		//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fw)));
		int line = br.read();
		int count = 0;
		while(line!=-1){
			//String[] part = line.split("second");
			//if(part.length==2){
			char a = (char)line;
			byte[] b = new byte[2]; 
	        b[0] = (byte) ((a& 0xFF00) >> 8); 
	        b[1] = (byte) (a & 0xFF); 
				System.out.print(+b[0]+","+b[1]+",");
				line = br.read();
				count++;
				if(count==256){
					break;
				}
				//continue;				
			//}
			//bw.write(line+"\n");
			//line = br.readLine();
		}
		//bw.flush();
		br.close();
		//bw.close();
		
	}
}
