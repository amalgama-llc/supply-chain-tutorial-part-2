package tutorial.model;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
	private Mapping mapping = new Mapping();
	
	private List<Warehouse> warehouses = new ArrayList<>();
	private List<Store> stores = new ArrayList<>();
	private List<Truck> trucks = new ArrayList<>();
	private List<TransportationRequest> requests = new ArrayList<>();
	private Queue<TransportationRequest> waitingRequests = new LinkedList<>();
	private final Dispatcher dispatcher;
	private final Statistics statistics;

	private GraphEnvironment<Node, Arc, Truck> graphEnvironment = new GraphEnvironment<>();
	private List<Arc> arcs = new ArrayList<>();
	
	public Model(Engine engine, Scenario scenario) {
		super(engine);
		engine.setTemporal(scenario.getSimulationStartDt(), ChronoUnit.HOURS);
		engine.scheduleStop(engine.dateToTime(scenario.getSimulationEndDt()), "Stop");
		this.scenario = scenario;

		initializeModelObjects();
		
		RealDistribution newRequestIntervalDistribution = new ExponentialDistribution(randomGenerator, scenario.getIntervalBetweenRequestsHrs());
		RequestGenerator requestGenerator = new RequestGenerator(this, newRequestIntervalDistribution, scenario.getMaxDeliveryTimeHrs());
		requestGenerator.addNewRequestHandler(this::addRequest);

		dispatcher = new Dispatcher(this);
		requestGenerator.addNewRequestHandler(dispatcher::onNewRequest);
		statistics = new Statistics(this);
	}
	
	private void initializeModelObjects() {
		initializeGraph();
		initializeAssets();
		initializeTrucks();
	}

	private void initializeTrucks() {
		for (tutorial.scenario.Truck scenarioTruck : scenario.getTrucks()) {
			Truck truck = new Truck(scenarioTruck.getId(), scenarioTruck.getName(), scenarioTruck.getSpeed(), engine());
			truck.setGraphEnvironment(graphEnvironment);
			Node homeNode = mapping.nodesMap.get(scenarioTruck.getInitialNode());
			truck.jumpTo(homeNode);
			trucks.add(truck);
		}
	}

	private void initializeGraph() {
		for (int i = 0; i < scenario.getNodes().size(); i++) {
			var scenarioNode = scenario.getNodes().get(i);
	        Node node = new Node(scenarioNode.getId(), new Point(scenarioNode.getX(), scenarioNode.getY()));
	        graphEnvironment.addNode(node);
	        mapping.nodesMap.put(scenarioNode, node);
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
			var wh = new Warehouse(mapping.nodesMap.get(scenarioWarehouse.getNode()), scenarioWarehouse.getName());
			warehouses.add(wh);
			mapping.assetsMap.put(scenarioWarehouse, wh);
		}
		for (var scenarioStore : scenario.getStores()) {
			var store = new Store(mapping.nodesMap.get(scenarioStore.getNode()), scenarioStore.getName());
			stores.add(store);
			mapping.assetsMap.put(scenarioStore, store);
		}
	}
	
	public RandomGenerator getRandomGenerator() {
		return randomGenerator;
	}
	
	public List<Truck> getTrucks() {
		return trucks;
	}
	
	public Statistics getStatistics() {
		return statistics;
	}

	public List<Warehouse> getWarehouses() {
		return warehouses;
	}
	
	public List<Store> getStores() {
		return stores;
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
}
