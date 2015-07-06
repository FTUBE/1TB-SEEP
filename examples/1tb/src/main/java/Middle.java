import uk.ac.imperial.lsds.seep.api.API;
import uk.ac.imperial.lsds.seep.api.SeepTask;
import uk.ac.imperial.lsds.seep.api.data.ITuple;
import uk.ac.imperial.lsds.seep.api.data.OTuple;
import uk.ac.imperial.lsds.seep.api.data.Schema;
import uk.ac.imperial.lsds.seep.api.data.Type;
import uk.ac.imperial.lsds.seep.api.data.Schema.SchemaBuilder;


public class Middle implements SeepTask {

	private int count = 0;
	private boolean set = false;
	private double start = 0;
	@Override
	public void processData(ITuple data, API api) {
		if(!set){
			start = System.currentTimeMillis();
			set = true;
		}
		count++;
		int id = data.getInt("id");
		int score = data.getInt("score");
		//System.out.println("userid:"+id+"got: "+score);
		//String user = data.getString("user");
		//System.out.println(id+","+user);
		if(count % 500000==0){
			double totalsize = count*data.getData().length;
			double end = System.currentTimeMillis();
			double time = (end-start)/1000;
			System.out.println((totalsize/time)/1000000+"MB/s");
		}
	}
	
	private void waitHere(int time){
		try {
			Thread.sleep(time);
		} 
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void processDataGroup(ITuple dataBatch, API api) {
		// TODO Auto-generated method stub
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
	}
}