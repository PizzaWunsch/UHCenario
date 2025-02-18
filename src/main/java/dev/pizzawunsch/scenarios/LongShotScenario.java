package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * This class handles
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 16.10.2022
 */
public class LongShotScenario extends Scenario implements Listener {

    /**
     * Adding the scenario into the local storage list.
     */
    public LongShotScenario() {
        super("longshot");
    }

    /**
     * Handels the double damage to damaged player when he is hit by a bow in a
     * distance over 50 half blocks.
     *
     * @param event event the corresponding entity damage by entity event
     */
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if(UHCenario.getInstance().getMainConfiguration().getConfig().getStringList("disabled_worlds").contains(event.getEntity().getWorld().getName())) {
            return;
        }

        // if scenario is enabled
        if (this.isEnabled()) {
            // if damaged and damager is a player
            if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
                // the player who got damaged
                final Player entity = (Player) event.getEntity();
                // the player who damaged a player with a bow
                final Player damager = (Player) event.getDamager();
                /*
                 * if the distance between the damager and the damaged player is over 50 half
                 * blocks.
                 */
                if (damager.getLocation().distance(entity.getLocation()) >= 50.0D) {
                    // adds to damager healths 2 hearts
                    damager.setHealth(damager.getHealth() + 2.0D);
                    // multiplicate the damage by 2
                    event.setDamage(event.getDamage() * 2D);
                }
            }
        }
    }

}