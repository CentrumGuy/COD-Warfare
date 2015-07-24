package com.CentrumGuy.CodWarfare.Utilities;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {

	public static ItemStack createItem(Material M, int Ammount, Byte Data, String Name, ArrayList<String> Lore) {
		if (Data == null) Data = (byte) 0;
		ItemStack item = new ItemStack(M, Ammount, Data);
		ItemMeta itemMeta = item.getItemMeta();
		if (Name != null) itemMeta.setDisplayName(Name);
		if (Lore != null) itemMeta.setLore(Lore);
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack createItem(Material M, int Ammount, int Data, String Name, ArrayList<String> Lore) {
		ItemStack item = new ItemStack(M, Ammount, (byte) Data);
		ItemMeta itemMeta = item.getItemMeta();
		if (Name != null) itemMeta.setDisplayName(Name);
		if (Lore != null) itemMeta.setLore(Lore);
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack createItem(Material M, int Ammount, Byte Data, String Name) {
		if (Data == null) Data = (byte) 0;
		ItemStack item = new ItemStack(M, Ammount, Data);
		ItemMeta itemMeta = item.getItemMeta();
		if (Name != null) itemMeta.setDisplayName(Name);
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack createItem(Material M, int Ammount, int Data, String Name) {
		ItemStack item = new ItemStack(M, Ammount, (byte) Data);
		ItemMeta itemMeta = item.getItemMeta();
		if (Name != null) itemMeta.setDisplayName(Name);
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static void setItem(Player p, ItemStack item, int Space) {
		p.getInventory().setItem(Space, item);
	}
	
	public static void addItem(Player p, ItemStack item) {
		p.getInventory().addItem(item);
	}
}