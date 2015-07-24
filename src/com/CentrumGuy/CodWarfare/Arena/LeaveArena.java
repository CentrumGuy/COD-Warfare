package com.CentrumGuy.CodWarfare.Arena;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Interface.JoinCOD;
import com.CentrumGuy.CodWarfare.Interface.Scores;
import com.CentrumGuy.CodWarfare.OtherLoadout.PerkAPI;
import com.CentrumGuy.CodWarfare.OtherLoadout.WeaponUtils;
import com.CentrumGuy.CodWarfare.Utilities.GameCountdown;
import com.CentrumGuy.CodWarfare.Utilities.Prefix;
import com.CentrumGuy.CodWarfare.Utilities.SendCoolMessages;
import com.CentrumGuy.extras.parkour.Parkour;

public class LeaveArena {

	@SuppressWarnings("deprecation")
	public static void Leave (final Player p, Boolean Message, Boolean leave, Boolean tabHF) {
		if (Main.noHungerLoss.contains(p)) Main.noHungerLoss.remove(p);
		if (Main.invincible.contains(p)) Main.invincible.remove(p);
		if (GameCountdown.countdownPlayers.contains(p)) GameCountdown.countdownPlayers.remove(p);
		
		if (Main.WaitingPlayers.contains(p)) {
			if (leave) Main.WaitingPlayers.remove(p);
			
			for (int i = 0 ; i < p.getInventory().getSize() ; i++) {
				p.getInventory().setItem(i, new ItemStack(Material.AIR));
			}
			
			p.updateInventory();
			
			p.closeInventory();
			p.teleport(JoinCOD.teleport.get(p));
			p.getInventory().clear();
			p.getInventory().setContents(JoinCOD.inventory.get(p));
			p.updateInventory();
			p.getInventory().setArmorContents(JoinCOD.ArmorContents.get(p));
			p.updateInventory();
			p.setExp(JoinCOD.PlayerExp.get(p));
			p.setLevel(JoinCOD.Level.get(p));
			p.setMaxHealth(20);
			p.setHealth(JoinCOD.Health.get(p));
			p.setFoodLevel(JoinCOD.Food.get(p));
			p.setScoreboard(JoinCOD.sb.get(p));
			p.setGameMode(JoinCOD.gamemode.get(p));
			p.setWalkSpeed(JoinCOD.speed.get(p));
			Prefix.setDispName(p, null);
			p.setPlayerListName(JoinCOD.tabName.get(p));
			for (Player pp : Bukkit.getOnlinePlayers()) {
				if (Main.dispName.get(pp) != null) pp.setPlayerListName(Main.dispName.get(pp));
			}
			p.setAllowFlight(JoinCOD.canFly.get(p));
			p.setFlying(JoinCOD.isFlying.get(p));
			
			if (tabHF) {
				if (Main.header != null && Main.footer != null) {
					SendCoolMessages.TabHeaderAndFooter(Main.header, Main.footer, p);
				}else{
					SendCoolMessages.TabHeaderAndFooter("§e-§6-§e-§6-§e-§6-§e-§6-§e-§6-§e-§6-§e-", "§b-§3-§b-§3-§b-§3-§b-§3-§b-§3-§b-§3-§b-", p);
				}
			}
			
			if (Main.extras) Parkour.leaveParkour(p, true, false);
			
			for(PotionEffect effect : p.getActivePotionEffects()) {
			    p.removePotionEffect(effect.getType());
			}
			
			if (!(JoinCOD.pEffects.get(p) == null)) p.addPotionEffects(JoinCOD.pEffects.get(p));
			
			if (Message) p.sendMessage(Main.codSignature + "§aYou left COD-Warfare");
			
			Scores.saveScores(p);
			
			WeaponUtils.clearWeapons(p);
			
			PerkAPI.saveOwnedPerks(p);
			PerkAPI.savePerk(p);
			
		}else if (Main.PlayingPlayers.contains(p)) {
			
			if (leave) Main.PlayingPlayers.remove(p);
			
			if (BaseArena.type == BaseArena.ArenaType.INFECT) {
				if (INFECTArena.Blue.contains(p)) {
					INFECTArena.Blue.remove(p);
					if (INFECTArena.Blue.isEmpty()) {
						StopGameCountdown.endGame();
						INFECTArena.setTabInfo();
					}
				}else if (INFECTArena.Zombies.contains(p)) {
					INFECTArena.Zombies.remove(p);
					if (INFECTArena.Zombies.isEmpty()) {
						StopGameCountdown.endGame();
						INFECTArena.setTabInfo();
					}
				}
			}
			
			for (int i = 0 ; i < p.getInventory().getSize() ; i++) {
				p.getInventory().setItem(i, new ItemStack(Material.AIR));
			}
			
			p.updateInventory();
		
			p.closeInventory();
			p.teleport(JoinCOD.teleport.get(p));
			p.getInventory().clear();
			p.getInventory().setContents(JoinCOD.inventory.get(p));
			p.updateInventory();
			p.getInventory().setArmorContents(JoinCOD.ArmorContents.get(p));
			p.updateInventory();
			p.setExp(JoinCOD.PlayerExp.get(p));
			p.setLevel(JoinCOD.Level.get(p));
			p.setMaxHealth(20);
			p.setHealth(JoinCOD.Health.get(p));
			p.setFoodLevel(JoinCOD.Food.get(p));
			p.setScoreboard(JoinCOD.sb.get(p));
			p.setGameMode(JoinCOD.gamemode.get(p));
			p.setWalkSpeed(JoinCOD.speed.get(p));
			Prefix.setDispName(p, null);
			p.setPlayerListName(JoinCOD.tabName.get(p));
			for (Player pp : Bukkit.getOnlinePlayers()) {
				if (Main.dispName.get(pp) != null) pp.setPlayerListName(Main.dispName.get(pp));
			}
			p.setAllowFlight(JoinCOD.canFly.get(p));
			p.setFlying(JoinCOD.isFlying.get(p));
			
			if (tabHF) {
				if (Main.header != null && Main.footer != null) {
					SendCoolMessages.TabHeaderAndFooter(Main.header, Main.footer, p);
				}else{
					SendCoolMessages.TabHeaderAndFooter("§e-§6-§e-§6-§e-§6-§e-§6-§e-§6-§e-§6-§e-", "§b-§3-§b-§3-§b-§3-§b-§3-§b-§3-§b-§3-§b-", p);
				}
			}
			
			if (Main.extras) Parkour.leaveParkour(p, false, false);
			
			for(PotionEffect effect : p.getActivePotionEffects()) {
			    p.removePotionEffect(effect.getType());
			}
			
			if (!(JoinCOD.pEffects.get(p) == null)) p.addPotionEffects(JoinCOD.pEffects.get(p));
		
			if (Message) p.sendMessage(Main.codSignature + "§aYou left COD-Warfare");
			
			Scores.saveScores(p);
			
			WeaponUtils.clearWeapons(p);
			
			PerkAPI.saveOwnedPerks(p);
			PerkAPI.savePerk(p);
			
			if (getArena.getType(PickRandomArena.CurrentArena).equals("CTF")) {
				if (Main.PlayingPlayers.isEmpty()) {
					CTFArena.removeFlags(PickRandomArena.CurrentArena);
				}
				if (CTFArena.holdingBlueFlag.get(p) != null && CTFArena.holdingBlueFlag.get(p) == true) {
					CTFArena.returnFlag("blue", PickRandomArena.CurrentArena);
	        		for (Player pp : Main.PlayingPlayers) {
	        			pp.sendMessage(Main.codSignature + "§c" + p.getName() + " §6dropped the §9blue §6flag");
	        		}
					CTFArena.holdingBlueFlag.put(p, false);
				}else if (CTFArena.holdingRedFlag.get(p) != null && CTFArena.holdingRedFlag.get(p) == true) {
					CTFArena.returnFlag("red", PickRandomArena.CurrentArena);
	        		for (Player pp : Main.PlayingPlayers) {
	        			pp.sendMessage(Main.codSignature + "§9" + p.getName() + " §6dropped the §cred §6flag");
	        		}
        			CTFArena.holdingRedFlag.put(p, false);
				}
			}
			
			if ((Main.PlayingPlayers.isEmpty()) && (!(Main.WaitingPlayers.isEmpty()))) {
				StopGameCountdown.endGame();
			}
        	
			return;
		}else{
			if (Message) p.sendMessage(Main.codSignature + "§cYou are not in COD-Warfare");
			return;
		}
	}
}
