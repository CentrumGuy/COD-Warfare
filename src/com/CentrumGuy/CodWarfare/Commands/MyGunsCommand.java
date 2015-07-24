package com.CentrumGuy.CodWarfare.Commands;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.CentrumGuy.CodWarfare.Interface.ItemsAndInventories;
import com.CentrumGuy.CodWarfare.Inventories.AGPInventory;
import com.CentrumGuy.CodWarfare.Inventories.AGSInventory;
import com.CentrumGuy.CodWarfare.Inventories.Guns;

public class MyGunsCommand {

	public static void myGuns(Player p) {
		ArrayList<ItemStack> guns = new ArrayList<ItemStack>();
		
		for (int i = 0 ; i < AGPInventory.getAGP(p).getSize() ; i++) {
			ItemStack item = AGPInventory.getAGP(p).getItem(i);
			
			if (item == null || item.getType() == Material.AIR) continue;
			if (item.equals(ItemsAndInventories.backAG)) continue;
			
				guns.add(item);
		}
		
		for (int i = 0 ; i < AGSInventory.getAGS(p).getSize() ; i++) {
			ItemStack item = AGSInventory.getAGS(p).getItem(i);
			
			if (item == null || item.getType() == Material.AIR) continue;
			if (item.equals(ItemsAndInventories.backAG)) continue;
			
				guns.add(item);
		}
		
		p.sendMessage("§b§lYour Available Guns:");
		
		for (int i = 0 ; i < guns.size() ; i++) {
			if (!(guns.get(i).hasItemMeta())) continue;
			if (!(guns.get(i).getItemMeta().hasDisplayName())) continue;
			
			p.sendMessage(" §7-§e " + guns.get(i).getItemMeta().getDisplayName() + " §8§o" + Guns.getType(guns.get(i)).toLowerCase());
		}
	}
}
