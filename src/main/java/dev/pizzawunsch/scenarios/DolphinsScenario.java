package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.scenario.Executable;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * This class handles the dolphins scenario.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 21.10.2022
 */
public class DolphinsScenario extends Scenario implements Executable {

    /**
     * Adding the scenario into the local storage list.
     */
    public DolphinsScenario() {
        super("dolphins");
    }

    @Override
    public void onEnable() {
// creates a new timer to check every 5 ticks if a player is in water and add
        // the
        new BukkitRunnable() {
            @Override
            public void run() {
                // if scenario is enabled
                if (DolphinsScenario.this.isEnabled()) {
                    // loops all players
                    Bukkit.getOnlinePlayers().forEach(all -> {
                        // the material where the player is walking on
                        Material material = all.getLocation().getBlock().getType();
                        // if player is in water
                        if (material.equals(Material.WATER) || material.equals(Material.STATIONARY_WATER)) {
                            // if player have boots equiped without the depth striper enchantment
                            if (all.getEquipment().getBoots() != null && !all.getEquipment().getBoots()
                                    .getEnchantments().containsKey(Enchantment.DEPTH_STRIDER))
                                // adds the depth striper enchantment
                                all.getEquipment().getBoots().addEnchantment(Enchantment.DEPTH_STRIDER, 3);
                        } else {
                            // if player has depth strider enchantment
                            if (all.getEquipment().getBoots() != null && all.getEquipment().getBoots().getEnchantments()
                                    .containsKey(Enchantment.DEPTH_STRIDER))
                                // removes the depth strider enchantment
                                all.getEquipment().getBoots().removeEnchantment(Enchantment.DEPTH_STRIDER);
                        }
                    });
                } else {
                    // loops all players
                    Bukkit.getOnlinePlayers().forEach(all -> {
                        if (all.getEquipment().getBoots() != null && all.getEquipment().getBoots().getEnchantments()
                                .containsKey(Enchantment.DEPTH_STRIDER))
                            // removes the depth strider enchantment
                            all.getEquipment().getBoots().removeEnchantment(Enchantment.DEPTH_STRIDER);
                    });
                    // cancels running task to improve the performance
                    this.cancel();
                }
            }
        }.runTaskTimer(UHCenario.getInstance(), 0, 5);
    }

    @Override
    public void onDisable() {

    }
}