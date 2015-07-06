package uk.ac.imperial.lsds.seepworker.core;

import static com.codahale.metrics.MetricRegistry.name;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.imperial.lsds.seep.api.API;
import uk.ac.imperial.lsds.seep.api.SeepTask;
import uk.ac.imperial.lsds.seep.api.data.DataItem;
import uk.ac.imperial.lsds.seep.api.data.ITuple;
import uk.ac.imperial.lsds.seep.api.data.Schema;
import uk.ac.imperial.lsds.seep.api.data.Type;
import uk.ac.imperial.lsds.seep.api.data.Schema.SchemaBuilder;
import uk.ac.imperial.lsds.seep.api.state.SeepState;
import uk.ac.imperial.lsds.seep.core.InputAdapter;
import uk.ac.imperial.lsds.seep.core.OutputAdapter;
import uk.ac.imperial.lsds.seep.metrics.SeepMetrics;
import uk.ac.imperial.lsds.seepworker.WorkerConfig;
import uk.ac.imperial.lsds.seepworker.core.input.CoreInput;
import uk.ac.imperial.lsds.seepworker.core.input.InputAdapterReturnType;
import uk.ac.imperial.lsds.seepworker.core.output.CoreOutput;

import com.codahale.metrics.Meter;

public class SingleThreadProcessingEngine implements ProcessingEngine {

	final private Logger LOG = LoggerFactory.getLogger(SingleThreadProcessingEngine.class.getName());
	final private int MAX_BLOCKING_TIME_PER_INPUTADAPTER_MS;
	
	private boolean working = false;
	private Thread worker;
	
	private int id;
	private CoreInput coreInput;
	private CoreOutput coreOutput;
	
	private SeepTask task;
	private SeepState state;
	
	// Metrics
	final private Meter m;
	
	private int count=0;
	private double start = 0;
	private boolean set = false;
	public SingleThreadProcessingEngine(WorkerConfig wc) {
		this.MAX_BLOCKING_TIME_PER_INPUTADAPTER_MS = wc.getInt(WorkerConfig.MAX_WAIT_TIME_PER_INPUTADAPTER_MS);
		this.worker = new Thread(new Worker());
		this.worker.setName(this.getClass().getSimpleName());
		m = SeepMetrics.REG.meter(name(SingleThreadProcessingEngine.class, "event", "per", "sec"));
	}
	
	@Override
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public void setCoreInput(CoreInput coreInput) {
		this.coreInput = coreInput;
	}

	@Override
	public void setCoreOutput(CoreOutput coreOutput) {
		this.coreOutput = coreOutput;
	}
	
	@Override
	public void setTask(SeepTask task) {
		this.task = task;
	}
	
	@Override
	public void setSeepState(SeepState state) {
		this.state = state;
	}

	@Override
	public void start() {
		working = true;
		this.worker.start();
	}

	@Override
	public void stop() {
		if(task != null) task.close();	// to avoid nullpointer when Ctrl^c after stopping query
		working = false;
		this.closeAndCleanEngine();
	}
	
	private void closeAndCleanEngine(){
		try {
			LOG.debug("Waiting for worker thread to die...");
			worker.join();
			LOG.debug("Waiting for worker thread to die...OK");
		} 
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		coreInput = null;
		coreOutput = null;
		task = null;
		state = null;
	}
	
	private class Worker implements Runnable{

		@Override
		public void run() {
			List<InputAdapter> inputAdapters = coreInput.getInputAdapters();
			LOG.info("Configuring SINGLETHREAD processing engine with {} inputAdapters", inputAdapters.size());
			Iterator<InputAdapter> it = inputAdapters.iterator();
			short one = InputAdapterReturnType.ONE.ofType();
			short many = InputAdapterReturnType.MANY.ofType();
			List<OutputAdapter> outputAdapters = coreOutput.getOutputAdapters();
			LOG.info("Configuring SINGLETHREAD processing engine with {} outputAdapters", outputAdapters.size());
			API api = new Collector(id, outputAdapters);
			int pointer = 0;
			int ia_len = inputAdapters.size();
			int count = 0;
			while(working) {
				while(pointer<ia_len){
					InputAdapter ia = inputAdapters.get(pointer);
					pointer++;
					DataItem di = ia.pullDataItem(MAX_BLOCKING_TIME_PER_INPUTADAPTER_MS);
					if(di != null){
						ITuple d = di.consume();
						task.processData(d, api);									
					}
					if(!(pointer<ia_len) && working){
						pointer = 0;
					}
				}
				// If there are no input adapters, assume processData contain all necessary and give null input data
				if(working){
					LOG.info("About to call processData without data. Am I a source?");
					task.processData(null, api);
				}
			}
			this.closeEngine();
		}
		
		private void closeEngine(){
			LOG.info("Stopping main engine thread");
		}
		
	}
}
