package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * This class handles the fireless scenario.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 16.10.2022
 */
public class FireLessScenario extends Scenario implements Listener {

    /**
     * Adding the scenario into the local storage list.
     */
    public FireLessScenario() {
        super("fireless");
    }


    /**
     * Cancels enchanting fire aspect.
     *
     * @param event the corresponding enchant item event
     */
    @EventHandler
    public void onEnchantItem(EnchantItemEvent event) {
        // if this scenario is enabled
        if (this.isEnabled()) {
            // the player who wants to enchant a item
            Player player = event.getEnchanter();
            // if enchants to add contains key of the fire aspect enchantment
            if (event.getEnchantsToAdd().containsKey(Enchantment.FIRE_ASPECT)
                    || event.getEnchantsToAdd().containsKey(Enchantment.ARROW_FIRE)) {
                // canceling enchant item with fire aspect
                event.setCancelled(true);
                player.sendMessage(UHCenario.getInstance().getMessage("scenarios.fireless.cannotEnchant"));
            }
        }
    }

    /**
     * Cancels getting damage by fire
     *
     * @param event the corresponding entity damage event
     */
    @EventHandler
    public void onFire(EntityDamageEvent event) {
        // if scenario is enabled
        if (this.isEnabled()) {
            // if damaged entity is a player
            if (event.getEntity() instanceof Player) {
                // if damage cause is fire, lava or fire tick
                if (event.getCause().equals(EntityDamageEvent.DamageCause.FIRE)
                        || event.getCause().equals(EntityDamageEvent.DamageCause.LAVA)
                        || event.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK)) {
                    event.setCancelled(true);
                    event.getEntity().setFireTicks(0);
                }
            }
        }
    }
}