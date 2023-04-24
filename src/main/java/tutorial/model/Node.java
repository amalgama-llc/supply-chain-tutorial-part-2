package tutorial.model;

import com.amalgamasimulation.geometry.Point;
import com.amalgamasimulation.graphagent.AgentGraphNodeImpl;

public class Node extends AgentGraphNodeImpl {
	private final String id;
	
	public Node(String id, Point point) {
		super(point);
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
}
