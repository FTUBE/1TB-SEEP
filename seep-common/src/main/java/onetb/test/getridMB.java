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
		File f = new File("/Users/tianyang/Downloads/Testdata/WithMiddleLevel3/Case2/Leaf4");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		File fw = new File("/Users/tianyang/Downloads/Testdata/WithMiddleLevel3/Case2/speed4");
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
