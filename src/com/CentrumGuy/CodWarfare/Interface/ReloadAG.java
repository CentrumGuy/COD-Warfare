package com.CentrumGuy.CodWarfare.Interface;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.CentrumGuy.CodWarfare.Inventories.AGPInventory;
import com.CentrumGuy.CodWarfare.Inventories.AGSInventory;
import com.CentrumGuy.CodWarfare.Inventories.Guns;
import com.CentrumGuy.CodWarfare.Inventories.ShopInventoryPrimary;
import com.CentrumGuy.CodWarfare.Inventories.ShopInventorySecondary;
import com.CentrumGuy.CodWarfare.Leveling.Level;

public class ReloadAG {

	public static void load(Player p) {
		
		for (int i = 0 ; i < Guns.guns.size() ; i++) {
			ItemStack gun = Guns.guns.get(i);
			
			if (Guns.getLevel(gun) <= Level.Level.get(p)) {
				if (Guns.getType(gun).equalsIgnoreCase("Primary")) {
					
					if (AGPInventory.availablePrimaryGuns.get(p).contains(gun)) continue;
					
						ItemStack changedGun = new ItemStack(gun.getType(), 1);
						ItemMeta im = changedGun.getItemMeta();
						changedGun.setData(gun.getData());
						im.setDisplayName(gun.getItemMeta().getDisplayName());
						
						ArrayList<String> lore = new ArrayList<String>();
						lore.add("§7This gun costs§6 " + Guns.getCost(gun) + " §7credits");
						im.setLore(lore);
						changedGun.setItemMeta(im);
						
							if (ShopInventoryPrimary.getPrimaryShop(p).contains(changedGun)) continue;
							if (Guns.getLevel(gun) == -1) continue;
					
								ShopInventoryPrimary.Pshop.get(p).addItem(gun);
								ShopInventoryPrimary.savePrimaryShop(p);
								ShopInventoryPrimary.loadPrimaryShop(p);
								
								ItemsAndInventories.updateShop(p);
								
								continue;
								
				}else if (Guns.getType(gun).equalsIgnoreCase("Secondary")) {
					
					if (AGSInventory.availableSecondaryGuns.get(p).contains(gun)) continue;	
					
						ItemStack changedGun = new ItemStack(gun.getType(), 1);
						ItemMeta im = changedGun.getItemMeta();
						changedGun.setData(gun.getData());
						im.setDisplayName(gun.getItemMeta().getDisplayName());
						
						ArrayList<String> lore = new ArrayList<String>();
						lore.add("§7This gun costs§6 " + Guns.getCost(gun) + " §7credits");
						im.setLore(lore);
						changedGun.setItemMeta(im);
						
							if (ShopInventorySecondary.getSecondaryShop(p).contains(changedGun)) continue;
							if (Guns.getLevel(gun) == -1) continue;
							
								ShopInventorySecondary.Sshop.get(p).addItem(gun);
								ShopInventorySecondary.saveSecondaryShop(p);
								ShopInventorySecondary.loadSecondaryShop(p);
								
								ItemsAndInventories.updateShop(p);
								
								continue;
				}else{
					continue;
				}
			}
		}
	}
}
