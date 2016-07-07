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
import dev.chromenetwork.prison.Mines.Menus.MineSettings.Blocks.Menu_MineSettings_Blocks;
import dev.chromenetwork.prison.Mines.Mine.Mine;

public class Menu_Settings_Blocks extends MenuItem {
	private Mine mine;
	private ItemMenu menu;
	
	public Menu_Settings_Blocks(ItemMenu menu, Mine mine) {
		super(ChatColor.AQUA+""+ChatColor.BOLD+"Manage Blocks", new ItemStack(Material.GRASS));
		this.mine = mine;
		this.menu = menu;
	}
	
	@Override
	public void onItemClick(final ItemClickEvent event) {
		event.setWillClose(true);
		
		new BukkitRunnable() {
			public void run() {
				Menu_MineSettings_Blocks m = new Menu_MineSettings_Blocks(mine);
				m.setParent(menu);
				m.open(event.getPlayer());
			}
		}.runTaskLater(Main.Plugin, 2L);
	}
	
	@Override
	public ItemStack getFinalIcon(Player player) {
		ItemStack item = super.getFinalIcon(player);
		ItemMeta im = item.getItemMeta();
		String[] lore = {" ",ChatColor.GRAY+"You can manage your block and percentage chance here for the mine."};
		im.setLore(Arrays.asList(lore));
		item.setItemMeta(im);
		return item;
	}
}
