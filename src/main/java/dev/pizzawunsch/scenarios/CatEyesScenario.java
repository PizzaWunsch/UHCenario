package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.tasks.CatEyesTask;
import dev.pizzawunsch.utils.scenario.Executable;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

/**
 * This class handles the cat eyes scenario.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 17.10.2022
 */
public class CatEyesScenario extends Scenario implements Executable {

    /**
     * Adding the scenario into the local storage list.
     */
    public CatEyesScenario() {
        super("cateyes");
        new CatEyesTask().run();
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        }
    }
}