package dev.chromenetwork.prison.Config;

import java.util.Set;

import org.reflections.Reflections;

import dev.chromenetwork.prison.Managers.Manager;

public class ConfigManager extends Manager {
	public ConfigManager() {
		this.loadConfigs();
	}
	
	private void loadConfigs() {
		Reflections reflections = new Reflections("dev.chromenetwork.prison");
		Set<Class<? extends PluginConfig>> classes = reflections.getSubTypesOf(PluginConfig.class);
		
		for(Class<? extends PluginConfig> clazz : classes) {
			try {
				clazz.newInstance();
			} catch(Exception e) {}
		}
	}
}
