package dev.chromenetwork.prison.Config;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

import dev.chromenetwork.prison.Main;

public abstract class PluginConfig {
	private String fileName;
	private Config config;
	
	public PluginConfig() {
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
		File file = new File(Main.Plugin.getDataFolder()+"/"+this.fileName);
		if(!file.exists()) {
			this.setupConfig();
		}
	}
	
	public String getFileName() {
		return this.fileName;
	}
	
	public abstract void setupConfig();
	
	public FileConfiguration getConfig() {
		if(config == null) {
			config = new Config();
			config.setFileName(this.fileName);
			return config.getConfig();
		} else {
			return config.getConfig();
		}
	}
	
	public void saveConfig() {
		config.saveConfig();
		config = null;
	}
}
