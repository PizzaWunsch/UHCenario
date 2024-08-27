package dev.pizzawunsch.utils.scenario;

import com.google.common.collect.Lists;
import dev.pizzawunsch.UHCenario;
import lombok.Data;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.List;

/**
 * This class handles a scenario of the uhcenario plugin.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 15.10.2022
 */
@Data
public class Scenario {

    // instance variables.
    @Getter
    private static List<Scenario> scenarios = Lists.newArrayList();
    private Material material;
    private byte subid;
    private String name, key;
    private List<String> lore;
    private boolean enabled, registered, votable;
    private int votes;

    /**
     * Adding the scenario into the local storage list.
     * @param key the key of the scenario.
     */
    public Scenario(String key) {
        this.key = key;
        this.registered = false;
        this.enabled = false;
        this.votable = false;
        this.votes = 0;
        scenarios.add(this);
    }

    /**
     * Allows you to enable or disable a scenario.
     * @param enabled if the scenario should be enabled or be disabled.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if(enabled) {
            this.votable = false;
            Bukkit.broadcastMessage(UHCenario.getInstance().getMessage("scenarios.enabled").replace("%scenario%", this.getName()));
            System.out.println(this.getClass().isAssignableFrom(Executable.class));
            if (this instanceof Executable) {
                ((Executable) this).onEnable();
                System.out.println("Scenario " + this.key + " executed!");
            }
        } else {
            Bukkit.broadcastMessage(UHCenario.getInstance().getMessage("scenarios.disabled").replace("%scenario%", this.getName()));
            if (this instanceof Executable)
                ((Executable) this).onDisable();
        }
    }

    /**
     * Returns you a scenario if they is registered and the key is correct.
     * @param key the key of the scenario
     * @return the scenario of the given key.
     */
    public static Scenario getScenario(String key) {
        return scenarios.stream().filter(scenario -> scenario.getKey().equals(key) && scenario.isRegistered()).findAny().orElse(null);
    }

    /**
     * Returns you a unregistered scenario.
     * @param key the key of the scenario
     * @return the scenario of the given key.
     */
    public static Scenario getUnregisteredScenario(String key) {
        return scenarios.stream().filter(scenario -> scenario.getKey().equals(key) && !scenario.isRegistered()).findAny().orElse(null);
    }
}