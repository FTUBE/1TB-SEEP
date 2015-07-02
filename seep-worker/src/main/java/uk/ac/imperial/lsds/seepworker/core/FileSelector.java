package uk.ac.imperial.lsds.seepworker.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.channels.Selector;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.imperial.lsds.seep.api.DataStore;
import uk.ac.imperial.lsds.seep.api.FileConfig;
import uk.ac.imperial.lsds.seep.core.InputAdapter;
import uk.ac.imperial.lsds.seep.errors.NotImplementedException;
import uk.ac.imperial.lsds.seep.util.Utils;
import uk.ac.imperial.lsds.seepworker.WorkerConfig;
import uk.ac.imperial.lsds.seepworker.core.input.FileDataStream;

public class FileSelector {

	final private static Logger LOG = LoggerFactory.getLogger(FileSelector.class);
	
	private Reader reader;
	private Thread readerWorker;
	private Writer writer;
	private Thread writerWorker;
	
	private Map<Integer, InputAdapter> dataAdapters;
	
	public FileSelector(WorkerConfig wc) {
		
	}
	
	public void startFileSelector(){
		// Start readers
		/*if(readerWorker != null){
			LOG.info("Starting reader: {}", readerWorker.getName());
			readerWorker.start();
		}*/
		LOG.info("Nothing for reader");
	
		// Start writers
		if(writerWorker != null){
			LOG.info("Starting writer: {}", writerWorker.getName());
			writerWorker.start();
		}
	}
	
	public void stopFileSelector(){
		// Stop readers
		if(readerWorker != null){
			LOG.info("Stopping reader: {}", readerWorker.getName());
			reader.stop();
		}
		
		// Stop writers
		if(writerWorker != null){
			LOG.info("Stopping writer: {}", writerWorker.getName());
			writer.stop();
		}
	}
	
	public void configureAccept(Map<Integer, DataStore> fileOrigins, Map<Integer, InputAdapter> dataAdapters){
		this.dataAdapters = dataAdapters;
		//this.reader = new Reader();
		//this.readerWorker = new Thread(this.reader);
		//this.readerWorker.setName("File-Reader");
		for(Entry<Integer, DataStore> e : fileOrigins.entrySet()){
			try {
				FileConfig config = new FileConfig(e.getValue().getConfig());
				String absPath = Utils.absolutePath(config.getString(FileConfig.FILE_PATH));
				File file = new File(absPath);
				LOG.info("Created URI to local resource: {}", file.toString());
				BufferedReader br= new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				FileDataStream fs = (FileDataStream)dataAdapters.get(e.getKey());
				fs.availablebr(br);
			} 
			catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace();
			} 
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
	
	public void addNewAccept(Path resource, int id, Map<Integer, InputAdapter> dataAdapters){
		/*this.dataAdapters = dataAdapters;
		Map<SeekableByteChannel, Integer> channels = new HashMap<>();
		SeekableByteChannel sbc = null;
		try {
			sbc = Files.newByteChannel(resource, StandardOpenOption.READ);
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		channels.put(sbc, id);
		this.reader = new Reader();
		this.readerWorker = new Thread(this.reader);
		this.readerWorker.setName("File-Reader");
		this.reader.availableChannels(channels);*/
	}
	
	public void configureDownstreamFiles(Map<Integer, DataStore> fileDest){
		// TODO: implement this, configure writer, etc...
		throw new NotImplementedException("TODO: ");
	}
	
	class Reader implements Runnable {

		private boolean working = true;
		private Map<BufferedReader, Integer> channels;
		
		public Reader(){
			try {
				Selector.open();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void availableChannels(Map<BufferedReader, Integer> channels){
			this.channels = channels;
		}
		
		public void stop(){
			this.working = false;
		}
		
		@Override
		public void run() {
			LOG.info("Started File Reader worker: {}", Thread.currentThread().getName());
			while(working){
				for(Entry<BufferedReader, Integer> e: channels.entrySet()){
					int id = e.getValue();
					BufferedReader br = e.getKey();
					InputAdapter ia = dataAdapters.get(id);
					byte[] data = null;
					String read = null;
					try {
						read = br.readLine();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(read != null){
						data = read.getBytes(); 
						ia.pushData(data);
					}
					/*
					if(rbc.isOpen()){
						ia.readFrom(rbc, id);
					}
					else{
						working = false;
					}*/
				}
			}
			LOG.info("Finished File Reader worker: {}", Thread.currentThread().getName());
			this.closeReader();
		}
		
		private void closeReader(){
			for(BufferedReader sbc : channels.keySet()){
				try {
					sbc.close();
				} 
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	class Writer implements Runnable {

		public void stop(){
			// TODO: implement
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
	}
}
