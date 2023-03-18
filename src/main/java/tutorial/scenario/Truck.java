package tutorial.scenario;

public class Truck {

  private int id;
  private String name;
  private Node initialNode;

  public Truck(int id, String name, Node initialNode) {
    this.id = id;
    this.name = name;
    this.initialNode = initialNode;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Node getInitialNode() {
    return initialNode;
  }

}