package dev.chromenetwork.prison.Players;

import org.bukkit.entity.Player;

public class PrisonPlayer {
	private String uuid;
	private boolean isAdmin = false;
	private int tokens = 9999;
	private int cash = 0;
	
	public PrisonPlayer(Player player) {
		this.uuid = player.getUniqueId().toString();
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public boolean isAdmin() {
		return this.isAdmin;
	}
	
	public int getTokens() {
		return this.tokens;
	}
	
	public int getCash() {
		return this.cash;
	}
}
