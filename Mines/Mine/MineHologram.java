package dev.chromenetwork.prison.Mines.Mine;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class MineHologram {
	private MineHologramData data;
	private ArmorStand as;
	private ArmorStand asData;
	private boolean spawned = false;
	
	public MineHologram(MineHologramData data) {
		this.data = data;
	}
	
	public MineHologram(Location loc) {
		this.data = new MineHologramData(loc);
	}
	
	public MineHologramData getData() {
		return this.data;
	}
	
	public ArmorStand getHologramData() { return this.asData; }
	
	public void spawn() {
		for(org.bukkit.entity.Entity e : data.location.toBukkit().getWorld().getEntities()) {
			if(e instanceof ArmorStand) {
				if(e.getLocation().distance(data.location.toBukkit()) < 3) {
					e.remove();
				}
			}
		}
		
		this.as = (ArmorStand) data.location.toBukkit().getWorld().spawnEntity(data.location.toBukkit().add(0, 0.5, 0), EntityType.ARMOR_STAND);
		as.setGravity(false);
		as.setCustomNameVisible(true);
		as.setCustomName(ChatColor.BLUE+""+ChatColor.BOLD+"Loading...");
		as.setVisible(false);
		
		this.asData = (ArmorStand) data.location.toBukkit().getWorld().spawnEntity(data.location.toBukkit().add(0, 0.28, 0), EntityType.ARMOR_STAND);
		asData.setGravity(false);
		asData.setCustomNameVisible(true);
		asData.setCustomName(" ");
		asData.setVisible(false);
		
		this.spawned = true;
	}
	
	public ArmorStand getHologram() { return this.as; }
	public boolean getSpawned() {
		return this.spawned;
	}
}
