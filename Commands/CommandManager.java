package dev.chromenetwork.prison.Commands;

import java.util.ArrayList;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.reflections.Reflections;

import dev.chromenetwork.prison.Managers.Manager;

public class CommandManager extends Manager {
	private ArrayList<PluginCommand> commands = new ArrayList<PluginCommand>();
	
	public CommandManager() {
		this.loadCommands();
	}
	
	private void loadCommands() {
		Reflections reflections = new Reflections("dev.chromenetwork.prison");
		Set<Class<? extends PluginCommand>> classes = reflections.getSubTypesOf(PluginCommand.class);
		
		for(Class<? extends PluginCommand> clazz : classes) {
			try {
				commands.add(clazz.newInstance());
			} catch(Exception e) {}
		}
	}
	
	public boolean handleCommand(Player player, String message) {
		String[] args = message.split(" ");
		String command = args[0].substring(1);
		
		for(PluginCommand cmd : commands) {
			if(cmd.getAlts().size() > 0) {
				for(String c : cmd.getAlts()) {
					if(c.equalsIgnoreCase(command)) {
						if(!cmd.getPermission().equals("*") && !player.hasPermission(cmd.getPermission())) {
							player.sendMessage(ChatColor.WHITE+""+ChatColor.BOLD+"Chrome"+ChatColor.AQUA+""+ChatColor.BOLD+"Network "+ChatColor.RESET+""+ChatColor.RED+"You do not have permission to perform this command.");
							return true;
						}
						if(args.length == 2 && args[1].equalsIgnoreCase("help")) {
							cmd.help(player);
							return true;
						}
						cmd.command(player, command, args);
						return true;
					}
				}
			}
			if(cmd.getCommand().equalsIgnoreCase(command)) {
				if(!cmd.getPermission().equals("*") && !player.hasPermission(cmd.getPermission())) {
					player.sendMessage(ChatColor.WHITE+""+ChatColor.BOLD+"Chrome"+ChatColor.AQUA+""+ChatColor.BOLD+"Network "+ChatColor.RESET+""+ChatColor.RED+"You do not have permission.");
					return true;
				}
				if(args.length == 2 && args[1].equalsIgnoreCase("help")) {
					cmd.help(player);
					return true;
				}
				for(PluginCommandArgument a : cmd.getArguments()) {
					if(a.getArgument().equalsIgnoreCase(args[1])) {
						if(!player.hasPermission(a.getPermission())) {
							player.sendMessage(ChatColor.WHITE+""+ChatColor.BOLD+"Chrome"+ChatColor.AQUA+""+ChatColor.BOLD+"Network "+ChatColor.RESET+""+ChatColor.RED+"You do not have permission.");
							return true;
						}
						a.command(player, command, args);
						return true;
					}
				}
				cmd.command(player, command, args);
				return true;
			}
		}
		return false;
	}
}
