package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * This class creates a new scenario that disables crafting an enchanting table.
 * When a player dies be drops a enchanting table.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 21.10.2022
 */
public class EnchantedDeathScenario extends Scenario implements Listener {
    /**
     * Adding the scenario into the local storage list.
     */
    public EnchantedDeathScenario() {
        super("enchanteddeath");
    }

    /**
     * Dropping an enchanting table when a player dies
     *
     * @param event the corresponding player death event
     */
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        // if scenario is enabled
        if (this.isEnabled()) {
            event.getEntity().getLocation().getWorld().dropItem(event.getEntity().getLocation(),
                    new ItemStack(Material.ENCHANTMENT_TABLE));
        }
    }

    /**
     * Cancels crating an enchanting table
     *
     * @param event the corresponding craft item event
     */
    @EventHandler
    public void onCraft(CraftItemEvent event) {
        // if scenario is enabled
        if (this.isEnabled()) {
            // if result of crafting is an enchanting table
            if (event.getRecipe().getResult().getType().equals(Material.ENCHANTMENT_TABLE)) {
                // cancel crafting
                event.setCancelled(true);
            }
        }
    }

}