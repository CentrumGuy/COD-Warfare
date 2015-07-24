package com.CentrumGuy.CodWarfare.Arena;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import com.CentrumGuy.CodWarfare.Files.GunGameFile;

public class GGgunAPI {
	
	private static HashMap<ItemStack, ItemStack> Ammo = new HashMap<ItemStack, ItemStack>();
	public static ArrayList<ItemStack> Guns = new ArrayList<ItemStack>();

	//Add Gun Game Gun
	
	@SuppressWarnings("deprecation")
	public static void addGun(ItemStack gun, Integer GunData, ItemStack ammo, Integer AmmoData) {
		int nextNum = 0;
		while(!(GunGameFile.getData().get("Guns." + nextNum) == null)) {
			nextNum++;
		}
		
		GunGameFile.getData().set("Guns." + nextNum + ".Gun.GunItemID", gun.getTypeId());
		GunGameFile.getData().set("Guns." + nextNum + ".Gun.GunData", GunData);
		GunGameFile.getData().set("Guns." + nextNum + ".Gun.GunName", gun.getItemMeta().getDisplayName());
		
		GunGameFile.getData().set("Guns." + nextNum + ".Ammo.AmmoItemID", ammo.getTypeId());
		GunGameFile.getData().set("Guns." + nextNum + ".Ammo.AmmoData", AmmoData);
		GunGameFile.getData().set("Guns." + nextNum + ".Ammo.Amount", ammo.getAmount());
		GunGameFile.getData().set("Guns." + nextNum + ".Ammo.AmmoName", ammo.getItemMeta().getDisplayName());
		
		GunGameFile.saveData();
		GunGameFile.reloadData();
		
		gun.setData(new MaterialData(GunData));
		ammo.setData(new MaterialData(AmmoData));
		
		Guns.add(gun);
		Ammo.put(gun, ammo);
	}
	
	@SuppressWarnings("deprecation")
	public static void loadGuns() {
		Guns.clear();
		Ammo.clear();
		
		if (GunGameFile.getData().get("Guns.0.Gun") == null) return;
		int nextNum = 0;
		
		while (!(GunGameFile.getData().get("Guns." + nextNum) == null)) {
			nextNum++;
		}
		
		for (int i = 0 ; i < nextNum ; i++) {
			ItemStack gun = new ItemStack(GunGameFile.getData().getInt("Guns." + i + ".Gun.GunItemID"), 1, (byte) GunGameFile.getData().getInt("Guns." + i + ".Gun.GunData"));
			ItemMeta gunMeta = gun.getItemMeta();
			gunMeta.setDisplayName(GunGameFile.getData().getString("Guns." + i + ".Gun.GunName"));
			gun.setItemMeta(gunMeta);
			
			ItemStack ammo = new ItemStack(GunGameFile.getData().getInt("Guns." + i + ".Ammo.AmmoItemID"), GunGameFile.getData().getInt("Guns." + i + ".Ammo.Amount"), (byte) GunGameFile.getData().getInt("Guns." + i + ".Ammo.AmmoData"));
			ItemMeta ammoMeta = ammo.getItemMeta();
			ammoMeta.setDisplayName("§e" + GunGameFile.getData().getString("Guns." + i + ".Ammo.AmmoName"));
			ammo.setItemMeta(ammoMeta);
			
			Guns.add(gun);
			Ammo.put(gun, ammo);
		}
	}
	
	public static ItemStack getCurrentGun(Integer level) {
		return Guns.get(level);
	}
	
	public static ItemStack backGun(Integer currentLevel) {
		int backLevel = currentLevel - 1;
		
		if (Guns.get(backLevel) == null) {
			return Guns.get(0);
		}else{
			return Guns.get(backLevel);
		}
	}
	
	public static String getName(Integer level) {
		ItemStack gun = getCurrentGun(level);
		return gun.getItemMeta().getDisplayName();
	}
	
	public static ItemStack nextGun(Integer currentLevel) {
		int nextLevel = currentLevel + 1;
		
		return Guns.get(nextLevel);
	}
	
	public static ItemStack getAmmo(ItemStack gun) {
		return Ammo.get(gun);
	}
}
