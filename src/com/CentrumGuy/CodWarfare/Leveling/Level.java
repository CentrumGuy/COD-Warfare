package com.CentrumGuy.CodWarfare.Leveling;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Interface.ItemsAndInventories;
import com.CentrumGuy.CodWarfare.Interface.Scores;
import com.CentrumGuy.CodWarfare.Inventories.AGPInventory;
import com.CentrumGuy.CodWarfare.Inventories.AGSInventory;
import com.CentrumGuy.CodWarfare.Inventories.Guns;
import com.CentrumGuy.CodWarfare.Inventories.KitInventory;
import com.CentrumGuy.CodWarfare.Inventories.ShopInventoryPrimary;
import com.CentrumGuy.CodWarfare.Inventories.ShopInventorySecondary;
import com.CentrumGuy.CodWarfare.Utilities.PlaySounds;

public class Level {

	public static HashMap<Player, Integer> Level = new HashMap<Player, Integer>();
	
	public static void setLevel(Player p, Integer Amount) {
		Level.put(p, 0);
		Level.put(p, Amount);
	}
	
	public static void resetLevel(Player p) {
		Level.put(p, 1);
		Exp.ExpNow.put(p, 0);
		Exp.NeededExpNow.put(p, 210);
		Exp.NeededExpFromBefore.put(p, 0);
		Exp.setExp(p, 0);
		Exp.setNeededExp(p, 210);
		
		if (Main.LobbyLevelScore.get(p.getName()) != null) Main.LobbyLevelScore.get(p.getName()).setScore(1);
		
		Guns.loadGuns();
		
		AGPInventory.getAGP(p);
		AGSInventory.getAGS(p);
		ShopInventoryPrimary.getPrimaryShop(p);
		ShopInventorySecondary.getSecondaryShop(p);
		KitInventory.getKit(p);
		
		AGPInventory.getAGP(p).clear();
		AGSInventory.getAGS(p).clear();
		
		for (int i = 0 ; i < Guns.guns.size() ; i++) {
			ItemStack gun = Guns.guns.get(i);
			
			if (Guns.getLevel(gun) <= Level.get(p)) {
				if (Guns.getLevel(gun) == -1) continue;
				if (Guns.getType(gun).equalsIgnoreCase("Primary")) {
					AGPInventory.getAGP(p).addItem(gun);
					KitInventory.getKit(p).setItem(1, gun);
					KitInventory.getKit(p).setItem(2, Guns.getAmmo(gun));
					continue;
				}else if (Guns.getType(gun).equalsIgnoreCase("Secondary")) {
					AGSInventory.getAGS(p).addItem(gun);
					KitInventory.getKit(p).setItem(3, gun);
					KitInventory.getKit(p).setItem(4, Guns.getAmmo(gun));
					continue;
				}else{
					continue;
				}
			}
		}
		
		KitInventory.getKit(p).setItem(0, Main.knife);
		KitInventory.saveKit(p);
		KitInventory.loadKit(p);
		
		AGPInventory.saveAGP(p);
		AGPInventory.loadAGP(p);
		AGSInventory.saveAGS(p);
		AGSInventory.loadAGS(p);
		
		ShopInventoryPrimary.getPrimaryShop(p).clear();
		ShopInventoryPrimary.savePrimaryShop(p);
		
		ShopInventorySecondary.getSecondaryShop(p).clear();
		ShopInventorySecondary.saveSecondaryShop(p);
		
		ItemsAndInventories.setUpPlayer(p);
		ItemsAndInventories.setAvailableGuns(p);
		ItemsAndInventories.updateShop(p);
		
		Scores.saveScores(p);
	}
	
	public static void add(Player p, int Amount) {
		Level.put(p, Level.get(p) + Amount);
		
		for (int i = 0 ; i < Guns.guns.size() ; i++) {
			ItemStack gun = Guns.guns.get(i);
			
			if (Guns.getLevel(gun) == Level.get(p)) {
				if (Guns.getType(gun).equalsIgnoreCase("Primary")) {
					ShopInventoryPrimary.Pshop.get(p).addItem(gun);
					ShopInventoryPrimary.savePrimaryShop(p);
					ShopInventoryPrimary.loadPrimaryShop(p);
					continue;
				}else if (Guns.getType(gun).equalsIgnoreCase("Secondary")) {
					ShopInventorySecondary.Sshop.get(p).addItem(gun);
					ShopInventorySecondary.saveSecondaryShop(p);
					ShopInventorySecondary.loadSecondaryShop(p);
					continue;
				}else{
					continue;
				}
			}
		}
		
		p.sendMessage(Main.codSignature + "§bYou are now level§6 " + Level.get(p));
		PlaySounds.playLevelUp(p);
		ItemsAndInventories.setUpPlayer(p);
		ItemsAndInventories.setAvailableGuns(p);
		ItemsAndInventories.updateShop(p);
	}
	
	public static int getLevel(Player p) {
		
		if (Level.get(p) == null) {
			setLevel(p, 1);
			return Level.get(p);
		}else{		
			return Level.get(p);
		}
	}
}
