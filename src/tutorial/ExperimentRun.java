package tutorial;

import tutorial.model.Model;
import tutorial.scenario.Scenario;

import java.time.LocalDateTime;

import com.amalgamasimulation.engine.Engine;
import tutorial.model.Statistics;

public class ExperimentRun {
    private final Model model;
    private final Scenario scenario;
    private final Engine engine;

    public ExperimentRun(Scenario scenario, Engine engine) {
        this.scenario = scenario;
        this.engine = engine;
        this.model = new Model(engine, scenario);
    }

    public void run() {
        model.engine().setFastMode(true);
        model.engine().run(true);
    }

    public Scenario getScenario() {
        return scenario;
    }

    public Engine getEngine() {
        return engine;
    }

    public LocalDateTime getSimulationEndDateTime() {
        return model.engine().date();
    }
    
    public Statistics getStatistics() {
        return model.getStatistics();
    }
}