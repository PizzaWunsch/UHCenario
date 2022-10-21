package dev.pizzawunsch.utils;

import com.google.common.collect.Lists;
import dev.pizzawunsch.utils.scenario.Scenario;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * This class handles the scenario player object of the uhcenario plugin.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 15.10.2022
 */
@Data
@AllArgsConstructor
public class ScenarioPlayer {

    // instance variable
    private static List<ScenarioPlayer> scenarioPlayers = Lists.newArrayList();
    private UUID uniqueId;
    private boolean alreadyVoted;
    private Scenario votedScenario;

    /**
     * @return a list with all scenario players with some caches about the player
     */
    public static List<ScenarioPlayer> getScenarioPlayers() {
        return scenarioPlayers;
    }

    /**
     * @param uuid the unique id of player
     * @return the player that got found
     */
    public static ScenarioPlayer getScenarioPlayer(UUID uuid) {
        return scenarioPlayers.stream().filter(scenarioPlayer -> scenarioPlayer.getUniqueId().equals(uuid)).findAny()
                .orElse(null);
    }
}