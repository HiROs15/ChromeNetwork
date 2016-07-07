package dev.chromenetwork.prison.Libs.ItemMenu.Items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import dev.chromenetwork.prison.Libs.ItemMenu.Events.ItemClickEvent;

/**
 * A {@link ninja.amp.ampmenus.items.StaticMenuItem} that closes the {@link ninja.amp.ampmenus.menus.ItemMenu}.
 */
public class CloseItem extends StaticMenuItem {

    public CloseItem() {
        super(ChatColor.RED + "Close", new ItemStack(Material.RECORD_4));
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        event.setWillClose(true);
    }
}