package tutorial.model;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.random.RandomGenerator;

import com.amalgamasimulation.engine.Engine;
import com.amalgamasimulation.geometry.Point;
import com.amalgamasimulation.geometry.Polyline;
import com.amalgamasimulation.graphagent.GraphEnvironment;
import com.amalgamasimulation.utils.random.DefaultRandomGenerator;

import tutorial.Mapping;
import tutorial.scenario.Scenario;

public class Model extends com.amalgamasimulation.engine.Model {
    private final Scenario scenario;
    private final RandomGenerator randomGenerator = new DefaultRandomGenerator(1);
    private List<Truck> trucks = new ArrayList<>();
    private List<TransportationRequest> requests = new ArrayList<>();
    private Queue<TransportationRequest> waitingRequests = new LinkedList<>();
    private final Statistics statistics;
    private GraphEnvironment<Node, Arc, Truck> graphEnvironment = new GraphEnvironment<>();
    private Node homeNode;
    private Mapping mapping = new Mapping();
    private List<Warehouse> warehouses = new ArrayList<>();
    private List<Store> stores = new ArrayList<>();

    public Model(Engine engine, Scenario scenario) {
        super(engine);
        engine.setTemporal(scenario.getSimulationStartDt(), ChronoUnit.HOURS);
        engine.scheduleStop(engine.dateToTime(scenario.getSimulationEndDt()), "Stop");
        this.scenario = scenario;
        RealDistribution newRequestIntervalDistribution = new ExponentialDistribution(randomGenerator, scenario.getIntervalBetweenRequests());
        RequestGenerator requestGenerator = new RequestGenerator(engine, this, newRequestIntervalDistribution, scenario.getMaxDeliveryTimeHrs());
        initializeModelObjects();
        requestGenerator.addNewRequestHandler(this::addRequest);
        Dispatcher dispatcher = new Dispatcher(engine, this);
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
        for (int i = 0; i < scenario.getTruckCount(); i++) {
            Truck truck = new Truck(String.valueOf(i + 1), String.valueOf(i + 1), scenario.getTruckSpeed(), engine());
            truck.setGraphEnvironment(graphEnvironment);
            truck.jumpTo(homeNode);
            trucks.add(truck);
        }
    }
    
    public Statistics getStatistics() {
        return statistics;
    }
    
    private void initializeGraph() {
        for (var scenarioNode : scenario.getNodes()) {
            var modelNode = new tutorial.model.Node(new Point(scenarioNode.getLongitude(), scenarioNode.getLatitude()));
            mapping.nodesMap.put(scenarioNode, modelNode);
            graphEnvironment.addNode(modelNode);
            homeNode = modelNode;
        }

        for (var scenarioArc : scenario.getArcs()) {
            Node sourceNode = mapping.nodesMap.get(scenarioArc.getSource());
            Node destNode = mapping.nodesMap.get(scenarioArc.getDest());
            Polyline arcPolyline = createPolyline(scenarioArc);
            Arc forwardArc = new Arc(arcPolyline);
            Arc backwardArc = new Arc(arcPolyline.getReversed());
            forwardArc.setReverseArc(backwardArc);
            backwardArc.setReverseArc(forwardArc);
            graphEnvironment.addArc(sourceNode, destNode, forwardArc, backwardArc);
        }
    }

    private Polyline createPolyline(tutorial.scenario.Arc scenarioArc) {
        List<Point> points = new ArrayList<>();
        points.add(new Point(scenarioArc.getSource().getLongitude(), scenarioArc.getSource().getLatitude()));
        for (tutorial.scenario.Point bendpoint : scenarioArc.getPoints()) {
            points.add(new Point(bendpoint.getLongitude(), bendpoint.getLatitude()));
        }
        points.add(new Point(scenarioArc.getDest().getLongitude(), scenarioArc.getDest().getLatitude()));
        return new Polyline(points.stream().distinct().collect(Collectors.toList()));
    }

    private void initializeAssets() {
        for (var scenarioWarehouse : scenario.getWarehouses()) {
            warehouses.add(new Warehouse(mapping.nodesMap.get(scenarioWarehouse.getNode()), scenarioWarehouse.getName()));
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
}