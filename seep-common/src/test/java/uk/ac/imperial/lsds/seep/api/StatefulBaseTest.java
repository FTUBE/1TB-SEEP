package uk.ac.imperial.lsds.seep.api;

import uk.ac.imperial.lsds.seep.api.data.ITuple;
import uk.ac.imperial.lsds.seep.api.data.Schema;
import uk.ac.imperial.lsds.seep.api.data.Type;
import uk.ac.imperial.lsds.seep.api.state.stateimpl.SeepMap;

public class StatefulBaseTest implements QueryComposer {

	@Override
	public LogicalSeepQuery compose() {
		// Declare Source
		LogicalOperator src = queryAPI.newStatelessSource(new Source(), -1);
		// Declare processor
		LogicalOperator p = queryAPI.newStatefulOperator(new Processor(), new SeepMap(), 1);
		// Declare sink
		LogicalOperator snk = queryAPI.newStatelessSink(new Sink(), -2);
		
		Schema srcSchema = queryAPI.schemaBuilder.newField(Type.SHORT, "id").build();
		Schema pSchema = queryAPI.schemaBuilder.newField(Type.SHORT, "id").newField(Type.BYTES, "payload").build();
		
		System.out.println("SRC Schema: ");
		System.out.println(srcSchema.toString());
		System.out.println("Pro Schema: ");
		System.out.println(pSchema.toString());
		
		/** Connect operators **/
		src.connectTo(p, 0, srcSchema);
		p.connectTo(snk, 0, pSchema);
		
		/**
		 * Example of how to create a source with only java2sdg information
		Schema networkSchema = ...; // got from java2sdg
		networkSrc.connectTo(p, 0, networkSchema);
		**/
		
		return QueryBuilder.build();
	}
	
	class Source implements SeepTask {
		@Override
		public void setUp() {
			// TODO Auto-generated method stub	
		}
		@Override
		public void processData(ITuple data, API api) {
			// TODO Auto-generated method stub	
		}
		@Override
		public void processDataGroup(ITuple dataList, API api) {
			// TODO Auto-generated method stub	
		}
		@Override
		public void close() {
			// TODO Auto-generated method stub	
		}
	}
	
	class Processor implements StatefulSeepTask<SeepMap<Integer, String>> {
		
		private SeepMap<Integer, String> map;
		
		@Override
		public void setState(SeepMap<Integer, String> state) {
			this.map = (SeepMap<Integer, String>)state;
		}
		@Override
		public void setUp() {
			// TODO Auto-generated method stub	
		}
		@Override
		public void processData(ITuple data, API api) {
			// TODO Auto-generated method stub
		}
		@Override
		public void processDataGroup(ITuple dataList, API api) {
			// TODO Auto-generated method stub	
		}
		@Override
		public void close() {
			// TODO Auto-generated method stub	
		}
	}
	
	class Sink implements SeepTask {
		@Override
		public void setUp() {
			// TODO Auto-generated method stub	
		}
		@Override
		public void processData(ITuple data, API api) {
			// TODO Auto-generated method stub	
		}
		@Override
		public void processDataGroup(ITuple dataList, API api) {
			// TODO Auto-generated method stub	
		}
		@Override
		public void close() {
			// TODO Auto-generated method stub	
		}
	}
	
}
