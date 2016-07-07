package dev.chromenetwork.prison.Packets;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import com.comphenix.protocol.Packets;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import dev.chromenetwork.prison.Main;

@SuppressWarnings("deprecation")
public class RemovePrisonToolEnchantments {
	public static RemovePrisonToolEnchantments get() {
		return new RemovePrisonToolEnchantments();
	}
	
	public void init() {
		ProtocolLibrary.getProtocolManager().addPacketListener(
				new PacketAdapter(Main.Plugin, ConnectionSide.SERVER_SIDE, Packets.Server.SET_SLOT, Packets.Server.WINDOW_ITEMS) {
					@Override
					public void onPacketSending(PacketEvent event) {
						PacketContainer packet = event.getPacket();
						
						//Item Packets
						switch(event.getPacketID()) {
						case Packets.Server.SET_SLOT:
							int slotIndex = packet.getIntegers().read(1);
							if(isValidSlot(slotIndex)) {
								removeEnchantments(packet.getItemModifier().read(0));
							}
						break;
						
						case Packets.Server.WINDOW_ITEMS:
							ItemStack[] elements = packet.getItemArrayModifier().read(0);
							
							for(int i = 0; i< elements.length; i++) {
								if(elements[i] != null && isValidSlot(i)) {
									removeEnchantments(elements[i]);
								}
							}
						break;
						}
					}
				}
				);
	}
	
	private boolean isValidSlot(int slotIndex) {
		return true;
	}
	
	private void removeEnchantments(ItemStack stack) {
		if(stack == null) {
			return;
		}
		
		Object[] copy = stack.getEnchantments().keySet().toArray();
		
		for(Object enchantment : copy) {
			stack.removeEnchantment((Enchantment) enchantment);
		}
	}
}
