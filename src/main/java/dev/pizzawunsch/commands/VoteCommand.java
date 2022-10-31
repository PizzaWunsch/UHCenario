package dev.pizzawunsch.commands;

import com.google.common.collect.Lists;
import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.AbstractCommand;
import dev.pizzawunsch.utils.inventory.Button;
import dev.pizzawunsch.utils.item.ItemBuilder;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * This class handles the vote command of the uhcenario plugin.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 15.10.2022
 */
public class VoteCommand extends AbstractCommand {

    /**
     * Creates and registers a new abstract command.
     */
    public VoteCommand() {
        super("voting", "vote");
    }

    @Override
    public boolean command(CommandSender commandSender, String[] args) {
        // if the sender of the command is a player.
        if(commandSender instanceof Player) {
            // the player who executed the command.
            Player player = (Player) commandSender;
            // opens the voting inventory to the player.
            List<Button> buttons = Lists.newArrayList();
            // lists all voteable scenarios.
            for(Scenario scenario : Scenario.getScenarios()) {
                if(scenario.isRegistered())
                    if(scenario.isVotable()) {
                        buttons.add(new Button(new ItemBuilder(scenario.getMaterial(), scenario.getSubid()).amount(scenario.getVotes()).name(scenario.getName()).nbtTag("plugin", UHCenario.getInstance().getName()).lore(scenario.getLore()).nbtTag("cancelInteract", true).nbtTag("interactKey", "vote:" + scenario.getKey()).build()));
                    }
            }
            // opens the voting inventory for the player.
            UHCenario.getInstance().open(player, "voting_vote", buttons);

        } else // the console is not a player.
            commandSender.sendMessage(UHCenario.getInstance().getMessage("senderNotPlayer"));
        return false;
    }
}