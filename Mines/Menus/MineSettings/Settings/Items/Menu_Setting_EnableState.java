package dev.chromenetwork.prison.Mines.Menus.MineSettings.Settings.Items;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.chromenetwork.prison.Libs.ItemMenu.Events.ItemClickEvent;
import dev.chromenetwork.prison.Libs.ItemMenu.Items.MenuItem;
import dev.chromenetwork.prison.Libs.ItemMenu.Menus.ItemMenu;
import dev.chromenetwork.prison.Mines.Mine.Mine;

public class Menu_Setting_EnableState extends MenuItem {
	private Mine mine;
	private ItemMenu menu;
	
	public Menu_Setting_EnableState(ItemMenu menu, Mine mine) {
		super(ChatColor.AQUA+""+ChatColor.BOLD+"Mine State",new ItemStack(Material.INK_SACK, 1, (byte) 8));
		this.mine = mine;
		this.menu = menu;
	}
	
	@Override
	public void onItemClick(ItemClickEvent event) {
		if(mine.getData().enabled == false) {
			mine.getData().enabled = true;
			mine.save();
			
			mine.updateState();
			
			menu.update(event.getPlayer());
		} else {
			mine.getData().enabled = false;
			mine.save();
			
			mine.updateState();
			
			menu.update(event.getPlayer());
		}
		event.setWillClose(false);
	}
	
	@Override
	public ItemStack getFinalIcon(Player player) {
		ItemStack item;
		if(mine.getData().enabled == false) {
			item = new ItemStack(Material.INK_SACK, 1, (byte) 8);
		} else {
			item = new ItemStack(Material.INK_SACK, 1, (byte) 10);
		}
		ItemMeta im = item.getItemMeta();
		if(mine.getData().enabled == false) {
			im.setDisplayName(ChatColor.AQUA+""+ChatColor.BOLD+"Mine State: "+ChatColor.RED+"Disabled");
		} else {
			im.setDisplayName(ChatColor.AQUA+""+ChatColor.BOLD+"Mine State: "+ChatColor.GREEN+"Enabled");
		}
		String[] lore = {" ",ChatColor.GRAY+"Click this to toggle the state for the mine."};
		im.setLore(Arrays.asList(lore));
		item.setItemMeta(im);
		return item;
	}
}
