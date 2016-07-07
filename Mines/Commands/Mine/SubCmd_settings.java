package dev.chromenetwork.prison.Mines.Commands.Mine;

import org.bukkit.entity.Player;

import dev.chromenetwork.prison.Main;
import dev.chromenetwork.prison.Chat.ServerMessage;
import dev.chromenetwork.prison.Chat.ServerMessage.MessageType;
import dev.chromenetwork.prison.Commands.PluginCommandArgument;
import dev.chromenetwork.prison.Mines.MineManager;
import dev.chromenetwork.prison.Mines.Menus.MineSettings.Settings.Menu_Settings;
import dev.chromenetwork.prison.Mines.Mine.Mine;
import dev.chromenetwork.prison.Permissions.Permissions;

public class SubCmd_settings extends PluginCommandArgument {
	public SubCmd_settings() {
		this.setCommand("settings");
		this.setPermission(Permissions.MINES_ADMIN.getPermission());
	}
	
	@Override
	public void command(Player player, String command, String[] args) {
		if(args.length > 3 || args.length < 3) {
			ServerMessage.get().sendMessage(player, MessageType.ERROR, "There was an issue. If you need help type /mine adminhelp");
			return;
		}
		
		MineManager MineManager = (MineManager) Main.Managers.getManager(MineManager.class);
		String name = args[2];
		if(!MineManager.doesMineExist(name)) {
			ServerMessage.get().sendMessage(player, MessageType.ERROR, "There is no mine with that name.");
			return;
		}
		
		Mine mine = MineManager.getMine(name);
		
		Menu_Settings menu = new Menu_Settings(mine);
		menu.open(player);
		return;
	}
}
