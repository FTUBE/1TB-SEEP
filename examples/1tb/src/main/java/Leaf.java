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
	long start = 0;
	int cur;
	int cur_sum=0;
	//byte[] eternald = new byte[]{0, 0, 0, 42, 0, 0, 0, 6, 116, 111, 98, 105, 107, 111};
	public void processData(ITuple data, API api) {	
		if(!set){
			set = true;
			start = System.currentTimeMillis();
			String line = data.getRaw();
			int i = line.indexOf(',');
			cur = Integer.valueOf(line.substring(0,i));
			int next = line.indexOf(',', i+1);
			cur_sum+=Integer.valueOf(line.substring(i+1,next));
			return;
		}
		String line = data.getRaw();
		int i = line.indexOf(',');
		int now = Integer.valueOf(line.substring(0,i));
		if(now!=cur){
		byte[] d = OTuple.create(schema, new String[]{"id","score"}, new Object[]{cur,cur_sum});
		//System.out.println("Send "+cur+" "+cur_sum);
		api.send(d);
		long end = System.currentTimeMillis();
		double time = (end-start)/1000;
		System.out.println(time);
		int next = line.indexOf(',', i+1);
		cur_sum=Integer.valueOf(line.substring(i+1,next));
		cur=now;
		}
		else{
			int next = line.indexOf(',', i+1);
			cur_sum+=Integer.valueOf(line.substring(i+1,next));
		}
		count++;
		/*if(count % 500000 == 0){
			long totalsize = count*line.getBytes().length;
			long end = System.currentTimeMillis();
			float time = (end-start)/1000;
			System.out.println((totalsize/time)/1000000);
			//System.out.println(d.length);
		}*/
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