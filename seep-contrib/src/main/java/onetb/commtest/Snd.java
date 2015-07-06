package onetb.commtest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;

public class Snd {
	public static void main(String[] args) throws IOException{
		Selector selectorset = Selector.open();
		SocketChannel channel = SocketChannel.open();
		InetSocketAddress address = new InetSocketAddress("localhost", 5050);
        //Socket socket = channel.socket();
        channel.connect(address);
        //channel.socket().setTcpNoDelay(true);
        //channel.configureBlocking(false);
        channel.socket().setKeepAlive(false);
        //channel.socket().setSendBufferSize(100);
        int count = 0;
        OutputStream os = channel.socket().getOutputStream();
        int amount = 200000;
        byte[] data = new byte[amount];
        Arrays.fill(data, (byte)1);
        //ByteBuffer buf = ByteBuffer.allocate(amount);
        //buf.wrap(data);
        double start = System.currentTimeMillis();
        while(true){
        	count++;
        	IOUtils.write(data, os);
        	if(count % 1000000==0){
    			double totalsize = count*amount;
    			double end = System.currentTimeMillis();
    			double time = (end-start)/1000;
    			System.out.println("Writer:"+(totalsize/time)/1000000+"MB/s");
    			System.out.println(time+" s");
    			break;
        	}
        }
	}
}
