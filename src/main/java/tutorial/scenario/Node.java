package tutorial.scenario;

public class Node {
  private final double latitude;
  private final double longitude;

  public Node(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }
}