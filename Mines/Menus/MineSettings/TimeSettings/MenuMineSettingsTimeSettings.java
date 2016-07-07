package dev.chromenetwork.prison.Mines.Menus.MineSettings.TimeSettings;

import java.math.BigDecimal;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.chromenetwork.prison.Main;
import dev.chromenetwork.prison.Libs.TimeConvert;
import dev.chromenetwork.prison.Libs.ItemMenu.Events.ItemClickEvent;
import dev.chromenetwork.prison.Libs.ItemMenu.Items.MenuItem;
import dev.chromenetwork.prison.Libs.ItemMenu.Menus.ItemMenu;
import dev.chromenetwork.prison.Mines.Mine.Mine;

public class MenuMineSettingsTimeSettings extends ItemMenu {
	private Mine mine;
	public boolean editTimerSettings = false;
	public boolean editPercentageSettings = false;
	
	public int previousMineResetTimer;
	public float previousMinePercentageReset;
	
	public MenuMineSettingsTimeSettings(Mine mine) {
		super("Edit Timer Settings: "+ChatColor.BLUE+""+ChatColor.BOLD+""+mine.getData().name, Size.SIX_LINE, Main.Plugin);
		this.mine = mine;
		
		this.setPreviousDataHandler();
		
		this.setTimerSettingsItem();
		
		this.setItem(4, new MenuResetNowItem(mine));
		
		this.setPercentSettingsItem();
		
		if(editTimerSettings == true) {
			this.setEditTimerSettingsItems();
		}
	}
	
	@Override
	public void setParent(ItemMenu parent) {
		super.setParent(parent);
		if(parent != null) {
			this.setItem(0, new MenuBackItem());
		}
	}
	
	public void setEditTimerSettingsItems() {
		this.setItem(10, new MenuEditTimerSettingsItem(this, mine, 1));
		this.setItem(19, new MenuEditTimerSettingsItem(this, mine, 5));
		this.setItem(28, new MenuEditTimerSettingsItem(this, mine, 10));
		this.setItem(37, new MenuEditTimerSettingsItem(this, mine, 60));
		this.setItem(46, new MenuEditTimerSettingsItem(this, mine, 100));
		
		this.setItem(12, new MenuEditTimerSettingsItem(this, mine, -1));
		this.setItem(21, new MenuEditTimerSettingsItem(this, mine, -5));
		this.setItem(30, new MenuEditTimerSettingsItem(this, mine, -10));
		this.setItem(39, new MenuEditTimerSettingsItem(this, mine, -60));
		this.setItem(48, new MenuEditTimerSettingsItem(this, mine, -100));
		
		this.setItem(20, new MenuEditTimerSettingsSaveItem(this, mine));
		this.setItem(38, new MenuEditTimerSettingsCancelItem(this, mine));
	}
	
	public void setEditTimerSettingsItems(boolean hide) {
		if(hide == false) {
			return;
		}
		this.setItem(10, new MenuBlankItem());
		this.setItem(19, new MenuBlankItem());
		this.setItem(28, new MenuBlankItem());
		this.setItem(37, new MenuBlankItem());
		this.setItem(46, new MenuBlankItem());
		
		this.setItem(12, new MenuBlankItem());
		this.setItem(21, new MenuBlankItem());
		this.setItem(30, new MenuBlankItem());
		this.setItem(39, new MenuBlankItem());
		this.setItem(48, new MenuBlankItem());
		
		this.setItem(20, new MenuBlankItem());
		this.setItem(38, new MenuBlankItem());
	}
	
	public void setTimerSettingsItem() {
		this.setItem(2, new MenuTimerSettingsItem(this, mine));
	}
	
	public void setPreviousDataHandler() {
		this.previousMineResetTimer = mine.getData().resetTime;
		this.previousMinePercentageReset = mine.getData().resetPercentage;
	}
	
