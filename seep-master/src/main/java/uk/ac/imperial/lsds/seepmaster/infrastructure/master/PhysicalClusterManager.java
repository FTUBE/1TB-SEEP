package uk.ac.imperial.lsds.seepmaster.infrastructure.master;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.imperial.lsds.seep.comm.Connection;
import uk.ac.imperial.lsds.seep.infrastructure.ExecutionUnitType;
import uk.ac.imperial.lsds.seep.util.Utils;

public class PhysicalClusterManager implements InfrastructureManager {
	
	final private Logger LOG = LoggerFactory.getLogger(PhysicalClusterManager.class);
	
	public final ExecutionUnitType executionUnitType = ExecutionUnitType.PHYSICAL_NODE;
	private Deque<ExecutionUnit> physicalNodes;
	private Map<Integer, Connection> connectionsToPhysicalNodes;

	public PhysicalClusterManager(){
		this.physicalNodes = new ArrayDeque<>();
		this.connectionsToPhysicalNodes = new HashMap<>();
	}
	
	@Override
	public ExecutionUnit buildExecutionUnit(InetAddress ip, int port, int dataPort) {
		return new PhysicalNode(ip, port, dataPort);
	}
	
	@Override
	public void addExecutionUnit(ExecutionUnit eu) {
		physicalNodes.push(eu);
		connectionsToPhysicalNodes.put(eu.getId(), new Connection(eu.getEndPoint()));
	}
	public void iterall(){
		for(ExecutionUnit eu: physicalNodes){
			System.out.println(eu.getEndPoint().getIp().getHostAddress()+":"+eu.getEndPoint().getPort());
		}
	}
	@Override
	public ExecutionUnit getExecutionUnit(){
		if(physicalNodes.size() > 0){
			LOG.debug("Returning 1 executionUnit, remaining: {}", physicalNodes.size()-1);
			return physicalNodes.pop();
		}
		else{
			LOG.error("No available executionUnits !!!");
			return null;
		}
	}

	@Override
	public boolean removeExecutionUnit(int id) {
		for(ExecutionUnit eu : physicalNodes){
			if(eu.getId() == id){
				boolean success = physicalNodes.remove(eu);
				if(success){
					LOG.info("ExecutionUnit id: {} was removed");
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int executionUnitsAvailable() {
		return physicalNodes.size();
	}

	@Override
	public void claimExecutionUnits(int numExecutionUnits) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decommisionExecutionUnits(int numExecutionUnits) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decommisionExecutionUnit(ExecutionUnit eu) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Connection> getConnectionsTo(Set<Integer> executionUnitIds) {
		Set<Connection> cs = new HashSet<>();
		for(Integer id : executionUnitIds) {
			// TODO: check that the conn actually exists
			cs.add(connectionsToPhysicalNodes.get(id));
		}
		return cs;
	}

	@Override
	public Connection getConnectionTo(int executionUnitId) {
		return connectionsToPhysicalNodes.get(executionUnitId);
	}

	@Override
	public ExecutionUnit seacrhND(String ip, String port) {
		int id = -1;
		try {
			id = Utils.computeIdFromIpAndPort(InetAddress.getByName(ip), Integer.valueOf(port));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ExecutionUnit eu = null;
		for(ExecutionUnit e : physicalNodes){
			if(e.getId() == id){
				System.out.println("Found!");
				eu = e;
				physicalNodes.remove(e);
			}
		}
		
		return eu;
	}

}
