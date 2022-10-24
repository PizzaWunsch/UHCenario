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

    private Scoreboard scoreboard;
    private Objective objective;

    /**
     * Adding the scenario into the local storage list.
     */
    public ShowHealthScenario() {
        super("showhealth");

        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = this.scoreboard.getObjective("indicator") != null ? this.scoreboard.getObjective("indicator") : this.scoreboard.registerNewObjective("indicator", "health");
        this.objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        this.objective.setDisplayName(UHCenario.getInstance().getMessage("scenarios.showhealths.hearts"));
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(this.isEnabled()) {
            if(!player.getScoreboard().equals(this.scoreboard))
                player.setScoreboard(this.scoreboard);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(this.isEnabled()) {
            if(player.getScoreboard().equals(this.scoreboard))
                player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
    }

    @Override
    public void onEnable() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if(!onlinePlayer.getScoreboard().equals(this.scoreboard))
                onlinePlayer.setScoreboard(this.scoreboard);

        }
    }

    @Override
    public void onDisable() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if(onlinePlayer.getScoreboard().equals(this.scoreboard))
                onlinePlayer.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
    }
}