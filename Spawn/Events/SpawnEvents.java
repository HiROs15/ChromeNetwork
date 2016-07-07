package dev.chromenetwork.prison.Spawn.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import dev.chromenetwork.prison.Main;
import dev.chromenetwork.prison.Events.PluginEvent;
import dev.chromenetwork.prison.Libs.Particles.ParticleEffect;
import dev.chromenetwork.prison.Spawn.Spawn;
import dev.chromenetwork.prison.Spawn.SpawnManager;

public class SpawnEvents extends PluginEvent {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		// Add player to spawn.
		SpawnManager SpawnManager = (SpawnManager) Main.Managers.getManager(SpawnManager.class);
		SpawnManager.joinSpawn(event.getPlayer());
		event.setJoinMessage("");
	}
	
	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent event) {
		// Check if its a spawn world
		SpawnManager SpawnManager = (SpawnManager) Main.Managers.getManager(SpawnManager.class);
		for(Spawn spawn : SpawnManager.getSpawns()) {
			if(event.getLocation().getWorld().getName().equals(spawn.getSpawnLocation().getWorld().getName())) {
				if(event.getSpawnReason() == SpawnReason.NATURAL) {
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent event) {
		SpawnManager SpawnManager = (SpawnManager) Main.Managers.getManager(SpawnManager.class);
		for(Spawn spawn : SpawnManager.getSpawns()) {
			if(event.getWorld().getName().equals(spawn.getSpawnLocation().getWorld().getName())) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		SpawnManager SpawnManager = (SpawnManager) Main.Managers.getManager(SpawnManager.class);
		if(SpawnManager.isPlayerInSpawn(event.getPlayer())) {
			SpawnManager.leaveSpawn(event.getPlayer());
			event.setQuitMessage("");
		}
	}
	
	@EventHandler
	public void onPlayerStarve(FoodLevelChangeEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			SpawnManager SpawnManager = (SpawnManager) Main.Managers.getManager(SpawnManager.class);
			if(SpawnManager.isPlayerInSpawn(player)) {
				event.setCancelled(true);
				player.setFoodLevel(20);
			}
		}
	}
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			
			SpawnManager SpawnManager = (SpawnManager) Main.Managers.getManager(SpawnManager.class);
			if(SpawnManager.isPlayerInSpawn(player)) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerBreakBlock(BlockBreakEvent event) {	
		Player player = event.getPlayer();
		SpawnManager SpawnManager = (SpawnManager) Main.Managers.getManager(SpawnManager.class);
		if(SpawnManager.isPlayerInSpawn(player)) {
			event.setCancelled(true);
			if(event.getBlock().getType().isSolid()) {
				ParticleEffect.BARRIER.display(0, 0, 0, 1, 1, event.getBlock().getLocation().add(0.5, 1.5,0.5), player);
			} else {
				ParticleEffect.BARRIER.display(0, 0, 0, 1, 1, event.getBlock().getLocation().add(0.5, 0.5,0.5), player);
			}
		}
	}
	
	@EventHandler
	public void onBlockPlayer(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		SpawnManager SpawnManager = (SpawnManager) Main.Managers.getManager(SpawnManager.class);
		if(SpawnManager.isPlayerInSpawn(player)) {
			event.setCancelled(true);
			if(event.getBlock().getType().isSolid()) {
				ParticleEffect.BARRIER.display(0, 0, 0, 1, 1, event.getBlock().getLocation().add(0.5, 1.5,0.5), player);
			} else {
				ParticleEffect.BARRIER.display(0, 0, 0, 1, 1, event.getBlock().getLocation().add(0.5, 0.5,0.5), player);
			}
		}
	}
	
	@EventHandler
	public void onPlayerTeleOut(PlayerTeleportEvent event) {
		SpawnManager SpawnManager = (SpawnManager) Main.Managers.getManager(SpawnManager.class);
		Player player = event.getPlayer();
		if(SpawnManager.isPlayerInSpawn(player)) {
			if(!event.getFrom().getWorld().getName().equals(event.getTo().getWorld().getName())) {
				SpawnManager.leaveSpawn(player);
				return;
			}
			
			if(event.getFrom().distance(event.getTo()) > 100) {
				SpawnManager.leaveSpawn(player);
				return;
			}
		}
	}
}
