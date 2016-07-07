package dev.chromenetwork.prison.Config.Configs;


import java.util.Arrays;
import java.util.List;

import dev.chromenetwork.prison.Config.PluginConfig;

public class Config_settings extends PluginConfig {
	public Config_settings() {
		this.setFileName("Settings/settings.yml");
	}

	@Override
	public void setupConfig() {
		this.getConfig().set("setup", true);
		
		List<String> stringsbossbar = Arrays.asList("&aPage 1","&cPage 2");
		this.getConfig().set("settings.spawn.bossbar.messages",stringsbossbar);
		this.getConfig().set("settings.spawn.bossbar.rotateTimeSeconds", 10);
		this.saveConfig();
	}
}
