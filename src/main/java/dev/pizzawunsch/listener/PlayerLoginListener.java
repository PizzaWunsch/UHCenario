package dev.pizzawunsch.listener;

import dev.pizzawunsch.utils.ScenarioPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * This class creates a new scenario player object and adds them to local
 * storage.
 *
 * @author Lucas | PizzaWunsch
 * @version 0.1
 * @since 22.06.2021
 */
public class PlayerLoginListener implements Listener {

    /**
     * Creates a new scenario player object
     *
     * @param event the corresponding player login event
     */
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        // the player that try to login
        Player player = event.getPlayer();
        // if scenario player does not exist.
        if (ScenarioPlayer.getScenarioPlayer(player.getUniqueId()) == null)
            ScenarioPlayer.getScenarioPlayers().add(new ScenarioPlayer(player.getUniqueId(), false, null));
    }
}