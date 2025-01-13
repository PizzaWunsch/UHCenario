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

        ItemStack upgradedItem = getNextArmor(player, item, index);

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

    private ItemStack getNextArmor(Player player, ItemStack item,int  i) {
        if (item == null || item.getType() == Material.AIR) {
            switch (i) {
                case 0:
                    return new ItemStack(Material.LEATHER_HELMET);
                case 1:
                    return new ItemStack(Material.LEATHER_CHESTPLATE);
                case 2:
                    return new ItemStack(Material.LEATHER_LEGGINGS);
                case 3:
                    return new ItemStack(Material.LEATHER_BOOTS);
            }

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
                return upgradeEnchantment(player, item);
        }

        return (nextMaterial != null) ? new ItemStack(nextMaterial) : item;
    }

    private Material upgradeLeather(Material type) {
        switch (type) {
            case LEATHER_CHESTPLATE:
                return Material.GOLD_CHESTPLATE;
            case LEATHER_LEGGINGS:
                return Material.GOLD_LEGGINGS;
            case LEATHER_HELMET:
                return Material.GOLD_HELMET;
            case LEATHER_BOOTS:
                return Material.GOLD_BOOTS;
            default:
                return type;
        }
    }

    private Material upgradeGold(Material type) {
        switch (type) {
            case GOLD_CHESTPLATE:
                return Material.CHAINMAIL_CHESTPLATE;
            case GOLD_LEGGINGS:
                return Material.CHAINMAIL_LEGGINGS;
            case GOLD_HELMET:
                return Material.CHAINMAIL_HELMET;
            case GOLD_BOOTS:
                return Material.CHAINMAIL_BOOTS;
            default:
                return type;
        }
    }

    private Material upgradeChainmail(Material type) {
        switch (type) {
            case CHAINMAIL_CHESTPLATE:
                return Material.IRON_CHESTPLATE;
            case CHAINMAIL_LEGGINGS:
                return Material.IRON_LEGGINGS;
            case CHAINMAIL_HELMET:
                return Material.IRON_HELMET;
            case CHAINMAIL_BOOTS:
                return Material.IRON_BOOTS;
            default:
                return type;
        }
    }

    private Material upgradeIron(Material type) {
        switch (type) {
            case IRON_CHESTPLATE:
                return Material.DIAMOND_CHESTPLATE;
            case IRON_LEGGINGS:
                return Material.DIAMOND_LEGGINGS;
            case IRON_HELMET:
                return Material.DIAMOND_HELMET;
            case IRON_BOOTS:
                return Material.DIAMOND_BOOTS;
            default:
                return type;
        }
    }


    private ItemStack upgradeEnchantment(Player player, ItemStack item) {
        if (item.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {
            int currentLevel = item.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
            if (currentLevel < 4) {
                item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, currentLevel + 1);
            } else {
                int check = 0;
                for (ItemStack armorContent : player.getInventory().getArmorContents()) {
                    if(armorContent.getEnchantments().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL))
                        if(armorContent.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) ==4)
                            check++;
                }
                System.out.println(check);
                if(check < 4)
                    upgradeRandomArmorPiece(player);
            }
        } else {
            item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        }
        return item;
    }
}

