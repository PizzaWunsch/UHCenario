package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.event.Listener;

/**
 * This class handles the gold less scenario
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 21.10.2022
 */
public class GoldLessScenario extends Scenario implements Listener {
    /**
     * Adding the scenario into the local storage list.
     */
    public GoldLessScenario() {
        super("goldless");
    }
}