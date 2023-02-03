package tutorial;

import com.amalgamasimulation.engine.Engine;
import java.time.LocalDateTime;
import tutorial.scenario.Scenario;
import tutorial.model.Statistics;
import com.amalgamasimulation.utils.format.Formats;
import java.util.List;
import tutorial.scenario.Node;
import tutorial.scenario.Arc;
import tutorial.scenario.Store;
import tutorial.scenario.Warehouse;

public class Main {
  public static void main(String[] args) {
    Node nodeNorth = new Node(50, 15);
    Warehouse warehouseNorth = new Warehouse("Warehouse North", nodeNorth);
    Node nodeSouth = new Node(10, 15);
    Warehouse warehouseSouth = new Warehouse("Warehouse South", nodeSouth);
    Node nodeA = new Node(50, 45);
    Store storeA = new Store("Store A", nodeA);
    Node nodeB = new Node(30, 15);
    Store storeB = new Store("Store B", nodeB);
    Node nodeC = new Node(30, 45);
    Store storeC = new Store("Store C", nodeC);
    Node nodeD = new Node(10, 75);
    Store storeD = new Store("Store D", nodeD);
    Node nodeE = new Node(50, 75);
    Store storeE = new Store("Store E", nodeE);
    for (int numberOfTrucks = 11; numberOfTrucks <= 25; numberOfTrucks++) {
      Scenario scenario = new Scenario(numberOfTrucks, 35, 0.2, 12,
          List.of(nodeNorth, nodeSouth, nodeA, nodeB, nodeC, nodeD, nodeE),
          List.of(new Arc(nodeNorth, nodeB),
              new Arc(nodeNorth, nodeA),
              new Arc(nodeA, nodeC),
              new Arc(nodeA, nodeE),
              new Arc(nodeSouth, nodeC),
              new Arc(nodeSouth, nodeD),
              new Arc(nodeE, nodeD)),
          List.of(warehouseNorth, warehouseSouth),
          List.of(storeA, storeB, storeC, storeD, storeE),
          LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2020, 2, 1, 0, 0));
      runExperiment(scenario);
    }
  }

  private static void runExperiment(Scenario scenario) {
    ExperimentRun experiment = new ExperimentRun(scenario, new Engine());
    experiment.run();
    Statistics statistics = experiment.getStatistics();
    System.out.println("Trucks count:\t" + scenario.getTruckCount()
        + "\tSL:\t" + Formats.getDefaultFormats().percentTwoDecimals(statistics.getServiceLevel())
        + "\tExpenses:\t" + Formats.getDefaultFormats().dollarTwoDecimals(statistics.getExpenses())
        + "\tExpenses/SL:\t" + Formats.getDefaultFormats().dollarTwoDecimals(statistics.getExpensesPerServiceLevelPercent()));
  }
}
