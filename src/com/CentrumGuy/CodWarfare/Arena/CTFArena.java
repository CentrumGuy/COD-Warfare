package com.CentrumGuy.CodWarfare.Arena;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Score;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Arena.BaseArena.ArenaType;
import com.CentrumGuy.CodWarfare.Inventories.KitInventory;
import com.CentrumGuy.CodWarfare.Leveling.Exp;
import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;
import com.CentrumGuy.CodWarfare.Utilities.GameCountdown;
import com.CentrumGuy.CodWarfare.Utilities.GetNormalName;
import com.CentrumGuy.CodWarfare.Utilities.Prefix;
import com.CentrumGuy.CodWarfare.Utilities.SendCoolMessages;
import com.CentrumGuy.CodWarfare.Utilities.ShowPlayer;
import com.CentrumGuy.CodWarfare.Utilities.SpawnInstantFireworks;

public class CTFArena {
	
	public static ArrayList<Player> RedTeam = new ArrayList<Player>();
	public static ArrayList<Player> BlueTeam = new ArrayList<Player>();
	private static HashMap<Player, String> Team = new HashMap<Player, String>();
	private static ArrayList<Player> pls = Main.PlayingPlayers;
	
	public static int RedTeamScore;
	public static int BlueTeamScore;
	
	public static boolean redFlagPickedUp;
	public static boolean blueFlagPickedUp;
	
	public static HashMap<Player, Boolean> holdingRedFlag = new HashMap<Player, Boolean>();
	public static HashMap<Player, Boolean> holdingBlueFlag = new HashMap<Player, Boolean>();
	
	private static BukkitRunnable redFlagTask = null;
	private static BukkitRunnable blueFlagTask = null;
	
