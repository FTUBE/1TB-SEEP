package onetb.thread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class IOTask implements Runnable{
	float accu = 0;
	int benchmark = 1;
	BufferedReader br = null;
	String url_part;
	int prev = 0;
	public IOTask(String url){
		url_part = url;
	}
	public void run() {
		File f = new File(url_part);
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double st = System.currentTimeMillis();
				while(true){
					double start = System.currentTimeMillis();
					try {
						proc();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					double end = System.currentTimeMillis();
					accu += (end-start)/1000.0;
					int run = (int) ((end-st)/1000.0);
					if(run==prev){
						System.out.println("NormalTask: at: "+run+"s run: "+accu);
						prev += 1;
					}
				}
	}
	private void proc() throws IOException{
		br.readLine();
	}
}