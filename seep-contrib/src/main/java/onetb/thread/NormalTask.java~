package onetb.thread;

import java.util.HashMap;
import java.util.Map;

public class NormalTask implements Runnable{
	float accu = 0;
	Map<Integer,String> example = new HashMap<Integer,String>();
	int prev = 0;
	public void run() {
		double st = System.currentTimeMillis();
			while(true)	{
				double start = System.currentTimeMillis();
				proc();
				double end = System.currentTimeMillis();
				accu += (end-start)/1000.0;
				int run = (int) ((end-st)/1000.0);
				if(run==prev){
					System.out.println("NormalTask: at: "+run+"s run: "+accu);
					prev += 1;
				}
			}
	}
	private void proc(){
		for(int i=0;i<1;i++){
			int a = (int) Math.random();
			example.containsKey(a);
			example.put(a, "a");
			}
	}
}
