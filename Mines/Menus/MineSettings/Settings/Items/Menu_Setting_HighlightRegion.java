package dev.chromenetwork.prison.Mines.Menus.MineSettings.Settings.Items;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import dev.chromenetwork.prison.Main;
import dev.chromenetwork.prison.Libs.ItemMenu.Events.ItemClickEvent;
import dev.chromenetwork.prison.Libs.ItemMenu.Items.MenuItem;
import dev.chromenetwork.prison.Libs.ItemMenu.Menus.ItemMenu;
import dev.chromenetwork.prison.Libs.Particles.ParticleEffect;
import dev.chromenetwork.prison.Mines.Mine.Mine;

public class Menu_Setting_HighlightRegion extends MenuItem {
	private ItemMenu menu;
	private Mine mine;
	
	public Menu_Setting_HighlightRegion(ItemMenu menu, Mine mine) {
		super(ChatColor.AQUA+""+ChatColor.BOLD+"Highlight Region: ", new ItemStack(Material.NETHER_STAR));
		this.menu = menu;
		this.mine = mine;
	}
	
	@Override
	public void onItemClick(ItemClickEvent event) {
		if(mine.getHighlightMineRegion() == false) {
			mine.setHighlightMineRegion(true);
			this.startHighlightLoop(mine);
			menu.update(event.getPlayer());
		} else {
			mine.setHighlightMineRegion(false);
			menu.update(event.getPlayer());
		}
	}
	
	@Override
	public ItemStack getFinalIcon(Player player) {
		ItemStack item = new ItemStack(Material.NETHER_STAR);
		ItemMeta im = item.getItemMeta();
		if(mine.getHighlightMineRegion() == false) {
			im.setDisplayName(ChatColor.AQUA+""+ChatColor.BOLD+"Highlight Region: "+ChatColor.RED+"Disabled");
		} else {
			im.setDisplayName(ChatColor.AQUA+""+ChatColor.BOLD+"Highlight Region: "+ChatColor.GREEN+"Enabled");
		}
		String[] lore = {" ",ChatColor.GRAY+"Click this to toggle highlighting of the minable region for this mine."};
		im.setLore(Arrays.asList(lore));
		item.setItemMeta(im);
		return item;
	}
	
	private void startHighlightLoop(final Mine mine) {
		new BukkitRunnable() {
			Location min = mine.getData().mineRegionMin.toBukkit();
			Location max = mine.getData().mineRegionMax.toBukkit();
			public void run() {
				if(mine.getHighlightMineRegion() == false) {
					this.cancel();
				}
				
				for(int x = min.getBlockX(); x < max.getBlockX()+1; x++) {
					for(int y = min.getBlockY(); y < max.getBlockY()+1; y++) {
						for(int z = min.getBlockZ(); z < max.getBlockZ()+1; z++) {
							if(x == min.getBlockX() || x == max.getBlockX() || y == min.getBlockY() || y == max.getBlockY() || z == min.getBlockZ() || z == max.getBlockZ()) {
								ParticleEffect.REDSTONE.display(new ParticleEffect.OrdinaryColor(0,0,255), new Location(min.getWorld(), x+0.5, y+1, z+0.5), 50);
							}
						}
					}
				}
			}
		}.runTaskTimer(Main.Plugin, 0L, 5L);
	}
}
