package dev.pizzawunsch.utils.scenario;

/**
 * This interface handles
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 15.10.2022
 */
public interface Executable {

    /**
     * Will be executed whenever a scenario will be enabled.
     */
    void onEnable();

    /**
     * Will be executed whenever a scenario will be disabled.
     */
    void onDisable();

}
