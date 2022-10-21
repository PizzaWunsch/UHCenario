package dev.pizzawunsch.commands;

import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.AbstractCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



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

            }
        } else // the console is not a player.
            commandSender.sendMessage(UHCenario.getInstance().getMessage("senderNotPlayer"));
        return false;
    }
}