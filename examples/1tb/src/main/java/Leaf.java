import uk.ac.imperial.lsds.seep.api.API;
import uk.ac.imperial.lsds.seep.api.SeepTask;
import uk.ac.imperial.lsds.seep.api.data.ITuple;
import uk.ac.imperial.lsds.seep.api.data.OTuple;
import uk.ac.imperial.lsds.seep.api.data.Schema;
import uk.ac.imperial.lsds.seep.api.data.Type;
import uk.ac.imperial.lsds.seep.api.data.Schema.SchemaBuilder;


public class Leaf implements SeepTask {

	private Schema schema = SchemaBuilder.getInstance().newField(Type.INT, "id").newField(Type.STRING, "user").build();

	@Override
	public void processData(ITuple data, API api) {
		String line = new String(data.getData());
		//byte[] d = OTuple.create(schema, new String[]{"param1", "param2"}, new Object[]{param1, param2});
		System.out.println("Data received: "+line);
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