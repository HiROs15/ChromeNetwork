package dev.chromenetwork.prison.Mines.Menus.MineSettings.Settings;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.chromenetwork.prison.Main;
import dev.chromenetwork.prison.Libs.ItemMenu.Events.ItemClickEvent;
import dev.chromenetwork.prison.Libs.ItemMenu.Items.MenuItem;
import dev.chromenetwork.prison.Libs.ItemMenu.Menus.ItemMenu;
import dev.chromenetwork.prison.Mines.Menus.MineSettings.Settings.Items.Menu_Setting_EnableState;
import dev.chromenetwork.prison.Mines.Menus.MineSettings.Settings.Items.Menu_Setting_HighlightRegion;
import dev.chromenetwork.prison.Mines.Menus.MineSettings.Settings.Items.Menu_Settings_Blocks;
import dev.chromenetwork.prison.Mines.Menus.MineSettings.Settings.Items.Menu_Settings_Comparator;
import dev.chromenetwork.prison.Mines.Menus.MineSettings.Settings.Items.Menu_Settings_teleport;
import dev.chromenetwork.prison.Mines.Mine.Mine;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class Menu_Settings extends ItemMenu {
	private Mine mine;
	public Menu_Settings(Mine mine) {
		super("Control Panel: "+ChatColor.BLUE+""+ChatColor.BOLD+""+mine.getData().name, Size.TWO_LINE, Main.Plugin);
		this.mine = mine;
		
		// Add items
		this.setItem(0, new Menu_Settings_Blocks(this, mine));
		this.setItem(2, new Menu_Settings_Comparator(this, mine));
		this.setItem(4, new Menu_Setting_EnableState(this, mine));
		this.setItem(6, new Menu_Setting_HighlightRegion(this, mine));
		this.setItem(8, new Menu_Settings_teleport(this, mine));
		
		this.setItem(10, new MenuSpawnTimerHologramItem(this, mine, 0));
		this.setItem(12, new MenuSpawnTimerHologramItem(this, mine, 1));
		this.setItem(14, new MenuSpawnTimerHologramItem(this, mine, 2));
		this.setItem(16, new MenuSpawnTimerHologramItem(this, mine, 3));
	}
	
	public Mine getMine() {
		return this.mine;
	}

}

class MenuSpawnTimerHologramItem extends MenuItem {
	@SuppressWarnings("unused")
	private ItemMenu menu;
	private Mine mine;
	private int type;
	
	public MenuSpawnTimerHologramItem(ItemMenu menu, Mine mine, int type) {
		super(ChatColor.GOLD+""+ChatColor.BOLD+"Spawn Timer Hologram",new ItemStack(Material.MONSTER_EGG, 1, (byte) 120));
		this.menu = menu;
		this.mine = mine;
		this.type = type;
	}
	
	@Override
	public void onItemClick(ItemClickEvent event) {
		event.setWillClose(true);
		
		ItemStack item = new ItemStack(Material.MONSTER_EGG, 1, (byte) 120);
		
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = new NBTTagCompound();
		tag.set("prison.mine.hologram",nmsItem.save(new NBTTagCompound()));
		tag.setString("mine.name",mine.getData().name);
		if(type == 0) {
			tag.setString("hologram.type", "timer");
		}
		else if(type == 1) {
			tag.setString("hologram.type", "blocksmined");
		}
		else if(type == 2) {
			tag.setString("hologram.type", "percentmined");
		}
		else if(type == 3) {
			tag.setString("hologram.type", "percentleft");
		}
		
		nmsItem.setTag(tag);
		
		item = CraftItemStack.asBukkitCopy(nmsItem);
		
		ItemMeta im = item.getItemMeta();
		if(type == 0) {
			im.setDisplayName(ChatColor.GOLD+""+ChatColor.BOLD+"Reset Timer Hologram");
		}
		else if(type == 1) {
			im.setDisplayName(ChatColor.GOLD+""+ChatColor.BOLD+"Blocks Mined Hologram");
		}
		else if(type == 2) {
			im.setDisplayName(ChatColor.GOLD+""+ChatColor.BOLD+"Percent Mined Hologram");
		}
		else if(type == 3) {
			im.setDisplayName(ChatColor.GOLD+""+ChatColor.BOLD+"Percent Left Hologram");
		}
		String[] lore = {ChatColor.GRAY+"Click on the ground where you would like to spawn this hologram."};
		im.setLore(Arrays.asList(lore));
		item.setItemMeta(im);
		
		event.getPlayer().getInventory().addItem(item);
	}
	
	@Override
	public ItemStack getFinalIcon(Player player) {
		ItemStack item = super.getFinalIcon(player);
		ItemMeta im = item.getItemMeta();
		if(type == 0) {
			im.setDisplayName(ChatColor.GOLD+""+ChatColor.BOLD+"Reset Timer Hologram");
		}
		else if(type == 1) {
			im.setDisplayName(ChatColor.GOLD+""+ChatColor.BOLD+"Blocks Mined Hologram");
		}
		else if(type == 2) {
			im.setDisplayName(ChatColor.GOLD+""+ChatColor.BOLD+"Percent Mined Hologram");
		}
		else if(type == 3) {
			im.setDisplayName(ChatColor.GOLD+""+ChatColor.BOLD+"Percent Left Hologram");
		}
		String[] lore = {" ",ChatColor.GRAY+"Click to get the hologram spawn egg."};
		im.setLore(Arrays.asList(lore));
		item.setItemMeta(im);
		return item;
	}
}
