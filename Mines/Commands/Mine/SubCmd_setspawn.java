package dev.chromenetwork.prison.Mines.Commands.Mine;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import dev.chromenetwork.prison.Main;
import dev.chromenetwork.prison.Chat.ServerMessage;
import dev.chromenetwork.prison.Chat.ServerMessage.MessageType;
import dev.chromenetwork.prison.Commands.PluginCommandArgument;
import dev.chromenetwork.prison.Mines.MineManager;
import dev.chromenetwork.prison.Permissions.Permissions;

public class SubCmd_setspawn extends PluginCommandArgument {
	public SubCmd_setspawn() {
		this.setCommand("setspawn");
		this.setPermission(Permissions.MINES_ADMIN.getPermission());
	}
	
	@Override
	public void command(Player player, String command, String[] args) {
		if(args.length > 3 || args.length < 3) {
			ServerMessage.get().sendMessage(player, MessageType.ERROR, "There was an issue. If you need help type "+ChatColor.YELLOW+"/mine adminhelp");
			return;
		}
		
		MineManager MineManager = (MineManager) Main.Managers.getManager(MineManager.class);
		
		String name = args[2];
		
		if(!MineManager.doesMineExist(name)) {
			ServerMessage.get().sendMessage(player, MessageType.ERROR, "There is no mine with that name.");
			return;
		}
		
		MineManager.setMineSpawn(player, name);
		
		ServerMessage.get().sendMessage(player, MessageType.HELPHEADER, "");
		player.sendMessage(ChatColor.GREEN+"You have successfuly set the mine spawn point.");
		player.sendMessage(ChatColor.GRAY+"You can now configure the mine to your liking with the "+ChatColor.YELLOW+"/mine settings "+name+""+ChatColor.GRAY+" command!");
		player.sendMessage(" ");
	}
}
