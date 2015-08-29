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

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Inventories.KitInventory;
import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;
import com.CentrumGuy.CodWarfare.Utilities.GameCountdown;
import com.CentrumGuy.CodWarfare.Utilities.GetNormalName;
import com.CentrumGuy.CodWarfare.Utilities.Prefix;
import com.CentrumGuy.CodWarfare.Utilities.SendCoolMessages;
import com.CentrumGuy.CodWarfare.Utilities.ShowPlayer;

public class INFECTArena {

	public static ArrayList<Player> Zombies = new ArrayList<Player>();
	public static ArrayList<Player> Blue = new ArrayList<Player>();
	private static ArrayList<Player> pls = new ArrayList<Player>();
	public static HashMap<Player, ZombieTeam> team = new HashMap<Player, ZombieTeam>();
	
	public static Player firstZombie;
	
	public static void assignTeam() {
		
		Blue.clear();
		Zombies.clear();
		pls.clear();
		team.clear();

		if (Main.WaitingPlayers.isEmpty()) return;
		
		if (!(Main.WaitingPlayers.isEmpty())) Main.PlayingPlayers.addAll(Main.WaitingPlayers);
		Main.WaitingPlayers.clear();

		pls.addAll(Main.PlayingPlayers);
		
		int FirstZombie;

		if (pls.size() <= 1) {
			FirstZombie = 0;
		}else{
			FirstZombie = new Random().nextInt(pls.size());
		}
		
		if (pls.size() == 1) FirstZombie = 0;
		Player p = pls.get(FirstZombie);

		if (Zombies.isEmpty()) {
			Zombies.add(p);
			team.put(p, ZombieTeam.ZOMBIE);
		}

		for (int i = 0 ; i < pls.size() ; i++) {
			
			Player pp = pls.get(i);
			
			if (Zombies.contains(pp)) {
				continue;
			}else{
				Blue.add(pp);
				team.put(pp, ZombieTeam.BLUE);
			}
			
		}
		
		return;
		
	}
	
	public static void StartINFECTArena(final String Arena) {
		setTabInfo();
		for (final Player p : pls) {
			if (Blue.contains(p)) {
				p.sendMessage(Main.codSignature + "§9You joined the §1blue §9team");
				p.closeInventory();
				p.teleport(getArena.getBlueSpawn(Arena));
				p.getInventory().clear();
				p.setHealth(20);
				p.setFoodLevel(20);
				p.getInventory().clear();
				p.sendMessage(Main.codSignature + "§c" + Zombies.get(0).getName() + " §6was picked to be the first zombie");
				GameCountdown.startCountdown(p, getArena.getBlueSpawn(Arena), "§9§lGO GO GO!!!", Main.codSignature + "§9§lYou joined the §1§lblue§9§l team!");
				Prefix.setDispName(p, "§9" + p.getName());
				if (Main.dispName.get(p) != null) p.setPlayerListName(Main.dispName.get(p));
				
				/*for (Player pp : Blue) {
					Main.blue.addPlayer(pp);
				}
				
				for (Player pp : Zombies) {
					Main.red.addPlayer(pp);
				}*/
				
				Main.setGameBoard(p);
				
				Color c = Color.fromRGB(0, 0, 255);
				p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
				p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
				p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
				p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
				
				if (Main.extraCountdown) {
					Bukkit.getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
						public void run() {
							SendCoolMessages.clearTitleAndSubtitle(p);
							SendCoolMessages.resetTitleAndSubtitle(p);
							SendCoolMessages.sendTitle(p, "§6", 10, 50, 10);
					    	SendCoolMessages.sendSubTitle(p, "§9§lYOU JOINED THE §1§lBLUE TEAM", 10, 50, 10);
						}
					}, 60);
				}
				
				if (Main.extraCountdown) {
					Bukkit.getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
						public void run() {
							if (!(Main.PlayingPlayers.contains(p))) return;
							
							ShowPlayer.showPlayer(p);
							
							Color c = Color.fromRGB(0, 0, 255);
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
							p.sendMessage("§b§lYou are playing a game of §4§lInfected!");
							p.sendMessage(" §7§l- §6§lKill the §c§lZombies!");
							p.sendMessage(" §7§l- §6§lArena:§e§l " + Arena);
							p.sendMessage(" §7§l- §6§lGood luck!");
							p.sendMessage("§e§m=====================================================");
							p.sendMessage("");
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
					p.sendMessage("§b§lYou are playing a game of §4§lInfected!");
					p.sendMessage(" §7§l- §6§lKill the §c§lZombies!");
					p.sendMessage(" §7§l- §6§lArena:§e§l " + Arena);
					p.sendMessage(" §7§l- §6§lGood luck!");
					p.sendMessage("§e§m=====================================================");
					p.sendMessage("");
				}
			}else if (Zombies.contains(p)) {
				firstZombie = p;
				p.closeInventory();
				p.teleport(getArena.getRedSpawn(Arena));
				p.getInventory().clear();
				p.setHealth(20);
				p.setFoodLevel(20);
				p.getInventory().clear();
				p.sendMessage(Main.codSignature + "§cYou §6were picked to be the first zombie");
				Prefix.setDispName(p, "§c" + p.getName());
				if (Main.dispName.get(p) != null) p.setPlayerListName(Main.dispName.get(p));
				GameCountdown.startCountdown(p, getArena.getRedSpawn(Arena), "§c§lGO GO GO!!!", Main.codSignature + "§c§lYou joined the §4§lred§c§l team!");
				
				/*for (Player pp : Blue) {
					Main.blue.addPlayer(pp);
				}
				
				for (Player pp : Zombies) {
					Main.red.addPlayer(pp);
				}*/
				
				Main.setGameBoard(p);
				
				ShowPlayer.showPlayer(p);
				
				Color c = Color.fromRGB(0, 153, 0);
				p.getInventory().setHelmet(new ItemStack(Material.SKULL_ITEM, 1));
				p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
				p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
				p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
				
				if (Main.extraCountdown) {
					Bukkit.getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
						public void run() {
							SendCoolMessages.clearTitleAndSubtitle(p);
							SendCoolMessages.resetTitleAndSubtitle(p);
							SendCoolMessages.sendTitle(p, "§6", 10, 50, 10);
					    	SendCoolMessages.sendSubTitle(p, "§c§lYOU JOINED THE §4§lZOMBIE TEAM", 10, 50, 10);
						}
					}, 60);
				}
				
