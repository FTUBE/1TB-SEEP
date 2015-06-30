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
		
		Schema schema = SchemaBuilder.getInstance().newField(Type.INT, "id").newField(Type.STRING, "user").build();
		
		Properties p = new Properties();
		p.setProperty(FileConfig.FILE_PATH, "/Users/tianyang/EFile/t1.csv");
		
		FileSource src = FileSource.newSource(0, p);
		LogicalOperator leaf1 = queryAPI.newStatelessOperator(new Leaf(), 1);
		LogicalOperator middle = queryAPI.newStatelessOperator(new Middle(), 2);
		LogicalOperator snk = queryAPI.newStatelessSink(new Sink(), 3);
		
		src.connectTo(leaf1, 0, schema);
		leaf1.connectTo(middle, 1, schema);
		middle.connectTo(snk, 2, schema);
		
		return queryAPI.build();
	}

}