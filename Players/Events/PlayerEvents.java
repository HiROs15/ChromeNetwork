package dev.chromenetwork.prison.Players.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import dev.chromenetwork.prison.Events.PluginEvent;
import dev.chromenetwork.prison.Players.PlayerFileController;

public class PlayerEvents extends PluginEvent {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if(!PlayerFileController.get().checkIfPlayerFileExists(event.getPlayer())) {
			PlayerFileController.get().createPlayerFile(event.getPlayer());
		}
	}
}
