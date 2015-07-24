package com.CentrumGuy.CodWarfare.SpecialWeapons;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.OtherLoadout.Perk;
import com.CentrumGuy.CodWarfare.OtherLoadout.PerkAPI;

public class ElectroMagneticPulse {
	public static ItemStack EMP = new ItemStack(Material.SULPHUR);
	
	public static void setUp() {
		ItemMeta EMPMeta = EMP.getItemMeta();
		EMPMeta.setDisplayName("§c§lEMP");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§6Use this fast! This will");
		lore.add("§6blind everybody on the other");
		lore.add("§6team");
		EMPMeta.setLore(lore);
		EMP.setItemMeta(EMPMeta);
	}
	
	@SuppressWarnings("deprecation")
	public static void onEntityKill(EntityDeathEvent e) {
		if ((e.getEntity() instanceof Player) && (e.getEntity().getKiller() instanceof Player)) {
			Player killer = (Player) e.getEntity().getKiller();
			Player p = (Player) e.getEntity();
			
			if ((Main.PlayingPlayers.contains(killer)) && (Main.PlayingPlayers.contains(p))) {
				if ((Main.GameKillStreakScore.get(killer.getName()).getScore() == 10) || ((Main.GameKillStreakScore.get(killer.getName()).getScore() == 9) && (PerkAPI.getPerk(killer) == Perk.HARDLINE))) {
					killer.getInventory().addItem(EMP);
					killer.updateInventory();
					killer.sendMessage(Main.codSignature + "§c§lYou got an EMP. Use it fast by right-clicking it!");
				}
			}
		}
	}
	
	public static void onInteract(PlayerInteractEvent e) {
		if (e.getItem() == null) return;
		if (e.getItem().getType() == Material.AIR) return;
		if (!(e.getItem().equals(EMP))) return;
		if ((!(e.getAction().equals(Action.RIGHT_CLICK_AIR))) && (!(e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) && (!(e.getAction().equals(Action.LEFT_CLICK_AIR))) && (!(e.getAction().equals(Action.LEFT_CLICK_BLOCK)))) return;
		
		e.setCancelled(true);
		Player p = e.getPlayer();
		p.setItemInHand(null);
		p.sendMessage(Main.codSignature + "§aYou used your EMP!");
		
		if (GetPlayersOnOtherTeam.get(p).isEmpty()) return;
		for (Player pp : GetPlayersOnOtherTeam.get(p)) {
			pp.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 2));
		}
		
		for (Player pp : Main.PlayingPlayers) {
			if (Main.dispName.get(p) != null) {
				pp.sendMessage(Main.codSignature + Main.dispName.get(p) + " §4§llaunched an EMP!");
			}else{
				pp.sendMessage(Main.codSignature + p.getPlayerListName() + " §4§llaunched an EMP!");
			}
		}
	}
}
