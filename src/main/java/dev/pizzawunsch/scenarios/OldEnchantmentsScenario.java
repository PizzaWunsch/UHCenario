package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.scenario.Scenario;
import net.minecraft.server.v1_8_R3.ContainerEnchantTable;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.Random;

/**
 * This class handles the old enchantment scenario.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 16.10.2022
 */
public class OldEnchantmentsScenario extends Scenario implements Listener {

    // instance variables.
    private final Random random = new Random();

    /**
     * Adding the scenario into the local storage list.
     */
    public OldEnchantmentsScenario() {
        super("oldenchantments");
    }

    /**
     * Hides the enchantments, when the player wants to enchant something.
     * @param event the corresponding prepare item enchant event.
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void hideEnchants(PrepareItemEnchantEvent event) {
        if(UHCenario.getInstance().getMainConfiguration().getConfig().getStringList("disabled_worlds").contains(event.getEnchanter().getWorld().getName())) {
            return;
        }
        if (!this.isEnabled())
            return;
        CraftInventoryView view = (CraftInventoryView) event.getView();
        ContainerEnchantTable table = (ContainerEnchantTable) view.getHandle();
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) UHCenario.getInstance(), () -> clear(table.h), 0L);
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) UHCenario.getInstance(),
                () -> table.f = this.random.nextInt(), 4L);
    }

    public void clear(int[] array) {
        Arrays.fill(array, -1);
    }

    /**
     * Sets the lapis into the enchantment table automatically.
     * @param event the corresponding inventory open event.
     */
    @EventHandler
    public void openEnchantmentTable(InventoryOpenEvent event) {
        if(UHCenario.getInstance().getMainConfiguration().getConfig().getStringList("disabled_worlds").contains(event.getPlayer().getWorld().getName())) {
            return;
        }
        if (this.isEnabled()) {
            try {
                if (event.getInventory() instanceof EnchantingInventory) {
                    EnchantingInventory inv = (EnchantingInventory) event.getInventory();
                    Dye dye = new Dye();
                    dye.setColor(DyeColor.BLUE);
                    ItemStack itemStack = dye.toItemStack();
                    itemStack.setAmount(3);
                    inv.setItem(1, itemStack);
                }
            } catch (Exception exception) {
            }
        }
    }

    /**
     * Cancels stealing the lapis lazuli from the enchanting table.
     * @param event the corresponding inventory click event.
     */
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(UHCenario.getInstance().getMainConfiguration().getConfig().getStringList("disabled_worlds").contains(event.getWhoClicked().getWorld().getName())) {
            return;
        }
        if (this.isEnabled()) {
            Inventory inventory = event.getInventory();
            ItemStack itemStack = event.getCurrentItem();
            try {
                if (inventory.getType() == InventoryType.ENCHANTING && itemStack.getType() == Material.INK_SACK)
                    event.setCancelled(true);
            } catch (Exception exception) {
            }
        }
    }

    @EventHandler
    public void closeInventoryEvent(InventoryCloseEvent event) {
        if(UHCenario.getInstance().getMainConfiguration().getConfig().getStringList("disabled_worlds").contains(event.getPlayer().getWorld().getName())) {
            return;
        }
        if (this.isEnabled()) {
            try {
                if (event.getInventory() instanceof EnchantingInventory)
                    event.getInventory().setItem(1, null);
            } catch (Exception exception) {
            }
        }
    }

    @EventHandler
    public void enchantItemEvent(EnchantItemEvent event) {
        if(UHCenario.getInstance().getMainConfiguration().getConfig().getStringList("disabled_worlds").contains(event.getEnchanter().getWorld().getName())) {
            return;
        }
        if (this.isEnabled()) {
            try {
                Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) UHCenario.getInstance(), () -> {
                    EnchantingInventory inv = (EnchantingInventory) event.getInventory();
                    Dye dye = new Dye();
                    dye.setColor(DyeColor.BLUE);
                    ItemStack itemStack = dye.toItemStack();
                    itemStack.setAmount(3);
                    inv.setItem(1, itemStack);
                });
            } catch (Exception exception) {
            }
        }
    }
}