package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

/**
 * This class handles the UHC Scenario of the uhcenario plugin.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 17.10.2022
 */
public class UltraHardcoreScenario extends Scenario implements Listener {

    /**
     * Adding the scenario into the local storage list.
     */
    public UltraHardcoreScenario() {
        super("uhc");
    }

    /**
     * Cancel the natural regeneration
     *
     * @param event the corresponding entity regain health event.
     */
    @EventHandler
    public void onRegainHealth(EntityRegainHealthEvent event) {
        if(UHCenario.getInstance().getMainConfiguration().getConfig().getStringList("disabled_worlds").contains(event.getEntity().getWorld().getName())) {
            return;
        }
        // if scenario is enabled
        if (this.isEnabled()) {
            // if regain reason magic and magic regen and regen
            if (!event.getRegainReason().equals(EntityRegainHealthEvent.RegainReason.MAGIC)
                    && !event.getRegainReason().equals(EntityRegainHealthEvent.RegainReason.MAGIC_REGEN)
                    && !event.getRegainReason().equals(EntityRegainHealthEvent.RegainReason.REGEN))
                event.setCancelled(true);
        }
    }
}