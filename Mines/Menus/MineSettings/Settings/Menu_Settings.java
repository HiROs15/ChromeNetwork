package dev.chromenetwork.prison.Mines.Menus.MineSettings.Settings;

import org.bukkit.ChatColor;

import dev.chromenetwork.prison.Main;
import dev.chromenetwork.prison.Libs.ItemMenu.Menus.ItemMenu;
import dev.chromenetwork.prison.Mines.Menus.MineSettings.Settings.Items.Menu_Setting_EnableState;
import dev.chromenetwork.prison.Mines.Menus.MineSettings.Settings.Items.Menu_Setting_HighlightRegion;
import dev.chromenetwork.prison.Mines.Menus.MineSettings.Settings.Items.Menu_Settings_Blocks;
import dev.chromenetwork.prison.Mines.Menus.MineSettings.Settings.Items.Menu_Settings_Comparator;
import dev.chromenetwork.prison.Mines.Menus.MineSettings.Settings.Items.Menu_Settings_teleport;
import dev.chromenetwork.prison.Mines.Mine.Mine;

public class Menu_Settings extends ItemMenu {
	private Mine mine;
	public Menu_Settings(Mine mine) {
		super("Control Panel: "+ChatColor.BLUE+""+ChatColor.BOLD+""+mine.getData().name, Size.ONE_LINE, Main.Plugin);
		this.mine = mine;
		
		// Add items
		this.setItem(0, new Menu_Settings_Blocks(this, mine));
		this.setItem(2, new Menu_Settings_Comparator());
		this.setItem(4, new Menu_Setting_EnableState(this, mine));
		this.setItem(6, new Menu_Setting_HighlightRegion(this, mine));
		this.setItem(8, new Menu_Settings_teleport(this, mine));
	}
	
	public Mine getMine() {
		return this.mine;
	}

}
