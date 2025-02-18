package dev.pizzawunsch.scenarios;

import com.google.common.collect.Lists;
import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;

import java.util.List;

/**
 * This class handles the max enchantments scenario.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 16.10.2022
 */
public class MaxEnchantmentScenario extends Scenario implements Listener {

    /**
     * Adding the scenario into the local storage list.
     */
    public MaxEnchantmentScenario() {
        super("maxenchantment");
    }

    /**
     * Enchant item with max level
     *
     * @param event the corresponding prepare item enchant event
     */
    @EventHandler
    public void onEnchant(PrepareItemEnchantEvent event) {
        if(UHCenario.getInstance().getMainConfiguration().getConfig().getStringList("disabled_worlds").contains(event.getEnchanter().getWorld().getName())) {
            return;
        }

        // if scenario is enabled
        if (this.isEnabled()) {
            // removes all old enchantment and add them with max level
            List<Enchantment> enchantmentList = Lists.newArrayList();
            for (Enchantment enchantment : event.getItem().getEnchantments().keySet()) {
                enchantmentList.add(enchantment);
                event.getItem().removeEnchantment(enchantment);
            }
            for (Enchantment enchantment : enchantmentList) {
                event.getItem().addEnchantment(enchantment, enchantment.getMaxLevel());
            }
        }
    }

    /**
     * Enchant item with max level from Anvil
     *
     * @param event the corresponding inventory click evewnt
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if(UHCenario.getInstance().getMainConfiguration().getConfig().getStringList("disabled_worlds").contains(event.getWhoClicked().getWorld().getName())) {
            return;
        }

        // if scenario is enabled
        if (this.isEnabled()) {
            // if clicked inventory is not null
            if (event.getClickedInventory() != null) {
                // if the livling entity is a player
                if (event.getWhoClicked() instanceof Player) {
                    // if inventory is a anvil inventory
                    if (event.getInventory() instanceof AnvilInventory) {
                        // removes all old enchantment and add them with max level
                        List<Enchantment> enchantmentList = Lists.newArrayList();
                        for (Enchantment enchantment : event.getCurrentItem().getEnchantments().keySet()) {
                            enchantmentList.add(enchantment);
                            event.getCurrentItem().removeEnchantment(enchantment);
                        }
                        for (Enchantment enchantment : enchantmentList) {
                            event.getCurrentItem().addEnchantment(enchantment, enchantment.getMaxLevel());
                        }
                    }
                }
            }
        }
    }
}