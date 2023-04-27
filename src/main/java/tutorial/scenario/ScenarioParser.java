package tutorial.scenario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONObject;

public class ScenarioParser {

	private static final String ID = "id";
	private static final String COORDINATES_Y = "y";
	private static final String COORDINATES_X = "x";
	private static final String NAME = "name";

	public Scenario parseScenario(JSONObject jsonScenario) {

		LocalDateTime beginDate = parseLocalDateTime(jsonScenario, "beginDate");
		LocalDateTime endDate = parseLocalDateTime(jsonScenario, "endDate");

		double intervalBetweenRequestHrs = parseDouble(jsonScenario, "intervalBetweenRequestHrs");
		double maxDeliveryTimeHrs = parseDouble(jsonScenario, "maxDeliveryTimeHrs");

		Map<String, Node> nodes = new HashMap<>();

		List<Truck> trucks = new ArrayList<>();
		List<Arc> arcs = new ArrayList<>();
		List<Warehouse> warehouses = new ArrayList<>();
		List<Store> stores = new ArrayList<>();

		jsonScenario.getJSONArray("nodes").forEach(elem -> {
			JSONObject json = (JSONObject) elem;
			String id = parseString(json, ID);
			nodes.put(id, new Node(id, parseDouble(json, COORDINATES_Y), parseDouble(json, COORDINATES_X)));
		});

		jsonScenario.getJSONArray("arcs").forEach(elem -> {
			JSONObject json = (JSONObject) elem;
			Node source = parseNode(nodes, json, "source");
			Node dest = parseNode(nodes, json, "dest");
			if (source.equals(dest)) {
				String message = "Arc source and destination should be different Nodes.\n";
				System.err.println(message);
				throw new RuntimeException(message);
			}
			arcs.add(new Arc(source, dest));
		});

		jsonScenario.getJSONArray("trucks").forEach(elem -> {
			JSONObject json = (JSONObject) elem;
			trucks.add(new Truck(parseString(json, ID), parseString(json, NAME), parseDouble(json, "speed"), parseNode(nodes, json, "initialNode")));
		});

		jsonScenario.getJSONArray("warehouses").forEach(elem -> {
			JSONObject json = (JSONObject) elem;
			warehouses.add(new Warehouse(parseString(json, ID), parseString(json, NAME), parseNode(nodes, json, "node")));
		});

		jsonScenario.getJSONArray("stores").forEach(elem -> {
			JSONObject json = (JSONObject) elem;
			stores.add(new Store(parseString(json, ID), parseString(json, NAME), parseNode(nodes, json, "node")));
		});

		return new Scenario(trucks, intervalBetweenRequestHrs, maxDeliveryTimeHrs,
				nodes.values().stream().toList(), arcs, warehouses, stores, beginDate, endDate);
	}

	private static LocalDateTime parseLocalDateTime(JSONObject jsonScenario, String str) {
		return LocalDateTime.parse(jsonScenario.getString(str));
	}

	private static Node parseNode(Map<String, Node> nodes, JSONObject object, String nodeFieldName) {
		String nodeFieldValue = object.getString(nodeFieldName);
		Node node = nodes.get(nodeFieldValue);
		if (Objects.nonNull(node)) {
			return node;
		} else {
			String message = "Node with id '" + nodeFieldValue + "' does not exist.\n";
			System.err.println(message);
			throw new RuntimeException(message);
		}
	}

	private String parseString(JSONObject object, String str) {
		return object.getString(str);
	}

	private double parseDouble(JSONObject object, String str) {
		return object.getDouble(str);
	}
}
