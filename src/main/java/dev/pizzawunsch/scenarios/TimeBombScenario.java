package dev.pizzawunsch.scenarios;

import com.google.common.collect.Lists;
import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.scenario.Scenario;
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
 * @version 1.0
 * @since 21.10.2022
 */
public class TimeBombScenario extends Scenario implements Listener {

    /**
     * Adding the scenario into the local storage list.
     */
    public TimeBombScenario() {
        super("timebomb");
    }

    /**
     * Spawns a chest with the contents of player and explode after 30 seconds.
     *
     * @param event the corresponding tome bomb event
     */
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        // the player who died
        Player player = event.getEntity();
        // is scenario is enabled
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
            // get the down relative of block
            block.getRelative(BlockFace.DOWN);

            List<Block> chestClock = Lists.newArrayList();

            // sets the type of the block to a chest
            block.setType(Material.CHEST);
            chestClock.add(block);
            // the chest of by blocks state
            Chest chest = (Chest) block.getState();
            // get the relative of northern side from block
            block = block.getRelative(BlockFace.NORTH);
            // set the type of the relative block to chest
            block.setType(Material.CHEST);
            chestClock.add(block);
            // loops all item stacks from the drop list
            for (ItemStack itemStack : drops) {
                if (itemStack == null || itemStack.getType() == Material.AIR)
                    // skip the item if null or the type is air
                    continue;
                // adds the item from drop list to chest
                chest.getInventory().addItem(itemStack);
            }
            // spawns a new armor stand to display the time how long the time bomb exist
            ArmorStand armorStand = player.getWorld().spawn(chest.getLocation().clone().add(0.5, 1, 0),
                    ArmorStand.class);
            // set some relevant variables to the armor stand
            armorStand.setCustomNameVisible(true);
            armorStand.setSmall(true);
            armorStand.setGravity(false);
            armorStand.setVisible(false);
            armorStand.setMarker(true);
            // creates a new task to change the name of armor stand
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


                        for(Block block1 : chestClock) {
                            block1.setType(Material.AIR);
                        }

                        // removes the armor stand
                        armorStand.remove();
                        // cancels the task
                        cancel();
                    } else
                        armorStand.setCustomName(UHCenario.getInstance().getMessage("scenarios.timebomb.timer").replaceAll("%player%", player.getName()).replaceAll("%time%", time + ""));
                }
            }.runTaskTimer(UHCenario.getInstance(), 0, 20);
        }
    }
}