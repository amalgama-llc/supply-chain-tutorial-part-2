package tutorial.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.math3.distribution.RealDistribution;

import com.amalgamasimulation.engine.Engine;

public class RequestGenerator {
    private final Engine engine;

    private final RealDistribution newRequestIntervalDistribution;
    private final double maxDeliveryTimeHrs;
    private List<Consumer<TransportationRequest>> newRequestHandlers = new ArrayList<>();
    private int lastUsedRequestId = 0;
    private final Model model;

    public RequestGenerator(Engine engine,
            Model model,
            RealDistribution newRequestIntervalDistribution,
            double maxDeliveryTimeHrs) {
		this.engine = engine;
		this.model = model;
		this.maxDeliveryTimeHrs = maxDeliveryTimeHrs;
		
		this.newRequestIntervalDistribution = newRequestIntervalDistribution;
		engine.scheduleRelative(0, this::createTransportationRequest);
	}

    public void addNewRequestHandler(Consumer<TransportationRequest> newRequestHandler) {
        newRequestHandlers.add(newRequestHandler);
    }

    private void createTransportationRequest() {
        var warehouses = model.getWarehouses();
        var sourceAsset = warehouses.get(model.getRandomGenerator().nextInt(warehouses.size()));
        var stores = model.getStores();
        var destAsset = stores.get(model.getRandomGenerator().nextInt(stores.size()));
        TransportationRequest newRequest = new TransportationRequest(getNextRequestId(), sourceAsset, destAsset, engine.time(), engine.time() + maxDeliveryTimeHrs * engine.hour());
        newRequestHandlers.forEach(handler -> handler.accept(newRequest));
        engine.scheduleRelative(newRequestIntervalDistribution.sample(), this::createTransportationRequest);
    }

    private int getNextRequestId() {
        return ++lastUsedRequestId;
    }
}