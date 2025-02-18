package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;

/**
 * This class handles the no enchantment scenario.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 21.10.2022
 */
public class NoEnchantScenario extends Scenario implements Listener {

    /**
     * Adding the scenario into the local storage list.
     */
    public NoEnchantScenario() {
        super("noenchants");
    }


    /**
     * Cancels crafting a enchantment table and a bow
     *
     * @param event the corresponding craft item event
     */
    @EventHandler
    public void onCraft(CraftItemEvent event) {

        // if scenario is enabled
        if (this.isEnabled())
            // if recipe is a enchantment table or a anvil
            if (event.getRecipe().getResult().getType().equals(Material.ENCHANTMENT_TABLE)
                    || event.getRecipe().getResult().getType().equals(Material.ANVIL))
                event.setCancelled(true);

    }

    /**
     * Cancels enchanting items
     *
     * @param event the corresponding enchant item event
     */
    @EventHandler
    public void onEnchant(EnchantItemEvent event) {
        if(UHCenario.getInstance().getMainConfiguration().getConfig().getStringList("disabled_worlds").contains(event.getEnchanter().getWorld().getName())) {
            return;
        }
        // if scenario is enabled
        if (this.isEnabled())
            // cancels using the enchantment table
            event.setCancelled(true);
    }
}