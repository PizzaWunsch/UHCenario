package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * This class handles the kill switch scenario.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 25.10.2022
 */
public class KillSwitchScenario extends Scenario implements Listener {
    /**
     * Adding the scenario into the local storage list.
     */
    public KillSwitchScenario() {
        super("killswitch");
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (this.isEnabled()) {
            if (event.getEntity().getKiller() != null) {
                event.getDrops().clear();
                event.setKeepInventory(true);
                Player damager = event.getEntity().getKiller();
                Player victim = event.getEntity();
                damager.getInventory().clear();
                damager.getInventory().setArmorContents(victim.getInventory().getArmorContents());
                damager.getInventory().setContents(victim.getInventory().getContents());
                victim.getInventory().clear();
                victim.getInventory().setArmorContents(null);
                damager.sendMessage(
                        UHCenario.getInstance().getMessage("scenarios.killswitch.killed").replaceAll("%player%", victim.getName()));
                event.getEntity().getInventory().clear();
                event.getEntity().getInventory().setArmorContents(null);
            }
        }
    }
}