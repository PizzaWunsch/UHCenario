package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;

/**
 * This class handles
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 21.10.2022
 */
public class XtraAppleScenario extends Scenario implements Listener {

    /**
     * Adding the scenario into the local storage list.
     */
    public XtraAppleScenario() {
        super("xtraapple");
    }

    /**
     * Handels that when a leave decays that the chance is increased that a apple
     * drops.
     *
     * @param event the corresponding leaves decay event
     */
    @EventHandler
    public void onDecay(LeavesDecayEvent event) {
        // the block that got decayed
        Block block = event.getBlock();
        // if scenario is enabled
        if (this.isEnabled()) {
            if (event.getBlock().getType().equals(Material.LEAVES)
                    || event.getBlock().getType().equals(Material.LEAVES_2))
                // in change of 20 percent
                if (Math.random() < 0.2D)
                    // drops an apple to the location of decayed block
                    block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.APPLE));
        }
    }

    /**
     * Handels that when a leave got breaked that the chance is increased that a
     * apple drops.
     *
     * @param event the corresponding leaves decay event
     */
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        // the block that got breaked
        Block block = event.getBlock();
        // if scenario is enabled
        if (this.isEnabled()) {
            if (event.getBlock().getType().equals(Material.LEAVES)
                    || event.getBlock().getType().equals(Material.LEAVES_2))
                // in change of 20 percent
                if (Math.random() < 0.2D)
                    // drops an apple to the location of breaked block
                    block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.APPLE));
        }
    }

}