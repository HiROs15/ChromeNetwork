package dev.chromenetwork.prison.Mines.Mine;

import java.util.ArrayList;
import java.util.UUID;

import dev.chromenetwork.prison.SerializedCraftbukkit.SerializedLocation;

public class MineData {
	public String name;
	public SerializedLocation mineRegionMin;
	public SerializedLocation mineRegionMax;
	public SerializedLocation spawnLocation;
	public boolean enabled = false;
	public int resetTime = 0;
	public float resetPercentage = 0f;
	public UUID uuid;
	public ArrayList<MineBlock> blocks = new ArrayList<MineBlock>();
	public ArrayList<MineHologramData> holograms = new ArrayList<MineHologramData>();
	
	public MineBlock getBlock(int id) {
		for(MineBlock b : blocks) {
			if(b.getId() == id) {
				return b;
			}
		}
		return null;
	}
}
