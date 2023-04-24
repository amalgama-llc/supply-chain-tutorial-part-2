package tutorial.model;

import com.amalgamasimulation.utils.Utils;

public class Statistics {
	private final Model model;
	
	public Statistics(Model model) {
		this.model = model;
	}
	
	public double getExpenses() {
		return model.getTrucks().stream().mapToDouble(Truck::getExpenses).sum();
	}
	
	public double getServiceLevel() {
		int fulfilledInTimeRequests = 0;
		int completedOrWillBeBelatedRequests = 0;
		for (TransportationRequest request : model.getRequests()) {
			if (request.isCompleted() && request.getCompletedTime() <= request.getDeadlineTime()) {
				fulfilledInTimeRequests++;
			}
			if (request.isCompleted() || request.getDeadlineTime() <= model.engine().time()) {
				completedOrWillBeBelatedRequests++;
			}
		}
		return Utils.zidz(fulfilledInTimeRequests, completedOrWillBeBelatedRequests);
	}
	
	public double getExpensesPerServiceLevelPercent() {
		return Utils.zidz(getExpenses(), getServiceLevel() * 100);
	}
}
