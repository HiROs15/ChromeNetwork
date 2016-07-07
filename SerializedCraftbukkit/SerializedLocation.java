package dev.chromenetwork.prison.SerializedCraftbukkit;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class SerializedLocation {
	public double x;
	public double y;
	public double z;
	public String world;
	public float pitch;
	public float yaw;
	
	public SerializedLocation(org.bukkit.Location loc) {
		this.x = loc.getX();
		this.y = loc.getY();
		this.z = loc.getZ();
		this.world = loc.getWorld().getName();
		this.pitch = loc.getPitch();
		this.yaw = loc.getYaw();
	}
	
	public org.bukkit.Location toBukkit() {
		return new Location(Bukkit.getWorld(world),x,y,z,yaw,pitch);
	}
}
