package onetb.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ShareQueueTest2 {
	public static void partmain(String[] args){
	Thread a = new Thread(){public void run(){
		for(;;){
			System.out.println(ShareQueueTest.memshare.size());
		}
	}};
	a.start();
	}
}
