package com.CentrumGuy.CodWarfare.Inventories;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.CentrumGuy.CodWarfare.Files.AvailableGunsFile;
import com.CentrumGuy.CodWarfare.Interface.ItemsAndInventories;

public class AGSInventory {
	
	public static HashMap<Player, Inventory> availableSecondaryGuns = new HashMap<Player, Inventory>();
	
	public static void saveAGS(final Player p) {
		if (availableSecondaryGuns.get(p) != null) {
			for (int i = 0 ; i < availableSecondaryGuns.get(p).getSize() ; i++) {
				ItemStack item = availableSecondaryGuns.get(p).getItem(i);
				if (item == null) continue;
				if (item.equals(ItemsAndInventories.backAG)) {
					availableSecondaryGuns.get(p).setItem(i, null);
				}
			}
				
			AvailableGunsFile.getData().set("AvailableSecondaryGuns." + p.getUniqueId(), SaveAndLoad.toString(availableSecondaryGuns.get(p)));
			AvailableGunsFile.saveData();
			AvailableGunsFile.reloadData();
				
			availableSecondaryGuns.get(p).setItem(49, ItemsAndInventories.backAG);
		}else{
			return;
		}
	}
	
	public static Inventory getAGS(Player p) {
			if (availableSecondaryGuns.get(p) != null) return availableSecondaryGuns.get(p);
			else return loadAGS(p);
	}
	
	public static Inventory loadAGS(Player p) {
		if (AvailableGunsFile.getData().getString("AvailableSecondaryGuns." + p.getUniqueId()) != null && !AvailableGunsFile.getData().getString("AvailableSecondaryGuns." + p.getUniqueId()).isEmpty()) {
			Inventory inv = Bukkit.getServer().createInventory(p, 54, "Available Secondary Guns");
			availableSecondaryGuns.put(p, SaveAndLoad.fromString(inv, AvailableGunsFile.getData().getList("AvailableSecondaryGuns." + p.getUniqueId())));
			return SaveAndLoad.fromString(inv, AvailableGunsFile.getData().getList("AvailableSecondaryGuns." + p.getUniqueId()));
		} else {
			Inventory inv = Bukkit.getServer().createInventory(p, 54, "Available Secondary Guns");
			availableSecondaryGuns.put(p, inv);
			
			return inv;
		}
	}
}