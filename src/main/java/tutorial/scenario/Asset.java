package tutorial.scenario;

public abstract class Asset {

  private final int id;
  private final String name;
  private final Node node;

  protected Asset(int id, String name, Node node) {
    this.id = id;
    this.name = name;
    this.node = node;
  }

  public String getName() {
    return name;
  }

  public Node getNode() {
    return node;
  }

  public int getId() {
    return id;
  }
}