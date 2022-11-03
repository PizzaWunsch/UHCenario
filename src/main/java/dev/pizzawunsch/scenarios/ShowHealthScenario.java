package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.scenario.Executable;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

/**
 * This class handles the show health scenario.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 24.10.2022
 */
public class ShowHealthScenario extends Scenario implements Listener, Executable {


    /**
     * Adding the scenario into the local storage list.
     */
    public ShowHealthScenario() {
        super("showhealth");
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Scoreboard scoreboard = player.getScoreboard();
        if(this.isEnabled()) {
            Objective objective = scoreboard.getObjective("indicator") != null ? scoreboard.getObjective("indicator") : scoreboard.registerNewObjective("indicator", "health");
            objective.setDisplayName(UHCenario.getInstance().getMessage("scenarios.showhealths.hearts"));
            objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Scoreboard scoreboard = player.getScoreboard();
        if(this.isEnabled()) {
            Objective objective = scoreboard.getObjective("indicator") != null ? scoreboard.getObjective("indicator") : null;
            if(objective != null) {
                objective.unregister();
            }

        }
    }

    @Override
    public void onEnable() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            Scoreboard scoreboard = onlinePlayer.getScoreboard();
            if(this.isEnabled()) {
                Objective objective = scoreboard.getObjective("indicator") != null ? scoreboard.getObjective("indicator") : scoreboard.registerNewObjective("indicator", "health");
                objective.setDisplayName(UHCenario.getInstance().getMessage("scenarios.showhealths.hearts"));
                objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
            }
        }
    }

    @Override
    public void onDisable() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            Scoreboard scoreboard = onlinePlayer.getScoreboard();
            if(this.isEnabled()) {
                Objective objective = scoreboard.getObjective("indicator") != null ? scoreboard.getObjective("indicator") : null;
                if(objective != null) {
                    objective.unregister();
                }
            }
        }
    }
}