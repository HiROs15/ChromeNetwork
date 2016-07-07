package dev.chromenetwork.prison.Spawn.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import dev.chromenetwork.prison.Main;
import dev.chromenetwork.prison.Chat.ServerMessage;
import dev.chromenetwork.prison.Chat.ServerMessage.MessageType;
import dev.chromenetwork.prison.Commands.PluginCommand;
import dev.chromenetwork.prison.Spawn.SpawnManager;

public class Command_spawn extends PluginCommand {
	public Command_spawn() {
		this.setCommand("spawn");
		this.setPermission("*");
	}
	
	@Override
	public void command(Player player, String command, String[] args) {
		if(args.length > 1) {
			ServerMessage.get().sendMessage(player, MessageType.ERROR, "To use this command, type "+ChatColor.AQUA+"/spawn");
			return;
		}
		SpawnManager SpawnManager = (SpawnManager) Main.Managers.getManager(SpawnManager.class);
		if(!SpawnManager.isPlayerInSpawn(player)) {
			SpawnManager.joinSpawn(player, false);
		} else {
			SpawnManager.joinSpawn(player, true);
		}
	}
}
