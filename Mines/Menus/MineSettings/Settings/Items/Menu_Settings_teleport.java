package dev.chromenetwork.prison.Mines.Menus.MineSettings.Settings.Items;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.chromenetwork.prison.Libs.ItemMenu.Events.ItemClickEvent;
import dev.chromenetwork.prison.Libs.ItemMenu.Items.MenuItem;
import dev.chromenetwork.prison.Libs.ItemMenu.Menus.ItemMenu;
import dev.chromenetwork.prison.Mines.Mine.Mine;

public class Menu_Settings_teleport extends MenuItem {
	@SuppressWarnings("unused")
	private ItemMenu menu;
	private Mine mine;
	
	public Menu_Settings_teleport(ItemMenu menu, Mine mine) {
		super(ChatColor.AQUA+""+ChatColor.BOLD+"Teleport to Mine Spawn",new ItemStack(Material.ENDER_PEARL));
		this.menu = menu;
		this.mine = mine;
	}
	
	@Override
	public void onItemClick(ItemClickEvent event) {
		Location loc = mine.getData().spawnLocation.toBukkit();
		event.getPlayer().teleport(loc);
		event.setWillClose(true);
	}
	
	@Override
	public ItemStack getFinalIcon(Player player) {
		ItemStack item = super.getFinalIcon(player);
		ItemMeta im = item.getItemMeta();
		String[] lore = {"",ChatColor.GRAY+"Teleport to the spawn location of the mine without joining."};
		im.setLore(Arrays.asList(lore));
		item.setItemMeta(im);
		return item;
	}
}
