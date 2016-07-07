package dev.chromenetwork.prison.Mines.Commands.Mine;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import dev.chromenetwork.prison.Main;
import dev.chromenetwork.prison.Chat.ServerMessage;
import dev.chromenetwork.prison.Chat.ServerMessage.MessageType;
import dev.chromenetwork.prison.Commands.PluginCommandArgument;
import dev.chromenetwork.prison.Mines.MineManager;
import dev.chromenetwork.prison.Permissions.Permissions;

public class SubCmd_setregion extends PluginCommandArgument {
	public SubCmd_setregion() {
		this.setCommand("setregion");
		this.setPermission(Permissions.MINES_ADMIN.getPermission());
	}
	
	@Override
	public void command(Player player, String command, String[] args) {
		if(args.length < 3 && args.length > 3) {
			ServerMessage.get().sendMessage(player, MessageType.ERROR, "There was an issue. If you need help type "+ChatColor.YELLOW+"/mine adminhelp");
			return;
		}
		
		String name = args[2];
		
		MineManager MineManager = (MineManager) Main.Managers.getManager(MineManager.class);
		if(!MineManager.doesMineExist(name)) {
			ServerMessage.get().sendMessage(player, MessageType.ERROR, "There is no mine with that name.");
			return;
		}
		
		MineManager.setMineRegion(player, name);
		ServerMessage.get().sendMessage(player, MessageType.HELPHEADER, "");
		player.sendMessage(ChatColor.GREEN+"You have successfully set the mine region.");
		player.sendMessage(ChatColor.GRAY+"The next step is to set the spawn where players are teleported when entering the mine.");
		player.sendMessage(" ");
		player.sendMessage(ChatColor.AQUA+"Step 1) "+ChatColor.GRAY+"Stand in the location where you want the spawn to be.");
		player.sendMessage(ChatColor.RED+"IMPORTANT - Where you are looking when you set the spawn point is where they will be looking when they enter.");
		player.sendMessage(" ");
		player.sendMessage(ChatColor.AQUA+"Step 2) "+ChatColor.GRAY+"Type the command: "+ChatColor.YELLOW+"/mine setspawn "+name);
		return;
	}
}
