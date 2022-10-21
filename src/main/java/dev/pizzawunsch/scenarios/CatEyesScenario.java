package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.tasks.CatEyesTask;
import dev.pizzawunsch.utils.scenario.Scenario;

/**
 * This class handles the cat eyes scenario.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 17.10.2022
 */
public class CatEyesScenario extends Scenario {

    /**
     * Adding the scenario into the local storage list.
     */
    public CatEyesScenario() {
        super("cateyes");
        new CatEyesTask().run();
    }
}