package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the webcage scenario.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 16.10.2022
 */
public class WebCageScenario extends Scenario implements Listener {

    /**
     * Adding the scenario into the local storage list.
     */
    public WebCageScenario() {
        super("webcage");
    }

    /**
     * Spawns a sphere of webs when a player dies
     *
     * @param event the corresponding death
     */
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if(UHCenario.getInstance().getMainConfiguration().getConfig().getStringList("disabled_worlds").contains(event.getEntity().getWorld().getName())) {
            return;
        }
        // if scenario is enabled
        if (this.isEnabled()) {
            // the player who died
            Player player = event.getEntity();
            // the list of blocks of a sphere
            List<Location> locations = getSphere(player.getLocation(), 4, true);
            // loops all blocks and set the web blocks
            for (Location blocks : locations) {
                if (blocks.getBlock().getType() == Material.AIR) {
                    blocks.getBlock().setType(Material.WEB);
                }
            }
        }
    }

    /**
     * Returns a list of all block in a sphere
     *
     * @param centerBlock the center block of location
     * @param radius      the radius of sphere
     * @param hollow      if sphere is a hollow
     * @return a list with all blocks
     */
    public List<Location> getSphere(Location centerBlock, int radius, boolean hollow) {
        List<Location> circleBlocks = new ArrayList<Location>();
        int bx = centerBlock.getBlockX();
        int by = centerBlock.getBlockY();
        int bz = centerBlock.getBlockZ();
        for (int x = bx - radius; x <= bx + radius; x++) {
            for (int y = by - radius; y <= by + radius; y++) {
                for (int z = bz - radius; z <= bz + radius; z++) {
                    double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)) + ((by - y) * (by - y)));
                    if (distance < radius * radius && !(hollow && distance < ((radius - 1) * (radius - 1)))) {
                        Location l = new Location(centerBlock.getWorld(), x, y, z);
                        circleBlocks.add(l);
                    }
                }
            }
        }
        return circleBlocks;
    }
}