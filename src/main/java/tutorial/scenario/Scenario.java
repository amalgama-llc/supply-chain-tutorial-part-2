package tutorial.scenario;

import java.time.LocalDateTime;
import java.util.List;

public class Scenario {
	private final double intervalBetweenRequestsHrs;
	private final double maxDeliveryTimeHrs;
	private final List<Warehouse> warehouses;
	private final List<Store> stores;
	private final LocalDateTime beginDate;
	private final LocalDateTime endDate; 
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
					LocalDateTime beginDate, LocalDateTime endDate) {
		this.trucks = trucks;
		this.intervalBetweenRequestsHrs = intervalBetweenRequestsHrs;
		this.maxDeliveryTimeHrs = maxDeliveryTimeHrs;
		this.beginDate = beginDate;
		this.endDate = endDate;
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
	
	public LocalDateTime getBeginDate() {
		return beginDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
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
