package tutorial.scenario;

public abstract class Asset {
	
	private final String id;
	private final String name;
	private final Node node;

	protected Asset(String id, String name, Node node) {
		this.id = id;
		this.name = name;
		this.node = node;
	}

	public String getName() {
		return name;
	}

	public Node getNode() {
		return node;
	}

	public String getId() {
		return id;
	}
}