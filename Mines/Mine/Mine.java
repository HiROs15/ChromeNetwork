package dev.chromenetwork.prison.Mines.Mine;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.esotericsoftware.kryo.io.Output;

import dev.chromenetwork.prison.Main;
import dev.chromenetwork.prison.Chat.ServerMessage;
import dev.chromenetwork.prison.Chat.ServerMessage.MessageType;
import dev.chromenetwork.prison.Libs.TimeConvert;

public class Mine {
	private MineData data;
	
	//Admin Stuff
	private boolean highlightingMineRegion;
	
	//Non persistent vars
	int timer = 0;
	private ArrayList<MinePlayer> players = new ArrayList<MinePlayer>();
	@SuppressWarnings("unused")
	private Mine instance;
	private boolean active = false;
	private int blocksMined = 0;
	private int totalBlocks = 0;
	private ArrayList<MineHologram> holograms = new ArrayList<MineHologram>();
	
	public Mine(MineData data) {
		this.data = data;
		
		this.save();
		
		this.instance = this;
		
		this.updateState();
	}
	
	public Mine(String name) {
		this.data = new MineData();
		this.data.name = name;
		this.data.uuid = UUID.randomUUID();
		
		this.save();
		
		this.instance = this;
		
		this.updateState();
	}
	
	public MineData getData() {
		return data;
	}
	
	public void setHighlightMineRegion(boolean b) {
		this.highlightingMineRegion = b;
	}
	
	public boolean getHighlightMineRegion() {
		return this.highlightingMineRegion;
	}
	
	public void save() {
		if(data == null) {
			return;
		}
		
		UUID u = data.uuid;
		File file = new File(Main.Plugin.getDataFolder()+"/Mines/MineData/"+u.toString()+".mine");
		
		try {
			Output output = new Output(new FileOutputStream(file));
			Main.Plugin.getSerializer().writeObject(output, this.data);
			output.close();
		} catch(Exception e) {
			Bukkit.getLogger().warning("Could not save mine data.");
		}
	}
	
	public void resetMine() {
		if(data.enabled == true) {
			this.timer = 0;
			this.blocksMined = 0;
			this.generateBlocks();
			
			if(!active) {
				this.start();
				active = true;
			}
			
			for(MinePlayer p : players) {
				p.setBlocksBroken(0);
				if(isInMine(p.getPlayer().getLocation())) {
					p.getPlayer().teleport(data.spawnLocation.toBukkit());
				}
			}
		} else {
			this.timer = 0;
			this.removeBlocks();
		}
	}
	
	public void updateState() {
		this.spawnHolograms();
		this.resetMine();
	}
	
	private void spawnHolograms() {
		for(MineHologramData h : data.holograms) {
			MineHologram holo = new MineHologram(h);
			holo.spawn();
			holograms.add(holo);
		}
	}
	
	public void spawnHologram(Location loc, String type) {
		MineHologram holo = new MineHologram(loc);
		holo.getData().type = type;
		holo.spawn();
		holograms.add(holo);
		data.holograms.add(holo.getData());
		save();
	}
	
	public int getTotalBlocks() { return this.totalBlocks; }
	public int getTimer() { return this.timer; }
	public int getMinedBlocks() { return this.blocksMined; }
	
	private void generateBlocks() {
		Location min = data.mineRegionMin.toBukkit();
		Location max = data.mineRegionMax.toBukkit();
		ArrayList<MineBlock> blocks = data.blocks;
		
		for(int x = min.getBlockX(); x <= max.getBlockX(); x++) {
			for(int y = min.getBlockY(); y <= max.getBlockY(); y++) {
				for(int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
					double random = Math.random()*100;
					boolean gotBlock = false;
					for(MineBlock b : blocks) {
						if((random -= b.getPercentage()) < 0) {
							min.getWorld().getBlockAt(x, y, z).setType(b.getBukkitBlock().getType());
							gotBlock = true;
							totalBlocks++;
							break;
						}
					}
					if(!gotBlock) {
						min.getWorld().getBlockAt(x, y, z).setType(blocks.get(0).getBukkitBlock().getType());
						totalBlocks++;
					}
				}
			}
		}
	}
	
