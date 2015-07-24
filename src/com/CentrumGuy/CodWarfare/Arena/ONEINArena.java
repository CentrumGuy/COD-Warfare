package com.CentrumGuy.CodWarfare.Arena;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Interface.ResetPlayer;
import com.CentrumGuy.CodWarfare.Inventories.KitInventory;
import com.CentrumGuy.CodWarfare.Lobby.Lobby;
import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;
import com.CentrumGuy.CodWarfare.Utilities.GameCountdown;
import com.CentrumGuy.CodWarfare.Utilities.GameVersion;
import com.CentrumGuy.CodWarfare.Utilities.GetNormalName;
import com.CentrumGuy.CodWarfare.Utilities.Prefix;
import com.CentrumGuy.CodWarfare.Utilities.SendCoolMessages;
import com.CentrumGuy.CodWarfare.Utilities.ShowPlayer;

public class ONEINArena {
	
	public static ArrayList<Player> spectators = new ArrayList<Player>();
	private static ArrayList<Player> ps = Main.PlayingPlayers;
	public static HashMap<Player, Integer> Lives = new HashMap<Player, Integer>();
	private static HashMap<Player, Integer> kills = new HashMap<Player, Integer>();
	
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
			if (!(specContains(p))) SendCoolMessages.TabHeaderAndFooter("§d§lOne §5§lIn §d§lThe §5§lChamber §7- §c" + Lives.get(p) + " §4❤", "§6§lCOD-Warfare\n" + name, p);
		}
	}

	public static void startONEIN(final String Arena) {
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
			SendCoolMessages.TabHeaderAndFooter("§d§lOne §5§lIn §d§lThe §5§lChamber §7- §c3 §4❤", "§6§lCOD-Warfare", p);
			
			p.setHealth(20);
			p.setFoodLevel(20);
			
			kills.put(p, 0);
			
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
						SendCoolMessages.sendTitle(p, "§6", 10, 50, 10);
						SendCoolMessages.sendSubTitle(p, "§d§lOne In The Chamber", 10, 50, 10);
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
						
						p.getInventory().setItem(0, KitInventory.getKit(p).getItem(0));
						if (!(Main.CrackShot)) {
							p.getInventory().setItem(1, KitInventory.getKit(p).getItem(3));
						}else{
							p.getInventory().setItem(1, Main.CrackShotAPI.generateWeapon(GetNormalName.get(KitInventory.getKit(p).getItem(3).getItemMeta().getDisplayName())));
						}
						
						ItemStack ammo = new ItemStack(KitInventory.getKit(p).getItem(4).getType(), 1);
						ItemMeta ammoMeta = ammo.getItemMeta();
						ammoMeta.setDisplayName(KitInventory.getKit(p).getItem(4).getItemMeta().getDisplayName());
						ammo.setItemMeta(ammoMeta);
						
						p.getInventory().setItem(2, ammo);
						
						p.sendMessage("");
						p.sendMessage("§e§m=====================================================");
						p.sendMessage("§b§lYou are playing a game of §4§lOne In The Chamber!");
						p.sendMessage(" §7§l- §6§lKill §d§leveryone");
						p.sendMessage(" §7§l- §6§lOne hit kill");
						p.sendMessage(" §7§l- §6§lGet one bullet every kill");
						p.sendMessage(" §7§l- §6§lArena:§e§l " + Arena);
						p.sendMessage(" §7§l- §6§lGood luck!");
						p.sendMessage("§e§m=====================================================");
						p.sendMessage("");
						
						p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 5));
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
					p.getInventory().setItem(1, KitInventory.getKit(p).getItem(3));
				}else{
					p.getInventory().setItem(1, Main.CrackShotAPI.generateWeapon(GetNormalName.get(KitInventory.getKit(p).getItem(3).getItemMeta().getDisplayName())));
				}
				
				ItemStack ammo = new ItemStack(KitInventory.getKit(p).getItem(4).getType(), 1);
				ItemMeta ammoMeta = ammo.getItemMeta();
				ammoMeta.setDisplayName(KitInventory.getKit(p).getItem(4).getItemMeta().getDisplayName());
				ammo.setItemMeta(ammoMeta);
				
				p.getInventory().setItem(2, ammo);
				
				p.sendMessage("");
				p.sendMessage("§e§m=====================================================");
				p.sendMessage("§b§lYou are playing a game of §4§lOne In The Chamber!");
				p.sendMessage(" §7§l- §6§lKill §d§leveryone");
				p.sendMessage(" §7§l- §6§lOne hit kill");
				p.sendMessage(" §7§l- §6§lGet one bullet every kill");
				p.sendMessage(" §7§l- §6§lArena:§e§l " + Arena);
				p.sendMessage(" §7§l- §6§lGood luck!");
				p.sendMessage("§e§m=====================================================");
				p.sendMessage("");
				
				p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 5));
			}
			
			Lives.put(p, 3);
		}
	}
	
	public static Location getSpawn(Player p) {
		if (ps.contains(p)) {
			if (Lives.get(p) > 1) {
				return FFASpawningSystem.SpawnPlayer(p, false);
			}else{
				if (Main.ONEINspectate) {
					return getArena.getSpectatorSpawn(PickRandomArena.CurrentArena);
				}else{
					return Lobby.getLobby();
				}
			}
		}else{
			return null;
		}
	}
	
	public static void respawnPlayer(final Player p, String Arena) {	
		Lives.put(p, (Lives.get(p)) - (1));
		
		setTabInfo();
		
		if (Lives.get(p) == 0) {
			if (Main.ONEINspectate) {
				spectators.add(p);
				
				p.sendMessage("");
				p.sendMessage("§e§m=======================");
				p.sendMessage("§b§lYou ran out of lives");
				p.sendMessage("§e§m=======================");
				p.sendMessage("");
				
				p.getInventory().clear();
				p.getInventory().setArmorContents(null);
				
				if (spectators.equals(Main.PlayingPlayers)) {
					StopGameCountdown.endGame();
					return;
				}else if (spectators.size() >= (Main.PlayingPlayers.size() - 1)) {
					StopGameCountdown.endGame();
					return;
				}
				
				p.teleport(getArena.getSpectatorSpawn(Arena));
				p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 15, 3));
				
				for (Player pp : Bukkit.getOnlinePlayers()) {
					if (!(pp.equals(p))) pp.hidePlayer(p);
				}
				
				p.setGameMode(GameMode.ADVENTURE);
				p.setAllowFlight(true);
				p.setFlying(true);
				Main.invincible.add(p);
				
				SendCoolMessages.TabHeaderAndFooter("§a§lSpectator", "§6§lCOD-Warfare", p);
				return;
			}else{
				ResetPlayer.reset(p);
				Main.PlayingPlayers.remove(p);
				
				p.sendMessage("");
				p.sendMessage("§e§m=======================");
				p.sendMessage("§b§lYou ran out of lives");
				p.sendMessage("§e§m=======================");
				p.sendMessage("");
				
				if (Main.PlayingPlayers.size() <= 1) {
					StopGameCountdown.endGame();
					return;
				}
				
				return;
			}
		}
		
		Main.invincible.add(p);
		
		Color c = Color.fromRGB(255, 0, 255);
		p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
		p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
		p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
		p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
		
		p.getInventory().setItem(0, KitInventory.getKit(p).getItem(0));
		if (!(Main.CrackShot)) {
			p.getInventory().setItem(1, KitInventory.getKit(p).getItem(3));
		}else{
			p.getInventory().setItem(1, Main.CrackShotAPI.generateWeapon(GetNormalName.get(KitInventory.getKit(p).getItem(3).getItemMeta().getDisplayName())));
		}
		
		ItemStack ammo = new ItemStack(KitInventory.getKit(p).getItem(4).getType(), 1);
		ItemMeta ammoMeta = ammo.getItemMeta();
		ammoMeta.setDisplayName(KitInventory.getKit(p).getItem(4).getItemMeta().getDisplayName());
		ammo.setItemMeta(ammoMeta);
		
		p.getInventory().setItem(2, ammo);
		
		p.sendMessage(Main.codSignature + "§b§lOne In The Chamber §7- §3Kill everyone!");
		
		if (GameVersion.above47(p)) {
			p.sendMessage(Main.codSignature + "§c§l" + Lives.get(p) + " §4§lLives");
		}
		
		SendCoolMessages.sendOverActionBar(p, "§c§l" + Lives.get(p) + " §4§lLives");
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
			public void run() {
				if (Main.invincible.contains(p)) Main.invincible.remove(p);
			}
		}, 100);
	}
	
	public static void onKill(Player killer) {
		ItemStack ammo = new ItemStack(KitInventory.getKit(killer).getItem(4).getType(), 1);
		ItemMeta ammoMeta = ammo.getItemMeta();
		ammoMeta.setDisplayName(KitInventory.getKit(killer).getItem(4).getItemMeta().getDisplayName());
		ammo.setItemMeta(ammoMeta);
		
		killer.getInventory().addItem(ammo);
		
		kills.put(killer, (kills.get(killer)) + 1);
		
		if (kills.get(killer) >= 15) {
			StopGameCountdown.endGame();
			return;
		}
	}
	
	public static boolean specContains(Player p) {
		if (spectators == null) return false;
		if (spectators.isEmpty()) return false;
		if (spectators.contains(p)) {
			return true;
		}else{
			return false;
		}
	}
	
	private static ItemStack getColorArmor(Material m, Color c) {
		ItemStack i = new ItemStack(m , 1);
		LeatherArmorMeta meta = (LeatherArmorMeta) i.getItemMeta();
		meta.setColor(c);
		i.setItemMeta(meta);
		return i;
	}
	
	public static void sendEndMessage() {
		Player bestPlayer = null;
		int score = 0;
		
		for (Player p : Main.PlayingPlayers) {
			if (kills.get(p) == null) continue;
			if ((kills.get(p) > score) && (!(specContains(p)))) {
				score = kills.get(p);
				if (p != null) bestPlayer = p;
			}else{
				continue;
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
			if (Lives.get(pp) != null) {
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
		}
		
		for (Player pp : Main.WaitingPlayers) {
			if(Lives.get(pp) == null) {
				if (name.equals("§7§lNONE")) name = "§7No one";
				pp.sendMessage(Main.codSignature + "§d" + name + " §5won!");
			}
		}
	}

	public static void endONEIN() {
		if (Main.ONEINspectate) {
			for (Player p : Main.PlayingPlayers) {
				if (specContains(p)) {
					p.setGameMode(GameMode.SURVIVAL);
					p.setFlying(false);
					p.setAllowFlight(false);
					
					for (Player pp : Bukkit.getOnlinePlayers()) {
						if (!(pp.equals(p))) pp.showPlayer(p);
					}
	
					spectators.remove(p);
				}
			}
		}
	}
}
