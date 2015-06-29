package uk.ac.imperial.lsds.seep.comm;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.imperial.lsds.seep.comm.protocol.MasterWorkerCommand;
import uk.ac.imperial.lsds.seep.comm.serialization.Serializer;
import uk.ac.imperial.lsds.seep.util.ExtendedObjectOutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

public class IOComm implements Comm {

	final private Logger LOG = LoggerFactory.getLogger(IOComm.class.getName());
	
	private Serializer s;
	private ExecutorService e;
	
	public IOComm(Serializer s, ExecutorService e){
		this.s = s;
		this.e = e;
	}
	
	@Override
	public boolean send_sync(byte[] data, Connection c) {
		try {
			Socket connection = c.getOpenSocket();
			DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			dos.writeInt(data.length);
			dos.write(data);
			
			String ack = in.readLine();
			if(! ack.equals("ACK")){
				// retry or something...
				dos.close();
				in.close();
				return false;
			}
			dos.close();
			in.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void send_async(byte[] data, Connection c) {
		try {
			Socket connection = c.getOpenSocket();
			DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
			dos.writeInt(data.length);
			dos.write(data);
			dos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void send_sync(byte[] data, Set<Connection> cs) {
		for(Connection c : cs){
			this.send_sync(data, c);
		}
	}

	@Override
	public void send_async(byte[] data, Set<Connection> cs) {
		for(Connection c : cs){
			this.send_async(data, c);
		}
		
	}

	@Override
	public boolean send_sync_parallel(byte[] data, Set<Connection> cs) {
		Set<SyncSend> tasks = new HashSet<>();
		List<Future<SendResult>> results = new ArrayList<>();
		final IOComm cu = this;
		for(Connection c : cs){
			SyncSend ss = new SyncSend(data, c, cu);
			tasks.add(ss);
		}
		try {
			results = e.invokeAll(tasks);
			for(Future<SendResult> f : results){
				try {
					if(! f.get().isDone())
						return false;
				} 
				catch (ExecutionException e1) {
					e1.printStackTrace();
				}
			}
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	class SyncSend implements Callable<SendResult>{

		private byte[] data;
		private Connection c;
		private IOComm cu;
		
		public SyncSend(byte[] data, Connection c, IOComm cu){
			this.data = data;
			this.c = c;
			this.cu = cu;
		}
		
		@Override
		public SendResult call() throws Exception {
			
			cu.send_sync(data, c);
			
			return null;
		}
		
	}
	
	class SendResult implements Future<SendResult>{

		@Override
		public boolean cancel(boolean mayInterruptIfRunning) {
			
			return false;
		}

		@Override
		public boolean isCancelled() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isDone() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public SendResult get() throws InterruptedException, ExecutionException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public SendResult get(long timeout, TimeUnit unit)
				throws InterruptedException, ExecutionException,
				TimeoutException {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

	@Override
	public void send_async_parallel(byte[] data, Set<Connection> cs) {
		final IOComm cu = this;
		for(Connection c : cs){
			e.execute(new Runnable(){
				@Override
				public void run() {
					cu.send_async(data, c);
				}
			});
		}
	}

	@Override
	public void send_sync(Object data, Connection c) {
		byte[] d = s.serialize(data);
		this.send_sync(d, c);
	}

	@Override
	public void send_async(Object data, Connection c) {
		byte[] d = s.serialize(data);
		this.send_async(d, c);
	}

	@Override
	public void send_sync(Object data, Set<Connection> cs) {
		byte[] d = s.serialize(data);
		this.send_sync(d, cs);
	}

	@Override
	public void send_async(Object data, Set<Connection> cs) {
		byte[] d = s.serialize(data);
		this.send_async(d, cs);
	}

	@Override
	public void send_sync_parallel(Object data, Set<Connection> cs) {
		byte[] d = s.serialize(data);
		this.send_sync_parallel(d, cs);
	}

	@Override
	public void send_async_parallel(Object data, Set<Connection> cs) {
		byte[] d = s.serialize(data);
		this.send_async_parallel(d, cs);
	}

	@Override
	public void send_sync(String data, Connection c) {
		byte[] d = s.serialize(data);
		this.send_sync(d, c);
	}

	@Override
	public void send_async(String data, Connection c) {
		byte[] d = s.serialize(data);
		this.send_async(d, c);
	}

	@Override
	public void send_sync(String data, Set<Connection> cs) {
		byte[] d = s.serialize(data);
		this.send_sync(d, cs);
	}

	@Override
	public void send_async(String data, Set<Connection> cs) {
		byte[] d = s.serialize(data);
		this.send_async(d, cs);
	}

	@Override
	public void send_sync_parallel(String data, Set<Connection> cs) {
		byte[] d = s.serialize(data);
		this.send_sync_parallel(d, cs);
	}

	@Override
	public void send_async_parallel(String data, Set<Connection> cs) {
		byte[] d = s.serialize(data);
		this.send_async_parallel(d, cs);
	}

	@Override
	public boolean send_object_sync(Object data, Connection c) {
		ExtendedObjectOutputStream oos = null;
		BufferedReader in = null;
		
		try {
			Socket connection = c.getOpenSocket();
			oos = new ExtendedObjectOutputStream(connection.getOutputStream());
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			oos.writeClassDescriptor(ObjectStreamClass.lookup(data.getClass()));
			oos.writeObject(data);
			
			String ack = in.readLine();
			
			if(! ack.equals("ACK")){
				// retry or something...
				oos.close();
				in.close();
				return false;
			}
			oos.close();
			in.close();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;	
	}

	@Override
	public void send_object_async(Object data, Connection c) {
		ExtendedObjectOutputStream oos = null;
		
		try {
			Socket connection = c.getOpenSocket();
			oos = new ExtendedObjectOutputStream(connection.getOutputStream());
			oos.writeClassDescriptor(ObjectStreamClass.lookup(data.getClass()));
			oos.writeObject(data);
			oos.close();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void send_object_sync(Object data, Set<Connection> cs) {
		for(Connection c : cs){
			this.send_object_sync(data, c);
		}
	}

	@Override
	public void send_object_async(Object data, Set<Connection> cs) {
		for(Connection c : cs){
			this.send_object_async(data, c);
		}
	}

	@Override
	public boolean send_object_sync_parallel(Object data, Set<Connection> cs) {
		try {
			throw new Exception("NOT IMPLEMENTED");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return false;
	}

	@Override
	public void send_object_async_parallel(Object data, Set<Connection> cs) {
		try {
			throw new Exception("NOT IMPLEMENTED");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public boolean send_object_sync(MasterWorkerCommand co, Connection c, Kryo k) {
		
		try {
			Socket s = c.getOpenSocket();
			OutputStream bos = s.getOutputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			Output o = new Output(bos);
			k.writeObject(o, co);
			o.flush(); // this line
			String ack = in.readLine();
			if(!ack.equals("ack")){
				in.close();
				o.close();
				return false;
			}
			o.close();
			return true;
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean send_object_async(MasterWorkerCommand co, Connection c, Kryo k, int numRetries, int reconnectBackoff) {
		for (int i = 0; i < numRetries; i++) {
			try {
				Socket s = c.getOpenSocket();
				OutputStream bos = s.getOutputStream();
				Output o = new Output(bos);
				k.writeObject(o, co);
				o.close();
				return true;
			} 
			catch (IOException e) {
				LOG.error("Failed connection attempt {}", i);
			}
			try {
				LOG.info("Reconnecting in {}", reconnectBackoff+"...");
				Thread.sleep(reconnectBackoff);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean send_object_sync(MasterWorkerCommand co, Set<Connection> cs, Kryo k) {
		for(Connection c : cs){
			LOG.trace("Send to: {}", c.toString());
			boolean success = this.send_object_sync(co, c, k);
			if(!success)
				return false;
		}
		return true;
	}

	@Override
	public boolean send_object_async(MasterWorkerCommand co, Set<Connection> cs, Kryo k) {
		try {
			throw new Exception("NOT IMPLEMENTED");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean send_object_sync_parallel(MasterWorkerCommand data, Set<Connection> cs,
			Kryo k) {
		try {
			throw new Exception("NOT IMPLEMENTED");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public void send_object_async_parallel(MasterWorkerCommand data, Set<Connection> cs,
			Kryo k) {
		try {
			throw new Exception("NOT IMPLEMENTED");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
}
