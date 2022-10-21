package dev.pizzawunsch.utils;

import java.lang.reflect.Field;

import dev.pizzawunsch.UHCenario;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

/**
 * This class handles the abstract commands which directly registers a new
 * command to the server's command manager.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 15.05.2021
 */
public abstract class AbstractCommand extends BukkitCommand {

    /**
     * Creates and registers a new abstract command
     *
     * @param commandName the name of the command
     * @param aliases     the command's aliases
     */
    public AbstractCommand(String commandName, String... aliases) {
        super(commandName);
        try {
            // saving the aliases
            for (String string : aliases) {
                this.getAliases().add(string);
            }
            // registering this command to the local command map
            getCommandMap().register(UHCenario
                    .getInstance().getName(), this);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace(); // in case something goes wrong
        }
    }

    /**
     * Handles the execution of the command.
     */
    @Override
    public boolean execute(CommandSender commandSender, String label, String[] args) {
        return command(commandSender, args);
    }

    public abstract boolean command(CommandSender commandSender, String[] args);

    /**
     * @return the command map
     */
    public CommandMap getCommandMap()
            throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        Field commandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        commandMap.setAccessible(true);
        return (CommandMap) commandMap.get(Bukkit.getServer());
    }
}
