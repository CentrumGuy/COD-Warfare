package com.CentrumGuy.CodWarfare.Arena;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
import com.CentrumGuy.CodWarfare.Arena.BaseArena.ArenaType;
import com.CentrumGuy.CodWarfare.Inventories.KitInventory;
import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;
import com.CentrumGuy.CodWarfare.Utilities.GameCountdown;
import com.CentrumGuy.CodWarfare.Utilities.GetNormalName;
import com.CentrumGuy.CodWarfare.Utilities.Prefix;
import com.CentrumGuy.CodWarfare.Utilities.SendCoolMessages;
import com.CentrumGuy.CodWarfare.Utilities.ShowPlayer;

public class TDMArena {
	
	public static ArrayList<Player> RedTeam = new ArrayList<Player>();
	public static ArrayList<Player> BlueTeam = new ArrayList<Player>();
	private static HashMap<Player, String> Team = new HashMap<Player, String>();
	private static ArrayList<Player> pls = Main.PlayingPlayers;
	
	public static int RedTeamScore;
	public static int BlueTeamScore;
	
	public static void assignTeam(String Arena) {
		
		RedTeam.clear();
		BlueTeam.clear();
		Main.PlayingPlayers.clear();
		pls.clear();
		Team.clear();
		
		if (getArena.getType(Arena).equals("TDM")) {
			if (BaseArena.state == BaseArena.ArenaState.STARTED) {
				
				Main.PlayingPlayers.addAll(Main.WaitingPlayers);
				Main.WaitingPlayers.clear();
				
				BaseArena.type = ArenaType.TDM;
				
				for (int assign = 0 ; assign < pls.size() ; assign++) {
					
					Player p = pls.get(assign);
								
					if (RedTeam.size() < BlueTeam.size()) {
						RedTeam.add(p);
					}else if (BlueTeam.size() < RedTeam.size()) {
						BlueTeam.add(p);
					}else{
						
						Random RandomTeam = new Random();
						int TeamID = 0;
						
						TeamID = RandomTeam.nextInt(2);
						
						if (TeamID == 0) {
							RedTeam.add(p);
						}else{
							BlueTeam.add(p);
						}
					}
					

					
					if (RedTeam.contains(p)) {
						Team.put(p, "Red");
					}else{
						Team.put(p, "Blue");
					}
					
					continue;
					
				}
			}
		}
	}
	