	private void removeBlocks() {
		if(data.mineRegionMin == null) {
			return;
		}
		Location min = data.mineRegionMin.toBukkit();
		Location max = data.mineRegionMax.toBukkit();
		
		for(int x = min.getBlockX(); x <= max.getBlockX(); x++) {
			for(int y = min.getBlockY(); y <= max.getBlockY(); y++) {
				for(int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
					min.getWorld().getBlockAt(x,y,z).setType(Material.AIR);
				}
			}
		}
	}
	
	public boolean isPlayerInMine(Player player) {
		for(MinePlayer p : players) {
			if(p.getPlayer().getName().equals(player.getName())) {
				return true;
			}
		}
		return false;
	}
	
	private void start() {
		new BukkitRunnable() {
			public void run() {
				if(data.enabled == false) {
					this.cancel();
				}
				
				if(timer >= data.resetTime) {
					resetMine();
					timer = 0;
					return;
				}
				
				timer++;
				updateHolograms();
				updatePlayerScoreboard();
			}
		}.runTaskTimer(Main.Plugin, 0L, 20L);
	}
	
	private void updatePlayerScoreboard() {
		for(MinePlayer p : players) {
			p.updateScoreboard();
		}
	}
	
	public MinePlayer getPlayer(Player player) {
		for(MinePlayer p : players) {
			if(p.getPlayer().getName().equals(player.getName())) {
				return p;
			}
		}
		return null;
	}
	
	private void updateHolograms() {
		for(MineHologram h : holograms) {
			if(h.getData().type.equals("timer")) {
				int[] times = TimeConvert.splitToComponentTimes(new BigDecimal((data.resetTime-timer)));
				h.getHologram().setCustomName(ChatColor.AQUA+""+ChatColor.BOLD+"Resetting in");
				h.getHologramData().setCustomName(ChatColor.GREEN+""+ChatColor.BOLD+""+times[1]+"m"+times[2]+"s");
			}
			else if(h.getData().type.equals("blocksmined")) {
				h.getHologram().setCustomName(ChatColor.AQUA+""+ChatColor.BOLD+"Blocks Mined");
				h.getHologramData().setCustomName(ChatColor.GREEN+""+ChatColor.BOLD+""+blocksMined);
			}
			else if(h.getData().type.equals("percentmined")) {
				h.getHologram().setCustomName(ChatColor.AQUA+""+ChatColor.BOLD+"Percent Mined");
				h.getHologramData().setCustomName(ChatColor.GREEN+""+ChatColor.BOLD+""+((blocksMined/totalBlocks)*100)+"%");
			}
			else if(h.getData().type.equals("percentleft")) {
				h.getHologram().setCustomName(ChatColor.AQUA+""+ChatColor.BOLD+"Percent Left");
				if(blocksMined == 0) {
					h.getHologramData().setCustomName(ChatColor.GREEN+""+ChatColor.BOLD+"0.0%");
				} else {
				h.getHologramData().setCustomName(ChatColor.GREEN+""+ChatColor.BOLD+""+(((totalBlocks-blocksMined)/totalBlocks)*100)+"%");
				}
			}
		}
	}
	
	public void joinMine(Player player) {
		if(isPlayerInMine(player)) {
			ServerMessage.get().sendMessage(player, MessageType.ERROR, "You are currently in a mine.");
			return;
		}
		
		players.add(new MinePlayer(player));
		
		player.setHealth(20);
		player.setFoodLevel(20);
		
		player.teleport(data.spawnLocation.toBukkit());
		
		ServerMessage.get().sendMessage(player, MessageType.SERVERNAME, ChatColor.YELLOW+"You have joined the mine "+ChatColor.BLUE+""+ChatColor.BOLD+""+data.name);
	}
	
	public boolean isInMine(Location loc) {
		Location min = data.mineRegionMin.toBukkit();
		Location max = data.mineRegionMax.toBukkit();
		
		if(loc.getBlockX() >= min.getBlockX() && loc.getBlockX() <= max.getBlockX() && loc.getBlockY() >= min.getBlockY() && loc.getBlockY() <= max.getBlockY() && loc.getBlockZ() >= min.getBlockZ() && loc.getBlockZ() <= max.getBlockZ()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void breakBlock(Player player, Block block) {
		if(isInMine(block.getLocation())) {
			player.getInventory().addItem(new ItemStack(block.getType()));
			block.setType(Material.AIR);
			this.blocksMined++;
			
			getPlayer(player).addBlockBroken();
		}
	}
}
