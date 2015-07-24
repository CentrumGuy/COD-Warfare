package com.CentrumGuy.CodWarfare.SpecialWeapons;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Arena.BaseArena;
import com.CentrumGuy.CodWarfare.Arena.INFECTArena;
import com.CentrumGuy.CodWarfare.Inventories.Guns;
import com.CentrumGuy.CodWarfare.OtherLoadout.Perk;
import com.CentrumGuy.CodWarfare.OtherLoadout.PerkAPI;
import com.CentrumGuy.CodWarfare.Utilities.GetNormalName;

public class MoreAmmo {
	@SuppressWarnings("deprecation")
	public static void onEntityKill(EntityDeathEvent e) {
		if ((e.getEntity() instanceof Player) && (e.getEntity().getKiller() instanceof Player)) {
			Player killer = (Player) e.getEntity().getKiller();
			Player victim = (Player) e.getEntity();
			
			if ((Main.PlayingPlayers.contains(killer)) && (Main.PlayingPlayers.contains(victim))) {
				if (BaseArena.type == BaseArena.ArenaType.GUN) return;
				if (BaseArena.type == BaseArena.ArenaType.INFECT) {
					if (INFECTArena.Zombies.contains(killer)) return;
				}
				if ((Main.GameKillStreakScore.get(killer.getName()).getScore() == 5) || ((Main.GameKillStreakScore.get(killer.getName()).getScore() == 4) && (PerkAPI.getPerk(killer) == Perk.HARDLINE))) {
					if (!(Main.CrackShot)) {
						ItemStack primary = null;
						for (int i = 0 ; i < Guns.guns.size() ; i++) {
							if (Guns.guns.get(i).getType() == killer.getInventory().getItem(1).getType() &&
								Guns.guns.get(i).getData().getData() == killer.getInventory().getItem(1).getData().getData()) {
									primary = Guns.guns.get(i);
									break;
							}
						}
						
						if (primary != null) {
							ItemStack pAmmo = new ItemStack(Guns.getAmmo(primary).getType(), Guns.getAmmo(primary).getAmount(), Guns.getAmmo(primary).getData().getData());
							ItemMeta pAmmoMeta = pAmmo.getItemMeta();
							pAmmoMeta.setDisplayName(Guns.getAmmo(primary).getItemMeta().getDisplayName());
							pAmmo.setItemMeta(pAmmoMeta);
							
							killer.getInventory().setItem(20, pAmmo);
						}
						
						if (killer.getInventory().getItem(2) != null) {
							ItemStack secondary = null;
							for (int i = 0 ; i < Guns.guns.size() ; i++) {
								if (Guns.guns.get(i).getType() == killer.getInventory().getItem(2).getType() &&
									Guns.guns.get(i).getData().getData() == killer.getInventory().getItem(2).getData().getData()) {
										secondary = Guns.guns.get(i);
										break;
								}
							}
							
							if (secondary != null) {
								ItemStack sAmmo = new ItemStack(Guns.getAmmo(secondary).getType(), Guns.getAmmo(secondary).getAmount(), Guns.getAmmo(secondary).getData().getData());
								ItemMeta sAmmoMeta = sAmmo.getItemMeta();
								sAmmoMeta.setDisplayName(Guns.getAmmo(secondary).getItemMeta().getDisplayName());
								sAmmo.setItemMeta(sAmmoMeta);
								
								killer.getInventory().setItem(24, sAmmo);
							}
						}
					}else{
						ItemStack realPrimary = null;
						String realPrimaryName = "§e" + GetNormalName.get(Main.CrackShotAPI.getWeaponTitle(killer.getInventory().getItem(1)));
						for (int i = 0 ; i < Guns.guns.size() ; i++) {
							if (Guns.guns.get(i).getItemMeta().getDisplayName().equals(realPrimaryName)) {
								realPrimary = Guns.guns.get(i);
								break;
							}
						}
						
						if (realPrimary != null) {
							ItemStack pAmmo = new ItemStack(Guns.getAmmo(realPrimary).getType(), Guns.getAmmo(realPrimary).getAmount(), Guns.getAmmo(realPrimary).getData().getData());
							ItemMeta pAmmoMeta = pAmmo.getItemMeta();
							pAmmoMeta.setDisplayName(Guns.getAmmo(realPrimary).getItemMeta().getDisplayName());
							pAmmo.setItemMeta(pAmmoMeta);
							
							killer.getInventory().setItem(20, pAmmo);
						}
						
						//===============================================================================================
						
						if (killer.getInventory().getItem(2) != null) {
							ItemStack realSecondary = null;
							String realSecondaryName = "§e" + GetNormalName.get(Main.CrackShotAPI.getWeaponTitle(killer.getInventory().getItem(2)));
							for (int i = 0 ; i < Guns.guns.size() ; i++) {
								if (Guns.guns.get(i).getItemMeta().getDisplayName().equals(realSecondaryName)) {
									realSecondary = Guns.guns.get(i);
									break;
								}
							}
							
							if (realSecondary != null) {
								ItemStack sAmmo = new ItemStack(Guns.getAmmo(realSecondary).getType(), Guns.getAmmo(realSecondary).getAmount(), Guns.getAmmo(realSecondary).getData().getData());
								ItemMeta sAmmoMeta = sAmmo.getItemMeta();
								sAmmoMeta.setDisplayName(Guns.getAmmo(realSecondary).getItemMeta().getDisplayName());
								sAmmo.setItemMeta(sAmmoMeta);
								
								killer.getInventory().setItem(24, sAmmo);
							}
						}
					}
					
					killer.sendMessage(Main.codSignature + "§e5 Killstreak! More Ammo!");
				}
			}
		}
	}
}
