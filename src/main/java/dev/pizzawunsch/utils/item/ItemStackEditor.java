package dev.pizzawunsch.utils.item;

import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class ItemStackEditor {

    // instance variables
    private net.minecraft.server.v1_8_R3.ItemStack nmsItemStack;
    private NBTTagCompound compound;

    public ItemStackEditor(org.bukkit.inventory.ItemStack stack) {
        if (stack == null) {
            throw new NullPointerException("ItemStack cannot be null");
        }
        this.nmsItemStack = CraftItemStack.asNMSCopy(stack);
        try {
            this.compound = (this.nmsItemStack.getTag() != null ? this.nmsItemStack.getTag() : new NBTTagCompound());
        } catch (NullPointerException ignored) {
            this.compound = new NBTTagCompound();
        }
    }

    public NBTTagCompound getNBTTagCompound() {
        return this.compound;
    }

    public org.bukkit.inventory.ItemStack build() {
        return CraftItemStack.asBukkitCopy(this.nmsItemStack);
    }

    public ItemStackEditor nbt(String key, String value) {
        this.compound.setString(key, value);
        return this;
    }

    public ItemStackEditor nbt(String key, boolean value) {
        this.compound.setBoolean(key, value);
        return this;
    }

    public ItemStackEditor nbt(String key, int value) {
        this.compound.setInt(key, value);
        return this;
    }

    public ItemBuilder builder() {
        return new ItemBuilder(build());
    }
}