package dev.pizzawunsch.tasks;

import com.google.common.collect.Lists;
import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.scenarios.NoCleanScenario;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.entity.Player;

import java.util.List;

public class NoCleanTask extends Task{

    /**
     * The task to give each player on the server the night vision effect.
     */
    public NoCleanTask() {
        super(true, 20, 20);
    }

    /**
     * Gives every player who is online the night vision effect.
     */
    @Override
    public void execute() {
        // the cat eyes scenario.
        Scenario scenario = Scenario.getScenario("noclean");
        if(scenario == null)
            return;
        if(!scenario.isRegistered())
            return;
        if(!scenario.isEnabled())
            return;
        List<Player> remove = Lists.newArrayList();
        // loops all player there online
        NoCleanScenario.getPlayers().forEach((player, integer) -> {
            NoCleanScenario.getPlayers().replace(player, integer-1);

            if(integer == 0) {
                remove.add(player);
            } else {
                player.sendMessage(UHCenario.getInstance().getMessage("scenarios.noclean.protection_timer").replaceAll("%seconds%", integer + ""));
            }
        });

        for(Player player : remove) {
            NoCleanScenario.getPlayers().remove(player);
            player.sendMessage(UHCenario.getInstance().getMessage("scenarios.noclean.protection_ended"));
        } // protection_timer protection_ended
    }
}