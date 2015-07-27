package uk.ac.imperial.lsds.seepworker.core.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import uk.ac.imperial.lsds.seep.api.DataStoreType;
import uk.ac.imperial.lsds.seep.api.data.ITuple;
import uk.ac.imperial.lsds.seep.api.data.Schema;
import uk.ac.imperial.lsds.seep.core.InputAdapter;
import uk.ac.imperial.lsds.seep.errors.NotImplementedException;
import uk.ac.imperial.lsds.seepworker.WorkerConfig;

public class FileDataStream implements InputAdapter {

	final private short RETURN_TYPE = InputAdapterReturnType.ONE.ofType();
	final private DataStoreType TYPE = DataStoreType.FILE;
	
	final private int streamId;
	final private List<Integer> representedIds;
	private ITuple iTuple;
	
	private InputBuffer buffer;
	private BlockingQueue<byte[]> queue;
	private int queueSize;
	private ArrayList<String> mem;
	
	private BufferedReader br;
	private int count = 0;
	private int size = 0;
	private String read = null;
	
	public FileDataStream(WorkerConfig wc, int opId, int streamId, Schema expectedSchema){
		this.streamId = streamId;
		this.representedIds = new ArrayList<>();
		this.representedIds.add(opId);
		this.iTuple = new ITuple(expectedSchema);
		this.queueSize = wc.getInt(WorkerConfig.SIMPLE_INPUT_QUEUE_LENGTH);
		this.queue = new ArrayBlockingQueue<byte[]>(queueSize);
		int headroom = wc.getInt(WorkerConfig.BATCH_SIZE) * 2;
		this.buffer = new InputBuffer(headroom);
	}
	
	private FileDataStream(int opId, int streamId, Schema expectedSchema, int inputQueueLength, int rxBufSize){
		this.representedIds = new ArrayList<>();
		this.representedIds.add(opId);
		this.streamId = streamId;
		this.iTuple = new ITuple(expectedSchema);
		this.queueSize = inputQueueLength;
		this.queue = new ArrayBlockingQueue<byte[]>(queueSize);
		this.buffer = new InputBuffer(rxBufSize);
	}
	
	public static FileDataStream getFileDataStream_test(int opId, int streamId, Schema s, int qLength, int rxSize){
		return new FileDataStream(opId, streamId, s, qLength, rxSize);
	}
	
	@Override
	public List<Integer> getRepresentedOpId(){
		return representedIds;
	}
	
	@Override
	public int getStreamId() {
		return streamId;
	}

	@Override
	public short returnType() {
		return RETURN_TYPE;
	}

	@Override
	public DataStoreType getDataOriginType() {
		return TYPE;
	}

	@Override
	public void readFrom(ReadableByteChannel channel, int id) {
		//buffer.readFrom(channel, this);
	}

	@Override
	public void pushData(byte[] data) {
		try {
			queue.put(data);
		} 
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void pushData(List<byte[]> data) {
		throw new NotImplementedException("why needed list of arrays");
	}

	@Override
	public ITuple pullDataItem(int timeout) {
		if(count<size){
			read = mem.get(count);
			count++;
		}
		else{
			return null;
		}
		iTuple.setRawData(read);
		iTuple.setStreamId(streamId);
		return iTuple;
	}

	@Override
	public ITuple pullDataItems(int timeout) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void availablemem(ArrayList<String> _mem){
		this.mem=_mem;
		this.size = mem.size();
	}
	public void availablebr(BufferedReader br) {
		this.br=br;
	}

}
