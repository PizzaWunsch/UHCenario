package dev.pizzawunsch.utils.item;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Dye;
import org.bukkit.material.MaterialData;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagByte;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagDouble;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagString;

/**
 * The ItemStackBuilder is a class to edit an {@link ItemStack} fastly.
 *
 * @version 1.0
 */
public class ItemBuilder {

    /**
     * Item which get edited
     */
    private ItemStack item;
    /**
     * The item meta of the {@link #item}
     */
    private ItemMeta meta;
    /**
     * List of NBTTags which get set by the [@link {@link #build()}} method
     */
    private HashMap<String, NBTBase> nbtTags = new HashMap<>();

    /**
     * Creates a new item with the given material
     *
     * @param material Material of the new item
     */
    public ItemBuilder(final Material material) {
        this(new ItemStack(material));
    }

    /**
     * Creates a new item with the given material and data
     *
     * @param material Material of the new material
     * @param data     Data of the new item
     */
    public ItemBuilder(final Material material, byte data) {
        this(new ItemStack(material, 1, data));
    }

    /**
     * Creates a new item with the given color Material of the item is
     * {@link Material#INK_SACK}
     *
     * @param dyeColor Color of the dye
     */
    public ItemBuilder(final DyeColor dyeColor) {
        this(new Dye(dyeColor).toItemStack());
        this.amount(1);
    }

    /**
     * Edits the given item
     *
     * @param item ItemStack which get edits
     */
    public ItemBuilder(final ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
    }

    /**
     * Sets the amount of the item
     *
     * @param amount new amount of the item
     * @return current instance
     */
    public ItemBuilder amount(final int amount) {
        item.setAmount(amount);
        return this;
    }

    /**
     * Sets the displayed name of the item
     *
     * @param name new name
     * @return current instance
     */
    public ItemBuilder name(final String name) {
        meta.setDisplayName(name);
        return this;
    }

    /**
     * Adds lines to the lore of the item
     *
     * @param lines lines which should be added
     * @return current instance
     */
    public ItemBuilder lore(final List<String> lines) {
        return this.lore(lines.toArray(new String[lines.size()]));
    }

    /**
     * Adds lines to the lore of the item
     *
     * @param lines lines which should be added
     * @return current instance
     */
    public ItemBuilder lore(final String... lines) {
        final List<String> lore = meta.getLore() == null ? new ArrayList<>() : meta.getLore();
        Arrays.stream(lines).forEach(lore::add);
        meta.setLore(lore);
        return this;
    }

    /**
     * Adds lines to the lore of the item
     *
     * @param lines lines which should be added
     * @return current instance
     */
    public ItemBuilder lore(final String line) {
        List<String> lore = Lists.newArrayList();
        final String description = line;
        final String[] replaced = description.split("/l");
        for (String lines : replaced) {
            lore.add(lines);
        }
        meta.setLore(lore);
        return this;
    }

    /**
     * Sets the owner of the item
     *
     * @param name new owner
     * @return current instance
     * @throws IllegalArgumentException if the type of the {@link #item} !=
     *                                  {@link Material#SKULL_ITEM}
     */
    public ItemBuilder owner(final String name) {
        if (item.getType() == Material.SKULL_ITEM) {
            ((SkullMeta) meta).setOwner(name);
            return this;
        } else {
            throw new IllegalArgumentException("owner() only applicable for skull item");
        }
    }

    /**
     * Sets the durability of the item
     *
     * @param durability new durability
     * @return current instance
     */
    public ItemBuilder durability(final Integer durability) {
        item.setDurability((short) durability.intValue());
        return this;
    }

    /**
     * Sets the data of the item
     *
     * @param data new data
     * @return current instance
     */
    @SuppressWarnings("deprecation")
    public ItemBuilder data(final Integer data) {
        item.setData(new MaterialData(item.getType(), (byte) data.intValue()));
        return this;
    }

    /**
     * Adds a {@link Enchantment} to the item
     *
     * @param enchantment {@link Enchantment} which should be added
     * @param level       level of the enchantment
     * @return current instance
     */
    public ItemBuilder enchantment(final Enchantment enchantment, final int level) {
        meta.addEnchant(enchantment, level, true);
        return this;
    }

