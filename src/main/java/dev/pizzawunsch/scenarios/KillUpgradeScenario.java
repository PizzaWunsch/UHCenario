package dev.pizzawunsch.scenarios;

import dev.pizzawunsch.UHCenario;
import dev.pizzawunsch.utils.scenario.Scenario;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KillUpgradeScenario extends Scenario implements Listener {

    public KillUpgradeScenario() {
        super("killupgrade");
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        if(!this.isEnabled())
            return;

        Player killed = event.getEntity();
        Player killer = killed.getKiller();

        if (killer != null && killer instanceof Player) {
            upgradeRandomArmorPiece(killer);
        }
    }

    private void upgradeRandomArmorPiece(Player player) {
        List<ItemStack> armor = new ArrayList<>();

        armor.add(player.getInventory().getHelmet());
        armor.add(player.getInventory().getChestplate());
        armor.add(player.getInventory().getLeggings());
        armor.add(player.getInventory().getBoots());

        Random random = new Random();
        int index = random.nextInt(armor.size());
        ItemStack item = armor.get(index);

        ItemStack upgradedItem = getNextArmor(item);

        switch (index) {
            case 0:
                player.getInventory().setHelmet(upgradedItem);
                break;
            case 1:
                player.getInventory().setChestplate(upgradedItem);
                break;
            case 2:
                player.getInventory().setLeggings(upgradedItem);
                break;
            case 3:
                player.getInventory().setBoots(upgradedItem);
                break;
        }
    }

    private ItemStack getNextArmor(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return new ItemStack(Material.LEATHER_CHESTPLATE);
        }

        Material nextMaterial = null;
        switch (item.getType()) {
            case LEATHER_CHESTPLATE:
            case LEATHER_LEGGINGS:
            case LEATHER_HELMET:
            case LEATHER_BOOTS:
                nextMaterial = upgradeLeather(item.getType());
                break;
            case GOLD_CHESTPLATE:
            case GOLD_LEGGINGS:
            case GOLD_HELMET:
            case GOLD_BOOTS:
                nextMaterial = upgradeGold(item.getType());
                break;
            case CHAINMAIL_CHESTPLATE:
            case CHAINMAIL_LEGGINGS:
            case CHAINMAIL_HELMET:
            case CHAINMAIL_BOOTS:
                nextMaterial = upgradeChainmail(item.getType());
                break;
            case IRON_CHESTPLATE:
            case IRON_LEGGINGS:
            case IRON_HELMET:
            case IRON_BOOTS:
                nextMaterial = upgradeIron(item.getType());
                break;
            case DIAMOND_CHESTPLATE:
            case DIAMOND_LEGGINGS:
            case DIAMOND_HELMET:
            case DIAMOND_BOOTS:
                return upgradeEnchantment(item);
        }

        return (nextMaterial != null) ? new ItemStack(nextMaterial) : item;
    }

    private Material upgradeLeather(Material type) {
        return switch (type) {
            case LEATHER_CHESTPLATE -> Material.GOLD_CHESTPLATE;
            case LEATHER_LEGGINGS -> Material.GOLD_LEGGINGS;
            case LEATHER_HELMET -> Material.GOLD_HELMET;
            case LEATHER_BOOTS -> Material.GOLD_BOOTS;
            default -> type;
        };
    }

    private Material upgradeGold(Material type) {
        return switch (type) {
            case GOLD_CHESTPLATE -> Material.CHAINMAIL_CHESTPLATE;
            case GOLD_LEGGINGS -> Material.CHAINMAIL_LEGGINGS;
            case GOLD_HELMET -> Material.CHAINMAIL_HELMET;
            case GOLD_BOOTS -> Material.CHAINMAIL_BOOTS;
            default -> type;
        };
    }

    private Material upgradeChainmail(Material type) {
        return switch (type) {
            case CHAINMAIL_CHESTPLATE -> Material.IRON_CHESTPLATE;
            case CHAINMAIL_LEGGINGS -> Material.IRON_LEGGINGS;
            case CHAINMAIL_HELMET -> Material.IRON_HELMET;
            case CHAINMAIL_BOOTS -> Material.IRON_BOOTS;
            default -> type;
        };
    }

    private Material upgradeIron(Material type) {
        return switch (type) {
            case IRON_CHESTPLATE -> Material.DIAMOND_CHESTPLATE;
            case IRON_LEGGINGS -> Material.DIAMOND_LEGGINGS;
            case IRON_HELMET -> Material.DIAMOND_HELMET;
            case IRON_BOOTS -> Material.DIAMOND_BOOTS;
            default -> type;
        };
    }

    private ItemStack upgradeEnchantment(ItemStack item) {
        if (item.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {
            int currentLevel = item.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
            if (currentLevel < 4) {
                item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, currentLevel + 1);
            }
        } else {
            item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        }
        return item;
    }
}

