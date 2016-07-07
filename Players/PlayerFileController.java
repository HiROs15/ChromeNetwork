package dev.chromenetwork.prison.Players;

import java.io.File;
import java.io.FileOutputStream;

import org.bukkit.entity.Player;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

import dev.chromenetwork.prison.Main;

public class PlayerFileController {
	public static PlayerFileController get() {
		return new PlayerFileController();
	}
	
	public boolean checkIfPlayerFileExists(Player player) {
		File dirs = new File(Main.Plugin.getDataFolder()+"/Players/");
		dirs.mkdirs();
		
		File f = new File(Main.Plugin.getDataFolder()+"/Players/"+player.getUniqueId().toString()+".dat");
		if(!f.exists()) {
			return false;
		} else {
			return true;
		}
	}
	
	public PrisonPlayer createPlayerFile(Player player) {
		PrisonPlayer p = new PrisonPlayer(player);
		
		Kryo kryo = new Kryo();
		try {
			File file = new File(Main.Plugin.getDataFolder()+"/Players/"+player.getUniqueId().toString()+".dat");
			Output out = new Output(new FileOutputStream(file));
			kryo.writeObject(out, p);
			out.close();
		} catch(Exception e){}
		return p;
	}
}
