package dev.pizzawunsch.utils.inventory;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.SortedMap;

import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.Getter;

/**
 * This class handles a paged pane, that generally is a simple inventory that
 * have some pages that can be skipped et cetera.
 *
 * @author Lucas | PizzaWunsch & I Al Ianstaan (from SpigotMC)
 * @version 1.0
 * @since 14.11.2021
 */
@Getter
@Setter
public class PagedPane implements InventoryHolder {

    // instance variables.
    private static final List<PagedPane> currentPagedPanes = Lists.newArrayList();
    private Inventory inventory;
    private Inventory cache;
    private final SortedMap<Integer, Page> pages = Maps.newTreeMap();
    private int currentIndex;
    private final int pageSize;

    private final Player player;

    /**
     * This is the constructor of the paged inventory that sets some usefull
     * variables.
     *
     * @param pageSize the size of the page.
     * @param rows     the rows of the page.
     * @param title    the title of the paged inventory.
     */
    public PagedPane(int pageSize, int rows, String title, Player player) {
        Objects.requireNonNull(title, "title can not be null!");
        if (rows > 6) {
            throw new IllegalArgumentException("Rows must be <= 6, got " + rows);
        }
        if (pageSize > 6) {
            throw new IllegalArgumentException("Page size must be <= 6, got" + pageSize);
        }
        this.player = player;
        this.pageSize = pageSize;
        this.cache = null;
        inventory = Bukkit.createInventory(this, rows * 9, title);

        pages.put(0, new Page(pageSize));

        // if the page does already exist.
        if (getPane(player) != null)
            currentPagedPanes.remove(getPane(player));
        currentPagedPanes.add(this);
    }

    /**
     * This method will return a paged pane if the given player had opened one.
     *
     * @param player the player who should get the paged pane.
     * @return the paged pane object.
     */
    public static PagedPane getPane(Player player) {
        return currentPagedPanes.stream().filter(pane -> pane.getPlayer().equals(player)).findAny().orElse(null);
    }

    /**
     * This method will return a list with all current paged panes.
     *
     * @return a list with all paged panes.
     */
    public static List<PagedPane> getPanes() {
        return currentPagedPanes;
    }

    /**
     * This method allows you to add a {@link Button} into the paged inventory.
     *
     * @param button the button that should added.
     */
    public void addButton(Button button) {
        for (Entry<Integer, Page> entry : pages.entrySet()) {
            if (entry.getValue().addButton(button)) {
                if (entry.getKey() == currentIndex) {
                    reRender();
                }
                return;
            }
        }
        Page page = new Page(pageSize);
        page.addButton(button);
        pages.put(pages.lastKey() + 1, page);

        reRender();
    }

    /**
     * This method allows you to remove a {@link Button} from the paged inventory.
     *
     * @param button the button that should removed.
     */
    public void removeButton(Button button) {
        for (Iterator<Entry<Integer, Page>> iterator = pages.entrySet().iterator(); iterator.hasNext();) {
            Entry<Integer, Page> entry = iterator.next();
            if (entry.getValue().removeButton(button)) {

                // we may need to delete the page
                if (entry.getValue().isEmpty()) {
                    // we have more than one page, so delete it
                    if (pages.size() > 1) {
                        iterator.remove();
                    }
                    // the currentIndex now points to a page that does not exist. Correct it.
                    if (currentIndex >= pages.size()) {
                        currentIndex--;
                    }
                }
                // if we modified the current one, re-render
                // if we deleted the current page, re-render too
                if (entry.getKey() >= currentIndex) {
                    reRender();
                }
                return;
            }
        }
    }

    /**
     * @return the amount of pages that exists.
     */
    public int getPageAmount() {
        return pages.size();
    }

    /**
     * @return the current amount of pages.
     */
    public int getCurrentPage() {
        return currentIndex + 1;
    }

    /**
     * This method allows you to set the selected page.
     *
     * @param index the page that should set.
     */
    public void selectPage(int index) {
        if (index < 0 || index >= getPageAmount()) {
            throw new IllegalArgumentException(
                    "Index out of bounds s: " + index + " [" + 0 + " " + getPageAmount() + ")");
        }
        if (index == currentIndex) {
            return;
        }

        currentIndex = index;
        reRender();
    }

    /**
     * This method will re-renders the paged inventory.
     */
    public void reRender() {
        inventory.clear();
        pages.get(currentIndex).render(inventory);

        for (int j = 0; j < this.getInventory().getSize(); j++) {
            ItemStack itemStack = this.getCache().getItem(j);
            if(itemStack != null) {
                if(!itemStack.getType().equals(Material.AIR)) {
                    this.inventory.setItem(j, itemStack);
                }
            }
        }
    }

    /**
     * This method will opens the inventory to the player.
     *
     * @param player the player who wants to open this inventory.
     */
    public void open(Player player) {
        reRender();
        player.openInventory(getInventory());
    }

    private static class Page {
        private final List<Button> buttons = new ArrayList<>();
        private final int maxSize;

        Page(int maxSize) {
            this.maxSize = maxSize;
        }

        /**
         * @param event The click event
         */
        void handleClick(InventoryClickEvent event) {
            // user clicked in his own inventory. Silently drop it
            if (event.getRawSlot() > event.getInventory().getSize()) {
                return;
            }
            // user clicked outside of the inventory
            if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) {
                return;
            }
            if (event.getSlot() >= buttons.size()) {
                return;
            }
            Button button = buttons.get(event.getSlot());
            button.onClick(event);
        }

        /**
         * Checks if the inventory hav enough space for a another item.
         *
         * @return a boolean if some space is given for a another item
         */
        boolean hasSpace() {
            return buttons.size() < maxSize * 9;
        }

        /**
         * @param button The {@link Button} to add
         *
         * @return True if the button was added, false if there was no space
         */
        boolean addButton(Button button) {
            if (!hasSpace()) {
                return false;
            }
            buttons.add(button);

            return true;
        }

        /**
         * @param button The {@link Button} to remove
         *
         * @return True if the button was removed
         */
        boolean removeButton(Button button) {
            return buttons.remove(button);
        }

        /**
         * @param inventory The inventory to render in
         */
        void render(Inventory inventory) {
            for (int i = 0; i < buttons.size(); i++) {
                Button button = buttons.get(i);
                inventory.setItem(i, button.getItemStack());
            }
        }

        /**
         * @return true if this page is empty
         */
        boolean isEmpty() {
            return buttons.isEmpty();
        }
    }
}