    /**
     * Calls {@link #enchantment(Enchantment, int)} with level 1
     *
     * @param enchantment {@link Enchantment} which should be added
     * @return current instance
     */
    public ItemBuilder enchantment(final Enchantment enchantment) {
        meta.addEnchant(enchantment, 1, false);
        return this;
    }

    /**
     * Calls {@link #enchantment(Enchantment)}
     *
     * @param enchantment {@link Enchantment} which should be added
     * @return current instance
     */
    public ItemBuilder removeEnchantment(final Enchantment enchantment) {
        meta.removeEnchant(enchantment);
        return this;
    }

    /**
     * Clears the enchantments of the item
     *
     * @return current instance
     */
    public ItemBuilder clearEnchantments() {
        item.getEnchantments().keySet().forEach(item::removeEnchantment);
        return this;
    }

    /**
     * Sets the type of the item
     *
     * @param material new material type
     * @return current instance
     */
    public ItemBuilder type(final Material material) {
        item.setType(material);
        return this;
    }

    /**
     * Clears the lore of the item
     *
     * @return current instance
     */
    public ItemBuilder clearLore() {
        meta.setLore(new ArrayList<>());
        return this;
    }

    /**
     * Sets the color of the item
     *
     * @param color new color
     * @return current instance
     * @throws IllegalArgumentException if the type of the {@link #item} is not type
     *                                  of a leather armor
     */
    public ItemBuilder color(Color color) {
        if (item.getType() == Material.LEATHER_BOOTS || item.getType() == Material.LEATHER_CHESTPLATE
                || item.getType() == Material.LEATHER_HELMET || item.getType() == Material.LEATHER_LEGGINGS) {
            ((LeatherArmorMeta) meta).setColor(color);
            return this;
        } else {
            throw new IllegalArgumentException("color() only applicable for leather armor!");
        }
    }

    /**
     * Sets the owner of the item
     *
     * @param name new author
     * @return current instance
     * @throws IllegalArgumentException if the type of the {@link #item} !=
     *                                  {@link Material#WRITTEN_BOOK}
     */
    public ItemBuilder author(String name) {
        if (item.getType() == Material.WRITTEN_BOOK) {
            ((BookMeta) meta).setAuthor(name);
            return this;
        } else {
            throw new IllegalArgumentException("author() only applicable for written books!");
        }
    }

    /**
     * Adds a page to the item if it is a book
     *
     * @param content content of the page
     * @param index   page index
     * @return current instance
     * @throws IllegalArgumentException if the type of the {@link #item} !=
     *                                  {@link Material#WRITTEN_BOOK}
     */
    public ItemBuilder page(String content, int index) {
        if (item.getType() == Material.WRITTEN_BOOK) {
            ((BookMeta) meta).setPage(index, content);
            return this;
        } else {
            throw new IllegalArgumentException("page() only applicable for written books!");
        }
    }

    /**
     * Adds a page to the item if it is a book
     *
     * @param content content of the page
     * @return current instance
     * @throws IllegalArgumentException if the type of the {@link #item} !=
     *                                  {@link Material#WRITTEN_BOOK}
     */
    public ItemBuilder page(String content) {
        if (item.getType() == Material.WRITTEN_BOOK) {
            ((BookMeta) meta).addPage(content);
            return this;
        } else {
            throw new IllegalArgumentException("page() only applicable for written books!");
        }
    }

    /**
     * Adds the given flags to the item
     *
     * @param flag flags which should be added
     * @return current instance
     */
    public ItemBuilder flag(ItemFlag... flag) {
        meta.addItemFlags(flag);
        return this;
    }

    /**
     * Removes the given flags from the item
     *
     * @param flag flags which should be removed
     * @return current instance
     */
    public ItemBuilder removeFlag(ItemFlag... flag) {
        meta.removeItemFlags(flag);
        return this;
    }

