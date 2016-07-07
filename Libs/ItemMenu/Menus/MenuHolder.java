package dev.chromenetwork.prison.Libs.ItemMenu.Menus;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 * Allows you to set the {@link ninja.amp.ampmenus.menus.ItemMenu} that created the Inventory as the Inventory's holder.
 */
public class MenuHolder implements InventoryHolder {
    private ItemMenu menu;
    private Inventory inventory;

    public MenuHolder(ItemMenu menu, Inventory inventory) {
        this.menu = menu;
        this.inventory = inventory;
    }

    /**
     * Gets the {@link ninja.amp.ampmenus.menus.ItemMenu} holding the Inventory.
     *
     * @return The {@link ninja.amp.ampmenus.menus.ItemMenu} holding the Inventory.
     */
    public ItemMenu getMenu() {
        return menu;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
