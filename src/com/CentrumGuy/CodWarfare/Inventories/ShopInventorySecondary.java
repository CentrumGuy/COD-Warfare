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

public class ShopInventorySecondary {
	
	public static HashMap<Player, Inventory> Sshop = new HashMap<Player, Inventory>();
	
	public static void saveSecondaryShop(final Player p) {
		if (Sshop.get(p) != null) {
			for (int i = 0 ; i < Sshop.get(p).getSize() ; i++) {
				ItemStack item = Sshop.get(p).getItem(i);
				if (item == null) continue;
				if (item.equals(ItemsAndInventories.backShop)) {
					Sshop.get(p).setItem(i, null);
				}
			}
				
			ShopFile.getData().set("SecondaryShops." + p.getUniqueId(), SaveAndLoad.toString(Sshop.get(p)));
			ShopFile.saveData();
			ShopFile.reloadData();
				
			Sshop.get(p).setItem(49, ItemsAndInventories.backShop);
		}else{
			return;
		}
	}
	
	public static Inventory getSecondaryShop(Player p) {
			if (Sshop.get(p) != null) return Sshop.get(p);
			else return loadSecondaryShop(p);
	}
	
	public static Inventory loadSecondaryShop(Player p) {
		if (ShopFile.getData().getString("SecondaryShops." + p.getUniqueId()) != null && !ShopFile.getData().getString("SecondaryShops." + p.getUniqueId()).isEmpty()) {
			Inventory inv = Bukkit.getServer().createInventory(p, 54, "Buy Secondary Guns");
			inv = SaveAndLoad.fromString(inv, ShopFile.getData().getList("SecondaryShops." + p.getUniqueId()));
			
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
			
			Sshop.put(p, inv);
			Sshop.get(p).setItem(49, ItemsAndInventories.backShop);
			
			return inv;
		} else {
			Inventory inv = Bukkit.getServer().createInventory(p, 54, "Buy Secondary Guns");
			Sshop.put(p, inv);
			Sshop.get(p).setItem(49, ItemsAndInventories.backShop);
			
			return inv;
		}
	}
}