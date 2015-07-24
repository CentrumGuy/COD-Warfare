package com.CentrumGuy.CodWarfare.Arena;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Inventories.KitInventory;
import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;
import com.CentrumGuy.CodWarfare.Utilities.GameCountdown;
import com.CentrumGuy.CodWarfare.Utilities.GetNormalName;
import com.CentrumGuy.CodWarfare.Utilities.Prefix;
import com.CentrumGuy.CodWarfare.Utilities.SendCoolMessages;
import com.CentrumGuy.CodWarfare.Utilities.ShowPlayer;

public class FFAArena {
	
	private static ArrayList<Player> ps = Main.PlayingPlayers;
	
	private static void setTabInfo() {
		Player bestPlayer = null;
		int score = 0;
		
		for (Player p : Main.PlayingPlayers) {
			if (Main.GameKillsScore.get(p.getName()).getScore() > score) {
				score = Main.GameKillsScore.get(p.getName()).getScore();
				bestPlayer = p;
			}
		}
		
		String name;
		
		if (bestPlayer != null) {
			name = "§d" + bestPlayer.getName() + "§5(§d" + score + "§5)";
		}else{
			name = "§7None";
		}
		
		for (Player p : Main.PlayingPlayers) {
			SendCoolMessages.TabHeaderAndFooter("§d§lFree §5§lFor §d§lAll", "§6§lCOD-Warfare\n" + name, p);
		}
	}

	public static void startFFA(final String Arena) {
		Main.PlayingPlayers.addAll(Main.WaitingPlayers);
		Main.WaitingPlayers.clear();
		
		FFASpawningSystem.RegisterSpawns(ps, Arena);
		
		for (final Player p : ps) {
			Location l = FFASpawningSystem.SpawnPlayer(p, true);
			GameCountdown.startCountdown(p, l, "§d§lGO GO GO!!!", null);
			Main.setGameBoard(p);
			p.getInventory().clear();
			p.closeInventory();
			Prefix.setDispName(p, "§d" + p.getName());
			if (Main.dispName.get(p) != null) p.setPlayerListName(Main.dispName.get(p));
			SendCoolMessages.TabHeaderAndFooter("§d§lFree §5§lFor §d§lAll", "§6§lCOD-Warfare", p);
			
			p.setHealth(20);
			p.setFoodLevel(20);
			
			Color c = Color.fromRGB(255, 0, 255);
			p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
			p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
			p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
			p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
			
			/*for (Player pp : Main.PlayingPlayers) {
				Main.purple.addPlayer(pp);
			}*/
			
			if (Main.extraCountdown) {
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
					public void run() {
						SendCoolMessages.clearTitleAndSubtitle(p);
						SendCoolMessages.resetTitleAndSubtitle(p);
						SendCoolMessages.sendTitle(p, "§5§lFFA", 10, 50, 10);
						SendCoolMessages.sendSubTitle(p, "§d§lKill Everyone", 10, 50, 10);
					}
				}, 60);
			}
			
