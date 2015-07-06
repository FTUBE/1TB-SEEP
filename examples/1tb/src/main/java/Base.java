import java.util.Properties;

import uk.ac.imperial.lsds.seep.api.FileConfig;
import uk.ac.imperial.lsds.seep.api.FileSource;
import uk.ac.imperial.lsds.seep.api.LogicalOperator;
import uk.ac.imperial.lsds.seep.api.LogicalSeepQuery;
import uk.ac.imperial.lsds.seep.api.QueryComposer;
import uk.ac.imperial.lsds.seep.api.data.Schema;
import uk.ac.imperial.lsds.seep.api.data.Schema.SchemaBuilder;
import uk.ac.imperial.lsds.seep.api.data.Type;


public class Base implements QueryComposer {

	@Override
	public LogicalSeepQuery compose() {
		
		Schema schema = SchemaBuilder.getInstance().newField(Type.INT, "id").newField(Type.INT,"score").build();
		
		Properties p1 = new Properties();
		p1.setProperty(FileConfig.FILE_PATH, "/Users/tianyang/EFile/t1.csv");
		
		Properties p2 = new Properties();
		p2.setProperty(FileConfig.FILE_PATH, "/Users/tianyang/EFile/t2.csv");
		
		FileSource src1 = FileSource.newSource(0, p1);
		LogicalOperator leaf1 = queryAPI.newStatelessOperator(new Leaf(), 1);
		FileSource src2 = FileSource.newSource(0, p2);
		LogicalOperator leaf2 = queryAPI.newStatelessOperator(new Leaf(), 2);
		
		LogicalOperator middle = queryAPI.newStatelessOperator(new Middle(), 3);
		
		
		LogicalOperator snk = queryAPI.newStatelessSink(new Sink(), 4);
		
		src1.connectTo(leaf1, 0, schema);
		leaf1.connectTo(middle, 1, schema);
		src2.connectTo(leaf2, 2, schema);
		leaf2.connectTo(middle, 3, schema);
		
		middle.connectTo(snk, 2, schema);
		
		return queryAPI.build();
	}

}