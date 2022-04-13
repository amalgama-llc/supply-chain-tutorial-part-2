package tutorial.model;

import java.util.function.Consumer;
import com.amalgamasimulation.engine.Engine;
import com.amalgamasimulation.graphagent.GraphAgentPosition;

public class TransportationTask {
    private final String id;
    private final Truck truck;
    private final TransportationRequest request;
    private final Consumer<Truck> truckReleaseHandler;
    private final Engine engine;
    private double beginTime;

    public TransportationTask(String id, Truck truck, TransportationRequest request,
            Consumer<Truck> truckReleaseHandler, Engine engine) {
        this.id = id;
        this.truck = truck;
        this.request = request;
        this.truckReleaseHandler = truckReleaseHandler;
        this.engine = engine;
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

    public void execute() {
        this.beginTime = engine.time();
        truck.onTaskStarted(this, this::onDestinationReached);
        truck.moveTo(request.getSourceAsset().getNode(), truck.getSpeed());
    }
    
    private void onDestinationReached(Truck truck, GraphAgentPosition<Node, Arc> destPosition) {
        boolean truckIsAtSourceNode = destPosition.getNode().getValue().equals(request.getSourceAsset().getNode());
        if (truckIsAtSourceNode) {
            truck.moveTo(request.getDestAsset().getNode(), truck.getSpeed());
        } else {
            truck.onTaskCompleted();
            request.setCompletedTime(engine.time());
            truckReleaseHandler.accept(truck);
        }
    }
}