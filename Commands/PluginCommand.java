package dev.chromenetwork.prison.Commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class PluginCommand {
	private String command;
	private String permission;
	private boolean allowConsole = true;
	private String usage;
	private ArrayList<String> arghelplines;
	private ArrayList<PluginCommandArgument> arguments = new ArrayList<PluginCommandArgument>();
	private ArrayList<String> alts = new ArrayList<String>();
	
	public void setCommand(String command) {
		this.command = command;
	}
	
	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	public String getCommand() {
		return this.command;
	}
	
	public String getPermission() {
		return this.permission;
	}
	
	public void setAllowConsole(boolean b) {
		this.allowConsole = b;
	}
	
	public void setUsage(String usage) {
		this.usage = usage;
	}
	
	public void addAlt(String cmd) {
		this.alts.add(cmd);
	}
	
	public ArrayList<String> getAlts() {
		return this.alts;
	}
	
	public void addArgHelpLine(String helpLine) {
		arghelplines.add(helpLine);
	}
	
	public boolean getAllowConsole() {
		return this.allowConsole;
	}
	
	public String getUsage() {
		return this.usage;
	}
	
	public ArrayList<String> getArgHelpLines() {
		return this.arghelplines;
	}
	
	public ArrayList<PluginCommandArgument> getArguments() {
		return this.arguments;
	}
	
	public void addArgument(PluginCommandArgument argument) {
		this.arguments.add(argument);
	}
	
	public abstract void command(Player player, String command, String[] args);
	
	public void help(Player player) {
		player.sendMessage(ChatColor.YELLOW+"=============== "+ChatColor.WHITE+""+ChatColor.BOLD+"Chrome"+ChatColor.AQUA+""+ChatColor.BOLD+"Network"+ChatColor.RESET+""+ChatColor.YELLOW+" ===============");
		player.sendMessage(ChatColor.GOLD+"Command: "+ChatColor.WHITE+""+this.command);
		player.sendMessage(" ");
		player.sendMessage(ChatColor.GOLD+"Usage: "+this.getUsage());
	}
}
