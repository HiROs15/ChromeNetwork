package dev.chromenetwork.prison.Mines.Commands.Mine;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import dev.chromenetwork.prison.Chat.ServerMessage;
import dev.chromenetwork.prison.Chat.ServerMessage.MessageType;
import dev.chromenetwork.prison.Commands.PluginCommand;
import dev.chromenetwork.prison.Permissions.Permissions;

public class Command_mine extends PluginCommand {
	public Command_mine() {
		this.setCommand("mine");
		this.setPermission("chromenetwork.prison.mine");
		
		this.addArgument(new SubCmd_create());
		this.addArgument(new SubCmd_setregion());
		this.addArgument(new SubCmd_setspawn());
		this.addArgument(new SubCmd_settings());
	}
	
	@Override
	public void help(Player player) {
		ServerMessage.get().sendMessage(player, MessageType.HELPHEADER, "");
		player.sendMessage(ChatColor.AQUA+"/mine join <mine name> ");
		player.sendMessage(ChatColor.WHITE+"This will allow you to join a mine.");
		player.sendMessage(" ");
		if(player.hasPermission(Permissions.MINES_ADMIN.getPermission())) {
			player.sendMessage(ChatColor.AQUA+"/mine adminhelp");
			player.sendMessage(ChatColor.WHITE+"Display all the admin commands for mines.");
		}
	}
	
	@Override
	public void command(Player player, String command, String[] args) {
		
	}
}
