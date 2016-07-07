package dev.chromenetwork.prison.Commands;

import org.bukkit.entity.Player;

public abstract class PluginCommandArgument {
	private String argument;
	private String permission;
	
	public String getArgument() {
		return this.argument;
	}
	
	public String getPermission() {
		return this.permission;
	}
	
	public void setCommand(String cmd) {
		this.argument = cmd;
	}
	
	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	public abstract void command(Player player, String command, String[] args);
}
