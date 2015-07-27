package onetb.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ShareQueueTest {
	public static final BlockingQueue<byte[]> memshare= new ArrayBlockingQueue<>(1000);
	public static void partmain(String[] args){
	Thread a = new Thread(){public void run(){
		for(;;){
		try {
			memshare.put(new byte[20]);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(memshare.size());
		}
	}};
	a.start();
	}
}
