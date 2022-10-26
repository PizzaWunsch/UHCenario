package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * This class handles the fastgetaway scenario.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 26.10.2022
 */
public class FastGetawayScenario extends Scenario implements Listener {

    /**
     * Adding the scenario into the local storage list.
     */
    public FastGetawayScenario() {
        super("fastgetaway");
    }

    /**
     * Handles the fast getaway scenario and gives the player x seconds speed 2
     * after he killed a player
     *
     * @param event the corresponding player death event
     */
    @EventHandler
    public void onFastGetaway(PlayerDeathEvent event) {
        // if the scenario is active
        if (this.isEnabled()) {
            // the dead player
            final Player player = event.getEntity();
            // if the player got killed by another player
            if (player.getKiller() != null) {
                // the player's killer
                final Player killer = player.getKiller();
                int time = UHCenario.getInstance().getMainConfiguration().getConfig().getInt("scenarios.fastgetaway.time");
                // giving the killer the potion effect
                killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * time, 1));
                killer.sendMessage(UHCenario.getInstance().getMessage("scenarios.fastgetaway.killed").replaceAll("%time%", "" + time));
            }
        }
    }

}