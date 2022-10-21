package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.item.ItemBuilder;
import dev.pizzawunsch.utils.item.ItemStackEditor;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * This class handles the golden heads scenario.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 16.10.2022
 */
public class GoldenHeadsScenario extends Scenario implements Listener {
    /**
     * Adding the scenario into the local storage list.
     */
    public GoldenHeadsScenario() {
        super("goldenheads");
    }

    /**
     * Handels dropping a golden head while enabled
     *
     * @param event the corresponding player death event
     */
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        // if scenario is enabled
        if (this.isEnabled()) {
            // adds to players drops a golden head and let them drop.
            event.getDrops().add(new ItemBuilder(Material.GOLDEN_APPLE).glow().name(UHCenario.getInstance().getMessage("scenarios.goldenheads.name"))
                    .nbtTag("interactKey", "goldenhead").build());
        }
    }

    /**
     * Handels the player consume a item.
     *
     * @param event the corresponding player item consume event
     */
    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        // if scenario is enabled
        if (this.isEnabled()) {
            // the player who consume a item
            Player player = event.getPlayer();
            // receiving nbt-tags from current clicked item
            ItemStackEditor editor = new ItemStackEditor(event.getItem());
            // if interact key is not null
            if (editor.getNBTTagCompound().getString("interactKey") != null) {
                // if interact key is the key for the golden heads
                if (editor.getNBTTagCompound().getString("interactKey").equals("goldenhead")) {
                    // adds potion effects to the player
                    player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 120, 1));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 20, 2));
                }
            }
        }
    }
}