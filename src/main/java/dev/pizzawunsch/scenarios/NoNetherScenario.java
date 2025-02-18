package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.world.PortalCreateEvent;

/**
 * This class handles
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 21.10.2022
 */
public class NoNetherScenario extends Scenario implements Listener {
    /**
     * Adding the scenario into the local storage list.
     */
    public NoNetherScenario() {
        super("nonether");
    }


    /**
     * Disables creating a portal by a player
     *
     * @param event the corresponding portal create event
     */
    @EventHandler
    public void onPortalCreate(PortalCreateEvent event) {

        // if the scenario is enabled
        if (this.isEnabled())
            // cancels creating a portal
            event.setCancelled(true);
    }

    /**
     * Disables entering a portal by a player
     *
     * @param event the corresponding player portal event
     */
    @EventHandler
    public void onPortalEnter(PlayerPortalEvent event) {
        if(UHCenario.getInstance().getMainConfiguration().getConfig().getStringList("disabled_worlds").contains(event.getPlayer().getWorld().getName())) {
            return;
        }
        // if the scenario is enabled
        if (this.isEnabled())
            // cancels entering a portal
            event.setCancelled(true);
    }
}