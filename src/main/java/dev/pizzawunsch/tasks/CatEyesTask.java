package dev.pizzawunsch.tasks;

import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * This class handles the task of the cat eye task.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 17.10.2022
 */
public class CatEyesTask extends Task{

    /**
     * The task to give each player on the server the night vision effect.
     */
    public CatEyesTask() {
        super(true, 60, 60);
    }

    /**
     * Gives every player who is online the night vision effect.
     */
    @Override
    public void execute() {
        // the cat eyes scenario.
        Scenario scenario = Scenario.getScenario("cateyes");
        if(scenario == null)
            return;
        if(!scenario.isRegistered())
            return;
        if(!scenario.isEnabled())
            return;
        // loops all player there online
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            // adds to every player whos online the night vision effect
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 100, 1));
        }
    }
}