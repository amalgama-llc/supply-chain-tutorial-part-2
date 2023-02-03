package tutorial.scenario;

public abstract class Asset {
  private final String name;
  private final Node node;

  protected Asset(String name, Node node) {
    this.name = name;
    this.node = node;
  }

  public String getName() {
    return name;
  }

  public Node getNode() {
    return node;
  }
}