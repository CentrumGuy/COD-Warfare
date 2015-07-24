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

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Inventories.KitInventory;
import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;
import com.CentrumGuy.CodWarfare.Utilities.GameCountdown;
import com.CentrumGuy.CodWarfare.Utilities.GameVersion;
import com.CentrumGuy.CodWarfare.Utilities.GetNormalName;
import com.CentrumGuy.CodWarfare.Utilities.Prefix;
import com.CentrumGuy.CodWarfare.Utilities.SendCoolMessages;
import com.CentrumGuy.CodWarfare.Utilities.ShowPlayer;

public class GUNArena {
	
	private static ArrayList<Player> ps = new ArrayList<Player>();
	private static int goalScore;
	
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
			SendCoolMessages.TabHeaderAndFooter("§d§lGun §5§lGame", "§6§lCOD-Warfare\n" + name, p);
		}
	}

	public static void startGUN(final String Arena) {
		Main.PlayingPlayers.addAll(Main.WaitingPlayers);
		ps = Main.PlayingPlayers;
		Main.WaitingPlayers.clear();
		
		FFASpawningSystem.RegisterSpawns(ps, Arena);
		
		goalScore = GGgunAPI.Guns.size();
		
		for (final Player p : ps) {
			Location l = FFASpawningSystem.SpawnPlayer(p, true);
			GameCountdown.startCountdown(p, l, "§d§lGO GO GO!!!", null);
			Main.setGameBoard(p);
			p.getInventory().clear();
			p.closeInventory();
			Prefix.setDispName(p, "§d" + p.getName());
			if (Main.dispName.get(p) != null) p.setPlayerListName(Main.dispName.get(p));
			SendCoolMessages.TabHeaderAndFooter("§d§lGun §5§lGame", "§6§lCOD-Warfare", p);
			
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
						SendCoolMessages.sendTitle(p, "§5§lGun Game", 10, 50, 10);
						SendCoolMessages.sendSubTitle(p, "§d§lKill Everyone", 10, 50, 10);
					}
				}, 60);
			}
			
			if (Main.extraCountdown) {
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
					public void run() {
						if (!(Main.PlayingPlayers.contains(p))) return;
						
						ShowPlayer.showPlayer(p);
						
						Color c = Color.fromRGB(255, 0, 255);
						p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
						p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
						p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
						p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
						
						p.getInventory().setItem(1, GGgunAPI.getCurrentGun(0));
						p.getInventory().setItem(8, GGgunAPI.getAmmo(p.getInventory().getItem(1)));
						
						p.getInventory().setItem(0, KitInventory.getKit(p).getItem(0));
						
						if (!(Main.CrackShot)) {
							p.getInventory().setItem(1, GGgunAPI.getCurrentGun(0));
						}else{
							p.getInventory().setItem(1, Main.CrackShotAPI.generateWeapon(GetNormalName.get(GGgunAPI.getCurrentGun(0).getItemMeta().getDisplayName())));
						}
						
						p.sendMessage("");
						p.sendMessage("§e§m=====================================================");
						p.sendMessage("§b§lYou are playing a game of §4§lGun Game!");
						p.sendMessage(" §7§l- §6§lKill §d§leveryone");
						p.sendMessage(" §7§l- §6§lGun upgrades every kill");
						p.sendMessage(" §7§l- §6§lArena:§e§l " + Arena);
						p.sendMessage(" §7§l- §6§lGood luck!");
						p.sendMessage("§e§m=====================================================");
						p.sendMessage("");
						
						Main.invincible.add(p);
					}
				}, 225);
			}else{
				ShowPlayer.showPlayer(p);
				
				p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
				p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
				p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
				p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
				
				p.getInventory().setItem(0, KitInventory.getKit(p).getItem(0));
				p.getInventory().setItem(1, GGgunAPI.getCurrentGun(0));
				p.getInventory().setItem(8, GGgunAPI.getAmmo(p.getInventory().getItem(1)));
				
				if (!(Main.CrackShot)) {
					p.getInventory().setItem(1, GGgunAPI.getCurrentGun(0));
				}else{
					p.getInventory().setItem(1, Main.CrackShotAPI.generateWeapon(GetNormalName.get(GGgunAPI.getCurrentGun(0).getItemMeta().getDisplayName())));
				}
				
				p.sendMessage("");
				p.sendMessage("§e§m=====================================================");
				p.sendMessage("§b§lYou are playing a game of §4§lGun Game!");
				p.sendMessage(" §7§l- §6§lKill §d§leveryone");
				p.sendMessage(" §7§l- §6§lGun upgrades every kill");
				p.sendMessage(" §7§l- §6§lArena:§e§l " + Arena);
				p.sendMessage(" §7§l- §6§lGood luck!");
				p.sendMessage("§e§m=====================================================");
				p.sendMessage("");
				
				Main.invincible.add(p);
			}
			
			if (Main.extraCountdown) {
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
					public void run() {
						if (Main.invincible.contains(p)) Main.invincible.remove(p);
					}
				}, 305);
			}else{
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
					public void run() {
						if (Main.invincible.contains(p)) Main.invincible.remove(p);
					}
				}, 105);
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
	public static void respawnPlayer(final Player p) {
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
		
		p.getInventory().setItem(0, KitInventory.getKit(p).getItem(0));
		p.getInventory().setItem(1, GGgunAPI.getCurrentGun(Main.GameKillsScore.get(p.getName()).getScore()));
		p.getInventory().setItem(8, GGgunAPI.getAmmo(p.getInventory().getItem(1)));
		
		Color c = Color.fromRGB(255, 0, 255);
		p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
		p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
		p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
		p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
		
		if (!(Main.CrackShot)) {
			p.getInventory().setItem(1, GGgunAPI.getCurrentGun(Main.GameKillsScore.get(p.getName()).getScore()));
		}else{
			p.getInventory().setItem(1, Main.CrackShotAPI.generateWeapon(GetNormalName.get(GGgunAPI.getCurrentGun(Main.GameKillsScore.get(p.getName()).getScore()).getItemMeta().getDisplayName())));
		}
		
		for (Player pp : Bukkit.getOnlinePlayers()) {
			if (!(pp.equals(p))) pp.showPlayer(p);
		}
		
		Main.invincible.add(p);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
			public void run() {
				if (Main.invincible.contains(p)) Main.invincible.remove(p);
			}
		}, 100);
		
		p.sendMessage(Main.codSignature + "§b§lGun Game §7- §3Kill everyone! Gun upgrades every kill");
	}
	
	public static void onKill(Player Killer) {
		setTabInfo();
		if (Main.GameKillsScore.get(Killer.getName()).getScore() >= goalScore) {
			StopGameCountdown.endGame();
			return;
		}
		
		SendCoolMessages.sendOverActionBar(Killer, "§6§lGun Upgrade:§e§l " + GetNormalName.get(GGgunAPI.getName(Main.GameKillsScore.get(Killer.getName()).getScore())));
		
		if (!(GameVersion.above47(Killer))) {
			Killer.sendMessage(Main.codSignature + "§6§lGun Upgrade:§e§l " + GetNormalName.get(GGgunAPI.getName(Main.GameKillsScore.get(Killer.getName()).getScore())));
		}
		
		Killer.getInventory().setItem(1, GGgunAPI.getCurrentGun(Main.GameKillsScore.get(Killer.getName()).getScore()));
		Killer.getInventory().setItem(8, GGgunAPI.getAmmo(Killer.getInventory().getItem(1)));
		
		if (!(Main.CrackShot)) {
			Killer.getInventory().setItem(1, GGgunAPI.getCurrentGun(Main.GameKillsScore.get(Killer.getName()).getScore()));
		}else{
			Killer.getInventory().setItem(1, Main.CrackShotAPI.generateWeapon(GetNormalName.get(GGgunAPI.getCurrentGun(Main.GameKillsScore.get(Killer.getName()).getScore()).getItemMeta().getDisplayName())));
		}
	}
	
	private static ItemStack getColorArmor(Material m, Color c) {
		ItemStack i = new ItemStack(m , 1);
		LeatherArmorMeta meta = (LeatherArmorMeta) i.getItemMeta();
		meta.setColor(c);
		i.setItemMeta(meta);
		return i;
	}
	
	@SuppressWarnings("deprecation")
	public static void endGUN() {
		Player bestPlayer = null;
		int score = 0;
		
		for (Player p : Main.PlayingPlayers) {
			p.getInventory().clear();
			p.updateInventory();
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
