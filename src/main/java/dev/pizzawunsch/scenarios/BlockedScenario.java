package dev.pizzawunsch.scenarios;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.pizzawunsch.utils.scenario.Executable;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.List;
import java.util.Map;

/**
 * This class handles the blocked scenario.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 16.10.2022
 */
public class BlockedScenario extends Scenario implements Listener, Executable {

    // instance variables
    private final Map<String, List<Block>> placed;

    /**
     * Adding the scenario into the local storage list.
     */
    public BlockedScenario() {
        super("blocked");
        // initializing instance variables
        placed = Maps.newHashMap();
    }

    /**
     * Cleaning up the placed map
     */
    @Override
    public void onDisable() {
        // clear placed map
        this.placed.clear();
    }

    /**
     * Cancels breaking block if in placed list of player
     *
     * @param event the corresponding block break event
     */
    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        // the player who placed a block
        Player player = event.getPlayer();
        // the block that got placed
        Block block = event.getBlock();
        // if scenario is enabled
        if (this.isEnabled()) {
            // if player is in placed map and player contains that block
            if (this.placed.containsKey(player.getName()) && this.placed.get(player.getName()).contains(block))
                // cancels breaking block.
                event.setCancelled(true);
        }
    }

    /**
     * Adds the placed block to placed map
     *
     * @param event the corresponding block place event
     */
    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        // the player who placed a block
        Player player = event.getPlayer();
        // the block that got placed
        Block block = event.getBlock();
        // if scenario is enabled
        if (this.isEnabled()) {
            // if player's placed list does exist
            if (this.placed.containsKey(player.getName()))
                // add block to placed list
                this.placed.get(player.getName()).add(block);
            else {
                // add player to placed map
                this.placed.put(player.getName(), Lists.newArrayList());
                // add block to placed list
                this.placed.get(player.getName()).add(block);
            }
        }
    }

    @Override
    public void onEnable() {
    }
}