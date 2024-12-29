package dev.pizzawunsch.scenarios;

import com.google.common.collect.Lists;
import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

/**
 * This class handles the time bomb scenario.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.2
 * @since 21.10.2022
 */
public class TimeBombScenario extends Scenario implements Listener {


    private boolean trapped;
    /**
     * Adding the scenario into the local storage list.
     */
    public TimeBombScenario() {
        super("timebomb");
        trapped= false;
    }


    /**
     * Spawns a double chest with the contents of the player and explodes after 30 seconds.
     *
     * @param event the corresponding time bomb event
     */
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        // the player who died
        Player player = event.getEntity();
        // is scenario enabled
        if (this.isEnabled()) {
            // A list with all drops of a player
            List<ItemStack> drops = Lists.newArrayList();
            // adds the drops to the list
            drops.addAll(event.getDrops());
            // clear the drops
            event.getDrops().clear();
            // the location where the chest should spawn
            Location location = player.getLocation().add(0, 1, 0);
            // get the block from the location
            Block block = location.getBlock();

            trapped = !trapped;

            // Find a valid location for the double chest
            block = findValidChestLocation(block);

            List<Block> chestBlocks = Lists.newArrayList();

            // sets the type of the block to a chest
            block.setType((trapped ? Material.CHEST : Material.TRAPPED_CHEST));
            chestBlocks.add(block);
            // the chest of the block's state
            Chest chest = (Chest) block.getState();

            // Place the second chest adjacent to the first one
            Block adjacentBlock = findValidAdjacentBlock(block);
            if (adjacentBlock != null) {
                adjacentBlock.setType((trapped ? Material.CHEST : Material.TRAPPED_CHEST));
                chestBlocks.add(adjacentBlock);
            }

            // loops all item stacks from the drop list
            for (ItemStack itemStack : drops) {
                if (itemStack == null || itemStack.getType() == Material.AIR)
                    // skip the item if null or the type is air
                    continue;
                // adds the item from drop list to chest
                chest.getInventory().addItem(itemStack);
            }
            // spawns a new armor stand to display the time how long the time bomb exists
            ArmorStand armorStand = player.getWorld().spawn(chest.getLocation().clone().add(0.5, 1, 0),
                    ArmorStand.class);
            // set some relevant variables to the armor stand
            armorStand.setCustomNameVisible(true);
            armorStand.setSmall(true);
            armorStand.setGravity(false);
            armorStand.setVisible(false);
            armorStand.setMarker(true);
            // creates a new task to change the name of the armor stand
            new BukkitRunnable() {
                // time variable
                private int time = UHCenario.getInstance().getMainConfiguration().getConfig().getInt("scenarios.timebomb.time");

                @Override
                public void run() {
                    time--;
                    // if the time has the count of 0
                    if (time == 0) {
                        // clear the drops
                        event.getDrops().clear();
                        // creates a new explosion to destroy and damage the players
                        location.getWorld().createExplosion(location.getX() + 0.5, location.getY() + 0.5,
                                location.getZ() + 0.5, 4, true, true);
                        // spawns a new lightning
                        location.getWorld().strikeLightning(location);
                        for (Block block1 : chestBlocks) {
                            block1.setType(Material.AIR);
                        }
                        // removes the armor stand
                        armorStand.remove();
                        // cancels the task
                        cancel();
                    } else
                        armorStand.setCustomName(UHCenario.getInstance().getMessage("scenarios.timebomb.timer")
                                .replaceAll("%player%", player.getName())
                                .replaceAll("%time%", time + ""));
                }
            }.runTaskTimer(UHCenario.getInstance(), 0, 20);
        }
    }

    /**
     * Finds a valid location for placing the first chest.
     *
     * @param block the initial block location
     * @return a valid block for placing the chest
     */
    private Block findValidChestLocation(Block block) {
        // Check if the block is already occupied
        if (block.getType() == Material.AIR) {
            return block;
        }

        // Try adjacent blocks in order
        for (BlockFace face : new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP}) {
            Block relative = block.getRelative(face);
            if (relative.getType() == Material.AIR) {
                return relative;
            }
        }

        // Default to the original block if no valid location is found
        return block;
    }

    /**
     * Finds a valid adjacent block for the second chest.
     *
     * @param block the block where the first chest is placed
     * @return a valid block for placing the second chest or null if none is found
     */
    private Block findValidAdjacentBlock(Block block) {
        for (BlockFace face : new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST}) {
            Block relative = block.getRelative(face);
            if (relative.getType() == Material.AIR) {
                return relative;
            }
        }
        return null;
    }
}
