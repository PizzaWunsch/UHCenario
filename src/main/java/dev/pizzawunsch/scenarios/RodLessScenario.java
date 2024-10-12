package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;

/**
 * This class handles
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 21.10.2022
 */
public class RodLessScenario extends Scenario implements Listener {
    /**
     * Adding the scenario into the local storage list.
     */
    public RodLessScenario() {
        super("rodless");
    }

    /**
     * Cancels crafting a bow
     *
     * @param event the corresponding craft item event
     */
    @EventHandler
    public void onCraftRod(CraftItemEvent event) {
        // if this scenario is enabled
        if (this.isEnabled()) {
            // if result of the recipe is a rod
            if (event.getRecipe().getResult().getType().equals(Material.FISHING_ROD)) {
                // cancels crafting the rod
                event.setCancelled(true);
            }
        }
    }

    /**
     * Disables damaging with a rod
     *
     * @param event the corresponding entity damage by entity event
     */
    @EventHandler
    public void onDamageWithRod(EntityDamageByEntityEvent event) {
        // if this scenario is enabled
        if (this.isEnabled()) {
            // if damaged entity is a player
            if (event.getEntity() instanceof Player) {
                // the player
                Player player = (Player) event.getEntity();
                // if damager is a projectile
                if (event.getDamager() instanceof Projectile) {
                    // the projectile
                    Projectile projectile = (Projectile) event.getDamager();
                    // if shooter of the projectile is a player
                    if (projectile.getShooter() instanceof Player) {
                        // if projectile is a arrow
                        if (projectile.getType().equals(EntityType.FISHING_HOOK)
                                || projectile.getType().equals(EntityType.SNOWBALL)
                                || projectile.getType().equals(EntityType.EGG)) {
                            // cancel damaging the player
                            event.setCancelled(true);
                            // plays a sound and sends a message to player
                            player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1, 1);
                        }
                    }
                }
            }
        }
    }
}