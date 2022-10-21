package dev.pizzawunsch.listener;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.inventory.Button;
import dev.pizzawunsch.utils.inventory.PagedPane;
import dev.pizzawunsch.utils.item.ItemBuilder;
import dev.pizzawunsch.utils.item.ItemStackEditor;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;
import java.util.Map;

/**
 * This class handles the inventory click listener that will handle every click of a player in this plugin.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 15.10.2022
 */
public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // if the entity who clicked into the inventories is a player.
        if(event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if(event.getInventory() == null) return;
            if(event.getClickedInventory() == null) return;
            if(event.getCurrentItem() == null) return;
            if(!event.getCurrentItem().hasItemMeta()) return;;

            ItemStackEditor editor = new ItemStackEditor(event.getCurrentItem());
            // if interactions with this item should be canceled
            if (editor.getNBTTagCompound().getBoolean("cancelInteract"))
                // canceling interaction with this select item
                event.setCancelled(true);

            String interactKey = editor.getNBTTagCompound().getString("interactKey");
            if(interactKey != null) {
                // if the key is closed so they closes the inventory of the player.
                if(interactKey.equals("close"))
                    player.closeInventory();
                // if a new inventory should opened.
                if(interactKey.startsWith("open:")) {
                    String[] keys = interactKey.split(":");
                    String inventoryKey = keys[1];
                    if(UHCenario.getInstance().isPaged(inventoryKey)) {
                        List<Button> buttons = Lists.newArrayList();
                        if(inventoryKey.equals("voting_selection")) {
                            Scenario.getScenarios().forEach(scenario -> {
                                if(scenario.isRegistered()) {
                                    if(!scenario.isEnabled()) {
                                        if(scenario.isVotable())
                                            buttons.add(new Button(new ItemBuilder(scenario.getMaterial(), scenario.getSubid()).glow().name(scenario.getName()).lore(scenario.getLore()).nbtTag("cancelInteract", true).nbtTag("interactKey", "voting:" + scenario.getKey()).build()));
                                        else
                                            buttons.add(new Button(new ItemBuilder(scenario.getMaterial(), scenario.getSubid()).name(scenario.getName()).lore(scenario.getLore()).nbtTag("cancelInteract", true).nbtTag("interactKey", "voting:" + scenario.getKey()).build()));
                                    }
                                }
                            });
                        }
                        if(inventoryKey.equals("scenarios")) {
                            Scenario.getScenarios().forEach(scenario -> {
                                if(scenario.isRegistered()) {
                                    if(scenario.isEnabled())
                                        buttons.add(new Button(new ItemBuilder(scenario.getMaterial(), scenario.getSubid()).glow().name(scenario.getName()).lore(scenario.getLore()).nbtTag("cancelInteract", true).nbtTag("interactKey", "scenario:" + scenario.getKey()).build()));
                                    else
                                        buttons.add(new Button(new ItemBuilder(scenario.getMaterial(), scenario.getSubid()).name(scenario.getName()).lore(scenario.getLore()).nbtTag("cancelInteract", true).nbtTag("interactKey", "scenario:" + scenario.getKey()).build()));
                                }
                            });
                        }
                        UHCenario.getInstance().open(player, inventoryKey, buttons);
                    } else
                        player.openInventory(UHCenario.getInstance().getInventory(inventoryKey));
                }

                if(interactKey.startsWith("voting:")) {
                    String[] keys = interactKey.split(":");
                    String scenarioKey = keys[1];
                    // if the result of the voting should get.
                    if(scenarioKey.equals("result")) {
                        Scenario scenario = this.getVotingResult();
                        // if the scenario is not null
                        if(scenario != null) {
                            // enables the scenario that won.
                            player.closeInventory();
                            scenario.setEnabled(true);
                            Bukkit.broadcastMessage(UHCenario.getInstance().getMessage("voting.result").replaceAll("%scenario%", scenario.getName()).replaceAll("%votes%" ,scenario.getVotes() + ""));
                            scenario.setVotable(false);
                            scenario.setVotes(0);
                        } else // not result can be found.
                            player.sendMessage(UHCenario.getInstance().getMessage("voting.noresult"));
                        return;
                    }
                    Scenario scenario = Scenario.getScenario(scenarioKey);
                    // if the scenario is not null
                    if(scenario != null) {
                        // if the scenario is votable or not.
                        if(scenario.isVotable()) {
                            scenario.setVotable(false);
                            scenario.setVotes(0);
                            Bukkit.broadcastMessage(UHCenario.getInstance().getMessage("voting.removed").replaceAll("%scenario%", scenario.getName()));
                            event.getInventory().setItem(event.getSlot(), new ItemBuilder(event.getCurrentItem()).unglow().build());
                        } else {
                            scenario.setVotable(true);
                            scenario.setVotes(0);
                            Bukkit.broadcastMessage(UHCenario.getInstance().getMessage("voting.added").replaceAll("%scenario%", scenario.getName()));
                            event.getInventory().setItem(event.getSlot(), new ItemBuilder(event.getCurrentItem()).glow().build());
                        }
                    }
                }
                // Executes a command when anyone clicking on the item.
                if(interactKey.startsWith("command:")) {
                    String[] keys = interactKey.split(":");
                    String commandKey = keys[1];
                    String replaced = commandKey
                            .replaceAll("%player%", player.getName()
                                    .replaceAll("%x%", player.getLocation().getX() + "")
                                    .replaceAll("%y%", player.getLocation().getY() + "")
                                    .replaceAll("%z%", player.getLocation().getZ() + "")
                                    .replaceAll("%world%", player.getWorld().getName()));
                    player.performCommand(replaced);
                }
                // Enables or disables a scenario when clicking this item.
                if(interactKey.startsWith("scenario:")) {
                    String[] keys = interactKey.split(":");
                    String scenarioKey = keys[1];
                    Scenario scenario = Scenario.getScenario(scenarioKey);
                    if(scenario != null) {
                        if (scenario.isEnabled()) {
                            scenario.setEnabled(false);
                            event.getInventory().setItem(event.getSlot(), new ItemBuilder(event.getCurrentItem()).unglow().build());
                        } else {
                            scenario.setEnabled(true);
                            event.getInventory().setItem(event.getSlot(), new ItemBuilder(event.getCurrentItem()).glow().build());
                        }
                        player.updateInventory();
                    }
                }
                if(interactKey.startsWith("paged:")) {
                    // the paged pane that may be opened.
                    PagedPane pagedPane = PagedPane.getPane(player);
                    if(pagedPane != null) {
                        String[] keys = interactKey.split(":");
                        String inventoryKey = keys[1];
                        if(inventoryKey.equalsIgnoreCase("back") && pagedPane.getCurrentIndex() > 1)
                            pagedPane.selectPage(pagedPane.getCurrentIndex() - 1);
                        else if (inventoryKey.equalsIgnoreCase("next") && pagedPane.getCurrentPage() < pagedPane.getPageAmount())
                            pagedPane.selectPage(pagedPane.getCurrentPage());
                        else if (inventoryKey.equalsIgnoreCase("first"))
                            pagedPane.selectPage(0);
                    }
                }
            }

         }
    }

    /**
     * Return the scenario with the most votes.
     * @return the scenario with most votes.
     */
    public Scenario getVotingResult() {
        Map<Scenario, Integer> votes = Maps.newHashMap();
        // loops all scenarios
        for (Scenario scenario : Scenario.getScenarios())
            if(scenario.isRegistered())
                if(scenario.isVotable())
                    votes.put(scenario, scenario.getVotes());

        if(votes.isEmpty())
            return null;
        return votes.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get()
                .getKey();
    }
}