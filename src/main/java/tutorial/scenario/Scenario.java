package tutorial.scenario;

import java.time.LocalDateTime;
import java.util.List;

public class Scenario {
  private final int truckCount;
  private final double truckSpeed;
  private final double intervalBetweenRequests;
  private final double maxDeliveryTimeHrs;
  private final LocalDateTime simulationStartDt;
  private final LocalDateTime simulationEndDt;
  private final List<Node> nodes;
  private final List<Arc> arcs;
  private final List<Warehouse> warehouses;
  private final List<Store> stores;

  public Scenario(int truckCount,
      double truckSpeed,
      double intervalBetweenRequests,
      double maxDeliveryTimeHrs,
      List<Node> nodes, List<Arc> arcs,
      List<Warehouse> warehouses, List<Store> stores,
      LocalDateTime simulationStartDt, LocalDateTime simulationEndDt) {
    this.truckCount = truckCount;
    this.truckSpeed = truckSpeed;
    this.intervalBetweenRequests = intervalBetweenRequests;
    this.maxDeliveryTimeHrs = maxDeliveryTimeHrs;
    this.simulationStartDt = simulationStartDt;
    this.simulationEndDt = simulationEndDt;
    this.nodes = nodes;
    this.arcs = arcs;
    this.warehouses = warehouses;
    this.stores = stores;
  }

  public int getTruckCount() {
    return truckCount;
  }

  public double getTruckSpeed() {
    return truckSpeed;
  }

  public double getIntervalBetweenRequests() {
    return intervalBetweenRequests;
  }

  public double getMaxDeliveryTimeHrs() {
    return maxDeliveryTimeHrs;
  }

  public double[] getRouteLengthDistribution() {
    return new double[] {20.0, 30.0, 40.0};
  }

  public LocalDateTime getSimulationStartDt() {
    return simulationStartDt;
  }

  public LocalDateTime getSimulationEndDt() {
    return simulationEndDt;
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