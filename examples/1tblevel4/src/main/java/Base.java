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
		p1.setProperty(FileConfig.FILE_PATH, "/homes/ty1214/t1.csv");
		
		Properties p2 = new Properties();
		p2.setProperty(FileConfig.FILE_PATH, "/homes/ty1214/t2.csv");
		
		Properties p3 = new Properties();
		p3.setProperty(FileConfig.FILE_PATH, "/homes/ty1214/t3.csv");
		
		Properties p4 = new Properties();
		p4.setProperty(FileConfig.FILE_PATH, "/homes/ty1214/t4.csv");
		
		Properties p5 = new Properties();
		p5.setProperty(FileConfig.FILE_PATH, "/homes/ty1214/t5.csv");
		
		Properties p6 = new Properties();
		p6.setProperty(FileConfig.FILE_PATH, "/homes/ty1214/t6.csv");
		
		Properties p7 = new Properties();
		p7.setProperty(FileConfig.FILE_PATH, "/homes/ty1214/t7.csv");
		
		Properties p8 = new Properties();
		p8.setProperty(FileConfig.FILE_PATH, "/homes/ty1214/t8.csv");
		
		FileSource src1 = FileSource.newSource(0, p1);
		LogicalOperator leaf1 = queryAPI.newStatelessOperator(new Leaf(), 1);
		FileSource src2 = FileSource.newSource(0, p2);
		LogicalOperator leaf2 = queryAPI.newStatelessOperator(new Leaf(), 2);
		FileSource src3 = FileSource.newSource(0, p3);
		LogicalOperator leaf3 = queryAPI.newStatelessOperator(new Leaf(), 3);
		FileSource src4 = FileSource.newSource(0, p4);
		LogicalOperator leaf4 = queryAPI.newStatelessOperator(new Leaf(), 4);
		
		FileSource src5 = FileSource.newSource(0, p5);
		LogicalOperator leaf5 = queryAPI.newStatelessOperator(new Leaf(), 5);
		FileSource src6 = FileSource.newSource(0, p6);
		LogicalOperator leaf6 = queryAPI.newStatelessOperator(new Leaf(), 6);
		FileSource src7 = FileSource.newSource(0, p7);
		LogicalOperator leaf7 = queryAPI.newStatelessOperator(new Leaf(), 7);
		FileSource src8 = FileSource.newSource(0, p8);
		LogicalOperator leaf8 = queryAPI.newStatelessOperator(new Leaf(), 8);
		
		LogicalOperator middle1 = queryAPI.newStatelessOperator(new Middle(), 9);
		LogicalOperator middle2 = queryAPI.newStatelessOperator(new Middle(), 10);
		
		LogicalOperator middle3 = queryAPI.newStatelessOperator(new Middle(), 11);
		LogicalOperator middle4 = queryAPI.newStatelessOperator(new Middle(), 12);
		
		LogicalOperator middle5 = queryAPI.newStatelessOperator(new Middle(), 13);
		LogicalOperator middle6 = queryAPI.newStatelessOperator(new Middle(), 14);
		
		LogicalOperator top = queryAPI.newStatelessOperator(new Top(), 15);
		
		
		LogicalOperator snk = queryAPI.newStatelessSink(new Sink(), 16);
		
		src1.connectTo(leaf1, 0, schema);
		leaf1.connectTo(middle1, 1, schema);
		src2.connectTo(leaf2, 2, schema);
		leaf2.connectTo(middle1, 3, schema);	
		src3.connectTo(leaf3, 4, schema);
		leaf3.connectTo(middle2, 5, schema);
		src4.connectTo(leaf4, 6, schema);
		leaf4.connectTo(middle2, 7, schema);
		
		src5.connectTo(leaf5, 8, schema);
		leaf5.connectTo(middle3, 9, schema);
		src6.connectTo(leaf6, 10, schema);
		leaf6.connectTo(middle3, 11, schema);	
		src7.connectTo(leaf7, 12, schema);
		leaf7.connectTo(middle4, 13, schema);
		src8.connectTo(leaf8, 14, schema);
		leaf8.connectTo(middle4, 15, schema);
		
		middle1.connectTo(middle5, 16, schema);
		middle2.connectTo(middle5, 17, schema);
		
		middle3.connectTo(middle6, 18, schema);
		middle4.connectTo(middle6, 19, schema);

		middle5.connectTo(top, 20, schema);
		middle6.connectTo(top, 21, schema);
		
		top.connectTo(snk, 22, schema);
		
		return queryAPI.build();
	}

}