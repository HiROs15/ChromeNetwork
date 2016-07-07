package dev.chromenetwork.prison.Players;

import java.io.File;
import java.io.FileOutputStream;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.esotericsoftware.kryo.io.Output;

import dev.chromenetwork.prison.Main;

public class PrisonPlayer {
	private String uuid;
	private boolean isAdmin = false;
	private int tokens = 9999;
	private int cash = 0;
	
	public PrisonPlayer(Player player) {
		this.uuid = player.getUniqueId().toString();
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public boolean isAdmin() {
		return this.isAdmin;
	}
	
	public int getTokens() {
		return this.tokens;
	}
	
	public int getCash() {
		return this.cash;
	}
	
	public void setTokens(int i) {
		this.tokens = i;
	}
	
	public void setCash(int i) {
		this.cash = i;
	}
	
	public void save() {
		File file = new File(Main.Plugin.getDataFolder()+"/Players/"+this.uuid+".dat");
		try {
			Output output = new Output(new FileOutputStream(file));
			Main.Plugin.getSerializer().writeObject(output, this);
			output.close();
		} catch(Exception e) {
			Bukkit.getLogger().warning("Could not save prison player data file.");
		}
	}
}
