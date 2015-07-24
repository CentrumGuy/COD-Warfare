package com.CentrumGuy.CodWarfare.Commands;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Files.GunsFile;
import com.CentrumGuy.CodWarfare.Inventories.Guns;

public class DeleteGunCommand {

	@SuppressWarnings("deprecation")
	public static void deleteGun(String[] args, Player p) {
		if (args.length < 2) {
			p.sendMessage(Main.codSignature + "§cPlease type §4/cod deletegun [Gun Name]");
			return;
		}else{
			String gunName = "";
			String name = "";
			
			for (int i = 1 ; i < args.length ; i++) {
				if (i == 1) {
					gunName = gunName + "" + args[i];
					name = name + "" + args[i];
				}else{
					gunName = gunName + " " + args[i];
					name = name + " " + args[i];
				}
			}
			
			int nextInt = 0;
			
			ArrayList<ItemStack> guns = new ArrayList<ItemStack>();
			HashMap<ItemStack, ItemStack> ammo = new HashMap<ItemStack, ItemStack>();
			HashMap<ItemStack, Integer> cost = new HashMap<ItemStack, Integer>();
			HashMap<ItemStack, Integer> level = new HashMap<ItemStack, Integer>();
			HashMap<ItemStack, String> type = new HashMap<ItemStack, String>();
			
			while (GunsFile.getData().get("Guns." + nextInt) != null) {
				ItemStack gun = new ItemStack(GunsFile.getData().getInt("Guns." + nextInt + ".Gun.GunItemID"), 1, (byte) GunsFile.getData().getInt("Guns." + nextInt + ".Gun.GunData"));
				ItemMeta gunMeta = gun.getItemMeta();
				gunMeta.setDisplayName(GunsFile.getData().getString("Guns." + nextInt + ".Gun.GunName"));
				gun.setItemMeta(gunMeta);
				
				ItemStack gunAmmo = new ItemStack(GunsFile.getData().getInt("Guns." + nextInt + ".Ammo.AmmoItemID"), GunsFile.getData().getInt("Guns." + nextInt + ".Ammo.Amount"), (byte) GunsFile.getData().getInt("Guns." + nextInt + ".Ammo.AmmoData"));
				ItemMeta ammoMeta = gunAmmo.getItemMeta();
				ammoMeta.setDisplayName(GunsFile.getData().getString("Guns." + nextInt + ".Ammo.AmmoName"));
				gunAmmo.setItemMeta(ammoMeta);
				
				int gunCost = GunsFile.getData().getInt("Guns." + nextInt + ".Cost");
				int gunLevel = GunsFile.getData().getInt("Guns." + nextInt + ".LevelUnlock");
				String gunType = GunsFile.getData().getString("Guns." + nextInt + ".Type");
				
				guns.add(gun);
				ammo.put(gun, gunAmmo);
				cost.put(gun, gunCost);
				level.put(gun, gunLevel);
				type.put(gun, gunType);

				nextInt++;
			}
			
			if (checkIfGunsContains(gunName, guns, p)) {
				guns.remove(getGun(gunName, guns));
			
				GunsFile.getData().set("Guns", null);
				if (!(guns.isEmpty())) {
					for (int i = 0 ; i < guns.size() ; i++) {
						
						ItemStack gun = guns.get(i);
						
						GunsFile.getData().set("Guns." + i + ".Gun.GunItemID", gun.getTypeId());
						GunsFile.getData().set("Guns." + i + ".Gun.GunData", gun.getData().getData());
						GunsFile.getData().set("Guns." + i + ".Gun.GunName", gun.getItemMeta().getDisplayName());
						
						GunsFile.getData().set("Guns." + i + ".Ammo.AmmoItemID", ammo.get(gun).getTypeId());
						GunsFile.getData().set("Guns." + i + ".Ammo.AmmoData", ammo.get(gun).getData().getData());
						GunsFile.getData().set("Guns." + i + ".Ammo.Amount", ammo.get(gun).getAmount());
						GunsFile.getData().set("Guns." + i + ".Ammo.AmmoName", ammo.get(gun).getItemMeta().getDisplayName());
						
						GunsFile.getData().set("Guns." + i + ".Cost", cost.get(gun));
						GunsFile.getData().set("Guns." + i + ".LevelUnlock", level.get(gun));
						GunsFile.getData().set("Guns." + i + ".Type", type.get(gun));
					}
				}
				
				GunsFile.saveData();
				GunsFile.reloadData();
				Guns.loadGuns();
				
				p.sendMessage(Main.codSignature + "§aGun §2" + name + " §adeleted");
			}else{
				p.sendMessage(Main.codSignature + "§cGun §4" + name + " §cdoes not exist");
			}
		}
	}
	
	private static ItemStack getGun(String gunName, ArrayList<ItemStack> guns) {
		
		String name = gunName;
		
		for (int i = 0 ; i < guns.size() ; i++) {
			ItemStack gun = guns.get(i);
			if (gun.getItemMeta().getDisplayName().equals(name)) {
				return gun;
			}
		}
		
		return null;
	}
	
	private static boolean checkIfGunsContains(String gunName, ArrayList<ItemStack> guns, Player p) {
		
		for (int i = 0 ; i < guns.size() ; i++) {
			ItemStack gun = guns.get(i);
			if (gun.getItemMeta().getDisplayName().equals(gunName)) {
				return true;
			}
		}
		
		return false;
	}
}