	public static void startTDM(final String Arena) {
		if (getArena.getType(Arena).equals("TDM")) {
			if (BaseArena.state == BaseArena.ArenaState.STARTED) {
				
				RedTeamScore = 0;
				BlueTeamScore = 0;
				
				for (int ID = 0 ; ID < pls.size() ; ID++) {
					final Player p = pls.get(ID);
					
						if (RedTeam.contains(p)) {
							if (Main.extraCountdown) {
								p.sendMessage(Main.codSignature + "§cYou have joined the §4§lRED §cteam");
								Bukkit.getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
									public void run() {
										SendCoolMessages.clearTitleAndSubtitle(p);
										SendCoolMessages.resetTitleAndSubtitle(p);
										SendCoolMessages.sendTitle(p, "§6", 10, 30, 10);
								    	SendCoolMessages.sendSubTitle(p, "§c§lYOU JOINED THE §4§lRED TEAM", 10, 30, 10);
									}
								}, 40);
							}
							
							GameCountdown.startCountdown(p, getArena.getRedSpawn(Arena), "§c§lGO GO GO!!!", Main.codSignature + "§c§lYou joined the §4§lred §c§lteam!");
							
							p.closeInventory();
							p.teleport(getArena.getRedSpawn(Arena));
							p.getInventory().clear();
							p.setHealth(20);
							p.setFoodLevel(20);
							
							/*for (Player pp : BlueTeam) {
								Main.blue.addPlayer(pp);
							}
							
							for (Player pp : RedTeam) {
								Main.red.addPlayer(pp);
							}*/
							
							Prefix.setDispName(p, "§c" + p.getName());
							if (Main.dispName.get(p) != null) p.setPlayerListName(Main.dispName.get(p));
							SendCoolMessages.TabHeaderAndFooter("§4§lRed §c§lTeam", "§6§lCOD-Warfare\n" + getBetterTeam(), p);
							
							Color c = Color.fromRGB(255, 0, 0);
							p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
							p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
							p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
							p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
							
							Main.setGameBoard(p);
							
							if (Main.GameDeathsScore.get(p.getName()) != null) Main.GameDeathsScore.get(p.getName()).setScore(0);
							if (Main.GameKillsScore.get(p.getName()) != null) Main.GameKillsScore.get(p.getName()).setScore(0);
							if (Main.GameKillStreakScore.get(p.getName()) != null) Main.GameKillStreakScore.get(p.getName()).setScore(0);
							
						}else if (BlueTeam.contains(p)) {
							if (Main.extraCountdown) {
								p.sendMessage(Main.codSignature + "§9You have joined the §1§lBLUE §9team");
								Bukkit.getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
									public void run() {
										SendCoolMessages.clearTitleAndSubtitle(p);
										SendCoolMessages.resetTitleAndSubtitle(p);
										SendCoolMessages.sendTitle(p, "§6", 10, 50, 10);
								    	SendCoolMessages.sendSubTitle(p, "§9§lYOU JOINED THE §1§lBLUE TEAM", 10, 50, 10);
									}
								}, 40);
							}
							GameCountdown.startCountdown(p, getArena.getBlueSpawn(Arena), "§9§lGO GO GO!!!", Main.codSignature + "§9§lYou joined the §1§lblue §1§lteam!");
							
							p.closeInventory();
							p.teleport(getArena.getBlueSpawn(Arena));
							p.getInventory().clear();
							p.setHealth(20);
							p.setFoodLevel(20);
							
							/*for (Player pp : BlueTeam) {
								Main.blue.addPlayer(pp);
							}
							
							for (Player pp : RedTeam) {
								Main.red.addPlayer(pp);
							}*/
							
							Prefix.setDispName(p, "§9" + p.getName());
							if (Main.dispName.get(p) != null) p.setPlayerListName(Main.dispName.get(p));
							SendCoolMessages.TabHeaderAndFooter("§1§lBlue §9§lTeam", "§6§lCOD-Warfare\n" + getBetterTeam(), p);
							
							Color c = Color.fromRGB(0, 0, 255);
							p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
							p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
							p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
							p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
							
							Main.setGameBoard(p);
							
							if (Main.GameDeathsScore.get(p.getName()) != null) Main.GameDeathsScore.get(p.getName()).setScore(0);
							if (Main.GameKillsScore.get(p.getName()) != null) Main.GameKillsScore.get(p.getName()).setScore(0);
							if (Main.GameKillStreakScore.get(p.getName()) != null) Main.GameKillStreakScore.get(p.getName()).setScore(0);
						}
				}
		
		if (Main.extraCountdown) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
				@Override
				public void run() {
							if (BaseArena.type != ArenaType.TDM) {
									BaseArena.type = ArenaType.TDM;
							}
					
							RedTeamScore = 0;
							BlueTeamScore = 0;
					
							for (int ID = 0 ; ID < pls.size() ; ID++) {
								Player p = pls.get(ID);
								if (!(Main.PlayingPlayers.contains(p))) continue;
						
								if (RedTeam.contains(p)) {
							
									p.closeInventory();
									p.teleport(getArena.getRedSpawn(Arena));
									p.getInventory().clear();
									p.setHealth(20);
									p.setFoodLevel(20);
									p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 5));
									
	
									/*for (Player pp : BlueTeam) {
										Main.blue.addPlayer(pp);
									}
									
									for (Player pp : RedTeam) {
										Main.red.addPlayer(pp);
									}*/
							
									p.sendMessage("");
									p.sendMessage("§e§m=====================================================");
									p.sendMessage("§b§lYou are playing a game of §4§lTeam Death Match!");
									p.sendMessage(" §7§l- §6§lKill the §9§lBlue Team!");
									p.sendMessage(" §7§l- §6§lArena:§e§l " + Arena);
									p.sendMessage(" §7§l- §6§lGood luck!");
									p.sendMessage("§e§m=====================================================");
									p.sendMessage("");
									
									ShowPlayer.showPlayer(p);
									
									p.getInventory().clear();
									
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
									
							
									Color c = Color.fromRGB(255, 0, 0);
									p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
									p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
									p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
									p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
							
								}else if (BlueTeam.contains(p)) {
							
									p.closeInventory();
									p.teleport(getArena.getBlueSpawn(Arena));
									p.getInventory().clear();
									p.setHealth(20);
									p.setFoodLevel(20);
									p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 5));
									
									/*for (Player pp : BlueTeam) {
										Main.blue.addPlayer(pp);
									}
									
									for (Player pp : RedTeam) {
										Main.red.addPlayer(pp);
									}*/
							
									p.sendMessage("");
									p.sendMessage("§e§m=====================================================");
									p.sendMessage("§b§lYou are playing a game of §4§lTeam Death Match!");
									p.sendMessage(" §7§l- §6§lKill the §c§lRed Team!");
									p.sendMessage(" §7§l- §6§lArena:§e§l " + Arena);
									p.sendMessage(" §7§l- §6§lGood luck!");
									p.sendMessage("§e§m=====================================================");
									p.sendMessage("");
									
									ShowPlayer.showPlayer(p);
									
									p.getInventory().clear();
									
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
							
									Color c = Color.fromRGB(0, 0, 255);
									p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
									p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
									p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
									p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
							
							
								}else{
									return;
								}
							}
						}
					}, 225);
				}else{
					if (BaseArena.type != ArenaType.TDM) {
						BaseArena.type = ArenaType.TDM;
				}
		
				RedTeamScore = 0;
				BlueTeamScore = 0;
		
				for (int ID = 0 ; ID < pls.size() ; ID++) {
					Player p = pls.get(ID);
			
					if (RedTeam.contains(p)) {
				
						p.closeInventory();
						p.teleport(getArena.getRedSpawn(Arena));
						p.getInventory().clear();
						p.setHealth(20);
						p.setFoodLevel(20);
						p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 5));
				
						p.sendMessage("");
						p.sendMessage("§e§m=====================================================");
						p.sendMessage("§b§lYou are playing a game of §4§lTeam Death Match!");
						p.sendMessage(" §7§l- §6§lKill the §9§lBlue Team!");
						p.sendMessage(" §7§l- §6§lArena:§e§l " + Arena);
						p.sendMessage(" §7§l- §6§lGood luck!");
						p.sendMessage("§e§m=====================================================");
						p.sendMessage("");
						
						ShowPlayer.showPlayer(p);
						
						p.getInventory().clear();
						
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
						
				
						Color c = Color.fromRGB(255, 0, 0);
						p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
						p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
						p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
						p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
				
					}else if (BlueTeam.contains(p)) {
				
						p.closeInventory();
						p.teleport(getArena.getBlueSpawn(Arena));
						p.getInventory().clear();
						p.setHealth(20);
						p.setFoodLevel(20);
						p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 5));
				
						p.sendMessage("");
						p.sendMessage("§e§m=====================================================");
						p.sendMessage("§b§lYou are playing a game of §4§lTeam Death Match!");
						p.sendMessage(" §7§l- §6§lKill the §c§lRed Team!");
						p.sendMessage(" §7§l- §6§lArena:§e§l " + Arena);
						p.sendMessage(" §7§l- §6§lGood luck!");
						p.sendMessage("§e§m=====================================================");
						p.sendMessage("");
						
						ShowPlayer.showPlayer(p);
						
						p.getInventory().clear();
						
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
				
						Color c = Color.fromRGB(0, 0, 255);
						p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
						p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
						p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
						p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
				
				
					}else{
						return;
					}
				}
			}
		}
	}
}
	
	private static ItemStack getColorArmor(Material m, Color c) {
		ItemStack i = new ItemStack(m , 1);
		LeatherArmorMeta meta = (LeatherArmorMeta) i.getItemMeta();
		meta.setColor(c);
		i.setItemMeta(meta);
		return i;
	}
	
	public static Location getSpawn(Player p) {
		if (RedTeam.contains(p)) {
			return getArena.getRedSpawn(PickRandomArena.CurrentArena);
		}else if (BlueTeam.contains(p)) {
			return getArena.getBlueSpawn(PickRandomArena.CurrentArena);
		}else{
			return null;
		}
	}
	
    private static String getBetterTeam() {
		if (RedTeamScore > BlueTeamScore) {
			String team = "§c§lRed: §4§l" + RedTeamScore + " " + "§r§9Blue: §1" + BlueTeamScore;
			return team;
		}else if (BlueTeamScore > RedTeamScore) {
			String team = "§9§lBlue: §1§l" + BlueTeamScore + " " + "§r§cRed: §4" + RedTeamScore;
			return team;
		}else{
			String team = "§e§lTie §6§l" + RedTeamScore + " §e§l- §6§l" + BlueTeamScore;
			return team;
		}
    }
	
	public static void changeScore(Player p) {
		if (RedTeam.contains(p)) {
			BlueTeamScore = BlueTeamScore + 1;
		}else if (BlueTeam.contains(p)) {
			RedTeamScore = RedTeamScore + 1;
		}
		
		for (Player pp : Main.PlayingPlayers) {
			if (RedTeam.contains(pp)) {
				SendCoolMessages.TabHeaderAndFooter("§4§lRed §c§lTeam", "§6§lCOD-Warfare\n" + getBetterTeam(), pp);
			}else if (BlueTeam.contains(pp)) {
				SendCoolMessages.TabHeaderAndFooter("§1§lBlue §9§lTeam", "§6§lCOD-Warfare\n" + getBetterTeam(), pp);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void SpawnPlayer(Player p) {
		if (BaseArena.state == BaseArena.ArenaState.ENDING) {
			p.getInventory().clear();
			p.updateInventory();
			Main.invincible.add(p);
			
			if (Team.get(p).equalsIgnoreCase("Red")) {
				Color c = Color.fromRGB(255, 0, 0);
				p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
				p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
				p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
				p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
			}else if (Team.get(p).equalsIgnoreCase("Blue")) {
				Color c = Color.fromRGB(0, 0, 255);
				p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
				p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
				p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
				p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
			}
			
			return;
		}
		
		if (Team.get(p).equalsIgnoreCase("Red")) {
			
			p.teleport(getArena.getRedSpawn(PickRandomArena.CurrentArena));
			
			p.closeInventory();
			p.getInventory().clear();
			p.setHealth(20);
			p.setFoodLevel(20);
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 5));

			Color c = Color.fromRGB(255, 0, 0);
			p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
			p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
			p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
			p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
			
			Main.GameKillStreakScore.get(p.getName()).setScore(0);
			
			p.getInventory().clear();
			
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
			
		}else if (Team.get(p).equalsIgnoreCase("Blue")) {
		
			p.teleport(getArena.getBlueSpawn(PickRandomArena.CurrentArena));
			
			p.closeInventory();
			p.getInventory().clear();
			p.setHealth(20);
			p.setFoodLevel(20);
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 5));
			
			Color c = Color.fromRGB(0, 0, 255);
			p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
			p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
			p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
			p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
			
			Main.GameKillStreakScore.get(p.getName()).setScore(0);
			
			p.getInventory().clear();
			
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
			
		}else{
			return;
		}
	}
	
	public static void endTDM() {
		if (BlueTeamScore > RedTeamScore) {
            for (Player pp : Main.PlayingPlayers) {
                pp.sendMessage("");
                pp.sendMessage("");
                pp.sendMessage("");
                pp.sendMessage("§7║ §b§lStatistics:§6§l " + PickRandomArena.CurrentArena);
                pp.sendMessage("§7║");
                pp.sendMessage("§7║ §7§lWinner: §9§lBlue: §1§l" + BlueTeamScore + " " + "§r§cRed: §4" + RedTeamScore + "         §b§lTotal Kills:§a§l " + Main.GameKillsScore.get(pp.getName()).getScore());

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
                pp.sendMessage(Main.codSignature + "§bBlue §9team won!");
            }
        }else if (RedTeamScore > BlueTeamScore) {
            for (Player pp : Main.PlayingPlayers) {
                pp.sendMessage("");
                pp.sendMessage("");
                pp.sendMessage("");
                pp.sendMessage("§7║ §b§lStatistics:§6§l " + PickRandomArena.CurrentArena);
                pp.sendMessage("§7║");
                pp.sendMessage("§7║ §7§lWinner: §c§lRed: §4§l" + RedTeamScore + " " + "§r§9Blue: §1" + BlueTeamScore + "         §b§lTotal Kills:§a§l " + Main.GameKillsScore.get(pp.getName()).getScore());

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
                pp.sendMessage(Main.codSignature + "§4Red §cteam won!");
            }
        }else{
            for (Player pp : Main.PlayingPlayers) {
                pp.sendMessage("");
                pp.sendMessage("");
                pp.sendMessage("");
                pp.sendMessage("§7║ §b§lStatistics:§6§l " + PickRandomArena.CurrentArena);
                pp.sendMessage("§7║");
                pp.sendMessage("§7║ §7§lWinner: §e§lTie! §6§l" + RedTeamScore + " §e§l- §6§l" + BlueTeamScore + "         §b§lTotal Kills:§a§l " + Main.GameKillsScore.get(pp.getName()).getScore());

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
                pp.sendMessage(Main.codSignature + "§eTie! §6Nobody won");
            }
        }
	}
}
