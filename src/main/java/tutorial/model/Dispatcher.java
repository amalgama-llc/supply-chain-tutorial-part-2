package tutorial.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Dispatcher {
	private final Model model;
	private int lastTaskId = 0;
	private List<TransportationTask> transportationTasks = new ArrayList<>();

	public Dispatcher(Model model) {
		this.model = model;
	}

	public List<TransportationTask> getTransportationTasks() {
		return transportationTasks;
	}

	public void onNewRequest(TransportationRequest newRequest) {
		Optional<Truck> freeTruck = model.getTrucks().stream().filter(Truck::isIdle).findFirst();
		if (freeTruck.isPresent()) {
			startTransportation(freeTruck.get(), newRequest);
		} else {
			model.addWaitingRequest(newRequest);
		}
	}

	private void startTransportation(Truck truck, TransportationRequest request) {
		TransportationTask task = new TransportationTask(String.valueOf(++lastTaskId), truck, request, this::onTruckRelease, model);
		transportationTasks.add(task);
		task.execute();
	}

	private void onTruckRelease(Truck truck) {
		TransportationRequest waitingRequest = model.getNextWaitingRequest();
		if (waitingRequest != null) {
			startTransportation(truck, waitingRequest);
		}
	}
}
