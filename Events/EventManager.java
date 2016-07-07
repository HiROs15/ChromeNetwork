package dev.chromenetwork.prison.Events;

import java.util.Set;

import org.bukkit.Bukkit;
import org.reflections.Reflections;

import dev.chromenetwork.prison.Main;
import dev.chromenetwork.prison.Managers.Manager;

public class EventManager extends Manager {
	public EventManager() {
		this.loadEvents();
	}
	
	private void loadEvents() {
		Reflections reflections = new Reflections("dev.chromenetwork.prison");
		Set<Class<? extends PluginEvent>> classes = reflections.getSubTypesOf(PluginEvent.class);
		
		for(Class<? extends PluginEvent> clazz : classes) {
			try {
				PluginEvent event = clazz.newInstance();
				Bukkit.getServer().getPluginManager().registerEvents(event, Main.Plugin);
			} catch(Exception e) {}
		}
	}
}
