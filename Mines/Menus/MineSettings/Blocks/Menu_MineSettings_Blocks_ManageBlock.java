package dev.chromenetwork.prison.Mines.Menus.MineSettings.Blocks;

import java.util.Arrays;
import java.util.Iterator;

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
import dev.chromenetwork.prison.Mines.Menus.MineSettings.Settings.Menu_Settings;
import dev.chromenetwork.prison.Mines.Mine.Mine;
import dev.chromenetwork.prison.Mines.Mine.MineBlock;

public class Menu_MineSettings_Blocks_ManageBlock extends ItemMenu {
	@SuppressWarnings("unused")
	private Mine mine;
	private MineBlock block;
	
	public Menu_MineSettings_Blocks_ManageBlock(MineBlock block, Mine mine) {
		super("Change Block Settings",Size.SIX_LINE,Main.Plugin);
		this.mine = mine;
		this.block = block;
		
		this.setBlockItem();
		
		//The ajustments
		this.setItem(10, new MenuMineSettingsBlocksManageBlocksPercentRotateItem(this, block, mine, 0.001f));
		this.setItem(11, new MenuMineSettingsBlocksManageBlocksPercentRotateItem(this, block, mine, 0.01f));
		this.setItem(19, new MenuMineSettingsBlocksManageBlocksPercentRotateItem(this, block, mine, 0.1f));
		this.setItem(20, new MenuMineSettingsBlocksManageBlocksPercentRotateItem(this, block, mine, 1f));
		this.setItem(28, new MenuMineSettingsBlocksManageBlocksPercentRotateItem(this, block, mine, 5f));
		this.setItem(29, new MenuMineSettingsBlocksManageBlocksPercentRotateItem(this, block, mine, 10f));
		this.setItem(37, new MenuMineSettingsBlocksManageBlocksPercentRotateItem(this, block, mine, 20f));
		this.setItem(38, new MenuMineSettingsBlocksManageBlocksPercentRotateItem(this, block, mine, 30f));
		this.setItem(46, new MenuMineSettingsBlocksManageBlocksPercentRotateItem(this, block, mine, 50f));
		this.setItem(47, new MenuMineSettingsBlocksManageBlocksPercentRotateItem(this, block, mine, 100f));
		
		this.setItem(15, new MenuMineSettingsBlocksManageBlocksPercentRotateItem(this, block, mine, -0.001f));
		this.setItem(16, new MenuMineSettingsBlocksManageBlocksPercentRotateItem(this, block, mine, -0.01f));
		this.setItem(24, new MenuMineSettingsBlocksManageBlocksPercentRotateItem(this, block, mine, -0.1f));
		this.setItem(25, new MenuMineSettingsBlocksManageBlocksPercentRotateItem(this, block, mine, -1f));
		this.setItem(33, new MenuMineSettingsBlocksManageBlocksPercentRotateItem(this, block, mine, -5f));
		this.setItem(34, new MenuMineSettingsBlocksManageBlocksPercentRotateItem(this, block, mine, -10f));
		this.setItem(42, new MenuMineSettingsBlocksManageBlocksPercentRotateItem(this, block, mine, -20f));
		this.setItem(43, new MenuMineSettingsBlocksManageBlocksPercentRotateItem(this, block, mine, -30f));
		this.setItem(51, new MenuMineSettingsBlocksManageBlocksPercentRotateItem(this, block, mine, -50f));
		this.setItem(52, new MenuMineSettingsBlocksManageBlocksPercentRotateItem(this, block, mine, -100f));
		
		this.setItem(31, new MenuMineSettingsBlocksManageBlockSaveItem(this, mine));
		this.setItem(8, new MenuMineSettingsBlocksManageBlockRemoveBlockItem(this, mine, block));
	}
	
	@Override
	public void setParent(ItemMenu parent) {
		super.setParent(parent);
		if(parent != null) {
			this.setItem(0, new MenuBackItem());
		}
	}
	
	public void setBlockItem() {
		this.setItem(4, new MenuMineSettingsBlocksManageBlocksItem(block.getBukkitBlock().getType().toString().toLowerCase().replace("_", " "), new ItemStack(block.getBukkitBlock().getType()),
				ChatColor.YELLOW+""+ChatColor.BOLD+"Percent Spawn Rate: "+ChatColor.RESET+""+ChatColor.WHITE+""+block.getPercentage()+"%",
				" ",
				ChatColor.BLUE+"Don't forget to save the changes once you make them!"
				));
	}
}

class MenuMineSettingsBlocksManageBlocksItem extends MenuItem {
	private String[] lore;
	public MenuMineSettingsBlocksManageBlocksItem(String name, ItemStack item, String... lore) {
		super(name, item);
		this.lore = lore;
	}
	
	@Override
	public void onItemClick(ItemClickEvent event) {
		event.setWillClose(false);
	}
	
