package dev.pizzawunsch;

import com.google.common.reflect.ClassPath;
import dev.pizzawunsch.configuration.InventoryConfiguration;
import dev.pizzawunsch.configuration.MainConfiguration;
import dev.pizzawunsch.configuration.MessageConfiguration;
import dev.pizzawunsch.configuration.ScenarioConfiguration;
import dev.pizzawunsch.utils.inventory.Button;
import dev.pizzawunsch.utils.scenario.Scenario;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Random;

/**
 * This class handles the main class of the uhcenario plugin which handles all relevant variables and methods of this plugin.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 13.10.2022
 */
@Getter
public class UHCenario extends JavaPlugin {

    // Instance variables.
    @Getter
    private static UHCenario instance;
    private MainConfiguration mainConfiguration;
    private InventoryConfiguration inventoryConfiguration;
    private MessageConfiguration messageConfiguration;
    private ScenarioConfiguration scenarioConfiguration;
    private Random random;

    /**
     * Initializes all instance variables of the uhcenario plugin and runs all tasks if needed.
     */
    public void onEnable() {
        // Initializes the instance variables of the uhcenario plugin.
        instance = this;
        this.random = new Random();
        this.mainConfiguration = new MainConfiguration();
        this.inventoryConfiguration = new InventoryConfiguration();
        this.messageConfiguration = new MessageConfiguration();

        this.registerScenarios("dev.pizzawunsch.scenarios");
        this.scenarioConfiguration = new ScenarioConfiguration().read();
        // Registering all commands and listeners.
        this.register("dev.pizzawunsch.commands", "dev.pizzawunsch.listener");
    }

    /**
     * Registering a new scenario into the uhcenario plugin.
     * @param scenario the scenario that should be added.
     */
    public void registerScenario(Scenario scenario) {
        if (Listener.class.isAssignableFrom(scenario.getClass()))
            Bukkit.getPluginManager().registerEvents((Listener) scenario, UHCenario.getInstance());
        UHCenario.getInstance().getScenarioConfiguration().register(scenario.getKey());
    }

    /**
     * Returns you the configures inventory by the given key.
     * @param key the key of the searched inventory.
     * @return the inventory by the key.
     */
    public Inventory getInventory(String key) {
        return this.inventoryConfiguration.getInventory(key);
    }

    /**
     * Opens a paged inventory to the player.
     * @param player the player who wants to open the paged inventory.
     * @param key the key of the paged inventory.
     * @param buttons the buttons that should added.
     */
    public void open(Player player, String key, List<Button> buttons) {
        this.inventoryConfiguration.open(player, key, buttons);
    }

    /**
     * Checks if the inventory is paged.
     * @param key the key of the inventory that should checked.
     * @return if the inventory is paged.
     */
    public boolean isPaged(String key) {
        return this.inventoryConfiguration.isPaged(key);
    }

    /**
     * @param min minimal number
     * @param max maximal number
     * @return a number with a random number between the two given numbers
     */
    public int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return random.nextInt((max - min) + 1) + min;
    }

    /**
     * This method will return the message that you want to search with the key.
     * @param key the key of the message.
     * @return the message by the key.
     */
    public String getMessage(String key) { return this.messageConfiguration.getMessage(key); }

    /**
     * Registering all scenarios of the uhcenario plugin.
     * @param scenariosPackageName the package of the scenarios.
     */
    void registerScenarios(String scenariosPackageName) {
        try {
            for (ClassPath.ClassInfo classInfo : ClassPath.from(getClassLoader())
                    .getTopLevelClasses(scenariosPackageName)) {
                Class<?> currentClass = Class.forName(classInfo.getName());
                if (Listener.class.isAssignableFrom(currentClass))
                    Bukkit.getPluginManager().registerEvents((Listener) currentClass.newInstance(), this);
                else
                    currentClass.newInstance();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            getServer().getConsoleSender().sendMessage("[" + this.getName() + "] Could not load the UHCenario plugin due to a error occurred while registering the scenarios.");
        }
    }

    /**
     * Registers a command and listener package.
     *
     * @param commandPackageName      the name of the command package
     * @param listenerPackageNameList the name of the listener package
     */
    void register(String commandPackageName, String... listenerPackageNameList) {
        try {
            for (String listenerPackageName : listenerPackageNameList) {
                for (ClassPath.ClassInfo classInfo : ClassPath.from(getClassLoader())
                        .getTopLevelClasses(listenerPackageName)) {
                    Class<?> currentClass = Class.forName(classInfo.getName());
                    if (Listener.class.isAssignableFrom(currentClass))
                        Bukkit.getPluginManager().registerEvents((Listener) currentClass.newInstance(), this);
                }
            }
            for (ClassPath.ClassInfo classInfo : ClassPath.from(getClassLoader())
                    .getTopLevelClasses(commandPackageName)) {
                Class<?> currentClass = Class.forName(classInfo.getName());
                currentClass.newInstance();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            getServer().getConsoleSender().sendMessage("[" + this.getName() + "] Could not load the UHCenario plugin due to a error occurred while registering the commands and listener.");
        }
    }
}