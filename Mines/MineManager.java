package dev.chromenetwork.prison.Mines;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

import dev.chromenetwork.prison.Main;
import dev.chromenetwork.prison.Managers.Manager;
import dev.chromenetwork.prison.Mines.Mine.Mine;
import dev.chromenetwork.prison.Mines.Mine.MineData;
import dev.chromenetwork.prison.SerializedCraftbukkit.SerializedLocation;
import dev.chromenetwork.prison.Spawn.SpawnManager;

public class MineManager extends Manager {
	private ArrayList<Mine> mines = new ArrayList<Mine>();
	
	public MineManager() {
		this.loadMines();
	}
	
	private void loadMines() {
		//Create the dirs if needed.
		File dir = new File(Main.Plugin.getDataFolder()+"/Mines/MineData");
		if(!dir.exists()) {
			dir.mkdirs();
		}
		
		mines.clear();
		
		for(File file : dir.listFiles()) {
			try {
				Input input = new Input(new FileInputStream(file));
				
				Kryo kryo = Main.Plugin.getSerializer();
				kryo.register(MineData.class);
				MineData data = kryo.readObject(input, MineData.class);
				input.close();
				
				mines.add(new Mine(data));
			} catch(Exception e) {
				Bukkit.getLogger().warning("Could not load mines. Maybe corrupted: "+e.getMessage());
			}
		}
	}
	
	public boolean doesMineExist(String name) {
		for(Mine m : mines) {
			if(m.getData().name.equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	
	public void createMine(String name) {
		mines.add(new Mine(name));
	}
	
	public Mine getMine(String name) {
		for(Mine m : mines) {
			if(m.getData().name.equalsIgnoreCase(name)) {
				return m;
			}
		}
		return null;
	}
	
	public void setMineRegion(Player player, String mineName) {
		Mine mine = getMine(mineName);
		
		Location min = Main.WorldEdit.getSelection(player).getMinimumPoint();
		Location max = Main.WorldEdit.getSelection(player).getMaximumPoint();
		
		mine.getData().mineRegionMin = new SerializedLocation(min);
		mine.getData().mineRegionMax = new SerializedLocation(max);
		
		mine.save();
	}
	
	public void setMineSpawn(Player player, String mineName) {
		Mine mine = getMine(mineName);
		mine.getData().spawnLocation = new SerializedLocation(player.getLocation());
		mine.save();
	}
	
	public boolean isPlayerInMine(Player player) {
		for(Mine m : mines) {
			if(m.isPlayerInMine(player)) {
				return true;
			}
		}
		return false;
	}
	
	public void joinMine(String name, Player player) {
		SpawnManager SpawnManager = (SpawnManager) Main.Managers.getManager(SpawnManager.class);
		if(SpawnManager.isPlayerInSpawn(player)) {
			SpawnManager.leaveSpawn(player);
		}
		
		Mine mine = getMine(name);
		mine.joinMine(player);
	}
	
	public Mine getPlayerMine(Player player) {
		for(Mine m : mines) {
			if(m.isPlayerInMine(player)) {
				return m;
			}
		}
		return null;
	}
}
