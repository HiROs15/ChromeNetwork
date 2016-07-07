package dev.chromenetwork.prison.Libs.ItemMenu.Items;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import dev.chromenetwork.prison.Libs.ItemMenu.Events.ItemClickEvent;
import dev.chromenetwork.prison.Libs.ItemMenu.Menus.ItemMenu;

/**
 * A {@link ninja.amp.ampmenus.items.MenuItem} that opens a sub {@link ninja.amp.ampmenus.menus.ItemMenu}.
 */
public class SubMenuItem extends MenuItem {
    private final JavaPlugin plugin;
    private final ItemMenu menu;

    public SubMenuItem(JavaPlugin plugin, String displayName, ItemStack icon, ItemMenu menu, String... lore) {
        super(displayName, icon, lore);
        this.plugin = plugin;
        this.menu = menu;
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        event.setWillClose(true);
        final String playerName = event.getPlayer().getName();
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                Player p = Bukkit.getPlayerExact(playerName);
                if (p != null) {
                    menu.open(p);
                }
            }
        }, 3);
    }
}
