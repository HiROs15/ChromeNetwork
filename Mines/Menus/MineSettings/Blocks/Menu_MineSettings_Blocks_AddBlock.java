package dev.chromenetwork.prison.Mines.Menus.MineSettings.Blocks;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import dev.chromenetwork.prison.Main;
import dev.chromenetwork.prison.Chat.ServerMessage;
import dev.chromenetwork.prison.Chat.ServerMessage.MessageType;
import dev.chromenetwork.prison.Libs.ItemMenu.Events.ItemClickEvent;
import dev.chromenetwork.prison.Libs.ItemMenu.Items.MenuItem;
import dev.chromenetwork.prison.Libs.ItemMenu.Menus.ItemMenu;
import dev.chromenetwork.prison.Mines.Menus.MineSettings.Settings.Menu_Settings;
import dev.chromenetwork.prison.Mines.Mine.Mine;
import dev.chromenetwork.prison.Mines.Mine.MineBlock;

public class Menu_MineSettings_Blocks_AddBlock extends ItemMenu {
	@SuppressWarnings("unused")
	private Mine mine;
	
	public Menu_MineSettings_Blocks_AddBlock(Mine mine) {
		super("Place a Block Here", Size.ONE_LINE, Main.Plugin, true);
		this.mine = mine;
		
		this.setItem(7, new MenuMineSettingsSaveBlockItem(this, mine));
	}
	
	@Override
	public void setParent(ItemMenu parent) {
		super.setParent(parent);
		if(parent != null) {
			this.setItem(8, new MenuBackItem());
		}
	}
}

class MenuMineSettingsSaveBlockItem extends MenuItem {
	private Mine mine;
	private ItemMenu menu;
	
	public MenuMineSettingsSaveBlockItem(ItemMenu menu, Mine mine) {
		super(ChatColor.GREEN+""+ChatColor.BOLD+"Save Block", new ItemStack(Material.EMERALD));
		this.mine = mine;
	}
	
	@Override
	public void onItemClick(final ItemClickEvent event) {
		ItemStack item = event.getPlayer().getOpenInventory().getItem(0);
		
		if(item.getType() == Material.AIR) {
			ServerMessage.get().sendMessage(event.getPlayer(), MessageType.ERROR, "You must put a block in the inventory to add it.");
			event.setWillClose(false);
		}
		else if(!item.getType().isBlock()) {
			ServerMessage.get().sendMessage(event.getPlayer(), MessageType.ERROR, "You must add only blocks to the mine.");
			event.getPlayer().getOpenInventory().setItem(menu.getDropItemSlot(), null);
			event.setWillClose(false);
		}
		else {
			event.setWillClose(true);
			mine.getData().blocks.add(new MineBlock(item));
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
	}
	
	@Override
	public ItemStack getFinalIcon(Player player) {
		ItemStack item = super.getFinalIcon(player);
		ItemMeta im = item.getItemMeta();
		String[] lore = {ChatColor.GRAY+"Click to save the added block."};
		im.setLore(Arrays.asList(lore));
		item.setItemMeta(im);
		return item;
	}
}
