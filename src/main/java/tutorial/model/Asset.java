package tutorial.model;

public abstract class Asset {
  private final Node node;
  private final String name;

  protected Asset(Node node, String name) {
    this.node = node;
    this.name = name;
  }

  public Node getNode() {
    return node;
  }

  public String getName() {
    return name;
  }
}