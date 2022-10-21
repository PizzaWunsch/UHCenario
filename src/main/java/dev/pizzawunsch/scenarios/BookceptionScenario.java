package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.item.ItemBuilder;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * This class handles the bookception scenario.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 21.10.2022
 */
public class BookceptionScenario extends Scenario implements Listener {
    /**
     * Adding the scenario into the local storage list.
     */
    public BookceptionScenario() {
        super("bookception");
    }

    /**
     * Adds to players drops item a random enchanted book
     *
     * @param event the corresponding player death event
     */
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        // if scenario is enabled
        if (this.isEnabled()) {
            // gets a random enchantment to drop
            Enchantment enchantment = Enchantment.values()[UHCenario.getInstance().getRandomNumberInRange(0,
                    Enchantment.values().length - 1)];
            // add the new enchanted book to players drop items
            event.getDrops()
                    .add(new ItemBuilder(Material.ENCHANTED_BOOK)
                            .enchantment(enchantment, UHCenario.getInstance()
                                    .getRandomNumberInRange(enchantment.getStartLevel(), enchantment.getMaxLevel()))
                            .build());
        }
    }

}