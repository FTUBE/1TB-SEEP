package onetb.thread;

public class Pool {
	public static void main(String[] args){
		Thread a = new Thread(new NormalTask());
		//a.start();
		Thread b =new Thread(new IOTask("/Users/tianyang/Downloads/Level3/c0/Leaf4"));
		b.start();
		Thread c =new Thread(new IOTask("/Users/tianyang/Downloads/Level3/c2/Leaf4"));
		//b.start();
		
	}
	
}
