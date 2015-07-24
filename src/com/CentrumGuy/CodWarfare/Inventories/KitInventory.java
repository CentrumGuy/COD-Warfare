package com.CentrumGuy.CodWarfare.Inventories;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.CentrumGuy.CodWarfare.Files.KitFile;

public class KitInventory {
	
	public static HashMap<Player, Inventory> kit = new HashMap<Player, Inventory>();
	
	public static void saveKit(final Player p) {
		if (kit.get(p) != null) {
			ItemStack five = kit.get(p).getItem(5);
			ItemStack six = kit.get(p).getItem(6);
			
			kit.get(p).setItem(5, null);
			kit.get(p).setItem(6, null);
			
			KitFile.getData().set("Kit." + p.getUniqueId(), SaveAndLoad.toString(kit.get(p)));
			KitFile.saveData();
			KitFile.reloadData();
			
			kit.get(p).setItem(5, five);
			kit.get(p).setItem(6, six);
		}else{
			return;
		}
	}
	
	public static Inventory getKit(Player p) {
			if (kit.get(p) != null) return kit.get(p);
			else return loadKit(p);
	}
	
	public static Inventory loadKit(Player p) {
		if (KitFile.getData().getString("Kit." + p.getUniqueId()) != null && !KitFile.getData().getString("Kit." + p.getUniqueId()).isEmpty()) {
			Inventory inv = Bukkit.getServer().createInventory(p, 27, "Loadout");
			kit.put(p, SaveAndLoad.fromString(inv, KitFile.getData().getList("Kit." + p.getUniqueId())));
			return kit.get(p);
		}else{
			Inventory inv = Bukkit.getServer().createInventory(p, 27, "Loadout");
			kit.put(p, inv);
			return inv;
		}
	}
}