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
 * This class handles the scenarios command that allows you to configure the uhcscenario plugin.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 14.10.2022
 */
public class ScenariosCommand extends AbstractCommand {

    /**
     * Creates and registers a new abstract command.
     */
    public ScenariosCommand() {
        super("scenarios", "scen", "uhcenario");
    }

    @Override
    public boolean command(CommandSender commandSender, String[] args) {
        // if the sender of the command is a player.
        if(commandSender instanceof Player) {
            // the player who executed the command.
            Player player = (Player) commandSender;
            // if the player have the permission to perform the command.
            if(player.hasPermission("uhcenario.admin")) {
                // Opens the admin configuration panel.
                player.openInventory(UHCenario.getInstance().getInventory("admin"));
            } else {
                // Lists the enabled scenarios to the players.
                List<Button> buttons = Lists.newArrayList();
                Scenario.getScenarios().forEach(scenario -> {
                    if(scenario.isRegistered()) {
                        if(scenario.isEnabled())
                            buttons.add(new Button(new ItemBuilder(scenario.getMaterial(), scenario.getSubid()).name(scenario.getName()).nbtTag("plugin", UHCenario.getInstance().getName()).lore(scenario.getLore()).nbtTag("cancelInteract", true).build()));
                    }
                });
                UHCenario.getInstance().open(player, "enabled_scenarios", buttons);
            }
        } else // the console is not a player.
            commandSender.sendMessage(UHCenario.getInstance().getMessage("senderNotPlayer"));
        return false;
    }
}