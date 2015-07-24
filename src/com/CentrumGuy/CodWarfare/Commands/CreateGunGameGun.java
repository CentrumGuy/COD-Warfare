package com.CentrumGuy.CodWarfare.Commands;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Arena.GGgunAPI;

public class CreateGunGameGun {

	@SuppressWarnings("deprecation")
	public static void createGunGameGun(Player p, String[] args) {
		if (args.length == 8) {
			String gunID = args[1];
			String gunData = args[2];
			String gunName = args[3];
			String ammoID = args[4];
			String ammoData = args[5];
			String ammoAmount = args[6];
			String ammoName = args[7];
				
			ItemStack gun = new ItemStack(Integer.parseInt(gunID));
			ItemMeta gunMeta = gun.getItemMeta();
			gunMeta.setDisplayName(gunName);
			gun.setItemMeta(gunMeta);
		
			ItemStack ammo = new ItemStack(Integer.parseInt(ammoID), Integer.parseInt(ammoAmount));
			ItemMeta ammoMeta = ammo.getItemMeta();
			ammoMeta.setDisplayName(ammoName);
			ammo.setItemMeta(ammoMeta);
		
			GGgunAPI.addGun(gun, Integer.parseInt(gunData), ammo, Integer.parseInt(ammoData));
		
			p.sendMessage(Main.codSignature + "§bGun Created!");
		}else{
			p.sendMessage(Main.codSignature + "§ePlease type §b/cod creategggun §6[Gun Item ID] [Gun Data Value] [Gun Name] [Ammo Item ID] [Ammo Data Value] [Ammo Amount] [Ammo Name]");
		}
	}
}