	@Override
	public ItemStack getFinalIcon(Player player) {
		ItemStack item = super.getFinalIcon(player);
		ItemMeta im = item.getItemMeta();
		im.setLore(Arrays.asList(lore));
		item.setItemMeta(im);
		return item;
	}
}

class MenuMineSettingsBlocksManageBlocksPercentRotateItem extends MenuItem {
	private float percentDelta = 0f;
	private MineBlock block;
	private Mine mine;
	private ItemMenu menu;
	
	public MenuMineSettingsBlocksManageBlocksPercentRotateItem(ItemMenu menu, MineBlock block, Mine mine, float percentDelta) {
		super("",new ItemStack(Material.STAINED_CLAY));
		this.percentDelta = percentDelta;
		this.block = block;
		this.mine = mine;
		this.menu = menu;
	}
	
	@Override
	public void onItemClick(ItemClickEvent event) {
		float curPercent = block.getPercentage();
		
		if((curPercent+percentDelta) > 100) {
			curPercent = 100;
		}
		else if((curPercent+percentDelta) < 0) {
			curPercent = 0;
		}
		else {
			curPercent = curPercent+percentDelta;
		}
		
		mine.getData().getBlock(block.getId()).setPercentage(curPercent);
		event.setWillClose(false);
		event.setWillUpdate(true);
		Menu_MineSettings_Blocks_ManageBlock m = (Menu_MineSettings_Blocks_ManageBlock) menu;
		m.setBlockItem();
	}
	
	@Override
	public ItemStack getFinalIcon(Player player) {
		ItemStack item;
		if(percentDelta > 0) {
			item = new ItemStack(Material.STAINED_CLAY, 1, (byte) 5);
		} else {
			item = new ItemStack(Material.STAINED_CLAY, 1, (byte) 14);
		}
		
		ItemMeta im = item.getItemMeta();
		String[] lore = {ChatColor.GRAY+"Click to ajust the percentage for this block."};
		
		if(percentDelta > 0) {
			im.setDisplayName(ChatColor.GREEN+""+ChatColor.BOLD+"+"+percentDelta+"%");
		} else {
			im.setDisplayName(ChatColor.RED+""+ChatColor.BOLD+""+percentDelta+"%");
		}
		im.setLore(Arrays.asList(lore));
		item.setItemMeta(im);
		return item;
	}
}

class MenuMineSettingsBlocksManageBlockSaveItem extends MenuItem {
	@SuppressWarnings("unused")
	private ItemMenu menu;
	private Mine mine;
	public MenuMineSettingsBlocksManageBlockSaveItem(ItemMenu menu, Mine mine) {
		super(ChatColor.GREEN+""+ChatColor.BOLD+"Save",new ItemStack(Material.EMERALD));
		this.menu = menu;
		this.mine = mine;
	}
	
	
	@Override
	public void onItemClick(ItemClickEvent event) {
		event.setWillClose(true);
		event.setWillGoBack(true);
		mine.save();
	}
	
	@Override
	public ItemStack getFinalIcon(Player player) {
		ItemStack item = super.getFinalIcon(player);
		ItemMeta im = item.getItemMeta();
		String[] lore = {ChatColor.GRAY+"Click to save the changes to the block."};
		im.setLore(Arrays.asList(lore));
		item.setItemMeta(im);
		return item;
	}
}

class MenuMineSettingsBlocksManageBlockRemoveBlockItem extends MenuItem {
	@SuppressWarnings("unused")
	private ItemMenu menu;
	private Mine mine;
	private MineBlock block;
	public MenuMineSettingsBlocksManageBlockRemoveBlockItem(ItemMenu menu, Mine mine, MineBlock block) {
		super(ChatColor.RED+""+ChatColor.BOLD+"Delete Block",new ItemStack(Material.BARRIER));
		this.menu = menu;
		this.mine = mine;
		this.block = block;
	}
	
	
	@Override
	public void onItemClick(final ItemClickEvent event) {
		event.setWillClose(true);
		
		Iterator<MineBlock> it = mine.getData().blocks.iterator();
		while(it.hasNext()) {
			MineBlock ita = it.next();
			if(ita.getId() == block.getId()) {
				it.remove();
			}
		}
		
		mine.save();
		
		new BukkitRunnable() {
			public void run() {
				Menu_MineSettings_Blocks m = new Menu_MineSettings_Blocks(mine);
				Menu_Settings mp = new Menu_Settings(mine);
				m.setParent(mp);
				m.open(event.getPlayer());
			}
		}.runTaskLater(Main.Plugin, 3L);
	}
	
	@Override
	public ItemStack getFinalIcon(Player player) {
		ItemStack item = super.getFinalIcon(player);
		ItemMeta im = item.getItemMeta();
		String[] lore = {ChatColor.GRAY+"Click to save the changes to the block."};
		im.setLore(Arrays.asList(lore));
		item.setItemMeta(im);
		return item;
	}

}
