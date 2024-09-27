package dev.pizzawunsch.scenarios;

import com.google.common.collect.Maps;
import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.tasks.NoCleanTask;
import dev.pizzawunsch.utils.scenario.Executable;
import dev.pizzawunsch.utils.scenario.Scenario;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Map;

public class NoCleanScenario  extends Scenario implements Listener, Executable {

    @Getter
    private static Map<Player, Integer> players = Maps.newHashMap();
    private NoCleanTask noCleanTask = new NoCleanTask();

    public NoCleanScenario() {
        super("noclean");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if(!this.isEnabled())
            return;
        Player killer = event.getEntity().getKiller();
        if(killer != null) {
            players.put(killer, 30);
            killer.sendMessage(UHCenario.getInstance().getMessage("scenarios.noclean.protection_begin"));
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(!this.isEnabled())
            return;
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if(players.containsKey(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if(!this.isEnabled())
            return;
        if(event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if(players.containsKey(player)) {
                players.remove(player);
                player.sendMessage(UHCenario.getInstance().getMessage("scenarios.noclean.protection_ended_cause_damage"));
            }
        }

        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if(players.containsKey(player)) {
                event.setCancelled(true);
                if(event.getDamager() instanceof Player) {
                    event.getDamager().sendMessage(UHCenario.getInstance().getMessage("scenarios.noclean.cant_damage").replaceAll("%player%", player.getName()));
                }
            }
        }
    }

    @Override
    public void onEnable() {
        noCleanTask.run();
    }

    @Override
    public void onDisable() {
        noCleanTask.end();
    }
}