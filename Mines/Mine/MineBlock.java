package dev.chromenetwork.prison.Mines.Mine;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class MineBlock {
	private int id;
	private byte data;
	private float percentage;
	
	public MineBlock(int id, byte data, float percentage) {
		this.id = id;
		this.data = data;
		this.percentage = percentage;
	}
	
	@SuppressWarnings("deprecation")
	public MineBlock(ItemStack i) {
		this.id = i.getTypeId();
		this.data = i.getData().getData();
		this.percentage = 0f;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setData(byte data) {
		this.data = data;
	}
	
	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}
	
	public int getId() { return this.id; }
	public byte getData() { return this.data; }
	public float getPercentage() { return this.percentage; }
	
	@SuppressWarnings("deprecation")
	public Block getBukkitBlock() {
		Block b = Bukkit.getWorlds().get(0).getBlockAt(0,0,0);
		b.setTypeId(this.id);
		b.setData(this.data);
		return b;
	}
}