	public static void assignTeam(String Arena) {
		
		RedTeam.clear();
		BlueTeam.clear();
		Main.PlayingPlayers.clear();
		pls.clear();
		Team.clear();
		RedTeamScore = 0;
		BlueTeamScore = 0;
		
		if (getArena.getType(Arena).equals("CTF")) {
			if (BaseArena.state == BaseArena.ArenaState.STARTED) {
				
				Main.PlayingPlayers.addAll(Main.WaitingPlayers);
				Main.WaitingPlayers.clear();
				
				BaseArena.type = ArenaType.CTF;
				
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
	
	private static String getBetterTeam() {
		if (RedTeamScore > BlueTeamScore) {
			String team = "§cRed§4(§c" + (RedTeamScore - BlueTeamScore) + "§4)";
			return team;
		}else if (BlueTeamScore > RedTeamScore) {
			String team = "§9Blue§1(§9" + (BlueTeamScore - RedTeamScore) + "§1)";
			return team;
		}else{
			String team = "§eTie(0)";
			return team;
		}
	}
	
	public static void startCTF(final String Arena) {
		for (Player p : pls) {
			p.closeInventory();
			p.getInventory().clear();
			p.setHealth(20);
			p.setFoodLevel(20);
			
			Main.setGameBoard(p);
			if (Main.GameDeathsScore.get(p.getName()) != null) Main.GameDeathsScore.get(p.getName()).setScore(0);
			if (Main.GameKillsScore.get(p.getName()) != null) Main.GameKillsScore.get(p.getName()).setScore(0);
			if (Main.GameKillStreakScore.get(p.getName()) != null) Main.GameKillStreakScore.get(p.getName()).setScore(0);
			
			if (RedTeam.contains(p)) {
				SendCoolMessages.TabHeaderAndFooter("§4§lRed §c§lTeam", "§6§lCOD-Warfare\n" + getBetterTeam(), p);
				
				Color c = Color.fromRGB(255, 0, 0);
				p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
				p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
				p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
				p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
				
				Prefix.setDispName(p, "§c" + p.getName());
				if (Main.dispName.get(p) != null) p.setPlayerListName(Main.dispName.get(p));
				
				GameCountdown.startCountdown(p, getArena.getRedSpawn(Arena), "§c§lGO GO GO!!!", Main.codSignature + "§c§lYou joined the §4§lred§c§l team!");
			}else if (BlueTeam.contains(p)) {
				SendCoolMessages.TabHeaderAndFooter("§1§lBlue §9§lTeam", "§6§lCOD-Warfare\n" + getBetterTeam(), p);
				
				Color c = Color.fromRGB(0, 0, 255);
				p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
				p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
				p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
				p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
				
				Prefix.setDispName(p, "§9" + p.getName());
				if (Main.dispName.get(p) != null) p.setPlayerListName(Main.dispName.get(p));
				
				GameCountdown.startCountdown(p, getArena.getBlueSpawn(Arena), "§9§lGO GO GO!!!", Main.codSignature + "§9§lYou joined the §1§lblue§9§l team!");
			}
		}
			
			if (Main.extraCountdown) {
				BukkitRunnable br = new BukkitRunnable() {
					public void run() {						  	
						for (Player p : pls) {
							startGame(p, Arena);
						}
					}
				};
				
				br.runTaskLater(ThisPlugin.getPlugin(), 255L);
				
				BukkitRunnable brr = new BukkitRunnable() {
					public void run() {
						ItemStack redFlag = new ItemStack(Material.WOOL, 1, (byte) 14);
						
						final Item rFlag = getArena.getFlagLocation("red", Arena).getWorld().dropItem(getArena.getFlagLocation("red", Arena), redFlag);
						rFlag.setMetadata("codredflag", new FixedMetadataValue(ThisPlugin.getPlugin(), rFlag));
						rFlag.setVelocity(rFlag.getVelocity().multiply(0));
						
						Type type = Type.BALL;
				  	  	
				  	  	Color color1 = Color.RED;
				  	  	Color color2 = Color.PURPLE;
				  	  	
				  	  	FireworkEffect red = FireworkEffect.builder().flicker(true).withColor(color1).withFade(color2).with(type).build();
				  	  	
				  	  	try {
							SpawnInstantFireworks.playFirework(getArena.getFlagLocation("red", Arena).getWorld(), getArena.getFlagLocation("red", Arena), red);
						} catch (Exception e) {
							e.printStackTrace();
						}
						  	
						  	ItemStack blueFlag = new ItemStack(Material.WOOL, 1, (byte) 11);
						  	
						  	Item  bFlag = getArena.getFlagLocation("blue", Arena).getWorld().dropItem(getArena.getFlagLocation("blue", Arena), blueFlag);
						  	bFlag.setMetadata("codblueflag", new FixedMetadataValue(ThisPlugin.getPlugin(), bFlag));
							bFlag.setVelocity(bFlag.getVelocity().multiply(0));
						  	
						  	color1 = Color.BLUE;
						  	color2 = Color.AQUA;
						  	
						  	FireworkEffect blue = FireworkEffect.builder().flicker(true).withColor(color1).withFade(color2).with(type).build();
						  	
						try {
							SpawnInstantFireworks.playFirework(getArena.getFlagLocation("blue", Arena).getWorld(), getArena.getFlagLocation("blue", Arena), blue);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						for (Player p : pls) {
							p.sendMessage(Main.codSignature + "§bThe flags have spawned!");
						}
					}
				};
				
			brr.runTaskLater(ThisPlugin.getPlugin(), 355);
			}else{	
				for (Player pp : pls) {
					startGame(pp, Arena);
				}
				
				BukkitRunnable br = new BukkitRunnable() {
					public void run() {
						ItemStack redFlag = new ItemStack(Material.WOOL, 1, (byte) 14);
						
						final Item rFlag = getArena.getFlagLocation("red", Arena).getWorld().dropItem(getArena.getFlagLocation("red", Arena), redFlag);
						rFlag.setMetadata("codredflag", new FixedMetadataValue(ThisPlugin.getPlugin(), rFlag));
						rFlag.setVelocity(rFlag.getVelocity().multiply(0));
						
						Type type = Type.BALL;
				  	  	
				  	  	Color color1 = Color.RED;
				  	  	Color color2 = Color.PURPLE;
				  	  	
				  	  	FireworkEffect red = FireworkEffect.builder().flicker(true).withColor(color1).withFade(color2).with(type).build();
				  	  	
				  	  	try {
							SpawnInstantFireworks.playFirework(getArena.getFlagLocation("red", Arena).getWorld(), getArena.getFlagLocation("red", Arena), red);
						} catch (Exception e) {
							e.printStackTrace();
						}
						  	
						  	ItemStack blueFlag = new ItemStack(Material.WOOL, 1, (byte) 11);
						  	
						  	Item  bFlag = getArena.getFlagLocation("blue", Arena).getWorld().dropItem(getArena.getFlagLocation("blue", Arena), blueFlag);
						  	bFlag.setMetadata("codblueflag", new FixedMetadataValue(ThisPlugin.getPlugin(), bFlag));
							bFlag.setVelocity(bFlag.getVelocity().multiply(0));
						  	
						  	color1 = Color.BLUE;
						  	color2 = Color.AQUA;
						  	
						  	FireworkEffect blue = FireworkEffect.builder().flicker(true).withColor(color1).withFade(color2).with(type).build();
						  	
						try {
							SpawnInstantFireworks.playFirework(getArena.getFlagLocation("blue", Arena).getWorld(), getArena.getFlagLocation("blue", Arena), blue);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						for (Player p : pls) {
							p.sendMessage(Main.codSignature + "§bThe flags have spawned!");
						}
					}
				};
			br.runTaskLater(ThisPlugin.getPlugin(), 100);
		}
	}
	
	@SuppressWarnings("deprecation")
	private static void startGame(Player p, String Arena) {
		if (BaseArena.type != ArenaType.CTF) {
			BaseArena.type = ArenaType.CTF;
		}
				if (!(Main.PlayingPlayers.contains(p))) return;
		
				if (RedTeam.contains(p)) {
			
					p.closeInventory();
					p.teleport(getArena.getRedSpawn(Arena));
					p.getInventory().clear();
					p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 5));
			
					p.sendMessage("");
					p.sendMessage("§e§m=====================================================");
					p.sendMessage("§b§lYou are playing a game of §4§lCapture The Flag!");
					p.sendMessage(" §7§l- §6§lCapture the §9§lBlue Team's §6§lflag");
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
					
					p.updateInventory();
				}else if (BlueTeam.contains(p)) {
					p.closeInventory();
					p.teleport(getArena.getBlueSpawn(Arena));
					p.getInventory().clear();
					p.setHealth(20);
					p.setFoodLevel(20);
					p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 5));
			
					p.sendMessage("");
					p.sendMessage("§e§m=====================================================");
					p.sendMessage("§b§lYou are playing a game of §4§lCapture The Flag!");
					p.sendMessage(" §7§l- §6§lCapture the §c§lRed Team's §6§lflag");
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
			
					p.updateInventory();
				}else{
					return;
				}
			}
	
/*	public static void startCTF(final String Arena) {
		if (getArena.getType(Arena).equals("CTF")) {
			if (BaseArena.state == BaseArena.ArenaState.STARTED) {
				
				RedTeamScore = 0;
				BlueTeamScore = 0;
				
				for (int ID = 0 ; ID < pls.size() ; ID++) {
					final Player p = pls.get(ID);
					
						if (RedTeam.contains(p)) {
							if (Main.extraCountdown) {
								Bukkit.getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
									public void run() {
										SendCoolMessages.clearTitleAndSubtitle(p);
										SendCoolMessages.resetTitleAndSubtitle(p);
										SendCoolMessages.sendTitle(p, "§6", 10, 30, 10);
								    	SendCoolMessages.sendSubTitle(p, "§c§lYOU JOINED THE §4§lRED TEAM", 10, 30, 10);
									}
								}, 225);
							}
							GameCountdown.startCountdown(p, getArena.getRedSpawn(Arena), "§c§lGO GO GO!!!", Main.codSignature + "§c§lYou joined the §4§lred§c§l team!");
							
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
							}*//*
							
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
								Bukkit.getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
									public void run() {
										SendCoolMessages.clearTitleAndSubtitle(p);
										SendCoolMessages.resetTitleAndSubtitle(p);
										SendCoolMessages.sendTitle(p, "§6", 10, 30, 10);
								    	SendCoolMessages.sendSubTitle(p, "§9§lYOU JOINED THE §1§lBLUE TEAM", 10, 50, 10);
									}
								}, 225);
							}
							GameCountdown.startCountdown(p, getArena.getBlueSpawn(Arena), "§9§lGO GO GO!!!", Main.codSignature + "§9§lYou joined the §1§lblue§9§l team!");
							
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
							}*//*
							
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
							if (BaseArena.type != ArenaType.CTF) {
									BaseArena.type = ArenaType.CTF;
							}
					
							RedTeamScore = 0;
							BlueTeamScore = 0;
							
							ItemStack redFlag = new ItemStack(Material.WOOL, 1, (byte) 14);
							
							Item rFlag = getArena.getFlagLocation("red", Arena).getWorld().dropItem(getArena.getFlagLocation("red", Arena), redFlag);
							rFlag.setMetadata("codredflag", new FixedMetadataValue(ThisPlugin.getPlugin(), rFlag));
					  	  	rFlag.setVelocity(rFlag.getVelocity().multiply(0));
							
					  	  	Type type = Type.BALL;
					  	  	
					  	  	Color color1 = Color.RED;
					  	  	Color color2 = Color.PURPLE;
					  	  	
					  	  	FireworkEffect red = FireworkEffect.builder().flicker(true).withColor(color1).withFade(color2).with(type).build();
					  	  	
					  	  	try {
								SpawnInstantFireworks.playFirework(getArena.getFlagLocation("red", Arena).getWorld(), getArena.getFlagLocation("red", Arena), red);
							} catch (Exception e) {
								e.printStackTrace();
							}
					  	  	
					  	  	ItemStack blueFlag = new ItemStack(Material.WOOL, 1, (byte) 11);
					  	  	
					  	  	Item  bFlag = getArena.getFlagLocation("blue", Arena).getWorld().dropItem(getArena.getFlagLocation("blue", Arena), blueFlag);
					  	  	bFlag.setMetadata("codblueflag", new FixedMetadataValue(ThisPlugin.getPlugin(), bFlag));
					  	  	bFlag.setVelocity(bFlag.getVelocity().multiply(0));
					  	  	
					  	  	color1 = Color.BLUE;
					  	  	color2 = Color.AQUA;
					  	  	
					  	  	FireworkEffect blue = FireworkEffect.builder().flicker(true).withColor(color1).withFade(color2).with(type).build();
					  	  	
					  	  	try {
								SpawnInstantFireworks.playFirework(getArena.getFlagLocation("blue", Arena).getWorld(), getArena.getFlagLocation("blue", Arena), blue);
							} catch (Exception e) {
								e.printStackTrace();
							}
					  	  	
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
							
									p.sendMessage("");
									p.sendMessage("§e§m=====================================================");
									p.sendMessage("§b§lYou are playing a game of §4§lCapture The Flag!");
									p.sendMessage(" §7§l- §6§lCapture the §9§lBlue Team's §6§lflag");
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
									p.sendMessage("§b§lYou are playing a game of §4§lCapture The Flag!");
									p.sendMessage(" §7§l- §6§lCapture the §c§lRed Team's §6§lflag");
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
					if (BaseArena.type != ArenaType.CTF) {
						BaseArena.type = ArenaType.CTF;
					}
		
				RedTeamScore = 0;
				BlueTeamScore = 0;
				
				ItemStack redFlag = new ItemStack(Material.WOOL, 1, (byte) 14);
				
				Item rFlag = getArena.getFlagLocation("red", Arena).getWorld().dropItem(getArena.getFlagLocation("red", Arena), redFlag);
				rFlag.setMetadata("codredflag", new FixedMetadataValue(ThisPlugin.getPlugin(), rFlag));
		  	  	rFlag.setVelocity(rFlag.getVelocity().multiply(0));
				
		  	  	Type type = Type.BALL;
		  	  	
		  	  	Color color1 = Color.RED;
		  	  	Color color2 = Color.PURPLE;
		  	  	
		  	  	FireworkEffect red = FireworkEffect.builder().flicker(true).withColor(color1).withFade(color2).with(type).build();
		  	  	
		  	  	try {
					SpawnInstantFireworks.playFirework(getArena.getFlagLocation("red", Arena).getWorld(), getArena.getFlagLocation("red", Arena), red);
				} catch (Exception e) {
					e.printStackTrace();
				}
		  	  	
		  	  	ItemStack blueFlag = new ItemStack(Material.WOOL, 1, (byte) 11);
		  	  	
		  	  	Item  bFlag = getArena.getFlagLocation("blue", Arena).getWorld().dropItem(getArena.getFlagLocation("blue", Arena), blueFlag);
		  	  	bFlag.setMetadata("codblueflag", new FixedMetadataValue(ThisPlugin.getPlugin(), bFlag));
		  	  	bFlag.setVelocity(bFlag.getVelocity().multiply(0));
		  	  	
		  	  	color1 = Color.BLUE;
		  	  	color2 = Color.AQUA;
		  	  	
		  	  	FireworkEffect blue = FireworkEffect.builder().flicker(true).withColor(color1).withFade(color2).with(type).build();
		  	  	
		  	  	try {
					SpawnInstantFireworks.playFirework(getArena.getFlagLocation("blue", Arena).getWorld(), getArena.getFlagLocation("blue", Arena), blue);
				} catch (Exception e) {
					e.printStackTrace();
				}
		  	  	
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
						p.sendMessage("§b§lYou are playing a game of §4§lCapture The Flag!");
						p.sendMessage(" §7§l- §6§lCapture the §9§lBlue Team's §6§lflag");
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
						p.sendMessage("§b§lYou are playing a game of §4§lCapture The Flag!");
						p.sendMessage(" §7§l- §6§lCapture the §c§lRed Team's §6§lflag");
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
	}*/
	
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
	
	public static void removeFlags(String Arena) {
		returnFlag("red", PickRandomArena.CurrentArena);
		returnFlag("blue", PickRandomArena.CurrentArena);
		for (Entity e : getArena.getFlagLocation("red", Arena).getWorld().getEntities()) {
			if (!(e instanceof Item)) continue;
			if ((!(e.hasMetadata("codredflag"))) && (!(e.hasMetadata("codblueflag")))) continue;
			
			e.remove();
		}
		
		for (Entity e : getArena.getFlagLocation("blue", Arena).getWorld().getEntities()) {
			if (!(e instanceof Item)) continue;
			if ((!(e.hasMetadata("codredflag"))) && (!(e.hasMetadata("codblueflag")))) continue;
			
			e.remove();
		}
	}
	
	public static void endCTF(String Arena) {
		returnFlag("red", PickRandomArena.CurrentArena);
		returnFlag("blue", PickRandomArena.CurrentArena);
		for (Entity e : getArena.getFlagLocation("red", Arena).getWorld().getEntities()) {
			if (!(e instanceof Item)) continue;
			if ((!(e.hasMetadata("codredflag"))) && (!(e.hasMetadata("codblueflag")))) continue;
			
			e.remove();
		}
		
		for (Entity e : getArena.getFlagLocation("blue", Arena).getWorld().getEntities()) {
			if (!(e instanceof Item)) continue;
			if ((!(e.hasMetadata("codredflag"))) && (!(e.hasMetadata("codblueflag")))) continue;
			
			e.remove();
		}
		
		if (BlueTeamScore > RedTeamScore) {
			for (Player pp : Main.PlayingPlayers) {
				pp.sendMessage("");
				pp.sendMessage("");
				pp.sendMessage("");
				pp.sendMessage("§7║ §b§lStatistics:§6§l " + PickRandomArena.CurrentArena);
				pp.sendMessage("§7║");
				pp.sendMessage("§7║ §7§lWinner: §9§lBlue Team" + "             §b§lTotal Kills:§a§l " + Main.GameKillsScore.get(pp.getName()).getScore());
				
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
				pp.sendMessage("§7║ §7§lWinner: §c§lRed Team" + "             §b§lTotal Kills:§a§l " + Main.GameKillsScore.get(pp.getName()).getScore());
				
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
				pp.sendMessage("§7║ §7§lWinner: §6§lTIE" + "             §b§lTotal Kills:§a§l " + Main.GameKillsScore.get(pp.getName()).getScore());
				
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
				pp.sendMessage(Main.codSignature + "§eTie! §6nobody won");
			}
		}
	}
	
	public static void returnFlag(String team, String Arena) {
		if (team.equalsIgnoreCase("red")) {
			for (Entity e : getArena.getFlagLocation("red", Arena).getWorld().getEntities()) {
				if (!(e instanceof Item)) continue;
				
				if (redFlagTask != null) {
					redFlagTask.cancel();
					redFlagTask = null;
				}
				
				if (!(e.hasMetadata("codredflag"))) continue;
				
				e.remove();
			}
			
			redFlagPickedUp = false;
			
			ItemStack redFlag = new ItemStack(Material.WOOL, 1, (byte) 14);
			
			Item rFlag = getArena.getFlagLocation("red", Arena).getWorld().dropItem(getArena.getFlagLocation("red", Arena), redFlag);
			rFlag.setMetadata("codredflag", new FixedMetadataValue(ThisPlugin.getPlugin(), rFlag));
	  	  	rFlag.setVelocity(rFlag.getVelocity().multiply(0));
			
	  	  	Type type = Type.BALL;
	  	  	
	  	  	Color color1 = Color.RED;
	  	  	Color color2 = Color.PURPLE;
	  	  	
	  	  	FireworkEffect red = FireworkEffect.builder().flicker(true).withColor(color1).withFade(color2).with(type).build();
	  	  	
	  	  	try {
				SpawnInstantFireworks.playFirework(getArena.getFlagLocation("red", Arena).getWorld(), getArena.getFlagLocation("red", Arena), red);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (team.equalsIgnoreCase("blue")) {
			for (Entity e : getArena.getFlagLocation("blue", Arena).getWorld().getEntities()) {
				if (!(e instanceof Item)) continue;
				
				if (blueFlagTask != null) {
					blueFlagTask.cancel();
					blueFlagTask = null;
				}
				
				if (!(e.hasMetadata("codblueflag"))) continue;
				
				e.remove();
			}
			
			blueFlagPickedUp = false;
			
	  	  	ItemStack blueFlag = new ItemStack(Material.WOOL, 1, (byte) 11);
	  	  	
	  	  	Item  bFlag = getArena.getFlagLocation("blue", Arena).getWorld().dropItem(getArena.getFlagLocation("blue", Arena), blueFlag);
	  	  	bFlag.setMetadata("codblueflag", new FixedMetadataValue(ThisPlugin.getPlugin(), bFlag));
	  	  	bFlag.setVelocity(bFlag.getVelocity().multiply(0));
	  	  	
	  	  	Type type = Type.BALL;
	  	  	
	  	  	Color color1 = Color.BLUE;
	  	  	Color color2 = Color.AQUA;
	  	  	
	  	  	FireworkEffect blue = FireworkEffect.builder().flicker(true).withColor(color1).withFade(color2).with(type).build();
	  	  	
	  	  	try {
				SpawnInstantFireworks.playFirework(getArena.getFlagLocation("blue", Arena).getWorld(), getArena.getFlagLocation("blue", Arena), blue);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void captureFlag(PlayerPickupItemEvent e) {
		if (!(Main.PlayingPlayers.contains(e.getPlayer()))) return;
		if ((!(e.getItem().hasMetadata("codredflag"))) && (!(e.getItem().hasMetadata("codblueflag")))) return;
		if ((!(RedTeam.contains(e.getPlayer()))) && (!(BlueTeam.contains(e.getPlayer())))) return;
		
		final Player p = e.getPlayer();
		
		if (e.getItem().hasMetadata("codredflag")) {
			if (RedTeam.contains(p)) {
				e.setCancelled(true);		
				return;
			}else if (BlueTeam.contains(p)) {
				e.setCancelled(true);
				
				if (redFlagPickedUp) return;
				if (redFlagTask != null) return;
				
				redFlagPickedUp = true;
				holdingRedFlag.put(p, true);
				
				final Item flag = e.getItem();
				
				for (Player pp : Main.PlayingPlayers) {
					pp.sendMessage(Main.codSignature + "§9" + p.getName() + " §6picked up the §cred §6flag");
				}
				
				BukkitRunnable br = new BukkitRunnable() {
					public void run() {
						if (redFlagPickedUp) {
							World world = p.getEyeLocation().getWorld();
							double x = (p.getEyeLocation().getX()) + (0.5);
							double y = (p.getEyeLocation().getY()) + (0.5);
							double z = (p.getEyeLocation().getZ()) + (0.5);
							
							for (World w : Bukkit.getServer().getWorlds()) {
								for (Entity e : w.getEntities()) {
									if (e.hasMetadata("codredflag")) e.remove();
								}
							}
							
							Item i = world.dropItem(new Location(world, x , y , z), flag.getItemStack());
							i.setMetadata("codredflag", new FixedMetadataValue(ThisPlugin.getPlugin(), i));
							
							if ((p.getEyeLocation() != null) && (getArena.getFlagLocation("blue", PickRandomArena.CurrentArena) != null)) {
								if (p.getEyeLocation().distance(getArena.getFlagLocation("blue", PickRandomArena.CurrentArena)) <= 2) {
									returnFlag("red", PickRandomArena.CurrentArena);
									
									for (Player pp : Main.PlayingPlayers) {
										pp.sendMessage(Main.codSignature + "§9" + p.getName() + " §bcaptured the §cred §bflag!");
									}
									
									if (Main.PlayingPlayers.size() > 1) Exp.addExp(p, 100);
									
									Score GameCredits = Main.GameCreditsScore.get(p.getName());
									if (Main.PlayingPlayers.size() > 1) GameCredits.setScore(GameCredits.getScore() + 15);
									
									Score LobbyCredits = Main.LobbyCreditsScore.get(p.getName());
									if (Main.PlayingPlayers.size() > 1) LobbyCredits.setScore(LobbyCredits.getScore() + 15);
									
									p.sendMessage("§e§m===========================");
									p.sendMessage("§6You captured the §cred §6flag");
									if (Main.PlayingPlayers.size() > 1) p.sendMessage("§a+ 15 Credits");
									if (Main.PlayingPlayers.size() > 1) p.sendMessage("§a+ 100 Exp");
									p.sendMessage("§e§m===========================");
									
									cancel();
									redFlagTask = null;
									holdingRedFlag.put(p, false);
									
									BlueTeamScore = BlueTeamScore + 1;
									
									for (Player p : Main.PlayingPlayers) {
										if (RedTeam.contains(p)) {
											SendCoolMessages.TabHeaderAndFooter("§4§lRed §c§lTeam", "§6§lCOD-Warfare\n" + getBetterTeam(), p);
										}else if (BlueTeam.contains(p)) {
											SendCoolMessages.TabHeaderAndFooter("§1§lBlue §9§lTeam", "§6§lCOD-Warfare\n" + getBetterTeam(), p);
										}
									}
								}
							}else{
								cancel();
								redFlagTask = null;
							}
						}
					}
				};
				
				br.runTaskTimer(ThisPlugin.getPlugin(), 0L, 0L);
				redFlagTask = br;
			}
		}else if (e.getItem().hasMetadata("codblueflag")) {
			if (BlueTeam.contains(p)) {
				e.setCancelled(true);				
				return;
			}else if (RedTeam.contains(p)) {
				e.setCancelled(true);
				
				if (blueFlagPickedUp) return;
				if (blueFlagTask != null) return;
				
				blueFlagPickedUp = true;
				holdingBlueFlag.put(p, true);
				
				final Item flag = e.getItem();
				
				for (Player pp : Main.PlayingPlayers) {
					pp.sendMessage(Main.codSignature + "§c" + p.getName() + " §6picked up the §9blue §6flag");
				}
				
				BukkitRunnable br = new BukkitRunnable() {
					public void run() {
						if (blueFlagPickedUp) {
							World world = p.getEyeLocation().getWorld();
							double x = (p.getEyeLocation().getX()) + (0.5);
							double y = (p.getEyeLocation().getY()) + (0.5);
							double z = (p.getEyeLocation().getZ()) + (0.5);
							
							for (World w : Bukkit.getServer().getWorlds()) {
								for (Entity e : w.getEntities()) {
									if (e.hasMetadata("codblueflag")) e.remove();
								}
							}
							
							Item i = world.dropItem(new Location(world, x , y , z), flag.getItemStack());
							i.setMetadata("codblueflag", new FixedMetadataValue(ThisPlugin.getPlugin(), i));
							
							flag.setVelocity(flag.getVelocity().multiply(0));
							
							if ((p.getEyeLocation() != null) && (getArena.getFlagLocation("red", PickRandomArena.CurrentArena) != null)) {
								if (p.getEyeLocation().distance(getArena.getFlagLocation("red", PickRandomArena.CurrentArena)) <= 2) {
									returnFlag("blue", PickRandomArena.CurrentArena);
									
									for (Player pp : Main.PlayingPlayers) {
										pp.sendMessage(Main.codSignature + "§c" + p.getName() + " §bcaptured the §9blue §bflag!");
									}
									
									if (Main.PlayingPlayers.size() > 1) Exp.addExp(p, 100);
									
									Score GameCredits = Main.GameCreditsScore.get(p.getName());
									if (Main.PlayingPlayers.size() > 1) GameCredits.setScore(GameCredits.getScore() + 15);
									
									Score LobbyCredits = Main.LobbyCreditsScore.get(p.getName());
									if (Main.PlayingPlayers.size() > 1) LobbyCredits.setScore(LobbyCredits.getScore() + 15);
									
									p.sendMessage("§e§m===========================");
									p.sendMessage("§6You captured the §9blue §6flag");
									if (Main.PlayingPlayers.size() > 1) p.sendMessage("§a+ 15 Credits");
									if (Main.PlayingPlayers.size() > 1) p.sendMessage("§a+ 100 Exp");
									p.sendMessage("§e§m===========================");
									
									cancel();
									blueFlagTask = null;
									holdingBlueFlag.put(p, false);							
									
									RedTeamScore = RedTeamScore + 1;
									
									for (Player p : Main.PlayingPlayers) {
										if (RedTeam.contains(p)) {
											SendCoolMessages.TabHeaderAndFooter("§4§lRed §c§lTeam", "§6§lCOD-Warfare\n" + getBetterTeam(), p);
										}else if (BlueTeam.contains(p)) {
											SendCoolMessages.TabHeaderAndFooter("§1§lBlue §9§lTeam", "§6§lCOD-Warfare\n" + getBetterTeam(), p);
										}
									}
								}
							}else{
								cancel();
								blueFlagTask = null;
							}
						}
					}
				};
				
				br.runTaskTimer(ThisPlugin.getPlugin(), 0L, 0L);
				blueFlagTask = br;
			}
		}else{
			return;
		}
	}
}
