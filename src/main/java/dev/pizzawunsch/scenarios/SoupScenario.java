package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * This class handles the soup scenario.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 21.10.2022
 */
public class SoupScenario extends Scenario implements Listener {
    /**
     * Adding the scenario into the local storage list.
     */
    public SoupScenario() {
        super("soup");
    }

    /**
     * Healing a player while he consume a soup
     *
     * @param event the corresponding interaction event
     */
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(UHCenario.getInstance().getMainConfiguration().getConfig().getStringList("disabled_worlds").contains(event.getPlayer().getWorld().getName())) {
            return;
        }
        // if scenario is enabled
        if (this.isEnabled()) {
            // the player who interacts with an item
            Player player = event.getPlayer();
            // if players item in hand is not null and a mushroom soup
            if (player.getItemInHand() != null && player.getItemInHand().getType() == Material.MUSHROOM_SOUP) {
                int soup = 8;
                // if players health lower than 20
                if (player.getHealth() < 20.0D) {
                    // healing the player
                    player.setHealth(((CraftPlayer) player).getHealth() + soup > ((CraftPlayer) player).getMaxHealth()
                            ? ((CraftPlayer) player).getMaxHealth()
                            : ((CraftPlayer) player).getHealth() + soup);
                    // saturate the player
                    player.setFoodLevel(player.getFoodLevel() + 8);
                    // removes their stew
                    player.getItemInHand().setType(Material.BOWL);
                    return;
                }
            }
        }

    }
}