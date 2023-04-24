package tutorial.scenario;

import java.time.LocalDateTime;
import java.util.List;

public class Scenario {
	private final double intervalBetweenRequestsHrs;
	private final double maxDeliveryTimeHrs;
	private final List<Warehouse> warehouses;
	private final List<Store> stores;
	private final LocalDateTime simulationStartDt;
	private final LocalDateTime simulationEndDt; 
	private final List<Truck> trucks;
	private final List<Node> nodes;
	private final List<Arc> arcs;
	
	public Scenario(List<Truck> trucks, 
					double intervalBetweenRequestsHrs,
					double maxDeliveryTimeHrs,
					List<Node> nodes, 
					List<Arc> arcs,
					List<Warehouse> warehouses, 
					List<Store> stores,
					LocalDateTime simulationStartDt, LocalDateTime simulationEndDt) {
		this.trucks = trucks;
		this.intervalBetweenRequestsHrs = intervalBetweenRequestsHrs;
		this.maxDeliveryTimeHrs = maxDeliveryTimeHrs;
		this.simulationStartDt = simulationStartDt;
		this.simulationEndDt = simulationEndDt;
		this.nodes = nodes;
		this.arcs = arcs;
		this.warehouses = warehouses;
		this.stores = stores;
	}
	
	public double getIntervalBetweenRequestsHrs() {
		return intervalBetweenRequestsHrs;
	}
	
	public double getMaxDeliveryTimeHrs() {
		return maxDeliveryTimeHrs;
	}
	
	public LocalDateTime getSimulationStartDt() {
		return simulationStartDt;
	}

	public LocalDateTime getSimulationEndDt() {
		return simulationEndDt;
	}

	public List<Warehouse> getWarehouses() {
		return warehouses;
	}
	
	public List<Store> getStores() {
		return stores;
	}
	
	public List<Truck> getTrucks() {
		return trucks;
	}
	
	public List<Node> getNodes() {
		return nodes;
	}
	
	public List<Arc> getArcs() {
		return arcs;
	}

}
