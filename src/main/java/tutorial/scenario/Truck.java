package tutorial.scenario;

public class Truck {

	private String id;
	private String name;
	private double speed;
	private Node initialNode;

	public Truck(String id, String name, double speed, Node initialNode) {
		this.id = id;
		this.name = name;
		this.speed = speed;
		this.initialNode = initialNode;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public double getSpeed() {
		return speed;
	}

	public Node getInitialNode() {
		return initialNode;
	}

}