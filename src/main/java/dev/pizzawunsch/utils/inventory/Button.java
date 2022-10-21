package dev.pizzawunsch.utils.inventory;


import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * This class handles a button of a paged inventory, that can may used for
 * simple inventory structures.
 *
 * @author Lucas | PizzaWunsch & I Al Ianstaan (from SpigotMC)
 * @version 1.0
 * @since 14.11.2021
 */
public class Button {

    // instance variables.
    private static int counter;
    private final int ID = counter++;

    private ItemStack itemStack;
    private Consumer<InventoryClickEvent> action;

    /**
     * This is the constructor of the button, that allows you to add a item stack
     * into a paged inventory.
     *
     * @param itemStack the item stack that should added.
     */
    public Button(ItemStack itemStack) {
        this(itemStack, event -> {
        });
    }

    /**
     * This is the constructor of the button, that allows you to add a item stack
     * into a paged inventory.
     *
     * @param itemStack the item stack that should added.
     */
    public Button(ItemStack itemStack, Consumer<InventoryClickEvent> action) {
        this.itemStack = itemStack;
        this.action = action;
    }

    /**
     * This method allows you to get the item stack that is stored in a button.
     *
     * @return the item stack object.
     */
    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * This method allows you to set the action of the given button that should
     * executed.
     *
     * @param action the action that should executed.
     */
    public void setAction(Consumer<InventoryClickEvent> action) {
        this.action = action;
    }

    /**
     * This method allows you to use the inventory click inventory with the action
     * that can be executed.s
     *
     * @param event the corresponding inventory click event.
     */
    public void onClick(InventoryClickEvent event) {
        action.accept(event);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Button))
            return false;
        Button button = (Button) o;
        return ID == button.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}