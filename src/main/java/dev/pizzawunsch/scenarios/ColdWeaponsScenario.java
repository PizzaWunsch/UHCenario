package dev.pizzawunsch.scenarios;

import com.google.common.collect.Lists;
import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ColdWeaponsScenario extends Scenario implements Listener {
    /**
     * Adding the scenario into the local storage list.
     *
     * @param key the key of the scenario.
     */
    public ColdWeaponsScenario() {
        super("coldweapons");
    }

    @EventHandler
    public void onEnchant(PrepareItemEnchantEvent event) {
        // if scenario is enabled
        if (this.isEnabled()) {
            for (org.bukkit.enchantments.Enchantment enchantment : event.getItem().getEnchantments().keySet()) {
                if(enchantment.equals(Enchantment.FIRE_ASPECT) || enchantment.equals(Enchantment.ARROW_FIRE))
                    event.getItem().removeEnchantment(enchantment);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(!this.isEnabled())
            return;
        if(event.getWhoClicked() instanceof Player) {

            if(event.getCurrentItem() == null)
                return;

            if(event.getCurrentItem().getEnchantments().containsKey(Enchantment.FIRE_ASPECT)) {
                event.getCurrentItem().removeEnchantment(Enchantment.FIRE_ASPECT);
            }
            if(event.getCurrentItem().getEnchantments().containsKey(Enchantment.ARROW_FIRE)) {
                event.getCurrentItem().removeEnchantment(Enchantment.ARROW_FIRE);
            }
        }
    }

}
