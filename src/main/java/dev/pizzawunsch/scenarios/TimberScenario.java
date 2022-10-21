package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * This class handles
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 21.10.2022
 */
public class TimberScenario extends Scenario implements Listener {

    /**
     * Adding the scenario into the local storage list.
     */
    public TimberScenario() {
        super("timber");
    }


    /**
     * Breaks the full tree if you just break one block of it
     *
     * @param event the block break event
     */
    @EventHandler
    public void onTimber(BlockBreakEvent event) {
        // if the scenario is active
        if (this.isEnabled()) {
            // the block breaked
            Block initial = event.getBlock();
            Block block = event.getBlock();
            // doesn't apply for these types of materials
            if (block.getType() != Material.LOG && block.getType() != Material.LOG_2) {
                return;
            }
            // the block upper the just broke one
            block = block.getRelative(BlockFace.UP);
            // breaking and dropping all blocks of tree automatically
            while (block.getType() == Material.LOG || block.getType() == Material.LOG_2) {
                block.breakNaturally();
                block = block.getRelative(BlockFace.UP);
            }
            // the lower part of the tree
            block = initial.getRelative(BlockFace.DOWN);
            while (block.getType() == Material.LOG || block.getType() == Material.LOG_2) {
                block.breakNaturally();
                block = block.getRelative(BlockFace.DOWN);
            }
        }
    }
}