import uk.ac.imperial.lsds.seep.api.API;
import uk.ac.imperial.lsds.seep.api.SeepTask;
import uk.ac.imperial.lsds.seep.api.data.ITuple;
import uk.ac.imperial.lsds.seep.api.data.OTuple;
import uk.ac.imperial.lsds.seep.api.data.Schema;
import uk.ac.imperial.lsds.seep.api.data.Type;
import uk.ac.imperial.lsds.seep.api.data.Schema.SchemaBuilder;

import java.util.HashMap;
import java.util.Map;


public class Top implements SeepTask {

	private int count = 0;
	private boolean set = false;
	private long start = 0;
	Map<Integer,Integer> id_map = new HashMap<Integer,Integer>();
	private Schema schema = SchemaBuilder.getInstance().newField(Type.INT, "id").newField(Type.INT,"score").build();
	@Override
	public void processData(ITuple data, API api) {
		if(!set){
			start = System.currentTimeMillis();
			set = true;
		}
		count++;
		int id = data.getInt("id");
		int score = data.getInt("score");
		if(id_map.containsKey(id)){
			int total = id_map.remove(id)+score;
			if(id%500==0)
				System.out.println("id:"+id+" total:"+total);
		}
		else{
			id_map.put(id, score);
		}

		//String user = data.getString("user");
		//System.out.println(id+","+user);
		/*if(count % 500000==0){
			long totalsize = count*data.getData().length;
			long end = System.currentTimeMillis();
			float time = (end-start)/1000;
			System.out.println((totalsize/time)/1000000+"MB/s");
		}*/
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