    /**
     * Enchants the item with DURABILITY and hides the enchantment for player
     *
     * @return current instance
     */
    public ItemBuilder glow() {
        this.enchantment(Enchantment.DURABILITY);
        this.flag(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    /**
     * Remove DURABILITY enchantment from the item
     *
     * @return current instance
     */
    public ItemBuilder unglow() {
        this.removeEnchantment(Enchantment.DURABILITY);
        return this;
    }

    /**
     * Makes the item unbreakable
     *
     * @return current instance
     */
    public ItemBuilder unbreakable() {
        meta.spigot().setUnbreakable(true);
        return this;
    }

    /**
     * Applies the {@link ItemMeta} to the item
     *
     * @return current instance
     */
    public ItemBuilder setMeta() {
        this.item.setItemMeta(this.meta);
        return this;
    }

    /**
     * Gets the the item
     *
     * @return item
     */
    public ItemStack getItem() {
        return item;
    }

    /**
     * Applies the {@link ItemMeta} and the {@link #nbtTags} to the item
     *
     * @return the item
     */
    public ItemStack build() {
        item.setItemMeta(meta);

        /* sets the nbt tags */
        if (!this.nbtTags.isEmpty() && this.item.getType() != Material.AIR) {
            net.minecraft.server.v1_8_R3.ItemStack stack = CraftItemStack.asNMSCopy(this.item);
            NBTTagCompound compound = !stack.hasTag() ? new NBTTagCompound() : stack.getTag();
            this.nbtTags.forEach(compound::set);
            return CraftItemStack.asBukkitCopy(stack);
        }

        return item;
    }

    /**
     * Adds a nbt tag to the item Works like a @{@link Map}
     *
     * @return current instance
     */
    public ItemBuilder nbtTag(final String key, final String value) {
        this.nbtTags.put(key, new NBTTagString(value));
        return this;
    }

    /**
     * Adds a nbt tag to the item Works like a @{@link Map}
     *
     * @return current instance
     */
    public ItemBuilder nbtTag(final String key, final int value) {
        this.nbtTags.put(key, new NBTTagInt(value));
        return this;
    }

    /**
     * Adds a nbt tag to the item Works like a @{@link Map}
     *
     * @return current instance
     */
    public ItemBuilder nbtTag(final String key, final double value) {
        this.nbtTags.put(key, new NBTTagDouble(value));
        return this;
    }

    /**
     * Adds a nbt tag to the item Works like a @{@link Map}
     *
     * @return current instance
     */
    public ItemBuilder nbtTag(final String key, final boolean value) {
        this.nbtTags.put(key, new NBTTagByte((byte) (value ? 1 : 0)));
        return this;
    }

    /**
     * Get the {@link NBTTagCompound} of a item
     *
     * @param itemStack item with the tag
     * @return the tag of the item
     */
    public static NBTTagCompound getTag(ItemStack itemStack) {
        return CraftItemStack.asNMSCopy(itemStack).getTag() == null ? new NBTTagCompound()
                : CraftItemStack.asNMSCopy(itemStack).getTag();
    }

    /**
     * Calls {@link #createSkullItem(UUID, String)} with the uuid and the profile of
     * the given player
     *
     * @param player the player
     * @return new {@link ItemBuilder} with {@link Material#SKULL_ITEM} type
     */
    public static ItemBuilder createSkullItem(Player player) {
        return createSkullItem(player.getUniqueId(),
                new ArrayList<>(((CraftPlayer) player).getProfile().getProperties().get("textures")).get(0).getValue());
    }

    /**
     * Creates a new {@link ItemStack} with {@link Material#SKULL_ITEM} type
     *
     * @param uuid     uuid of the skull
     * @param textures textures of the skull
     * @return new skull item with the given texture
     */
    public static ItemBuilder createSkullItem(UUID uuid, String textures) {
        ItemStack skullItem = new ItemBuilder(Material.SKULL_ITEM).durability(3).build();
        SkullMeta meta = (SkullMeta) skullItem.getItemMeta();
        GameProfile gameProfile = new GameProfile(uuid, null);
        gameProfile.getProperties().put("textures", new Property("textures", textures));

        try {
            Field field = meta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(meta, gameProfile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        skullItem.setItemMeta(meta);
        return new ItemBuilder(skullItem);
    }
}