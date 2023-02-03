package tutorial.scenario;

import java.util.List;

public class Arc {
  private final Node source;
  private final Node dest;
  private List<Point> points;

  public Arc(Node source, Node dest, List<Point> points) {
    this.source = source;
    this.dest = dest;
    this.points = points;
  }

  public Arc(Node source, Node dest) {
    this(source, dest, List.of());
  }

  public Node getSource() {
    return source;
  }

  public Node getDest() {
    return dest;
  }

  public List<Point> getPoints() {
    return points;
  }
}