package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

/**
 * This class handles the bow switch scenario.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 25.10.2022
 */
public class BowSwitchScenario extends Scenario implements Listener {

    /**
     * Adding the scenario into the local storage list.
     */
    public BowSwitchScenario() {
        super("bowswitch");
    }

    @EventHandler
    public void onBowSwitch(ProjectileHitEvent event) {
        if (this.isEnabled()) {
            // if player got hit by an arrow
            if (event.getEntityType() == EntityType.ARROW) {
                try {
                    final Player shooter = (Player) event.getEntity().getShooter();

                    if(UHCenario.getInstance().getMainConfiguration().getConfig().getStringList("disabled_worlds").contains(shooter.getWorld().getName())) {
                        return;
                    }

                    for (Entity entities : event.getEntity().getNearbyEntities(2, 2, 2)) {
                        // checking nearby players that got hit by the arrow
                        if (entities instanceof Player) {
                            // switching locations with hit player
                            final Player damaged = (Player) entities;
                            if (shooter.canSee(damaged)) {
                                final Location temp = shooter.getLocation();
                                shooter.teleport(damaged);
                                shooter.playSound(shooter.getLocation(), Sound.ENDERMAN_TELEPORT, 5, 1);
                                damaged.teleport(temp);
                                damaged.playSound(damaged.getLocation(), Sound.ENDERMAN_TELEPORT, 5, 1);
                            }
                        }
                    }
                } catch (Exception e) { // just in case anything goes wrong
                }
            }
        }
    }

}