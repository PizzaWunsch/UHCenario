package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * This class handles
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 15.10.2022
 */
public class NoHungerScenario extends Scenario implements Listener {
    /**
     * Adding the scenario into the local storage list.
     */
    public NoHungerScenario() {
        super("nohunger");
    }

    /**
     * Cancels getting hunger.
     * @param event the corresponding food level change event.
     */
    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        // if the nofall scenario is enabled.
        if(this.isEnabled()) {
            if(event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
                event.setCancelled(true);
                player.setFoodLevel(20);
                player.setSaturation(10);
            }


        }
    }
}