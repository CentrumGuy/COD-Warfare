package com.CentrumGuy.CodWarfare.Inventories;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.CentrumGuy.CodWarfare.Files.AvailableGunsFile;
import com.CentrumGuy.CodWarfare.Interface.ItemsAndInventories;

public class AGPInventory {
	
	public static HashMap<Player, Inventory> availablePrimaryGuns = new HashMap<Player, Inventory>();
	
	public static void saveAGP(final Player p) {
		if (availablePrimaryGuns.get(p) != null) {
			for (int i = 0 ; i < availablePrimaryGuns.get(p).getSize() ; i++) {
				if (availablePrimaryGuns.get(p).getItem(i) == null) continue;
				ItemStack item = availablePrimaryGuns.get(p).getItem(i);
				if (item.equals(ItemsAndInventories.backAG)) {
					availablePrimaryGuns.get(p).setItem(i, null);
				}else{
					continue;
				}
			}
			
			AvailableGunsFile.getData().set("AvailablePrimaryGuns." + p.getUniqueId(), SaveAndLoad.toString(availablePrimaryGuns.get(p)));
			AvailableGunsFile.saveData();
			AvailableGunsFile.reloadData();
			
			availablePrimaryGuns.get(p).setItem(49, ItemsAndInventories.backAG);
		}else{
			return;
		}
	}
	
	public static Inventory getAGP(Player p) {
			if (availablePrimaryGuns.get(p) != null) return availablePrimaryGuns.get(p);
			else return loadAGP(p);
	}
	
	public static Inventory loadAGP(Player p) {
		if (AvailableGunsFile.getData().getString("AvailablePrimaryGuns." + p.getUniqueId()) != null && !AvailableGunsFile.getData().getString("AvailablePrimaryGuns." + p.getUniqueId()).isEmpty()) {
			Inventory inv = Bukkit.getServer().createInventory(p, 54, "Available Primary Guns");
			availablePrimaryGuns.put(p, SaveAndLoad.fromString(inv, AvailableGunsFile.getData().getList("AvailablePrimaryGuns." + p.getUniqueId())));
			availablePrimaryGuns.get(p).setItem(49, ItemsAndInventories.backAG);
			return availablePrimaryGuns.get(p);
		}else{
			Inventory inv = Bukkit.getServer().createInventory(p, 54, "Available Primary Guns");
			availablePrimaryGuns.put(p, inv);
			return inv;
		}
	}
}