package tutorial.model;

import com.amalgamasimulation.engine.Engine;
import com.amalgamasimulation.utils.Utils;

public class Statistics {
    private final Engine engine;
    private final Model model;

    public Statistics(Engine engine, Model model) {
        this.engine = engine;
        this.model = model;
    }

    public double getExpenses() {
        return model.getTrucks().stream().mapToDouble(Truck::getExpenses).sum();
    }

    public double getServiceLevel() {
        int inTimeCompletedRequests = 0;
        int totalRequests = 0;
        for (TransportationRequest request : model.getRequests()) {
            if (request.isCompleted() && request.getCompletedTime() <= request.getDeadlineTime()) {
                inTimeCompletedRequests++;
            }
            if (request.isCompleted() || request.getDeadlineTime() <= engine.time()) {
                totalRequests++;
            }
        }
        return Utils.zidz(inTimeCompletedRequests, totalRequests);
    }

    public double getExpensesPerServiceLevelPercent() {
        return Utils.zidz(getExpenses(), getServiceLevel() * 100);
    }
}