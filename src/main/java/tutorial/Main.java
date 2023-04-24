package tutorial;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.amalgamasimulation.engine.Engine;
import com.amalgamasimulation.utils.format.Formats;

import tutorial.model.Statistics;
import tutorial.scenario.Scenario;
import tutorial.scenario.Warehouse;
import tutorial.scenario.Store;
import tutorial.scenario.Truck;
import tutorial.scenario.Node;
import tutorial.scenario.Arc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import org.json.JSONObject;
import tutorial.scenario.ScenarioParser;

public class Main {
	
	private static final double TRUCK_SPEED = 40;
	private static final double INTERVAL_BETWEEN_REQUESTS_HRS = 0.5;
	private static final double MAX_DELIVERY_TIME_HRS = 6;
	
	private static List<Warehouse> warehouses;
	private static List<Store> stores;
	private static List<Node> nodes;
	private static List<Arc> arcs;
	private static List<Truck> trucks;
	
	static {
		Node node1 = new Node("node1", 0, 0);
		Node node2 = new Node("node2", 30, 0);
		Node node3 = new Node("node3", 30, 40);
		Node node4 = new Node("node4", 0, 40);
		nodes = List.of(node1, node2, node3, node4);
		Arc arc13 = new Arc(node1, node3);
		Arc arc14 = new Arc(node1, node4);
		Arc arc23 = new Arc(node2, node3);
		Arc arc24 = new Arc(node2, node4);
		arcs = List.of(arc13, arc14, arc23, arc24);
		Warehouse wh1 = new Warehouse("wh1", "Warehouse 1", node1);
		Warehouse wh2 = new Warehouse("wh2", "Warehouse 2", node1);
		Warehouse wh3 = new Warehouse("wh3", "Warehouse 3", node2);
		warehouses = List.of(wh1, wh2, wh3);
		Store st1 = new Store("st1", "Store 1", node3);
		Store st2 = new Store("st2", "Store 2", node3);
		Store st3 = new Store("st3", "Store 3", node4);
		stores = List.of(st1, st2, st3);
		Truck truck1 = new Truck("truck-1", "Truck 1", TRUCK_SPEED, node3);
		trucks = List.of(truck1);
	}
	
	private static final String SCENARIOS_PATH = "scenarios/";

	public static void main(String[] args) {
		runScenariosFromFiles();
	}
	
	private static void runScenarioAnalysis() {
		var trucks = new ArrayList<Truck>();
		for (int numberOfTrucks = 1; numberOfTrucks <= 10; numberOfTrucks++) {
			trucks.add(new Truck("truck-" + numberOfTrucks, "Truck " + numberOfTrucks, TRUCK_SPEED, stores.get(0).getNode()));
			Scenario scenario = new Scenario(	trucks, 
												INTERVAL_BETWEEN_REQUESTS_HRS,
												MAX_DELIVERY_TIME_HRS,
												nodes,
												arcs,
												warehouses,
												stores,
												LocalDateTime.of(2023, 1, 1, 0, 0), 
												LocalDateTime.of(2023, 2, 1, 0, 0));
			runExperimentWithStats(scenario, "scenario");
		}
	}

	private static void createAndRunOneExperimentWithStats() {
		Scenario scenario = new Scenario(	trucks,
											INTERVAL_BETWEEN_REQUESTS_HRS,
											MAX_DELIVERY_TIME_HRS,
											nodes,
											arcs,
											warehouses,
											stores,
											LocalDateTime.of(2023, 1, 1, 0, 0), 
											LocalDateTime.of(2023, 1, 1, 12, 0));
		runExperimentWithStats(scenario, "scenario");
	}

	private static boolean headerPrinted = false;
	private static void runExperimentWithStats(Scenario scenario, String scenarioName) {
		ExperimentRun experiment = new ExperimentRun(scenario, new Engine());
		experiment.run();
		Statistics statistics = experiment.getStatistics();
		if (!headerPrinted) {
			System.out.println("Scenario            \tTrucks count\tSL\tExpenses\tExpenses/SL");
			headerPrinted = true;
		}
		System.out.println("%-20s\t%12s\t%s\t%s\t%s".formatted(scenarioName, scenario.getTrucks().size(),
			Formats.getDefaultFormats().percentTwoDecimals(statistics.getServiceLevel()),
			Formats.getDefaultFormats().dollarTwoDecimals(statistics.getExpenses()),
			Formats.getDefaultFormats().dollarTwoDecimals(statistics.getExpensesPerServiceLevelPercent())));
	}

	private static void runScenariosFromFiles() {
		ScenarioParser parser = new ScenarioParser();
	    File folder = new File(SCENARIOS_PATH);
	    for (File file : folder.listFiles()) {
	        JSONObject jsonScenario = null;
	        try {
	            jsonScenario = new JSONObject(Files.readString(file.toPath()));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        if (Objects.nonNull(jsonScenario)) {
	            try {
	                Scenario scenario = parser.parseScenario(jsonScenario);
	                runExperimentWithStats(scenario, file.getName());
	            } catch (Exception e) {
	                System.err.printf("File '%s' contains error or has incorrect format\n", file.getName());
	                e.printStackTrace();
	            }
	        }
	    }
	}

}