package dev.chromenetwork.prison.Spawn;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.bossbar.BossBar;
import org.inventivetalent.bossbar.BossBarAPI;

import com.esotericsoftware.kryo.io.Output;

import dev.chromenetwork.prison.Main;
import dev.chromenetwork.prison.Chat.ServerMessage;
import dev.chromenetwork.prison.Chat.ServerMessage.MessageType;
import dev.chromenetwork.prison.Config.Configs.Config_settings;
import dev.chromenetwork.prison.Libs.Titles.Title;
import dev.chromenetwork.prison.SerializedCraftbukkit.SerializedLocation;

public class Spawn {
	private SpawnData data;
	private ArrayList<SpawnPlayer> players = new ArrayList<SpawnPlayer>();
	
	public Spawn() {
		this.data = new SpawnData();
		this.update();
	}
	
	public Spawn(SpawnData data) {
		this.data = data;
		
		this.update();
	}
	
	public void update() {
		players.clear();
	}
	
	public void setUUID(UUID uuid) {
		data.uuid = uuid;
	}
	
	// Function gets
	
	public UUID getUUID() { return data.uuid; }
	public Location getSpawnLocation() { return data.spawnLocation.toBukkit(); }
	public ArrayList<SpawnPlayer> getPlayers() { return this.players; }
	
	public void setPlayerSpawnLocation(Location loc) {
		data.spawnLocation = new SerializedLocation(loc);
	}
	
	public void save() {
		File file = new File(Main.Plugin.getDataFolder()+"/Spawns/"+getUUID()+".dat");
		try {
			Output output = new Output(new FileOutputStream(file));
			Main.Plugin.getSerializer().writeObject(output, data);
			output.close();
		} catch(Exception e) {}
	}
	
	public boolean isPlayerInSpawn(Player player) {
		for(SpawnPlayer p : players) {
			if(p.getPlayer().getUniqueId().toString().equals(player.getUniqueId().toString())) {
				return true;
			}
		}
		return false;
	}
	
	public void joinSpawn(Player player, boolean noRecord) {
		if(!this.isPlayerInSpawn(player)) {
			this.players.add(new SpawnPlayer(player));
			this.displayJoinTitle(player);
			this.bossBarStartup(player);
		}
		
		// Teleport the player and stuff :)
		player.teleport(data.spawnLocation.toBukkit());
		player.setHealth(20);
		player.setFoodLevel(20);
		player.setGameMode(GameMode.SURVIVAL);
	}
	
	public void joinSpawn(Player player) {
		this.joinSpawn(player, false);
	}
	
	private void displayJoinTitle(Player player) {
		Title title = new Title(ChatColor.WHITE+""+ChatColor.BOLD+"Chrome"+ChatColor.AQUA+""+ChatColor.BOLD+"Network",ChatColor.GOLD+"Prison",1,4,1);
		title.setTitleColor(ChatColor.AQUA);
		title.setSubtitleColor(ChatColor.GOLD);
		title.send(player);
	}
	
	private void bossBarStartup(final Player player) {
		final Config_settings config = new Config_settings();
		@SuppressWarnings("unchecked")
		final ArrayList<String> bossBarStrings = (ArrayList<String>) config.getConfig().getList("settings.spawn.bossbar.messages");
		
		
		new BukkitRunnable() {
			int bossBarIndex = 0;
			BossBar bossBar;
			@SuppressWarnings("deprecation")
			public void run() {
				if(isPlayerInSpawn(player) == false) {
					this.cancel();
				}
				if(bossBar == null) {
					
				} else {
					bossBar.removePlayer(player);
				}
					BossBarAPI.setMessage(player, ChatColor.translateAlternateColorCodes('&', bossBarStrings.get(bossBarIndex)), (float) 100.0);
					if(bossBarIndex >= bossBarStrings.size()-1) {
						bossBarIndex = 0;
					} else {
						bossBarIndex++;
					}
				}
		}.runTaskTimer(Main.Plugin, 0L, (long) (config.getConfig().getInt("settings.spawn.bossbar.rotateTimeSeconds")*20));
	}
	
	public void leaveSpawn(Player player) {
		Iterator<SpawnPlayer> it = players.iterator();
		while(it.hasNext()) {
			SpawnPlayer n = it.next();
			if(n.getPlayer().getName().equals(player.getName())) {
				it.remove();
				BossBarAPI.removeAllBars(player);
			}
		}
	}
}
