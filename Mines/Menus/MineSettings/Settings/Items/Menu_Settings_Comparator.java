package dev.chromenetwork.prison.Mines.Menus.MineSettings.Settings.Items;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import dev.chromenetwork.prison.Main;
import dev.chromenetwork.prison.Libs.ItemMenu.Events.ItemClickEvent;
import dev.chromenetwork.prison.Libs.ItemMenu.Items.MenuItem;
import dev.chromenetwork.prison.Libs.ItemMenu.Menus.ItemMenu;
import dev.chromenetwork.prison.Mines.Menus.MineSettings.TimeSettings.MenuMineSettingsTimeSettings;
import dev.chromenetwork.prison.Mines.Mine.Mine;

public class Menu_Settings_Comparator extends MenuItem {
	private Mine mine;
	private ItemMenu menu;
	public Menu_Settings_Comparator(ItemMenu menu, Mine mine) {
		super(ChatColor.AQUA+""+ChatColor.BOLD+"Manage Reset Timer",new ItemStack(Material.REDSTONE_COMPARATOR));
		this.mine = mine;
		this.menu = menu;
	}
	
	@Override
	public void onItemClick(final ItemClickEvent event) {
		event.setWillClose(true);
		
		new BukkitRunnable() {
			public void run() {
				MenuMineSettingsTimeSettings m = new MenuMineSettingsTimeSettings(mine);
				m.setParent(menu);
				m.open(event.getPlayer());
			}
		}.runTaskLater(Main.Plugin, 3L);
	}
	
	@Override
	public ItemStack getFinalIcon(Player player) {
		ItemStack item = super.getFinalIcon(player);
		ItemMeta im = item.getItemMeta();
		String[] lore = {" ",ChatColor.GRAY+"Manage the reset timers for the mine."};
		im.setLore(Arrays.asList(lore));
		item.setItemMeta(im);
		return item;
	}
}
