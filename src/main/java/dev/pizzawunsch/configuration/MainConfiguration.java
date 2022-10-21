package dev.pizzawunsch.configuration;

/**
 * This class handles the main configurations of the UHCenario plugin.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 14.10.2022
 */
public class MainConfiguration extends AbstractConfiguration {

    /**
     * This is the constructor of the abstract configuration file.
     * It will initialize all corresponding variables.
     */
    public MainConfiguration() {
        super("plugins/UHCenario", "config.yml");
    }
}