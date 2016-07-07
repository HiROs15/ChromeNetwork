package dev.chromenetwork.prison.Spawn;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import dev.chromenetwork.prison.Main;
import dev.chromenetwork.prison.Players.PlayerManager;
import dev.chromenetwork.prison.Players.PrisonPlayer;

public class SpawnPlayer {
	private Player player;
	
	private ScoreboardManager scoreboardManager;
	private Scoreboard board;
	private Objective objective;
	
	private int tokens = 0;
	private int cash = 0;
	
	public SpawnPlayer(Player player) {
		this.player = player;
		scoreboardManager = Bukkit.getScoreboardManager();
		board = scoreboardManager.getNewScoreboard();
		objective = board.registerNewObjective(player.getName(), "dummy");
		objective.setDisplayName(" ");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		this.startSpawnScoreboard();
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	private void startSpawnScoreboard() {
		player.setScoreboard(board);
		new BukkitRunnable() {
			String header = "ChromeNetwork - Prison ";
			int checkFrame = 0;
			public void run() {
				SpawnManager SpawnManager = (SpawnManager) Main.Managers.getManager(SpawnManager.class);
				if(!SpawnManager.isPlayerInSpawn(player)) {
					this.cancel();
				}
				
				if(checkFrame == 0) {
					updateDataFromFile();
				}
				
				header = header.substring(1,header.length())+""+header.substring(0,1);
				String pheader = ChatColor.GOLD+""+ChatColor.BOLD+""+header;
				objective.setDisplayName(pheader.substring(0,16));
				
				getScore("$"+cash+"",1);
				getScore(ChatColor.GREEN+""+ChatColor.BOLD+"Money",2);
				getScore(" ",3);
				getScore(""+tokens+" Tokens",4);
				getScore(ChatColor.YELLOW+""+ChatColor.BOLD+"Tokens",5);
				getScore("  ",6);
				
				if(checkFrame == 12) {
					checkFrame = 0;
				} else {
					checkFrame++;
				}
			}
		}.runTaskTimer(Main.Plugin, 0L, 5L);
	}
	
	private Score getScore(String name, int score) {
		Score s = objective.getScore(name);
		s.setScore(score);
		return s;
	}
	
	private void updateDataFromFile() {
		PlayerManager PlayerManager = (PlayerManager) Main.Managers.getManager(PlayerManager.class);
		PrisonPlayer p = PlayerManager.getPlayer(player);
		
		if(p == null) {
			return;
		}
		
		this.cash = p.getCash();
		this.tokens = p.getTokens();
	}
}
