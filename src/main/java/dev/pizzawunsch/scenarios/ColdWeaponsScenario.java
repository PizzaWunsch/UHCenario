package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.scenario.Scenario;
import net.minecraft.server.v1_8_R3.Enchantment;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;

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
    public void onEnchantItem(EnchantItemEvent event) {
        if(!this.isEnabled())
            return;
        Player player = event.getEnchanter();
        ItemStack item = event.getItem();


        if (item.getType() == Material.DIAMOND_SWORD || item.getType() == Material.IRON_SWORD || item.getType() == Material.GOLD_SWORD || item.getType() == Material.STONE_SWORD || item.getType() == Material.WOOD_SWORD) {
            if (event.getEnchantsToAdd().containsKey(Enchantment.FIRE_ASPECT)) {

                event.getEnchantsToAdd().remove(Enchantment.FIRE_ASPECT);
                player.sendMessage(UHCenario.getInstance().getMessage("scenarios.fireless.cannotEnchant"));
            }
        }
    }

}
