package dev.chromenetwork.prison.Prison;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.chromenetwork.prison.Managers.Manager;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class PrisonManager extends Manager {
	public PrisonManager() {
		
	}
	
	public boolean doesPlayerHavePrisonTool(Player player) {
		for(ItemStack i : player.getInventory().getContents()) {
			if(i != null) {
				net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(i);
				if(nmsItem.getTag() != null && nmsItem.getTag().hasKey("prison.tool")) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void givePlayerPrisonTool(Player player) {
		ItemStack tool = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemMeta im = tool.getItemMeta();
		im.setDisplayName(ChatColor.GOLD+""+ChatColor.BOLD+"Pickaxe");
		String[] lore = {};
		im.setLore(Arrays.asList(lore));
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		im.addEnchant(Enchantment.DIG_SPEED, 25, true);
		tool.setItemMeta(im);
		
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(tool);
		
		NBTTagCompound tag = nmsItem.getTag();
		if(tag == null) {
			tag = new NBTTagCompound();
			nmsItem.setTag(tag);
			tag = nmsItem.getTag();
		}
		
		tag.set("prison.tool", nmsItem.save(new NBTTagCompound()));
		
		tag.setBoolean("Unbreakable", true);
		nmsItem.setTag(tag);
		
		tool = CraftItemStack.asBukkitCopy(nmsItem);
		
		player.getInventory().addItem(tool);
		player.updateInventory();
	}
}
