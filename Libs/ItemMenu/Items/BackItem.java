package dev.chromenetwork.prison.Libs.ItemMenu.Items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import dev.chromenetwork.prison.Libs.ItemMenu.Events.ItemClickEvent;

/**
 * A {@link ninja.amp.ampmenus.items.StaticMenuItem} that opens the {@link ninja.amp.ampmenus.menus.ItemMenu}'s parent menu if it exists.
 */
public class BackItem extends StaticMenuItem {

    public BackItem() {
        super(ChatColor.RED + "Back", new ItemStack(Material.FENCE_GATE));
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        event.setWillGoBack(true);
    }
}