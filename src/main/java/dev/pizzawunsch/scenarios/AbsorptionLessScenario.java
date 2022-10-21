package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffectType;

/**
 * This class handles the absorption less scenario.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 16.10.2022
 */
public class AbsorptionLessScenario extends Scenario implements Listener {

    /**
     * Adding the scenario into the local storage list.
     */
    public AbsorptionLessScenario() {
        super("absorptionless");
    }

    /**
     * Cancels getting absorption hearts by consume a item
     *
     * @param event the corresponding absortption less event
     */
    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        // if this scenario is enabled
        if (this.isEnabled()) {
            // the player who consume a item
            Player player = event.getPlayer();
            // removes the absorption effect
            player.removePotionEffect(PotionEffectType.ABSORPTION);
            Bukkit.getScheduler().runTaskLater(UHCenario.getInstance(), () -> {
                player.removePotionEffect(PotionEffectType.ABSORPTION);
            }, 1);
        }
    }
}