				if (Main.extraCountdown) {
					Bukkit.getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
						public void run() {
							for (Player pp : Bukkit.getOnlinePlayers()) {
								pp.showPlayer(p);
							}
							
							p.sendMessage("");
							p.sendMessage("§e§m=====================================================");
							p.sendMessage("§b§lYou are playing a game of §4§lInfected!");
							p.sendMessage(" §7§l- §6§lInfect the §9§lBlue Team!");
							p.sendMessage(" §7§l- §6§lOne hit kill");
							p.sendMessage(" §7§l- §6§lArena:§e§l " + Arena);
							p.sendMessage(" §7§l- §6§lGood luck!");
							p.sendMessage("§e§m=====================================================");
							p.sendMessage("");
							
							p.setWalkSpeed((float) 0.3);
						}
					}, 225);
				}else{
					for (Player pp : Bukkit.getOnlinePlayers()) {
						pp.showPlayer(p);
					}
					
					p.sendMessage("");
					p.sendMessage("§e§m=====================================================");
					p.sendMessage("§b§lYou are playing a game of §4§lInfected!");
					p.sendMessage(" §7§l- §6§lInfect the §9§lBlue Team!");
					p.sendMessage(" §7§l- §6§lOne hit kill");
					p.sendMessage(" §7§l- §6§lArena:§e§l " + Arena);
					p.sendMessage(" §7§l- §6§lGood luck!");
					p.sendMessage("§e§m=====================================================");
					p.sendMessage("");
					
					p.setWalkSpeed((float) 0.3);
				}
			}else return;
		}
	}
	
	public static Location getSpawn(Player p) {
		if (Zombies.contains(p)) {
			return getArena.getRedSpawn(PickRandomArena.CurrentArena);
		}else if (Blue.contains(p)) {
			return getArena.getRedSpawn(PickRandomArena.CurrentArena);
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void spawnPlayer(final Player p, String Arena) {
		if (BaseArena.state == BaseArena.ArenaState.ENDING) {
			p.getInventory().clear();
			p.updateInventory();
			Main.invincible.add(p);
			p.setWalkSpeed((float) 0.2);
			
			if (p.equals(firstZombie)) {
				Color c = Color.fromRGB(0, 153, 0);
				p.getInventory().setHelmet(new ItemStack(Material.SKULL_ITEM, 1));
				p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
				p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
				p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
			}else{
				Color c = Color.fromRGB(0, 153, 0);
				p.getInventory().setHelmet(new ItemStack(Material.SKULL_ITEM, 1, (byte) 2));
				p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
				p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
				p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
			}
			
			return;
		}
		
		if (Blue.contains(p)) {
			Blue.remove(p);
			Zombies.add(p);
			team.put(p, ZombieTeam.ZOMBIE);
			
			p.getInventory().clear();
			Color c = Color.fromRGB(0, 153, 0);
			p.getInventory().setHelmet(new ItemStack(Material.SKULL_ITEM, 1, (byte) 2));
			p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
			p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
			p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
			
			for (Player pp : Main.PlayingPlayers) {
				pp.sendMessage(Main.codSignature + "§4" + p.getName() + " §cis now infected");
			}
			
			p.teleport(getArena.getRedSpawn(Arena));
			Prefix.setDispName(p, "§c" + p.getName());
			if (Main.dispName.get(p) != null) p.setPlayerListName(Main.dispName.get(p));
			
			p.setWalkSpeed((float) 0.3);
			
			setTabInfo();
			
			p.getInventory().setItem(0, KitInventory.getKit(p).getItem(5));
			p.getInventory().setItem(1, KitInventory.getKit(p).getItem(6));
			p.getInventory().setItem(2, KitInventory.getKit(p).getItem(7));
			
			if (Blue.isEmpty()) {
				StopGameCountdown.endGame();
				setTabInfo();
				return;
			}
			
			Main.invincible.add(p);
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
				public void run() {
					Main.invincible.remove(p);
				}
			}, 100L);
		}else if (p.equals(firstZombie)) {
			p.getInventory().clear();
			Color c = Color.fromRGB(0, 153, 0);
			p.getInventory().setHelmet(new ItemStack(Material.SKULL_ITEM, 1));
			p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
			p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
			p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
			
			p.teleport(getArena.getRedSpawn(Arena));
			
			p.setWalkSpeed((float) 0.3);
			
			p.getInventory().setItem(0, KitInventory.getKit(p).getItem(5));
			p.getInventory().setItem(1, KitInventory.getKit(p).getItem(6));
			p.getInventory().setItem(2, KitInventory.getKit(p).getItem(7));
			
			Main.invincible.add(p);
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
				public void run() {
					Main.invincible.remove(p);
				}
			}, 100L);
		}else{
			p.getInventory().clear();
			Color c = Color.fromRGB(0, 153, 0);
			p.getInventory().setHelmet(new ItemStack(Material.SKULL_ITEM, 1, (byte) 2));
			p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
			p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
			p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
			
			p.teleport(getArena.getRedSpawn(Arena));
			
			p.setWalkSpeed((float) 0.3);
			
			p.getInventory().setItem(0, KitInventory.getKit(p).getItem(5));
			p.getInventory().setItem(1, KitInventory.getKit(p).getItem(6));
			p.getInventory().setItem(2, KitInventory.getKit(p).getItem(7));
			
			Main.invincible.add(p);
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
				public void run() {
					Main.invincible.remove(p);
				}
			}, 100L);
		}
	}
	
	public static void endInfect() {
		if (!(Blue.isEmpty())) {
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
		}else{
			for (Player pp : Main.PlayingPlayers) {
				pp.sendMessage("");
				pp.sendMessage("");
				pp.sendMessage("");
				pp.sendMessage("§7║ §b§lStatistics:§6§l " + PickRandomArena.CurrentArena);
				pp.sendMessage("§7║");
				pp.sendMessage("§7║ §7§lWinner: §c§lZombies" + "             §b§lTotal Kills:§a§l " + Main.GameKillsScore.get(pp.getName()).getScore());
				
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
				pp.sendMessage(Main.codSignature + "§cThe §4zombies §cwon!");
			}
		}
		
		Blue.clear();
		Zombies.clear();
	}
	
	private static ItemStack getColorArmor(Material m, Color c) {
		ItemStack i = new ItemStack(m , 1);
		LeatherArmorMeta meta = (LeatherArmorMeta) i.getItemMeta();
		meta.setColor(c);
		i.setItemMeta(meta);
		return i;
	}
	
	public static void setTabInfo() {
		String score = "§9Blue§1(§9" + Blue.size() + "§1)";
		if (Blue.isEmpty()) score = "§cZombies §4Win";
		for (Player p : Main.PlayingPlayers) {
			if (Zombies.contains(p)) {
				SendCoolMessages.TabHeaderAndFooter("§4§lZombie", "§6§lCOD-Warfare\n" + score, p);
			}else if (Blue.contains(p)) {
				SendCoolMessages.TabHeaderAndFooter("§1§lBlue §9§lTeam", "§6§lCOD-Warfare\n" + score, p);
			}
		}
	}
}
