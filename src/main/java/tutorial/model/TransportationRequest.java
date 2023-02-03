package tutorial.model;

public class TransportationRequest {
  private final int id;
  private final double createdTime;
  private final double deadlineTime;
  private double completedTime;
  private boolean completed;
  private final Asset sourceAsset;
  private final Asset destAsset;

  public TransportationRequest(int id, Asset sourceAsset, Asset destAsset, double beginTime, double deadlineTime) {
    this.id = id;
    this.sourceAsset = sourceAsset;
    this.destAsset = destAsset;
    this.createdTime = beginTime;
    this.deadlineTime = deadlineTime;
  }

  public int getId() {
    return id;
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

  public Asset getSourceAsset() {
    return sourceAsset;
  }

  public Asset getDestAsset() {
    return destAsset;
  }
}