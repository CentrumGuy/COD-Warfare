package com.CentrumGuy.CodWarfare.Achievements;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Files.AchievementsFile;
import com.CentrumGuy.CodWarfare.Interface.ItemsAndInventories;
import com.CentrumGuy.CodWarfare.Interface.Scores;
import com.CentrumGuy.CodWarfare.Inventories.AGPInventory;
import com.CentrumGuy.CodWarfare.Inventories.AGSInventory;
import com.CentrumGuy.CodWarfare.MySQL.MySQL;

public class AchievementsAPI {
	public static HashMap<Player, Inventory> achievementsInv = new HashMap<Player, Inventory>();
	
	@SuppressWarnings("unused")
	public static void createAchievements() {
		Achievement achievement = new Achievement("First Blood", "Be the first to kill a player in a game", AchievementType.FIRST_BLOOD, 15, false);
		achievement = new Achievement("My First Kill", "Kill your first player", AchievementType.MY_FIRST_KILL, 5, true);
		achievement = new Achievement("Noob", "Die 10 times", AchievementType.NOOB, 5, false);
		achievement = new Achievement("Time To Upgrade", "Buy your first gun", AchievementType.TIME_TO_UPGRADE, 10, true);
		achievement = new Achievement("Pro", "Kill 200 players", AchievementType.PRO, 20, false);
		achievement = new Achievement("Ready For Battle", "Get to level 3", AchievementType.READY_FOR_BATTLE, 10, true);
		achievement = new Achievement("Master", "Kill 1000 players", AchievementType.MASTER, 100, false);
	}
	
	public static void setUpPlayer(Player p) {
		achievementsInv.put(p, Bukkit.getServer().createInventory(null, 45));
		setItems(p);
	}
	
	public static void setItems(Player p) {
		if (unlockedAchievement(p, AchievementType.MY_FIRST_KILL)) {
			achievementsInv.get(p).setItem(10, Achievement.getAchievementForType(AchievementType.MY_FIRST_KILL).getUnlockedAchievementItem());
		}else{
			achievementsInv.get(p).setItem(10, Achievement.getAchievementForType(AchievementType.MY_FIRST_KILL).getLockedAchievementItem());
		}
		
		if (unlockedAchievement(p, AchievementType.NOOB)) {
			achievementsInv.get(p).setItem(11, Achievement.getAchievementForType(AchievementType.NOOB).getUnlockedAchievementItem());
		}else{
			achievementsInv.get(p).setItem(11, Achievement.getAchievementForType(AchievementType.NOOB).getLockedAchievementItem());
		}
		
		if (unlockedAchievement(p, AchievementType.TIME_TO_UPGRADE)) {
			achievementsInv.get(p).setItem(12, Achievement.getAchievementForType(AchievementType.TIME_TO_UPGRADE).getUnlockedAchievementItem());
		}else{
			achievementsInv.get(p).setItem(12, Achievement.getAchievementForType(AchievementType.TIME_TO_UPGRADE).getLockedAchievementItem());
		}
		
		if (unlockedAchievement(p, AchievementType.READY_FOR_BATTLE)) {
			achievementsInv.get(p).setItem(13, Achievement.getAchievementForType(AchievementType.READY_FOR_BATTLE).getUnlockedAchievementItem());
		}else{
			achievementsInv.get(p).setItem(13, Achievement.getAchievementForType(AchievementType.READY_FOR_BATTLE).getLockedAchievementItem());
		}
		
		if (unlockedAchievement(p, AchievementType.FIRST_BLOOD)) {
			achievementsInv.get(p).setItem(14, Achievement.getAchievementForType(AchievementType.FIRST_BLOOD).getUnlockedAchievementItem());
		}else{
			achievementsInv.get(p).setItem(14, Achievement.getAchievementForType(AchievementType.FIRST_BLOOD).getLockedAchievementItem());
		}
		
		if (unlockedAchievement(p, AchievementType.PRO)) {
			achievementsInv.get(p).setItem(15, Achievement.getAchievementForType(AchievementType.PRO).getUnlockedAchievementItem());
		}else{
			achievementsInv.get(p).setItem(15, Achievement.getAchievementForType(AchievementType.PRO).getLockedAchievementItem());
		}
		
		if (unlockedAchievement(p, AchievementType.MASTER)) {
			achievementsInv.get(p).setItem(16, Achievement.getAchievementForType(AchievementType.MASTER).getUnlockedAchievementItem());
		}else{
			achievementsInv.get(p).setItem(16, Achievement.getAchievementForType(AchievementType.MASTER).getLockedAchievementItem());
		}
		
		achievementsInv.get(p).setItem(31, ItemsAndInventories.exit);
	}
	
	public static boolean unlockedAchievement(Player p, AchievementType at) {
		if (!(MySQL.mySQLenabled())) {
			List<String> achievements = AchievementsFile.getData().getStringList("Achievements." + p.getUniqueId());
			ArrayList<AchievementType> unlockedAchievements = new ArrayList<AchievementType>();
			
			for (String s : achievements) {
				unlockedAchievements.add(AchievementType.valueOf(s));
			}
			
			if (unlockedAchievements.contains(at)) return true;
			return false;
		}else{
			try {
				Connection conn = MySQL.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT uuid,list FROM CODAchievements");
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					if (rs.getString("uuid").equals(p.getUniqueId().toString())) {
						ArrayList<String> ach = new ArrayList<String>();
						ach = MySQL.stringToList(MySQL.clobToString(rs.getClob("list")));
						if (ach == null || ach.isEmpty()) {
							return false;
						}else{
							ArrayList<AchievementType> unlockedAchievements = new ArrayList<AchievementType>();
							
							for (String s : ach) {
								unlockedAchievements.add(AchievementType.valueOf(s));
							}
							
							if (unlockedAchievements.contains(at)) return true;
						}
					}
				}
				
				rs.close();
				ps.close();
				conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			return false;
		}
	}
	
