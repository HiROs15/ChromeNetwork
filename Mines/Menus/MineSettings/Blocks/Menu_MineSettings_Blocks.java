package dev.chromenetwork.prison.Mines.Menus.MineSettings.Blocks;

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
import dev.chromenetwork.prison.Mines.Mine.Mine;
import dev.chromenetwork.prison.Mines.Mine.MineBlock;

public class Menu_MineSettings_Blocks extends ItemMenu {
	@SuppressWarnings("unused")
	private Mine mine;
	public Menu_MineSettings_Blocks(Mine mine) {
		super("Manage Blocks: "+ChatColor.BLUE+""+ChatColor.BOLD+""+mine.getData().name, Size.SIX_LINE, Main.Plugin);
		this.mine = mine;
		
		// Add blocks
		int index = 0;
		for(MineBlock b : mine.getData().blocks) {
			this.setItem(index, new MenuBlockItem(this, mine, b));
		}
		
		this.setItem(49, new MenuAddBlockItem(this, mine));
	}
	
	@Override
	public void setParent(ItemMenu parent) {
		super.setParent(parent);
		if(parent != null) {
			this.setItem(53, new MenuBackItem());
		}
	}
}

class MenuBlockItem extends MenuItem {
	private Mine mine;
	private MineBlock block;
	private ItemMenu menu;
	public MenuBlockItem(ItemMenu menu, Mine mine, MineBlock b) {
		super("",new ItemStack(b.getBukkitBlock().getType()));
		this.mine = mine;
		this.block = b;
		this.menu = menu;
	}
	
	@Override
	public void onItemClick(final ItemClickEvent event) {
		event.setWillClose(true);
		new BukkitRunnable() {
			public void run() {
				Menu_MineSettings_Blocks_ManageBlock m = new Menu_MineSettings_Blocks_ManageBlock(block, mine);
				m.setParent(menu);
				m.open(event.getPlayer());
			}
		}.runTaskLater(Main.Plugin, 3L);
	}
	
	@Override
	public ItemStack getFinalIcon(Player player) {
		ItemStack item = new ItemStack(block.getBukkitBlock().getType());
		ItemMeta im = item.getItemMeta();
		String[] lore = {ChatColor.YELLOW+"Percentage: "+ChatColor.GRAY+""+block.getPercentage()+"%"," ",ChatColor.DARK_RED+"Click to edit the percentage for this block."};
		im.setLore(Arrays.asList(lore));
		item.setItemMeta(im);
		return item;
	}
}

class MenuBackItem extends MenuItem {
	public MenuBackItem() {
		super(ChatColor.RED+""+ChatColor.BOLD+"Back", new ItemStack(Material.ARROW));
	}
	
	@Override
	public void onItemClick(ItemClickEvent event) {
		event.setWillClose(true);
		event.setWillGoBack(true);
	}
	
	@Override
	public ItemStack getFinalIcon(Player player) {
		ItemStack item = super.getFinalIcon(player);
		ItemMeta im = item.getItemMeta();
		String[] lore = {" ",ChatColor.GRAY+"Return to the previous screen."};
		im.setLore(Arrays.asList(lore));
		item.setItemMeta(im);
		return item;
	}
}

class MenuAddBlockItem extends MenuItem {
	private Mine mine;
	private ItemMenu menu;
	
	public MenuAddBlockItem(ItemMenu menu, Mine mine) {
		super(ChatColor.GREEN+""+ChatColor.BOLD+"Add a Block", new ItemStack(Material.EMERALD));
		this.mine = mine;
		this.menu = menu;
	}
	
	@Override
	public void onItemClick(ItemClickEvent event) {
		Menu_MineSettings_Blocks_AddBlock m = new Menu_MineSettings_Blocks_AddBlock(mine);
		m.setParent(menu);
		m.open(event.getPlayer());
	}
	
	@Override
	public ItemStack getFinalIcon(Player player) {
		ItemStack item = super.getFinalIcon(player);
		ItemMeta im = item.getItemMeta();
		String[] lore = {" ",ChatColor.GRAY+"Add a block to the mine."};
		im.setLore(Arrays.asList(lore));
		item.setItemMeta(im);
		return item;
	}
}
