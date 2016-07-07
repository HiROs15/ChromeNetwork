package dev.chromenetwork.prison.Mines.Mine;

import org.bukkit.Location;

import dev.chromenetwork.prison.SerializedCraftbukkit.SerializedLocation;

public class MineHologramData {
	public String type;
	public SerializedLocation location;
	
	public MineHologramData(Location loc) {
		this.location = new SerializedLocation(loc);
	}
}
