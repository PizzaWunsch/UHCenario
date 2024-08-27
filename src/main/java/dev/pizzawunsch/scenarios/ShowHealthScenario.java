package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.scenario.Executable;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
public class ShowHealthScenario extends Scenario implements Executable {


    /**
     * Adding the scenario into the local storage list.
     */
    public ShowHealthScenario() {
        super("showhealth");
    }



    private void startShowHealth() {
        String objectiveName = "show_health";
        Bukkit.getScheduler().runTaskTimer((UHCenario.getInstance()), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Scoreboard scoreboard = player.getScoreboard();
                Objective healthName = scoreboard.getObjective(objectiveName);
                if (healthName == null) {
                    healthName = scoreboard.registerNewObjective(objectiveName, "health");
                    healthName.setDisplaySlot(DisplaySlot.BELOW_NAME);
                    healthName.setDisplayName(ChatColor.DARK_RED + "♥");
                }
                healthName.setDisplaySlot(DisplaySlot.BELOW_NAME);
                if (!this.isEnabled())
                    healthName.unregister();
            }
        },10L, 10L);
    }

    @Override
    public void onEnable() {
     this.startShowHealth();
    }

    @Override
    public void onDisable() {

    }
}