package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * This class handles the listeners for the nofall scenario.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 15.10.2022
 */
public class NofallScenario extends Scenario implements Listener {

    /**
     * Adding the scenario into the local storage list.
     */
    public NofallScenario() {
        super("nofall");
    }

    /**
     * Cancels getting fall damage.
     * @param event the corresponding entity damage event.
     */
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(UHCenario.getInstance().getMainConfiguration().getConfig().getStringList("disabled_worlds").contains(event.getEntity().getWorld().getName())) {
            return;
        }
        // if the nofall scenario is enabled.
        if(this.isEnabled())
            // if the damaged entity is a player.
            if(event.getEntity() instanceof Player)
                if(event.getCause().equals(EntityDamageEvent.DamageCause.FALL))
                    event.setCancelled(true);
    }
}