			if (Main.extraCountdown) {
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
					public void run() {
						if (Main.PlayingPlayers.contains(p)) {
							
							ShowPlayer.showPlayer(p);
							
							Color c = Color.fromRGB(255, 0, 255);
							p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
							p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
							p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
							p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
							
							p.getInventory().setItem(0, KitInventory.getKit(p).getItem(0));
							if (!(Main.CrackShot)) {
								p.getInventory().setItem(1, KitInventory.getKit(p).getItem(1));
								p.getInventory().setItem(2, KitInventory.getKit(p).getItem(3));
							}else{
								p.getInventory().setItem(1, Main.CrackShotAPI.generateWeapon(GetNormalName.get(KitInventory.getKit(p).getItem(1).getItemMeta().getDisplayName())));
								p.getInventory().setItem(2, Main.CrackShotAPI.generateWeapon(GetNormalName.get(KitInventory.getKit(p).getItem(3).getItemMeta().getDisplayName())));
							}
							p.getInventory().setItem(19, KitInventory.getKit(p).getItem(2));
							p.getInventory().setItem(25, KitInventory.getKit(p).getItem(4));
							p.getInventory().setItem(3, KitInventory.getKit(p).getItem(5));
							p.getInventory().setItem(4, KitInventory.getKit(p).getItem(6));
							p.getInventory().setItem(5, KitInventory.getKit(p).getItem(7));
							
							p.sendMessage("");
							p.sendMessage("§e§m=====================================================");
							p.sendMessage("§b§lYou are playing a game of §4§lFree For All!");
							p.sendMessage(" §7§l- §6§lKill §d§leveryone");
							p.sendMessage(" §7§l- §6§lArena:§e§l " + Arena);
							p.sendMessage(" §7§l- §6§lGood luck!");
							p.sendMessage("§e§m=====================================================");
							p.sendMessage("");
							
							p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 5));
						}
					}
				}, 225);
			}else{
				ShowPlayer.showPlayer(p);
				
				p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
				p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
				p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
				p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
				
				p.getInventory().setItem(0, KitInventory.getKit(p).getItem(0));
				if (!(Main.CrackShot)) {
					p.getInventory().setItem(1, KitInventory.getKit(p).getItem(1));
					p.getInventory().setItem(2, KitInventory.getKit(p).getItem(3));
				}else{
					p.getInventory().setItem(1, Main.CrackShotAPI.generateWeapon(GetNormalName.get(KitInventory.getKit(p).getItem(1).getItemMeta().getDisplayName())));
					p.getInventory().setItem(2, Main.CrackShotAPI.generateWeapon(GetNormalName.get(KitInventory.getKit(p).getItem(3).getItemMeta().getDisplayName())));
				}
				p.getInventory().setItem(19, KitInventory.getKit(p).getItem(2));
				p.getInventory().setItem(25, KitInventory.getKit(p).getItem(4));
				p.getInventory().setItem(3, KitInventory.getKit(p).getItem(5));
				p.getInventory().setItem(4, KitInventory.getKit(p).getItem(6));
				p.getInventory().setItem(5, KitInventory.getKit(p).getItem(7));
				
				p.sendMessage("");
				p.sendMessage("§e§m=====================================================");
				p.sendMessage("§b§lYou are playing a game of §4§lFree For All!");
				p.sendMessage(" §7§l- §6§lKill §d§leveryone");
				p.sendMessage(" §7§l- §6§lArena:§e§l " + Arena);
				p.sendMessage(" §7§l- §6§lGood luck!");
				p.sendMessage("§e§m=====================================================");
				p.sendMessage("");
				
				p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 5));
			}
		}
	}
	
	public static Location getSpawn(Player p) {
		if (ps.contains(p)) {
			return FFASpawningSystem.SpawnPlayer(p, false);
		}else{
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	public static void respawnPlayer(Player p) {
		if (BaseArena.state == BaseArena.ArenaState.ENDING) {
			p.getInventory().clear();
			p.updateInventory();
			
			Color c = Color.fromRGB(255, 0, 255);
			p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
			p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
			p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
			p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
			
			Main.invincible.add(p);
			return;
		}
		
		Color c = Color.fromRGB(255, 0, 255);
		p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
		p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
		p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
		p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
		
		p.getInventory().setItem(0, KitInventory.getKit(p).getItem(0));
		if (!(Main.CrackShot)) {
			p.getInventory().setItem(1, KitInventory.getKit(p).getItem(1));
			p.getInventory().setItem(2, KitInventory.getKit(p).getItem(3));
		}else{
			p.getInventory().setItem(1, Main.CrackShotAPI.generateWeapon(GetNormalName.get(KitInventory.getKit(p).getItem(1).getItemMeta().getDisplayName())));
			p.getInventory().setItem(2, Main.CrackShotAPI.generateWeapon(GetNormalName.get(KitInventory.getKit(p).getItem(3).getItemMeta().getDisplayName())));
		}
		p.getInventory().setItem(19, KitInventory.getKit(p).getItem(2));
		p.getInventory().setItem(25, KitInventory.getKit(p).getItem(4));
		p.getInventory().setItem(3, KitInventory.getKit(p).getItem(5));
		p.getInventory().setItem(4, KitInventory.getKit(p).getItem(6));
		p.getInventory().setItem(5, KitInventory.getKit(p).getItem(7));
		
		p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 5));
		
		p.sendMessage(Main.codSignature + "§b§lFFA §7- §3Kill everyone!");
	}
	
	private static ItemStack getColorArmor(Material m, Color c) {
		ItemStack i = new ItemStack(m , 1);
		LeatherArmorMeta meta = (LeatherArmorMeta) i.getItemMeta();
		meta.setColor(c);
		i.setItemMeta(meta);
		return i;
	}
	
	public static void onKill(Player killer) {
		setTabInfo();
		if (Main.GameKillsScore.get(killer.getName()).getScore() >= 21) {
			StopGameCountdown.endGame();
			return;
		}
	}
	
	public static void endFFA() {
		
		Player bestPlayer = null;
		int score = 0;
		
		for (Player p : Main.PlayingPlayers) {
			if (Main.GameKillsScore.get(p.getName()).getScore() > score) {
				score = Main.GameKillsScore.get(p.getName()).getScore();
				bestPlayer = p;
			}
		}
		
		String name;
		
		if (bestPlayer != null) {
			name = bestPlayer.getName() + "(" + score + ")";
		}else{
			name = "§7§lNONE";
		}
		
		for (Player pp : Main.PlayingPlayers) {
			pp.sendMessage("");
			pp.sendMessage("");
			pp.sendMessage("");
			pp.sendMessage("§7║ §b§lStatistics:§6§l " + PickRandomArena.CurrentArena);
			pp.sendMessage("§7║");
			pp.sendMessage("§7║ §7§lWinner:§d§l " + name + "             §b§lTotal Kills:§a§l " + Main.GameKillsScore.get(pp.getName()).getScore());
			
			DecimalFormat df = new DecimalFormat("#.##");
			
			float kdr = ((float) Main.GameKillsScore.get(pp.getName()).getScore()) / ((float) Main.GameDeathsScore.get(pp.getName()).getScore());
			
			if (Main.GameDeathsScore.get(pp.getName()).getScore() == 0) {
				pp.sendMessage("§7║" + " §b§lTotal Deaths:§a§l " + Main.GameDeathsScore.get(pp.getName()).getScore() + "       §b§lKDR:§a§l " + Main.GameKillsScore.get(pp.getName()).getScore());
			}else if (Main.GameKillsScore.get(pp.getName()).getScore() == 0) {
				pp.sendMessage("§7║" + " §b§lTotal Deaths:§a§l " + Main.GameDeathsScore.get(pp.getName()).getScore() + "       §b§lKDR:§a§l " + Main.GameKillsScore.get(pp.getName()).getScore());
			}else{
				pp.sendMessage("§7║" + " §b§lTotal Deaths:§a§l " + Main.GameDeathsScore.get(pp.getName()).getScore() + "       §b§lKDR:§a§l " + df.format(kdr));
			}
			
			pp.sendMessage("§7║");
			pp.sendMessage("§7╚§7§l════════════════════════════");
		}
		
		for (Player pp : Main.WaitingPlayers) {
			if (name.equals("§7§lNONE")) name = "§7No one";
			pp.sendMessage(Main.codSignature + "§d" + name + " §5won!");
		}
	}
	
}
