package dev.chromenetwork.prison.Managers;

public abstract class Manager {
	private boolean autoload = true;
	
	public void setAutoload(boolean v) {
		this.autoload = v;
	}
	
	public boolean getAutoload() {
		return this.autoload;
	}
}
