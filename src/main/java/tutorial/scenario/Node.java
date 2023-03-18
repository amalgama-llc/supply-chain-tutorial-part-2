package tutorial.scenario;

public class Node {

  private final double y;
  private final double x;
  private final int id;

  public Node(int id, double y, double x) {
    this.y = y;
    this.x = x;
    this.id = id;
  }

  public double getY() {
    return y;
  }

  public double getX() {
    return x;
  }

  public int getId() {
    return id;
  }
}