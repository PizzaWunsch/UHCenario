package dev.pizzawunsch.configuration;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.pizzawunsch.utils.inventory.Button;
import dev.pizzawunsch.utils.inventory.PagedPane;
import dev.pizzawunsch.utils.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.Map;

/**
 * This class handles the configurable inventories of the scenario plugin.
 *
 * @author Lucas | PizzaWunsch
 * @version 1.0
 * @since 14.10.2022
 */
public class InventoryConfiguration extends AbstractConfiguration {

    private final Map<String, Inventory> cache;

    /**
     * This is the constructor of the abstract configuration file.
     * It will initialize all corresponding variables.
     */
    public InventoryConfiguration() {
        super("plugins/UHCenario", "inventories.yml");
        this.cache = Maps.newHashMap();
    }

    /**
     * Opens a paged inventory to the player.
     * @param player the player who wants to open the paged inventory.
     * @param key the key of the paged inventory.
     * @param buttons the buttons that should added.
     */
    public void open(Player player, String key, List<Button> buttons) {
        int slots = this.getConfig().getInt("inventories." + key + ".rows");
        int listRows = this.getConfig().getInt("inventories." + key + ".listRows");

        String name = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("inventories." + key + ".name"));
        PagedPane pagedPane = new PagedPane(listRows, slots, name, player);
        pagedPane.setCache(this.getInventory(key));
        buttons.forEach(pagedPane::addButton);
        pagedPane.open(player);
    }

    /**
     * Checks if the inventory is paged.
     * @param key the key of the inventory that should checked.
     * @return if the inventory is paged.
     */
    public boolean isPaged(String key) {
        return this.getConfig().getBoolean("inventories." + key + ".paged");
    }

    /**
     * This method will return you the configured inventory by the given key.
     * @param key the key of the inventory in the configuration file.
     * @return the inventory.
     */
    public Inventory getInventory(String key) {
        if(cache.containsKey(key))
            return cache.get(key);

        int slots = this.getConfig().getInt("inventories." + key + ".rows");
        String name = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("inventories." + key + ".name"));
        // builds the inventory from the configuration file.
        Inventory inventory = Bukkit.createInventory(null, slots*9, name);

        // All variables to build the item.
        Material material = Material.getMaterial(this.getConfig().getString("inventories." + key + ".items.fill.material"));

        if(material != null && !material.equals(Material.AIR)) {
            byte id = Byte.parseByte(this.getConfig().getString("inventories." + key + ".items.fill.id"));
            String itemName = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("inventories." + key + ".items.fill.name"));
            List<String> lore = Lists.newArrayList();
            for(String loreString : this.getConfig().getStringList("inventories." + key + ".items.fill.lore"))
                lore.add(ChatColor.translateAlternateColorCodes('&', loreString));
            for (int i = 0; i < inventory.getSize() ; i++)
                inventory.setItem(i, new ItemBuilder(material, id).name(itemName).lore(lore).nbtTag("cancelInteract", true).build());
        }
        for (String itemKey : this.getConfig().getConfigurationSection("inventories." + key + ".items").getKeys(false)) {
            if(!itemKey.equals("fill")) {
                int slot = Integer.parseInt(itemKey);
                material = Material.getMaterial(this.getConfig().getString("inventories." + key + ".items." + slot + ".material"));
                if(!material.equals(Material.AIR)) {
                    byte id = Byte.parseByte(this.getConfig().getString("inventories." + key + ".items." + slot + ".id"));
                    String interactKey = this.getConfig().getString("inventories." + key + ".items." + slot + ".key");
                    String itemName = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("inventories." + key + ".items." + slot + ".name"));
                    List<String> lore = Lists.newArrayList();
                    for(String loreString : this.getConfig().getStringList("inventories." + key + ".items." + slot + ".lore"))
                        lore.add(ChatColor.translateAlternateColorCodes('&', loreString));
                    inventory.setItem(slot, new ItemBuilder(material, id).name(itemName).lore(lore).nbtTag("cancelInteract", true).nbtTag("interactKey", interactKey).build());
                }
            }
        }
        cache.put(key, inventory);
        return inventory;
    }

}