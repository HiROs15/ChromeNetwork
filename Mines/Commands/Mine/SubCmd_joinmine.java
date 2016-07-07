package dev.chromenetwork.prison.Mines.Commands.Mine;

import org.bukkit.entity.Player;

import dev.chromenetwork.prison.Main;
import dev.chromenetwork.prison.Chat.ServerMessage;
import dev.chromenetwork.prison.Chat.ServerMessage.MessageType;
import dev.chromenetwork.prison.Commands.PluginCommandArgument;
import dev.chromenetwork.prison.Mines.MineManager;

public class SubCmd_joinmine extends PluginCommandArgument {
	public SubCmd_joinmine() {
		this.setCommand("join");
		this.setPermission("chromenetwork.prison.joinmine");
	}
	
	@Override
	public void command(Player player, String command, String[] args) {
		if(args.length > 3 || args.length < 3) {
			ServerMessage.get().sendMessage(player, MessageType.ERROR, "There was an error in your command.");
			return;
		}
		
		MineManager MineManager = (MineManager) Main.Managers.getManager(MineManager.class);
		String name = args[2];
		
		if(!MineManager.doesMineExist(name)) {
			ServerMessage.get().sendMessage(player, MessageType.ERROR, "There is no mine with that name.");
			return;
		}
		
		MineManager.joinMine(name, player);
		return;
	}
}
