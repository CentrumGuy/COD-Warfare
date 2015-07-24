package com.CentrumGuy.CodWarfare.OtherLoadout;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.CentrumGuy.CodWarfare.Interface.ItemsAndInventories;
import com.CentrumGuy.CodWarfare.Inventories.KitInventory;

public class WeaponUtils {
	public static void clearWeapons(Player p) {
		KitInventory.getKit(p).setItem(5, null);
		KitInventory.getKit(p).setItem(6, null);
		
		KitInventory.saveKit(p);
		
		ItemStack none = new ItemStack(Material.EMERALD);
		
		ItemMeta noneMeta = none.getItemMeta();
		noneMeta.setDisplayName("§4None");
		none.setItemMeta(noneMeta);
		
		ItemsAndInventories.ClassSelection.get(p).setItem(31, none);
		ItemsAndInventories.ClassSelection.get(p).setItem(33, none);
	}
}
