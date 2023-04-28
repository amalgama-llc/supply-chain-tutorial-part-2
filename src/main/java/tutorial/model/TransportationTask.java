package tutorial.model;

import java.util.function.Consumer;

import com.amalgamasimulation.graphagent.GraphAgentPosition;

public class TransportationTask {
	private final String id;
	private final Truck truck;
	private final TransportationRequest request;
	private final Consumer<Truck> truckReleaseHandler;
	private final Model model;
	
	private double beginTime;
	private boolean movingWithCargo;

	public TransportationTask(String id, Truck truck, TransportationRequest request,
			Consumer<Truck> truckReleaseHandler, Model model) {
		this.id = id;
		this.truck = truck;
		this.request = request;
		this.truckReleaseHandler = truckReleaseHandler;
		this.model = model;
	}

	public String getId() {
		return id;
	}

	public Truck getTruck() {
		return truck;
	}

	public TransportationRequest getRequest() {
		return request;
	}
	
	public double getBeginTime() {
		return beginTime;
	}

	// tag::execute[]
	public void execute() {
		this.beginTime = model.engine().time();
//		System.out.println("%.3f\tTask #%s : TRANSPORTATION_STARTED. Request #%s; Truck #%s; From %s -> To %s"
//		.formatted(model.engine().time(), getId(), request.getId(), truck.getId(), 
//				request.getSourceAsset().getName(), request.getDestAsset().getName()));
		truck.onTaskStarted(this, this::onDestinationReached);
		truck.moveTo(request.getSourceAsset().getNode(), truck.getSpeed());
	}
	// end::execute[]

	public boolean isMovingWithCargo() {
		return movingWithCargo;
	}
	
	// tag::reached[]
	private void onDestinationReached(Truck truck, GraphAgentPosition<Node, Arc> destPosition) {
		boolean truckIsAtSourceNode = destPosition.getNode().getValue().equals(request.getSourceAsset().getNode());
		if (truckIsAtSourceNode) {
			movingWithCargo = true;
			truck.moveTo(request.getDestAsset().getNode(), truck.getSpeed());
		} else {
			truck.onTaskCompleted();
			request.setCompletedTime(model.engine().time());
			truckReleaseHandler.accept(truck);
//			System.out.println("%.3f\tTask #%s : TRANSPORTATION_FINISHED".formatted(model.engine().time(), getId()));
		}
	}
	// end::reached[]
}
