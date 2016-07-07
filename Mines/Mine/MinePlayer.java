package dev.chromenetwork.prison.Mines.Mine;

import java.math.BigDecimal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import dev.chromenetwork.prison.Main;
import dev.chromenetwork.prison.Libs.TimeConvert;
import dev.chromenetwork.prison.Mines.MineManager;
import dev.chromenetwork.prison.Players.PlayerManager;
import dev.chromenetwork.prison.Players.PrisonPlayer;

public class MinePlayer {
	private Player player;
	private int blocksBroken = 0;
	private int tokens = 0;
	private int cash = 0;
	
	private ScoreboardManager manager;
	private Scoreboard board;
	private Objective objective;
	
	public MinePlayer(Player player) {
		this.player = player;
		
		PlayerManager PlayerManager = (PlayerManager) Main.Managers.getManager(PlayerManager.class);
		PrisonPlayer prisonplayer = PlayerManager.getPlayer(player);
		
		this.tokens = prisonplayer.getTokens();
		this.cash = prisonplayer.getCash();
		
		manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		objective = board.registerNewObjective(player.getName(), "dummy");
	}
	
	public Player getPlayer() { return this.player; }
	
	public void addBlockBroken() {
		this.blocksBroken++;
	}
	
	public void setBlocksBroken(int i) {
		this.blocksBroken = 0;
	}
	
	public int getBlocksBroken() { return this.blocksBroken; }
	
	public void savePlayer() {
		PlayerManager PlayerManager = (PlayerManager) Main.Managers.getManager(PlayerManager.class);
		PrisonPlayer prisonplayer = PlayerManager.getPlayer(this.player);
		prisonplayer.setTokens(tokens);
		prisonplayer.setCash(cash);
		prisonplayer.save();
	}
	
	public void updateScoreboard() {
		MineManager MineManager = (MineManager) Main.Managers.getManager(MineManager.class);
		Mine mine = MineManager.getPlayerMine(player);
		
		int[] times = TimeConvert.splitToComponentTimes(new BigDecimal((mine.getData().resetTime-mine.getTimer())));
		objective.unregister();
		objective = board.registerNewObjective(player.getName(), "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(ChatColor.AQUA+""+ChatColor.BOLD+"Prison Mines: "+ChatColor.BLUE+""+ChatColor.BOLD+""+MineManager.getPlayerMine(player).getData().name);
		
		getScore(mine.getMinedBlocks()+" Blocks",1);
		getScore(ChatColor.YELLOW+""+ChatColor.BOLD+"Mined Blocks",2);
		getScore(" ",3);
		getScore("$"+cash+"",4);
		getScore(ChatColor.YELLOW+""+ChatColor.BOLD+"Money", 5);
		getScore("  ",6);
		getScore(tokens+" Tokens",7);
		getScore(ChatColor.YELLOW+""+ChatColor.BOLD+"Tokens",8);
		getScore("   ",8);
		getScore(times[1]+"m"+times[2]+"s",9);
		getScore(ChatColor.YELLOW+""+ChatColor.BOLD+"Resetting In", 10);
		getScore("    ",11);
		
		player.setScoreboard(board);
	}
	
	private Score getScore(String name, int score) {
		Score s = objective.getScore(name);
		s.setScore(score);
		return s;
	}
}
