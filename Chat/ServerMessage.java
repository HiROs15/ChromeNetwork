package dev.chromenetwork.prison.Chat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ServerMessage {
	public static ServerMessage get() {
		return new ServerMessage();
	}
	
	public enum MessageType {
		ERROR, SUCCESS, SERVERNAME, HELPHEADER
	}
	
	public void sendMessage(Player player, MessageType type, String message) {
		String prefix = "";
		
		if(type == MessageType.ERROR) {
			prefix = ChatColor.RED+""+ChatColor.BOLD+"ERROR "+ChatColor.RESET+""+ChatColor.GRAY+"";
		}
		if(type == MessageType.SUCCESS) {
			prefix = ChatColor.GREEN+""+ChatColor.BOLD+"SUCCESS "+ChatColor.RESET+""+ChatColor.GRAY+"";
		}
		if(type == MessageType.SERVERNAME) {
			prefix = ChatColor.WHITE+""+ChatColor.BOLD+"Chrome"+ChatColor.AQUA+""+ChatColor.BOLD+"Network "+ChatColor.RESET+""+ChatColor.GRAY+"";
		}
		if(type == MessageType.HELPHEADER) {
			prefix = ChatColor.YELLOW+"------------------- "+ChatColor.WHITE+""+ChatColor.BOLD+"Chrome"+ChatColor.AQUA+""+ChatColor.BOLD+"Network"+ChatColor.RESET+""+ChatColor.YELLOW+" ------------------";
		}
		
		player.sendMessage(prefix+""+message);
	}
}
