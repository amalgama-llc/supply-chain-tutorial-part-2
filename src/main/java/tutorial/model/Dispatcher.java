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
	private Queue<TransportationTask> waitingTasks = new LinkedList<>();
	private List<Consumer<TransportationTask>> taskStateChangeHandlers = new ArrayList<>();

	public Dispatcher(Model model) {
		this.model = model;
	}

	public List<TransportationTask> getTransportationTasks() {
		return transportationTasks;
	}
	
	public void addTaskStateChangeHandler(Consumer<TransportationTask> handler) {
		taskStateChangeHandlers.add(handler);
	}
	
	public void clearTaskStateChangeHandlers() {
		taskStateChangeHandlers.clear();
	}
	
	public void onNewRequest(TransportationRequest newRequest) {
		TransportationTask task = new TransportationTask(String.valueOf(++lastTaskId), newRequest, this::onTaskCompleted, model);
		transportationTasks.add(task);
		onTaskStateChanged(task);
		Optional<Truck> freeTruck = model.getTrucks().stream().filter(Truck::isIdle).findFirst();
		if (freeTruck.isPresent()) {
			task.execute(freeTruck.get());
			onTaskStateChanged(task);
		} else {
			addWaitingTask(task);
		}
	}

	private void onTaskCompleted(TransportationTask task) {
		onTaskStateChanged(task);
		TransportationTask waitingTask = getNextWaitingTask();
		if (waitingTask != null) {
			waitingTask.execute(task.getTruck());
			onTaskStateChanged(waitingTask);
		}
	}
	
	private TransportationTask getNextWaitingTask() {
		return waitingTasks.poll();
	}
	
	private void addWaitingTask(TransportationTask task) {
		waitingTasks.add(task);
	}
	
	private void onTaskStateChanged(TransportationTask task) {
		taskStateChangeHandlers.forEach(handler -> handler.accept(task));
	}
}
