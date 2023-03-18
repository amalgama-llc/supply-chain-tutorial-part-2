package tutorial.scenario;

public class Point {

  private final double y;
  private final double x;

  public Point(double y, double x) {
    this.y = y;
    this.x = x;
  }

  public double getLatitude() {
    return y;
  }

  public double getLongitude() {
    return x;
  }
}