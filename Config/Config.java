package dev.chromenetwork.prison.Config;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import dev.chromenetwork.prison.Main;

public class Config {
	private File file = null;
	private FileConfiguration config = null;
	private String filename = "";
	
	public Config() {
		
	}
	
	public void setFileName(String filename) {
		this.filename = filename;
	}
	
	public String getFileName() {
		return this.filename;
	}
	
	public void reloadConfig() {
		if(file == null) {
			file = new File(Main.Plugin.getDataFolder()+"/"+filename);
		}
		config = YamlConfiguration.loadConfiguration(file);
	}
	
	public FileConfiguration getConfig() {
		if(config == null) {
			reloadConfig();
		}
		
		return config;
	}
	
	public void saveConfig() {
		if(config == null) {
			reloadConfig();
		}
		try {
			config.save(file);
		} catch(Exception e) {}
	}
}
