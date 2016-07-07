package dev.chromenetwork.prison.Spawn.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import dev.chromenetwork.prison.Main;
import dev.chromenetwork.prison.Commands.PluginCommand;
import dev.chromenetwork.prison.Spawn.SpawnManager;

public class Command_setspawn extends PluginCommand {
	public Command_setspawn() {
		this.setCommand("setspawn");
		this.setPermission("chromenetwork.prison.setspawn");
		this.setUsage("/setspawn <optional: Spawn Name>");
	}

	@Override
	public void command(Player player, String command, String[] args) {
		if(args.length == 1) {
			SpawnManager SpawnManager = (SpawnManager) Main.Managers.getManager(SpawnManager.class);
			if(SpawnManager.createSpawn(player.getLocation())) {
				player.sendMessage(ChatColor.GREEN+""+ChatColor.BOLD+"SUCCESS "+ChatColor.RESET+""+ChatColor.GRAY+"You have successfully set the spawn.");
			}
		}
	}
}
