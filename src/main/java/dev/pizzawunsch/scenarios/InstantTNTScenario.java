package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.item.ItemBuilder;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

/**
 * This class handles the instant TNT scenario.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 26.10.2022
 */
public class InstantTNTScenario extends Scenario implements Listener {

    /**
     * Adding the scenario into the local storage list.
     */
    public InstantTNTScenario() {
        super("instanttnt");
    }

    /**
     * Handles the instant tnt scenario and thus gives the killer 5 tnt's on a
     * player kill
     *
     * @param event the corresponding player death event
     */
    @EventHandler
    public void onInstantTntKill(PlayerDeathEvent event) {

        if(UHCenario.getInstance().getMainConfiguration().getConfig().getStringList("disabled_worlds").contains(event.getEntity().getWorld().getName())) {
            return;
        }

        // if the scenario is active
        if (this.isEnabled()) {
            final Player player = event.getEntity();


            // if the player got killed by another player
            if (player.getKiller() != null) {
                // the player's killer
                final Player killer = player.getKiller();
                // giving the killer the diamonds
                killer.getInventory().addItem(new ItemBuilder(Material.TNT).amount(5).build());
            }
        }
    }

    /**
     * Handles the instant tnt explosion when placing a tnt.
     *
     * @param event the corresponding block place event
     */
    @EventHandler
    public void onTntPlace(BlockPlaceEvent event) {
        if(UHCenario.getInstance().getMainConfiguration().getConfig().getStringList("disabled_worlds").contains(event.getPlayer().getWorld().getName())) {
            return;
        }

        // if the scenario is active
        if (this.isEnabled()) {
            // if the placed block is a tnt
            if (event.getBlock().getType().equals(Material.TNT)) {
                // the player
                final Player player = event.getPlayer();

                // replacing the block with air and letting the tnt explode instantly
                event.getBlock().setType(Material.AIR);
                TNTPrimed tnt = (TNTPrimed) event.getBlock().getWorld().spawn(event.getBlock().getLocation(),
                        TNTPrimed.class);
                tnt.setFuseTicks(30);
                // removing the tnt from the player's inventory
                final ItemStack item = player.getInventory().getItemInHand();
                if (item.getAmount() <= 1) {
                    player.getInventory().remove(item);
                } else {
                    item.setAmount(item.getAmount() - 1);
                }
            }
        }
    }
}