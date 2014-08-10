package org.arkham.cs.hats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.server.v1_7_R4.PacketPlayOutEntityEquipment;

import org.arkham.cs.gui.Category;
import org.arkham.cs.interfaces.Button;
import org.arkham.cs.utils.Rank;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Hat extends Button {

	private ItemStack item;
	public static List<Hat> hats = new ArrayList<>();
	private Rank rank;
	private static HashMap<Rank, List<Hat>> hatsByRank = new HashMap<>();

	/**
	 * @param slot
	 * @param item
	 * @param name
	 * @param lore
	 */
	public Hat(int slot, ItemStack item, Category name, String permission, Rank rank) {
		super(slot, name, permission);
		this.item = item;
		this.rank = rank;
		List<Hat> h = hatsByRank.get(rank);
		if(h == null){
			h = new ArrayList<>();
		}
		h.add(this);
		hatsByRank.put(rank, h);
		hats.add(this);
	}
	
	/**
	 * @param slot
	 * @param item
	 */
	public Hat(int slot, ItemStack item, Rank rank){
		this(slot, item, Category.HATS, "cosmetics.hats." + item.getType().name().toLowerCase(), rank);
	}
    /**
     * @param mat
     * @param slot
     */
	public Hat(Material mat, int slot, Rank rank){
		this(slot, new ItemStack(mat), Category.HATS, "cosmetics.hats." + mat.name().toLowerCase(), rank);
	}

	@Override
	public ItemStack getDisplay() {
		return item;
	}
	
	public static List<Hat> getHats(Rank rank){
		return hatsByRank.get(rank);
	}

	@Override
	public void onClick(Player player) {
		player.getInventory().setHelmet(player.getInventory().getHelmet());
		PacketPlayOutEntityEquipment equip = new PacketPlayOutEntityEquipment(player.getEntityId(), 1, CraftItemStack.asNMSCopy(getDisplay()));
		for(Player p :  Bukkit.getOnlinePlayers()){
			if(!p.getName().equalsIgnoreCase(player.getName())){
				((CraftPlayer)p).getHandle().playerConnection.sendPacket(equip);
			}
		}
		player.closeInventory();
	}
	
	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}
}