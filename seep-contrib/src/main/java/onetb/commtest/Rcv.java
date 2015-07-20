package onetb.commtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import org.apache.commons.io.IOUtils;

public class Rcv {
	public static void main(String[] args) throws IOException{
		/*Selector acceptorSelector = Selector.open();
		Selector readerSelector = Selector.open();
		ServerSocketChannel channel = ServerSocketChannel.open();
		SocketAddress sa = new InetSocketAddress("127.0.0.1", 5050);
		channel.configureBlocking(false);
		channel.bind(sa);
		channel.register(acceptorSelector, SelectionKey.OP_ACCEPT);
		while(true){
		int readyChannels = acceptorSelector.select();
		if(readyChannels==0){
			continue;
		}
		System.out.println(readyChannels);
		}*/
		ServerSocketChannel channel = ServerSocketChannel.open();
		SocketAddress sa = new InetSocketAddress("127.0.0.1", 5050);
		//channel.configureBlocking(false);
		channel.bind(sa);
		SocketChannel readC = null;
		while(true){
			readC = channel.accept();
			if(readC!=null){
				break;
			}
		}
		readC.socket().setTcpNoDelay(true);
        readC.socket().setKeepAlive(true);
		readC.configureBlocking(true);
		ByteBuffer buf = ByteBuffer.allocate(20);
		SocketChannel is = readC.socket().getChannel();
		int count=0;
		while(true){
			buf.clear();
			int i = is.read(buf);
			if(!(i>0)){
				continue;
			}
			System.out.println(count);
			//System.out.println(buf.remaining());
        	/*if(count % 50000==0){
    			double totalsize = count;
    			double end = System.currentTimeMillis();
    			double time = (end-start)/1000;
    			System.out.println("Reader:"+(totalsize/time)/1000000+"MHZ");
    			
        	}*/
        	count++;
		}
	}
}
