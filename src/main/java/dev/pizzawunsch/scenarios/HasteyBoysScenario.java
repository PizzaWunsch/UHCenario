package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.item.ItemBuilder;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * This class creates a new scenario who every tools get efficiency 3 and
 * unbreaking 3.
 *
 * @author Lucas | PizzaWunsch
 * @version 0.1
 * @since 16.10.2022
 */
public class HasteyBoysScenario extends Scenario implements Listener {

    // instance variables
    private final Material[] tools;

    /**
     * Adding the scenario into the local storage list.
     */
    public HasteyBoysScenario() {
        super("hasteyboys");
        // initialize instance variables
        tools = new Material[] { Material.WOOD_AXE, Material.WOOD_SPADE, Material.WOOD_PICKAXE, Material.WOOD_HOE,
                Material.STONE_AXE, Material.STONE_SPADE, Material.STONE_PICKAXE, Material.STONE_HOE, Material.GOLD_AXE,
                Material.GOLD_SPADE, Material.GOLD_PICKAXE, Material.GOLD_HOE, Material.IRON_AXE, Material.IRON_SPADE,
                Material.IRON_PICKAXE, Material.IRON_HOE, Material.DIAMOND_AXE, Material.DIAMOND_SPADE,
                Material.DIAMOND_PICKAXE, Material.DIAMOND_HOE };
    }

    /**
     * Adds enchantments to the tools in tool list
     *
     * @param event the corresponding craft item event.
     */
    @EventHandler
    public void onCraft(CraftItemEvent event) {

        // if scenario is enabled
        if (this.isEnabled()) {
            // the result of revipe
            ItemStack itemStack = event.getRecipe().getResult();
            // if itemstack's material is in tools list
            for (Material material : this.tools) {
                if (itemStack.getType() == material)
                    // adding efficiency 3 and unbreaking 3.
                    itemStack = new ItemBuilder(itemStack.getType()).enchantment(Enchantment.DIG_SPEED, 3)
                            .enchantment(Enchantment.DURABILITY, 3).build();
            }
            // sets the result
            event.getInventory().setResult(itemStack);
        }
    }
}