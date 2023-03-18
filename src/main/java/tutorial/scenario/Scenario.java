package tutorial.scenario;

import java.time.LocalDateTime;
import java.util.List;

public class Scenario {

  private final double truckSpeed;
  private final double intervalBetweenRequestsHrs;
  private final double maxDeliveryTimeHrs;
  private final LocalDateTime simulationStartDt;
  private final LocalDateTime simulationEndDt;
  private final List<Node> nodes;
  private final List<Arc> arcs;
  private final List<Warehouse> warehouses;
  private final List<Store> stores;
  private final List<Truck> trucks;

  public Scenario(List<Truck> trucks,
      double truckSpeed,
      double intervalBetweenRequestsHrs,
      double maxDeliveryTimeHrs,
      List<Node> nodes, List<Arc> arcs,
      List<Warehouse> warehouses, List<Store> stores,
      LocalDateTime simulationStartDt, LocalDateTime simulationEndDt) {
    this.trucks = trucks;
    this.truckSpeed = truckSpeed;
    this.intervalBetweenRequestsHrs = intervalBetweenRequestsHrs;
    this.maxDeliveryTimeHrs = maxDeliveryTimeHrs;
    this.simulationStartDt = simulationStartDt;
    this.simulationEndDt = simulationEndDt;
    this.nodes = nodes;
    this.arcs = arcs;
    this.warehouses = warehouses;
    this.stores = stores;
  }

  public double getTruckSpeed() {
    return truckSpeed;
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

  public List<Truck> getTrucks() {
    return trucks;
  }

  public List<Node> getNodes() {
    return nodes;
  }

  public List<Arc> getArcs() {
    return arcs;
  }

  public List<Warehouse> getWarehouses() {
    return warehouses;
  }

  public List<Store> getStores() {
    return stores;
  }
}