	public static void unlockAchievement(Player p, AchievementType at) {
		List<String> achievements = new ArrayList<String>();
		if (!(MySQL.mySQLenabled())) {
			achievements = AchievementsFile.getData().getStringList("Achievements." + p.getUniqueId());
		}else{
			try {
				Connection conn = MySQL.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT uuid,list FROM CODAchievements");
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					if (rs.getString("uuid").equals(p.getUniqueId().toString())) {
						achievements = MySQL.stringToList(MySQL.clobToString(rs.getClob("list")));
						break;
					}
				}
				
				rs.close();
				ps.close();
				conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		ArrayList<AchievementType> unlockedAchievements = new ArrayList<AchievementType>();
		
		for (String s : achievements) {
			unlockedAchievements.add(AchievementType.valueOf(s));
		}
		
		if (unlockedAchievements.contains(at)) return;
		
		achievements.add("" + at);
		
		if (!(MySQL.mySQLenabled())) {
			AchievementsFile.getData().set("Achievements." + p.getUniqueId(), achievements);
			AchievementsFile.saveData();
			AchievementsFile.reloadData();
		}else{
			try {
				Connection conn = MySQL.getConnection();
				String INSERT = "INSERT INTO CODAchievements VALUES(?, ?) ON DUPLICATE KEY UPDATE list=?";
				PreparedStatement ps = conn.prepareStatement(INSERT);
				
				ps.setString(1, p.getUniqueId().toString());
				
				ArrayList<String> mySQLach = new ArrayList<String>();
				mySQLach.addAll(achievements);
				Clob clob = MySQL.stringToClob(MySQL.listToString(mySQLach), conn);
			
				ps.setClob(2, clob);
				ps.setClob(3, clob);
				
				ps.executeUpdate();
				
				ps.close();
				conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		Scores.saveScores(p);
		Scores.loadScores(p);
		
		Achievement a = Achievement.getAchievementForType(at);
		Main.LobbyCreditsScore.get(p.getName()).setScore(Main.LobbyCreditsScore.get(p.getName()).getScore() + a.getReward());
		//Main.GameCreditsScore.get(p.getName()).setScore(Main.GameCreditsScore.get(p.getName()).getScore() + a.getReward());
		p.sendMessage(Main.codSignature + "§aYou unlocked achievement§2 " + a.getName() + " §aand recieved§2 " + a.getReward() + " §acredits");
		
		setItems(p);
		
		Scores.saveScores(p);
		Scores.loadScores(p);
	}
	
	public static void unlockJoinAchievements(Player p) {
		if (Main.LobbyKillsScore.get(p.getName()).getScore() >= 1) unlockAchievement(p, AchievementType.MY_FIRST_KILL);
		if (Main.LobbyDeathsScore.get(p.getName()).getScore() >= 10) unlockAchievement(p, AchievementType.NOOB);
		if (Main.LobbyLevelScore.get(p.getName()).getScore() >= 3) unlockAchievement(p, AchievementType.READY_FOR_BATTLE);
		if ((AGPInventory.getAGP(p).getItem(1) != null) || (AGSInventory.getAGS(p).getItem(1) != null)) unlockAchievement(p, AchievementType.TIME_TO_UPGRADE);
		if (Main.LobbyKillsScore.get(p.getName()).getScore() >= 200) unlockAchievement(p, AchievementType.PRO);
		if (Main.LobbyKillsScore.get(p.getName()).getScore() >= 1000) unlockAchievement(p, AchievementType.MASTER);
	}
	
	public static void unlockMyFirstKill(Player p) {
		if (Main.LobbyKillsScore.get(p.getName()).getScore() >= 1) unlockAchievement(p, AchievementType.MY_FIRST_KILL);
	}
	
	public static void unlockNoob(Player p) {
		if (Main.LobbyDeathsScore.get(p.getName()).getScore() >= 10) unlockAchievement(p, AchievementType.NOOB);
	}
	
	public static void unlockTimeToUpgrade(Player p) {
		if ((AGPInventory.getAGP(p).getItem(1) != null) || (AGSInventory.getAGS(p).getItem(1) != null)) unlockAchievement(p, AchievementType.TIME_TO_UPGRADE);
	}
	
	public static void unlockReadyForBattle(Player p) {
		if (Main.LobbyLevelScore.get(p.getName()).getScore() >= 3) unlockAchievement(p, AchievementType.READY_FOR_BATTLE);
	}
	
	public static void unlockFirstBlood(Player p) {
		if (Main.GameKillsScore.get(p.getName()).getScore() >= 1) unlockAchievement(p, AchievementType.FIRST_BLOOD);
	}
	
	public static void unlockPro(Player p) {
		if (Main.LobbyKillsScore.get(p.getName()).getScore() >= 200) unlockAchievement(p, AchievementType.PRO);
	}
	
	public static void unlockMaster(Player p) {
		if (Main.LobbyKillsScore.get(p.getName()).getScore() >= 1000) unlockAchievement(p, AchievementType.MASTER);
	}
	
	public static ItemStack getAchievementsItem(Player p) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		 
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(p.getName());
        meta.setDisplayName("§c§lAchievements");
        skull.setItemMeta(meta);
        
        return skull;
	}
}
