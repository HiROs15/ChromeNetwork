package dev.chromenetwork.prison.Spawn;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.minlog.Log;

import dev.chromenetwork.prison.Main;
import dev.chromenetwork.prison.Managers.Manager;

public class SpawnManager extends Manager {
	private ArrayList<Spawn> spawns = new ArrayList<Spawn>();
	
	public SpawnManager() {
		this.loadSpawns();
	}
	
	public ArrayList<Spawn> getSpawns() {
		return this.spawns;
	}
	
	private void loadSpawns() {
		spawns.clear();
		
		File folder = new File(Main.Plugin.getDataFolder()+"/Spawns/");
		if(!folder.exists()) {
			folder.mkdirs();
		}
		for(File f : folder.listFiles()) {
			try {
				Log.ERROR();
				Input input = new Input(new FileInputStream(f));
				SpawnData spawndata = Main.Plugin.getSerializer().readObject(input, SpawnData.class);
				Spawn spawn = new Spawn(spawndata);
				spawns.add(spawn);
				input.close();
			} catch(Exception e) {
				System.out.println(e.getCause());
			}
		}
	}
	
	public boolean createSpawn(Location loc) {
		UUID uuid = UUID.randomUUID();
		Spawn spawn = new Spawn();
		spawn.setUUID(uuid);
		spawn.setPlayerSpawnLocation(loc);
		spawn.save();
		spawns.add(spawn);
		
		this.loadSpawns();
		return true;
	}
	
	public void joinSpawn(Player player, boolean noRecord) {
		if(spawns.size() == 0) {
			if(player.isOp()) {
				player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"ERROR "+ChatColor.RESET+""+ChatColor.GRAY+"There are not spawns setup. Please use /setspawn to create them.");
			}
			return;
		}
		
		Spawn spawn = spawns.get(0);
		spawn.joinSpawn(player, noRecord);
	}
	
	public void joinSpawn(Player player) {
		this.joinSpawn(player, false);
	}
	
	public Spawn getPlayerSpawn(Player player) {
		for(Spawn spawn : this.spawns) {
			if(spawn.isPlayerInSpawn(player)) {
				return spawn;
			}
		}
		return null;
	}
	
	public boolean isPlayerInSpawn(Player player) {
		for(Spawn spawn : this.spawns) {
			if(spawn.isPlayerInSpawn(player)) {
				return true;
			}
		}
		return false;
	}
	
	public void leaveSpawn(Player player) {
		this.getPlayerSpawn(player).leaveSpawn(player);
	}
}
