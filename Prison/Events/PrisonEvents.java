package dev.chromenetwork.prison.Prison.Events;

import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import dev.chromenetwork.prison.Main;
import dev.chromenetwork.prison.Events.PluginEvent;
import dev.chromenetwork.prison.Prison.PrisonManager;

public class PrisonEvents extends PluginEvent {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		PrisonManager PrisonManager = (PrisonManager) Main.Managers.getManager(PrisonManager.class);
		if(!PrisonManager.doesPlayerHavePrisonTool(event.getPlayer())) {
			PrisonManager.givePlayerPrisonTool(event.getPlayer());
		}
	}
	
	@EventHandler
	public void onPlayerDropitem(PlayerDropItemEvent event) {
		org.bukkit.entity.Item item = event.getItemDrop();
		
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item.getItemStack());
		if(nmsItem.getTag() != null && nmsItem.getTag().hasKey("prison.tool")) {
			event.setCancelled(true);
		}
	}
}
