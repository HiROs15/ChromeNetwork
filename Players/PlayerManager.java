package dev.chromenetwork.prison.Players;

import java.io.File;
import java.io.FileInputStream;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.esotericsoftware.kryo.io.Input;

import dev.chromenetwork.prison.Main;
import dev.chromenetwork.prison.Managers.Manager;

public class PlayerManager extends Manager {
	public PlayerManager() {
		
	}
	
	public PrisonPlayer getPlayer(Player player) {
		File file = new File(Main.Plugin.getDataFolder()+"/Players/"+player.getUniqueId().toString()+".dat");
		if(!file.exists()) {
			return null;
		}
		
		try {
			Input input = new Input(new FileInputStream(file));
			PrisonPlayer p = Main.Plugin.getSerializer().readObject(input, PrisonPlayer.class);
			input.close();
			return p;
		} catch(Exception e) {
			Bukkit.getLogger().info("ERROR: "+e.getMessage());
			return null;
		}
	}
}
