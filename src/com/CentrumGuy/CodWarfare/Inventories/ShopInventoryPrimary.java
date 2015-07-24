package com.CentrumGuy.CodWarfare.Inventories;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.CentrumGuy.CodWarfare.Files.ShopFile;
import com.CentrumGuy.CodWarfare.Interface.ItemsAndInventories;

public class ShopInventoryPrimary {
	
	public static HashMap<Player, Inventory> Pshop = new HashMap<Player, Inventory>();
	
	public static void savePrimaryShop(final Player p) {
		if (Pshop.get(p) != null) {
			for (int i = 0 ; i < Pshop.get(p).getSize() ; i++) {
				ItemStack item = Pshop.get(p).getItem(i);
				if (item == null) continue;
				if (item.equals(ItemsAndInventories.backShop)) {
					Pshop.get(p).setItem(i, null);
				}
			}
				
			ShopFile.getData().set("PrimaryShops." + p.getUniqueId(), SaveAndLoad.toString(Pshop.get(p)));
			ShopFile.saveData();
			ShopFile.reloadData();
				
				Pshop.get(p).setItem(49, ItemsAndInventories.backShop);
		}else{
			return;
		}
	}
	
	public static Inventory getPrimaryShop(Player p) {
			if (Pshop.get(p) != null) return Pshop.get(p);
			else return loadPrimaryShop(p);
	}
	
	public static Inventory loadPrimaryShop(Player p) {
		if (ShopFile.getData().getString("PrimaryShops." + p.getUniqueId()) != null && !ShopFile.getData().getString("PrimaryShops." + p.getUniqueId()).isEmpty()) {
			Inventory inv = Bukkit.getServer().createInventory(p, 54, "Buy Primary Guns");
			inv = SaveAndLoad.fromString(inv, ShopFile.getData().getList("PrimaryShops." + p.getUniqueId()));
			
			for (int i = 0 ; i < inv.getSize() ; i++) {
				ItemStack item = inv.getItem(i);
				if (item == null) continue;
				if (item.equals(ItemsAndInventories.backShop)) continue;
				
				ArrayList<String> lore = new ArrayList<String>();
				ItemMeta im = item.getItemMeta();
				lore.add("§7This gun costs§6 " + Guns.getCost(item) + " §7credits");
				im.setLore(lore);
				item.setItemMeta(im);
				
				inv.setItem(i, item);
			}
			
			Pshop.put(p, inv);
			Pshop.get(p).setItem(49, ItemsAndInventories.backShop);
			
			return inv;
		} else {
			Inventory inv = Bukkit.getServer().createInventory(p, 54, "Buy Primary Guns");
			Pshop.put(p, inv);
			Pshop.get(p).setItem(49, ItemsAndInventories.backShop);
			
			return inv;
		}
	}
}