package dev.chromenetwork.prison.Mines.Commands.Mine;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import dev.chromenetwork.prison.Main;
import dev.chromenetwork.prison.Chat.ServerMessage;
import dev.chromenetwork.prison.Chat.ServerMessage.MessageType;
import dev.chromenetwork.prison.Commands.PluginCommandArgument;
import dev.chromenetwork.prison.Mines.MineManager;
import dev.chromenetwork.prison.Permissions.Permissions;

public class SubCmd_create extends PluginCommandArgument {
	public SubCmd_create() {
		this.setCommand("create");
		this.setPermission(Permissions.MINES_ADMIN.getPermission());
	}
	
	@Override
	public void command(Player player, String command, String[] args) {
		if(args.length < 2 && args.length > 2) {
			ServerMessage.get().sendMessage(player, MessageType.ERROR, "You need to enter a name for the mine.");
			return;
		}
		
		String name = args[2];
		MineManager MineManager = (MineManager) Main.Managers.getManager(MineManager.class);
		if(MineManager.doesMineExist(name)) {
			ServerMessage.get().sendMessage(player, MessageType.ERROR, "A mine with that name already exists.");
			return;
		}
		
		MineManager.createMine(name);
		ServerMessage.get().sendMessage(player, MessageType.HELPHEADER,"");
		player.sendMessage(ChatColor.GREEN+"You have created a mine named: "+ChatColor.YELLOW+""+name+""+ChatColor.GREEN+".");
		player.sendMessage(ChatColor.DARK_AQUA+"The next step is to set the mine region with world edit. Below are the steps.");
		player.sendMessage(" ");
		player.sendMessage(ChatColor.AQUA+"1) "+ChatColor.GRAY+"Select two corners of the minable region with world edit's "+ChatColor.YELLOW+"//wand"+ChatColor.GRAY+" command.");
		player.sendMessage(ChatColor.AQUA+"2) "+ChatColor.GRAY+"Once you have selected the region type: "+ChatColor.YELLOW+"/mine setregion "+name+""+ChatColor.GRAY+". This will create the region.");
		return;
	}
}
