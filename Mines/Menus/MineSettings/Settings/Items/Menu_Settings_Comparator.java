package dev.chromenetwork.prison.Mines.Menus.MineSettings.Settings.Items;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.chromenetwork.prison.Libs.ItemMenu.Events.ItemClickEvent;
import dev.chromenetwork.prison.Libs.ItemMenu.Items.MenuItem;

public class Menu_Settings_Comparator extends MenuItem {
	public Menu_Settings_Comparator() {
		super(ChatColor.AQUA+""+ChatColor.BOLD+"Manage Reset Timer",new ItemStack(Material.REDSTONE_COMPARATOR));
	}
	
	@Override
	public void onItemClick(ItemClickEvent event) {
		
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
