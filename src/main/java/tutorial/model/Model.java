package tutorial.model;

import tutorial.scenario.Scenario;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.distribution.TriangularDistribution;
import org.apache.commons.math3.random.RandomGenerator;

import com.amalgamasimulation.engine.Engine;
import com.amalgamasimulation.utils.random.DefaultRandomGenerator;
import com.amalgamasimulation.geometry.Point;
import com.amalgamasimulation.geometry.Polyline;
import com.amalgamasimulation.graphagent.GraphEnvironment;
import tutorial.Mapping;
import java.util.stream.Collectors;

public class Model extends com.amalgamasimulation.engine.Model {

  private final Scenario scenario;
  private final RandomGenerator randomGenerator = new DefaultRandomGenerator(1);
  private List<Truck> trucks = new ArrayList<>();
  private List<TransportationRequest> requests = new ArrayList<>();
  private Queue<TransportationRequest> waitingRequests = new LinkedList<>();
  private final Statistics statistics;

  private GraphEnvironment<Node, Arc, Truck> graphEnvironment = new GraphEnvironment<>();
  private Mapping mapping = new Mapping();
  private List<Arc> arcs = new ArrayList<>();
  private List<Warehouse> warehouses = new ArrayList<>();
  private List<Store> stores = new ArrayList<>();
  private final Dispatcher dispatcher;

  public Model(Engine engine, Scenario scenario) {
    super(engine);
    engine.setTemporal(scenario.getSimulationStartDt(), ChronoUnit.HOURS);
    engine.scheduleStop(engine.dateToTime(scenario.getSimulationEndDt()), "Stop");
    this.scenario = scenario;

    RealDistribution newRequestIntervalDistribution = new ExponentialDistribution(randomGenerator,
        scenario.getIntervalBetweenRequestsHrs());
    RequestGenerator requestGenerator = new RequestGenerator(engine, this,
        newRequestIntervalDistribution,
        scenario.getMaxDeliveryTimeHrs());

    initializeModelObjects();

    requestGenerator.addNewRequestHandler(this::addRequest);

    requestGenerator.addNewRequestHandler(this::addRequest);
    this.dispatcher = new Dispatcher(engine, this);
    requestGenerator.addNewRequestHandler(dispatcher::onNewRequest);
    this.statistics = new Statistics(engine, this);
  }

  public RandomGenerator getRandomGenerator() {
    return randomGenerator;
  }

  public List<Truck> getTrucks() {
    return trucks;
  }

  public List<TransportationRequest> getRequests() {
    return requests;
  }

  public void addRequest(TransportationRequest request) {
    requests.add(request);
  }

  public TransportationRequest getNextWaitingRequest() {
    return waitingRequests.poll();
  }

  public void addWaitingRequest(TransportationRequest request) {
    waitingRequests.add(request);
  }

  private void initializeModelObjects() {
    initializeGraph();
    initializeAssets();
    initializeTrucks();
  }

  private void initializeTrucks() {
    for (tutorial.scenario.Truck scenarioTruck : scenario.getTrucks()) {
      Truck truck = new Truck(scenarioTruck.getId(), scenarioTruck.getName(),
          scenarioTruck.getSpeed(), engine());
      truck.setGraphEnvironment(graphEnvironment);
      Node homeNode = mapping.nodesMap.get(scenarioTruck.getInitialNode());
      truck.jumpTo(homeNode);
      trucks.add(truck);
    }
  }

  private void initializeGraph() {
    for (int i = 0; i < scenario.getNodes().size(); i++) {
      Node node = new Node(
          new Point(scenario.getNodes().get(i).getX(), scenario.getNodes().get(i).getY()));
      graphEnvironment.addNode(node);
      mapping.nodesMap.put(scenario.getNodes().get(i), node);
    }
    for (var scenarioArc : scenario.getArcs()) {
      Polyline polyline = createPolyline(scenarioArc);
      if (polyline.getLength() != 0) {
        Node sourceNode = mapping.nodesMap.get(scenarioArc.getSource());
        Node destNode = mapping.nodesMap.get(scenarioArc.getDest());

        Arc forwardArc = new Arc(polyline);
        Arc backwardArc = new Arc(polyline.getReversed());

        forwardArc.setReverseArc(backwardArc);
        backwardArc.setReverseArc(forwardArc);
        graphEnvironment.addArc(sourceNode, destNode, forwardArc, backwardArc);
        mapping.arcsMap.put(scenarioArc, forwardArc);
        this.arcs.add(forwardArc);
        this.arcs.add(backwardArc);
      }
    }
  }

  private Polyline createPolyline(tutorial.scenario.Arc scenarioArc) {
    List<Point> points = new ArrayList<>();
    points.add(new Point(scenarioArc.getSource().getX(), scenarioArc.getSource().getY()));
    for (tutorial.scenario.Point bendpoint : scenarioArc.getPoints()) {
      points.add(new Point(bendpoint.getLongitude(), bendpoint.getLatitude()));
    }
    points.add(new Point(scenarioArc.getDest().getX(), scenarioArc.getDest().getY()));
    return new Polyline(points.stream().distinct().toList());
  }

  private void initializeAssets() {
    for (var scenarioWarehouse : scenario.getWarehouses()) {
      warehouses.add(new Warehouse(mapping.nodesMap.get(scenarioWarehouse.getNode()),
          scenarioWarehouse.getName()));
    }
    for (var scenarioStore : scenario.getStores()) {
      stores.add(new Store(mapping.nodesMap.get(scenarioStore.getNode()), scenarioStore.getName()));
    }
  }

  public List<Warehouse> getWarehouses() {
    return warehouses;
  }

  public List<Store> getStores() {
    return stores;
  }

  public Statistics getStatistics() {
    return statistics;
  }
}