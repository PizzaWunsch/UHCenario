package dev.pizzawunsch.configuration;

import com.google.common.collect.Lists;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.List;

/**
 * This class handles the scenarios configuration of the uhcenario plugin.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 15.10.2022
 */
public class ScenarioConfiguration extends AbstractConfiguration {

    /**
     * This is the constructor of the abstract configuration file.
     * It will initialize all corresponding variables.
     */
    public ScenarioConfiguration() {
        super("plugins/UHCenario", "scenarios.yml");
    }

    /**
     * Reads all scenarios from the configuration file.
     * @return the configuration class of the scenarios file.
     */
    public ScenarioConfiguration read() {
        // Loops all configuration from the scenarios yaml file.
        for(String key : this.getConfig().getConfigurationSection("scenarios").getKeys(false)) {
           register(key);
        }
        return this;
    }

    public void register(String key) {
        // Creates a fallback scenario into the configuration file that avoids issues on loading the scenarios.
        while(this.getConfig().getConfigurationSection("scenarios." + key) == null) {
            try {
                this.getConfig().set("scenarios." + key + ".material", Material.BARRIER.name());
                this.getConfig().set("scenarios." + key + ".name", "&cFallback-Name");
                this.getConfig().set("scenarios." + key + ".id", 0);
                this.getConfig().set("scenarios." + key + ".enabled", false);
                this.getConfig().set("scenarios." + key + ".votable", false);
                List<String> lore = Lists.newArrayList();
                lore.add("&cStandart-Lore");
                this.getConfig().set("scenarios." + key + ".lore", lore);
                this.getConfig().save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Gettong all relevant variables from the scenarios configuration file.
        Material material = Material.getMaterial(this.getConfig().getString("scenarios." + key + ".material"));
        String name = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("scenarios." + key + ".name"));
        byte subid = Byte.parseByte(this.getConfig().getString("scenarios." + key + ".id"));
        boolean enabled = this.getConfig().getBoolean("scenarios." + key + ".enabled");
        boolean votable = this.getConfig().getBoolean("scenarios." + key + ".votable");
        List<String> lore = Lists.newArrayList();
        for(String loreString : this.getConfig().getStringList("scenarios." + key + ".lore"))
            lore.add(ChatColor.translateAlternateColorCodes('&', loreString));
        // Setting up all relevant variables of the scenario plugin.
        Scenario scenario = Scenario.getUnregisteredScenario(key);
        if(scenario != null) {
            scenario.setMaterial(material);
            scenario.setName(name);
            scenario.setSubid(subid);
            scenario.setEnabled(enabled);
            scenario.setLore(lore);
            scenario.setVotable(votable);
            scenario.setRegistered(true);
        }
    }
}