package dev.chromenetwork.prison.Mines.Events;

import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import dev.chromenetwork.prison.Main;
import dev.chromenetwork.prison.Chat.ServerMessage;
import dev.chromenetwork.prison.Chat.ServerMessage.MessageType;
import dev.chromenetwork.prison.Events.PluginEvent;
import dev.chromenetwork.prison.Mines.MineManager;

public class MineEvents extends PluginEvent {
	@EventHandler
	public void onPlayerSpawnHologram(PlayerInteractEvent event) {
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		ItemStack item = event.getPlayer().getItemInHand();
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		if(nmsItem.getTag().hasKey("prison.mine.hologram")) {
			String mine = nmsItem.getTag().getString("mine.name");
			String type = nmsItem.getTag().getString("hologram.type");
			
			MineManager MineManager = (MineManager) Main.Managers.getManager(MineManager.class);
			MineManager.getMine(mine).spawnHologram(event.getClickedBlock().getLocation(), type);
			ServerMessage.get().sendMessage(event.getPlayer(), MessageType.SUCCESS, "You have spawned a hologram for the mine!");
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			MineManager MineManager = (MineManager) Main.Managers.getManager(MineManager.class);
			if(MineManager.isPlayerInMine(player)) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerStarve(FoodLevelChangeEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			MineManager MineManager = (MineManager) Main.Managers.getManager(MineManager.class);
			if(MineManager.isPlayerInMine(player)) {
				event.setCancelled(true);
				((Player)event.getEntity()).setFoodLevel(20);
			}
		}
	}
	
	@EventHandler
	public void onPlayerBreakBlock(BlockBreakEvent event) {
		Player player = event.getPlayer();
		
		MineManager MineManager = (MineManager) Main.Managers.getManager(MineManager.class);
		if(MineManager.isPlayerInMine(player)) {
			event.setCancelled(true);
			MineManager.getPlayerMine(player).breakBlock(player, event.getBlock());
		}
	}
}
