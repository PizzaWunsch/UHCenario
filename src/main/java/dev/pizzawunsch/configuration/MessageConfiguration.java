package dev.pizzawunsch.configuration;

import org.bukkit.ChatColor;

/**
 * This class handles
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 14.10.2022
 */
public class MessageConfiguration extends AbstractConfiguration {
    /**
     * This is the constructor of the abstract configuration file.
     * It will initialize all corresponding variables.
     */
    public MessageConfiguration() {
        super("plugins/UHCenario", "messages.yml");
    }

    /**
     * This method will return the message that you want to search with the key.
     * @param key the key of the message.
     * @return the message by the key.
     */
    public String getMessage(String key) {
        return ChatColor.translateAlternateColorCodes('&', this.getConfig().getString(key).replaceAll("%prefix%", this.getConfig().getString("prefix")).replaceAll("\n", System.lineSeparator()));
    }
}