	public void setPercentSettingsItem() {
		this.setItem(6, new MenuPercentResetSettingsItem(this, mine));
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

class MenuTimerSettingsItem extends MenuItem {
	private Mine mine;
	private ItemMenu menu;
	public MenuTimerSettingsItem(ItemMenu menu, Mine mine) {
		super(ChatColor.AQUA+""+ChatColor.BOLD+"Timer Settings", new ItemStack(Material.REDSTONE_COMPARATOR));
		this.mine = mine;
		this.menu = menu;
	}
	
	@Override
	public void onItemClick(ItemClickEvent event) {
		event.setWillClose(false);
		event.setWillUpdate(true);
		
		if(((MenuMineSettingsTimeSettings)menu).editTimerSettings == false) {
			((MenuMineSettingsTimeSettings)menu).editTimerSettings = true;
			
			((MenuMineSettingsTimeSettings)menu).setEditTimerSettingsItems();
		} else {
			((MenuMineSettingsTimeSettings)menu).editTimerSettings = false;
			
			((MenuMineSettingsTimeSettings)menu).setEditTimerSettingsItems(true);
		}
	}
	
	@Override
	public ItemStack getFinalIcon(Player player) {
		ItemStack item = super.getFinalIcon(player);
		ItemMeta im = item.getItemMeta();
		
		int[] times = TimeConvert.splitToComponentTimes(new BigDecimal(mine.getData().resetTime));
		
		String[] lore = {ChatColor.YELLOW+""+ChatColor.BOLD+"Reset Time: "+ChatColor.RESET+""+times[1]+""+ChatColor.AQUA+"m "+ChatColor.WHITE+""+times[2]+""+ChatColor.AQUA+"s", "", ChatColor.BLUE+"Click to edit the reset timer."};
		im.setLore(Arrays.asList(lore));
		item.setItemMeta(im);
		return item;
	}
}

class MenuEditTimerSettingsItem extends MenuItem {
	private Mine mine;
	private ItemMenu menu;
	private int timeDelta;
	public MenuEditTimerSettingsItem(ItemMenu menu, Mine mine, int timeDelta) {
		super("", new ItemStack(Material.STAINED_CLAY));
		this.mine = mine;
		this.menu = menu;
		this.timeDelta = timeDelta;
	}
	
	@Override
	public void onItemClick(ItemClickEvent event) {
		event.setWillClose(false);
		event.setWillUpdate(true);
		
		int curTime = mine.getData().resetTime;
		
		if((curTime+timeDelta) < 0) {
			curTime = 0;
		}
		else {
			curTime = curTime+timeDelta;
		}
		
		mine.getData().resetTime = curTime;
		
		((MenuMineSettingsTimeSettings)menu).setTimerSettingsItem();
	}
	
	@Override
	public ItemStack getFinalIcon(Player player) {
		ItemStack item;
		
		if(timeDelta > 0) {
			item = new ItemStack(Material.STAINED_CLAY, 1, (byte) 5);
		} else {
			item = new ItemStack(Material.STAINED_CLAY, 1, (byte) 14);
		}
		ItemMeta im = item.getItemMeta();
		
		if(timeDelta > 0) {
			im.setDisplayName(ChatColor.GREEN+""+ChatColor.BOLD+"+"+timeDelta+" Seconds");
		} else {
			im.setDisplayName(ChatColor.RED+""+ChatColor.BOLD+""+timeDelta+" Seconds");
		}
		
		String[] lore = {ChatColor.GRAY+"Click to ajust the reset timer."};
		im.setLore(Arrays.asList(lore));
		item.setItemMeta(im);
		return item;
	}
}

class MenuBlankItem extends MenuItem {
	public MenuBlankItem() {
		super(" ", new ItemStack(Material.AIR));
	}
	
	@Override
	public void onItemClick(ItemClickEvent event) {
		event.setWillClose(false);
	}
}

class MenuEditTimerSettingsCancelItem extends MenuItem {
	private ItemMenu menu;
	private Mine mine;
	public MenuEditTimerSettingsCancelItem(ItemMenu menu, Mine mine) {
		super(ChatColor.RED+""+ChatColor.BOLD+"Cancel", new ItemStack(Material.BARRIER));
		this.menu = menu;
		this.mine = mine;
	}
	
	@Override
	public void onItemClick(ItemClickEvent event) {
		event.setWillClose(false);
		event.setWillUpdate(true);
		
		MenuMineSettingsTimeSettings m = (MenuMineSettingsTimeSettings) menu;
		mine.getData().resetTime = m.previousMineResetTimer;
		
		m.editTimerSettings = false;
		m.setEditTimerSettingsItems(true);
	}
	
	@Override
	public ItemStack getFinalIcon(Player player) {
		ItemStack item = super.getFinalIcon(player);
		ItemMeta im = item.getItemMeta();
		String[] lore = {ChatColor.GRAY+"Click to cancel changes."};
		im.setLore(Arrays.asList(lore));
		item.setItemMeta(im);
		return item;
	}
}

class MenuEditTimerSettingsSaveItem extends MenuItem {
	private ItemMenu menu;
	private Mine mine;
	
	public MenuEditTimerSettingsSaveItem(ItemMenu menu, Mine mine) {
		super(ChatColor.GREEN+""+ChatColor.BOLD+"Save Changes", new ItemStack(Material.EMERALD));
		this.menu = menu;
		this.mine = mine;
	}
	
	@Override
	public void onItemClick(ItemClickEvent event) {
		event.setWillClose(false);
		event.setWillUpdate(true);
		
		mine.save();
		
		((MenuMineSettingsTimeSettings)menu).editTimerSettings = false;
		((MenuMineSettingsTimeSettings)menu).setEditTimerSettingsItems(true);
	}
	
	@Override
	public ItemStack getFinalIcon(Player player) {
		ItemStack item = super.getFinalIcon(player);
		ItemMeta im = item.getItemMeta();
		String[] lore = {ChatColor.GRAY+"Click to save the changes you have made."};
		im.setLore(Arrays.asList(lore));
		item.setItemMeta(im);
		return item;
	}
}

class MenuResetNowItem extends MenuItem {
	private Mine mine;
	public MenuResetNowItem(Mine mine) {
		super(ChatColor.AQUA+""+ChatColor.BOLD+"Reset Mine Now", new ItemStack(Material.REDSTONE));
		this.mine = mine;
	}
	
	@Override
	public void onItemClick(ItemClickEvent event) {
		event.setWillClose(false);
		
		mine.resetMine();
	}
	
	@Override
	public ItemStack getFinalIcon(Player player) {
		ItemStack item = super.getFinalIcon(player);
		ItemMeta im = item.getItemMeta();
		String[] lore = {ChatColor.GRAY+"Click to force reset the mine."};
		im.setLore(Arrays.asList(lore));
		item.setItemMeta(im);
		return item;
	}
}

class MenuPercentResetSettingsItem extends MenuItem {
	private Mine mine;
	@SuppressWarnings("unused")
	private ItemMenu menu;
	public MenuPercentResetSettingsItem(ItemMenu menu, Mine mine) {
		super(ChatColor.AQUA+""+ChatColor.BOLD+"Percent Mined Reset", new ItemStack(Material.REDSTONE_BLOCK));
		this.mine = mine;
		this.menu = menu;
	}
	
	@Override
	public void onItemClick(ItemClickEvent event) {
		event.setWillClose(false);
		
	}
	
	@Override
	public ItemStack getFinalIcon(Player player) {
		ItemStack item = super.getFinalIcon(player);
		ItemMeta im = item.getItemMeta();
		String[] lore = {ChatColor.YELLOW+""+ChatColor.BOLD+"Reset When Percent Mined: "+ChatColor.RESET+""+ChatColor.GRAY+""+mine.getData().resetPercentage, " ", ChatColor.GRAY+"Click to ajust the settings."};
		im.setLore(Arrays.asList(lore));
		item.setItemMeta(im);
		return item;
	}
}