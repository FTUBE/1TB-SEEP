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
		
		Properties p3 = new Properties();
		p3.setProperty(FileConfig.FILE_PATH, "/Users/tianyang/EFile/t3.csv");
		
		Properties p4 = new Properties();
		p4.setProperty(FileConfig.FILE_PATH, "/Users/tianyang/EFile/t4.csv");
		/*
		Properties p1 = new Properties();
		p1.setProperty(FileConfig.FILE_PATH, "/homes/ty1214/t1.csv");
		
		Properties p2 = new Properties();
		p2.setProperty(FileConfig.FILE_PATH, "/homes/ty1214/t2.csv");
		
		Properties p3 = new Properties();
		p3.setProperty(FileConfig.FILE_PATH, "/homes/ty1214/t3.csv");
		
		Properties p4 = new Properties();
		p4.setProperty(FileConfig.FILE_PATH, "/homes/ty1214/t4.csv");*/
		FileSource src1 = FileSource.newSource(0, p1);
		LogicalOperator leaf1 = queryAPI.newStatelessOperator(new Leaf(), 1);
		FileSource src2 = FileSource.newSource(0, p2);
		LogicalOperator leaf2 = queryAPI.newStatelessOperator(new Leaf(), 2);
		FileSource src3 = FileSource.newSource(0, p3);
		LogicalOperator leaf3 = queryAPI.newStatelessOperator(new Leaf(), 3);
		FileSource src4 = FileSource.newSource(0, p4);
		LogicalOperator leaf4 = queryAPI.newStatelessOperator(new Leaf(), 4);
		
		LogicalOperator middle1 = queryAPI.newStatelessOperator(new Middle(), 5);
		LogicalOperator middle2 = queryAPI.newStatelessOperator(new Middle(), 6);
		
		LogicalOperator top = queryAPI.newStatelessOperator(new Top(), 7);
		
		LogicalOperator snk = queryAPI.newStatelessSink(new Sink(), 8);
		
		src1.connectTo(leaf1, 0, schema);
		leaf1.connectTo(middle1, 1, schema);
		src2.connectTo(leaf2, 2, schema);
		leaf2.connectTo(middle1, 3, schema);
		
		src3.connectTo(leaf3, 4, schema);
		leaf3.connectTo(middle2, 5, schema);
		src4.connectTo(leaf4, 6, schema);
		leaf4.connectTo(middle2, 7, schema);
		
		middle1.connectTo(top, 8, schema);
		middle2.connectTo(top, 9, schema);
		
		top.connectTo(snk, 10, schema);
		
		return queryAPI.build();
	}

}