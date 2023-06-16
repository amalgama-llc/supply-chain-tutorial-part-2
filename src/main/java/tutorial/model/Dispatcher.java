package tutorial.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Consumer;

public class Dispatcher {
	private final Model model;
	private int lastTaskId = 0;
	private List<TransportationTask> transportationTasks = new ArrayList<>();
	private Queue<TransportationRequest> waitingRequests = new LinkedList<>();
	private List<Consumer<TransportationTask>> taskCompletedHandlers = new ArrayList<>();

	public Dispatcher(Model model) {
		this.model = model;
	}

	public List<TransportationTask> getTransportationTasks() {
		return transportationTasks;
	}
	
	public void addTaskCompletedHandler(Consumer<TransportationTask> handler) {
		taskCompletedHandlers.add(handler);
	}

	public void onNewRequest(TransportationRequest newRequest) {
		Optional<Truck> freeTruck = model.getTrucks().stream().filter(Truck::isIdle).findFirst();
		if (freeTruck.isPresent()) {
			startTransportation(freeTruck.get(), newRequest);
		} else {
			addWaitingRequest(newRequest);
		}
	}

	private void startTransportation(Truck truck, TransportationRequest request) {
		TransportationTask task = new TransportationTask(String.valueOf(++lastTaskId), truck, request, this::onTaskCompleted, model);
		transportationTasks.add(task);
		task.execute();
	}

	private void onTaskCompleted(TransportationTask task) {
		TransportationRequest waitingRequest = getNextWaitingRequest();
		if (waitingRequest != null) {
			startTransportation(task.getTruck(), waitingRequest);
		}
		taskCompletedHandlers.forEach(handler -> handler.accept(task));
	}
	
	private TransportationRequest getNextWaitingRequest() {
		return waitingRequests.poll();
	}
	
	private void addWaitingRequest(TransportationRequest request) {
		waitingRequests.add(request);
	}
}
