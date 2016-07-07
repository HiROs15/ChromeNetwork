package dev.chromenetwork.prison.Managers;

import java.util.ArrayList;
import java.util.Set;

import org.reflections.Reflections;

public class Managers {
	private ArrayList<Manager> managers = new ArrayList<Manager>();
	
	public Managers() {
		this.loadManagers();
	}
	
	private void loadManagers() {
		Reflections reflections = new Reflections("dev.chromenetwork.prison");
		Set<Class<? extends Manager>> classes = reflections.getSubTypesOf(Manager.class);
		
		for(Class<? extends Manager> clazz : classes) {
			if(clazz != Manager.class) {
				try {
					managers.add(clazz.newInstance());
				} catch(Exception e){}
			}
		}
	}
	
	public Manager getManager(Class<? extends Manager> clazz) {
		for(Manager m : managers) {
			if(m.getClass() == clazz) {
				return m;
			}
		}
		return null;
	}
}
