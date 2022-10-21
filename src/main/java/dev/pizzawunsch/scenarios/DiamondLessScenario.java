package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * This class handles the diamondless scenario.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 21.10.2022
 */
public class DiamondLessScenario extends Scenario implements Listener {

    /**
     * Adding the scenario into the local storage list.
     */
    public DiamondLessScenario() {
        super("diamondless");
    }


    /**
     * Disables breaking a diamond block
     *
     * @param event the corresponding block break event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreakBlock(BlockBreakEvent event) {
        // if scenario is already enabled
        if (this.isEnabled()) {
            // the player who breaked the block
            Player player = event.getPlayer();
            // the block that should be breaked
            Block block = event.getBlock();
            // if scenario is enabled
            if (block.getType().equals(Material.DIAMOND_ORE)) {
                // cancels block breking
                event.setCancelled(true);
                // removes the block without dropping anything
                block.setType(Material.AIR);
                // drops the experience that should be dropped
                player.getLocation().getWorld().spawn(block.getLocation(), ExperienceOrb.class)
                        .setExperience(event.getExpToDrop());
            }

        }
    }

}