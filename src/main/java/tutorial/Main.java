package tutorial;

import com.amalgamasimulation.engine.Engine;
import tutorial.scenario.Scenario;
import tutorial.model.Statistics;
import com.amalgamasimulation.utils.format.Formats;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import org.json.JSONObject;
import tutorial.scenario.ScenarioParser;

public class Main {

  private static final String SCENARIOS_PATH = "scenarios/";

  public static void main(String[] args) {
    ScenarioParser parser = new ScenarioParser();
    File folder = new File(SCENARIOS_PATH);
    for (File file : folder.listFiles()) {
      JSONObject jsonScenario = null;
      try {
        byte[] bytes = new FileInputStream(file).readAllBytes();
        jsonScenario = new JSONObject(new String(bytes));
      } catch (IOException e) {
        e.printStackTrace();
      }
      if (Objects.nonNull(jsonScenario)) {
        try {
          Scenario scenario = parser.parseScenario(jsonScenario);
          runExperiment(scenario, file.getName());
        } catch (Exception e) {
          System.err.printf("File '%s' contains error or has incorrect format\n", file.getName());
          e.printStackTrace();
        }
      }
    }
  }

  private static void runExperiment(Scenario scenario, String scenarioName) {
    ExperimentRun experiment = new ExperimentRun(scenario, new Engine());
    experiment.run();
    Statistics statistics = experiment.getStatistics();
    System.out.println("Scenario\tTrucks count\tSL\tExpenses\tExpenses/SL");
    System.out.println("%s\t%s\t%s\t%s\t%s".formatted(scenarioName, scenario.getTrucks().size(),
        Formats.getDefaultFormats().percentTwoDecimals(statistics.getServiceLevel()),
        Formats.getDefaultFormats().dollarTwoDecimals(statistics.getExpenses()),
        Formats.getDefaultFormats().dollarTwoDecimals(statistics.getExpensesPerServiceLevelPercent())));
  }
}
