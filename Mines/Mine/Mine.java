package dev.chromenetwork.prison.Mines.Mine;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import org.bukkit.Bukkit;

import com.esotericsoftware.kryo.io.Output;

import dev.chromenetwork.prison.Main;

public class Mine {
	private MineData data;
	
	//Admin Stuff
	private boolean highlightingMineRegion;
	
	//Non persistent vars
	int timer = 0;
	
	public Mine(MineData data) {
		this.data = data;
		
		this.save();
	}
	
	public Mine(String name) {
		this.data = new MineData();
		this.data.name = name;
		this.data.uuid = UUID.randomUUID();
		
		this.save();
	}
	
	public MineData getData() {
		return data;
	}
	
	public void setHighlightMineRegion(boolean b) {
		this.highlightingMineRegion = b;
	}
	
	public boolean getHighlightMineRegion() {
		return this.highlightingMineRegion;
	}
	
	public void save() {
		if(data == null) {
			return;
		}
		
		UUID u = data.uuid;
		File file = new File(Main.Plugin.getDataFolder()+"/Mines/MineData/"+u.toString()+".mine");
		
		try {
			Output output = new Output(new FileOutputStream(file));
			Main.Plugin.getSerializer().writeObject(output, this.data);
			output.close();
		} catch(Exception e) {
			Bukkit.getLogger().warning("Could not save mine data.");
		}
	}
}
