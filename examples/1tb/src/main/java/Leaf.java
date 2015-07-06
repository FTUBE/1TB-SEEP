import uk.ac.imperial.lsds.seep.api.API;
import uk.ac.imperial.lsds.seep.api.SeepTask;
import uk.ac.imperial.lsds.seep.api.data.ITuple;
import uk.ac.imperial.lsds.seep.api.data.OTuple;
import uk.ac.imperial.lsds.seep.api.data.Schema;
import uk.ac.imperial.lsds.seep.api.data.Type;
import uk.ac.imperial.lsds.seep.api.data.Schema.SchemaBuilder;

public class Leaf implements SeepTask {

	private Schema schema = SchemaBuilder.getInstance().newField(Type.INT, "id").newField(Type.INT,"score").build();
	int count = 0;
	boolean set = false;
	double start = 0;
	//byte[] eternald = new byte[]{0, 0, 0, 42, 0, 0, 0, 6, 116, 111, 98, 105, 107, 111};
	public void processData(ITuple data, API api) {	
		if(!set){
			set = true;
			start = System.currentTimeMillis();
		}
		String line = data.getRaw();
		int i = line.indexOf(',');
		int next = line.indexOf(',', i+1);
		//System.out.println(line);
		//String[] parts = line.split(",");
		byte[] d = OTuple.create(schema, new String[]{"id","score"}, new Object[]{Integer.valueOf(line.substring(0,i)),Integer.valueOf(line.substring(i+1,next))});
		count++;
		if(count % 500000 == 0){
			double totalsize = count*d.length;
			double end = System.currentTimeMillis();
			double time = (end-start)/1000;
			System.out.println((totalsize/time)/1000000+"MB/s");
			//System.out.println(d.length);
		}
		api.send(d);
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