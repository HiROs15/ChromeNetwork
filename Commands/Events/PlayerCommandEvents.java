package dev.chromenetwork.prison.Commands.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import dev.chromenetwork.prison.Main;
import dev.chromenetwork.prison.Commands.CommandManager;
import dev.chromenetwork.prison.Events.PluginEvent;

public class PlayerCommandEvents extends PluginEvent {
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		CommandManager CommandManager = (CommandManager) Main.Managers.getManager(CommandManager.class);
		if(CommandManager.handleCommand(event.getPlayer(), event.getMessage())) {
			event.setCancelled(true);
		}
	}
}
