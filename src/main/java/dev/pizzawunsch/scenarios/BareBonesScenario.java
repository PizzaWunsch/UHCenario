package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.item.ItemBuilder;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * This class handles the bare-bones scenario.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 21.10.2022
 */
public class BareBonesScenario extends Scenario implements Listener {

    /**
     * Adding the scenario into the local storage list.
     */
    public BareBonesScenario() {
        super("barebones");
    }

    /**
     * Handles dropping 3 diamonds while enabled
     *
     * @param event the corresponding player death event
     */
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        // if scenario is enabled
        if (this.isEnabled()) {
            // adds to players drops 3 diamonds
            event.getDrops().add(new ItemBuilder(Material.DIAMOND).amount(UHCenario.getInstance().getMainConfiguration().getConfig().getInt("scenarios.barebones.diamonds")).build());
        }
    }
}