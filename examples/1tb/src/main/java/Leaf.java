import uk.ac.imperial.lsds.seep.api.API;
import uk.ac.imperial.lsds.seep.api.SeepTask;
import uk.ac.imperial.lsds.seep.api.data.ITuple;
import uk.ac.imperial.lsds.seep.api.data.OTuple;
import uk.ac.imperial.lsds.seep.api.data.Schema;
import uk.ac.imperial.lsds.seep.api.data.Type;
import uk.ac.imperial.lsds.seep.api.data.Schema.SchemaBuilder;

public class Leaf implements SeepTask {

	private Schema schema = SchemaBuilder.getInstance().newField(Type.INT, "id").newField(Type.STRING, "user").build();
	int count = 0;
	boolean set = false;
	double start = 0;
	@Override
	public void processData(ITuple data, API api) {	
		if(!set){
			set = true;
			start = System.currentTimeMillis();
		}
		//String line = new String(data.getData());
		//byte[] d = OTuple.create(schema, new String[]{"id", "user"}, new Object[]{1, "1010101010"});
		count++;
		if(count % 500000 == 0){
			double totalsize = count*16;
			double end = System.currentTimeMillis();
			double time = (end-start)/1000;
			System.out.println((totalsize/time)/1000000+"MB/s");
		}
		//api.send(d);	
		//waitHere(10);
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