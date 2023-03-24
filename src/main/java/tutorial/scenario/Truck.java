package tutorial.scenario;

public class Truck {

  private int id;
  private String name;
  private double speed;
  private Node initialNode;

  public Truck(int id, String name, double speed, Node initialNode) {
    this.id = id;
    this.name = name;
    this.speed = speed;
    this.initialNode = initialNode;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public double getSpeed() {
    return speed;
  }

  public Node getInitialNode() {
    return initialNode;
  }

}