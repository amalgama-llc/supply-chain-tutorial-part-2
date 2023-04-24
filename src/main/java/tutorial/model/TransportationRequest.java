package tutorial.model;

public class TransportationRequest {
	private final int id;
	private final Warehouse sourceAsset;
	private final Store destAsset;
	private final double createdTime;
	private final double deadlineTime;
	private boolean completed;
	private double completedTime;
	
	public TransportationRequest(int id, Warehouse sourceAsset, Store destAsset, double beginTime, double deadlineTime) {
		this.id = id;
		this.sourceAsset = sourceAsset;
		this.destAsset = destAsset;
		this.createdTime = beginTime;
		this.deadlineTime = deadlineTime;
		//System.out.println(String.format("%.3f\tRequest #%s created", createdTime, id));
	}
	
	public int getId() {
		return id;
	}
	
	public Asset getSourceAsset() {
		return sourceAsset;
	}
	
	public Asset getDestAsset() {
		return destAsset;
	}

	public double getCreatedTime() {
		return createdTime;
	}

	public double getDeadlineTime() {
		return deadlineTime;
	}

	public double getCompletedTime() {
		return completedTime;
	}

	public boolean isCompleted() {
		return completed;
	}
	
	public void setCompletedTime(double completedTime) {
		this.completedTime = completedTime;
		this.completed = true;
	}
}
