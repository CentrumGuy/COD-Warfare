package com.CentrumGuy.CodWarfare.Clans;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.CentrumGuy.CodWarfare.Files.ClansFile;
import com.CentrumGuy.CodWarfare.MySQL.MySQL;

public class MainClan {
	
	public static ArrayList<String> clans = new ArrayList<String>();
	public static HashMap<String, ArrayList<String>> clan = new HashMap<String, ArrayList<String>>();
	public static HashMap<String, ArrayList<String>> admins = new HashMap<String, ArrayList<String>>();
	public static HashMap<Player, ArrayList<String>> invites = new HashMap<Player, ArrayList<String>>();
	
	public static void setUp(Plugin p) {
		ClansFile.setup(p);
		loadClans();
	}
	
	public static void loadClans() {
		clans.clear();
		clan.clear();
		admins.clear();
		
		if (!(MySQL.mySQLenabled())) {
			int i = 0;
			while (ClansFile.getData().get("Clans." + i) != null) {
				clans.add(ClansFile.getData().getString("Clans." + i + ".Name"));
				getClanMembers(ClansFile.getData().getString("Clans." + i + ".Name"));
				getClanAdmins(ClansFile.getData().getString("Clan." + i + ".Name"));
				i++;
			}
		}else{
			try {
				Connection conn = MySQL.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT clan FROM CODClans");
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					clans.add(rs.getString("clan"));
					getClanMembers(rs.getString("clan"));
					getClanAdmins(rs.getString("clan"));
				}
				
				rs.close();
				ps.close();
				conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void saveClans() {
		if (!(MySQL.mySQLenabled())) {
			HashMap<Integer, String> Owner = new HashMap<Integer, String>();
			int next = 0;
			
			while (ClansFile.getData().get("Clans." + next) != null) {
				Owner.put(next, ClansFile.getData().getString("Clans." + next + ".Owner"));
				next++;
			}
			
			ClansFile.getData().set("Clans", null);
			ClansFile.saveData();
			ClansFile.reloadData();
			
			for (int i = 0 ; i < clans.size() ; i++) {
				ClansFile.getData().set("Clans." + i + ".Name", clans.get(i));
				if (clan.get(clans.get(i)) != null) {
					ClansFile.getData().set(("Clans." + i + ".Players"), clan.get(clans.get(i)));
				}
				if (admins.get(clans.get(i)) != null) {
					ClansFile.getData().set("Clans." + i + ".Admins", admins.get(clans.get(i)));
				}
				ClansFile.getData().set(("Clans." + i + ".Owner"), Owner.get(i));
			}
			
			ClansFile.saveData();
			ClansFile.reloadData();
			loadClans();
		}else{
			try {
				HashMap<String, String> Owner = new HashMap<String, String>();
				
				Connection conn = MySQL.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT clan,owner FROM CODClans");
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					Owner.put(rs.getString("clan"), rs.getString("owner"));
				}
				
				ps = conn.prepareStatement("DELETE FROM CODClans");
				ps.executeUpdate();
				
				for (int i = 0 ; i < clans.size() ; i++) {
					String INSERT = "INSERT INTO CODClans (clan, owner, players, admins) VALUES(?, ?, ?, ?) ON DUPLICATE KEY UPDATE owner=?,players=?,admins=?";
					ps = conn.prepareStatement(INSERT);
					ps.setString(1, clans.get(i));
					if (clan.get(clans.get(i)) != null) {
						Clob clob = MySQL.stringToClob(MySQL.listToString(clan.get(clans.get(i))), conn);
						ps.setClob(3, clob);
						ps.setClob(6, clob);
					}else{
						Clob clob = MySQL.stringToClob(MySQL.listToString(new ArrayList<String>()), conn);
						ps.setClob(3, clob);
						ps.setClob(6, clob);
					}
					
					if (admins.get(clans.get(i)) != null) {
						Clob clob = MySQL.stringToClob(MySQL.listToString(admins.get(clans.get(i))), conn);
						ps.setClob(4, clob);
						ps.setClob(7, clob);
					}else{
						Clob clob = MySQL.stringToClob(MySQL.listToString(new ArrayList<String>()), conn);
						ps.setClob(4, clob);
						ps.setClob(7, clob);
					}
					
					ps.setString(2, Owner.get(clans.get(i)));
					ps.setString(5, Owner.get(clans.get(i)));
					
					ps.executeUpdate();
					ps.close();
					conn.close();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean deleteClan(String ClanName) {
	if (!(MySQL.mySQLenabled())) {
			if (getClanNumber(ClanName) != -1) {
				if (clans.contains(ClanName)) clans.remove(ClanName);
				if (clan.get(ClanName) != null) clan.put(ClanName, null);
				if (admins.get(ClanName) != null) admins.put(ClanName, null);
				
				ClansFile.getData().set("Clans", null);
				
				saveClans();
				
				return true;
			}
			
			return false;
		}else{
			try {
				if (clanExists(ClanName)) {
					if (clans.contains(ClanName)) clans.remove(ClanName);
					if (clan.get(ClanName) != null) clan.put(ClanName, null);
					if (admins.get(ClanName) != null) admins.put(ClanName, null);
					
					Connection conn = MySQL.getConnection();
					PreparedStatement ps = conn.prepareStatement("DELETE FROM CODClans WHERE clan=?");
					ps.executeUpdate();
					
					ps.setString(1, ClanName);
					
					ps.close();
					conn.close();
					
					return true;
				}
				
				return false;
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		return false;
	}
	
	public static String getClan(Player p) {
		loadClans();
		
		UUID uuid = p.getUniqueId();
		String id = "" + uuid;
		
		for (int i = 0 ; i < clans.size() ; i++) {
			String clanName = clans.get(i);
			if (clan.get(clanName).contains(id)) return clanName;
		}
		
		return null;
	}
	
	public static ArrayList<String> getClanMembers(String clanName) {
		/**
		 * Returns uuids of players
		 */
		
		if (clanExists(clanName)) {
			ArrayList<String> players = new ArrayList<String>();
			if (!(MySQL.mySQLenabled())) {
				players = (ArrayList<String>) ClansFile.getData().getStringList("Clans." + getClanNumber(clanName) + ".Players");
			}else{
				try {
					Connection conn = MySQL.getConnection();
					PreparedStatement ps = conn.prepareStatement("SELECT clan,players FROM CODClans");
					ResultSet rs = ps.executeQuery();
					
					while (rs.next()) {
						if (rs.getString("clan").equals(clanName)) {
							players = MySQL.stringToList(MySQL.clobToString(rs.getClob("players")));
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
			
			clan.put(clanName, players);
			return players;
		}

		ArrayList<String> newList = new ArrayList<String>();
		clan.put(clanName, newList);
		return newList;
	}
	
	public static ArrayList<String> getClanAdmins(String clanName) {
		/**
		 * Returns uuids of players
		 */
		
		if (clanExists(clanName)) {
			ArrayList<String> Admins = new ArrayList<String>();
			if (!(MySQL.mySQLenabled())) {
				Admins = (ArrayList<String>) ClansFile.getData().getStringList("Clans." + getClanNumber(clanName) + ".Admins");
			}else{
				try {
					Connection conn = MySQL.getConnection();
					PreparedStatement ps = conn.prepareStatement("SELECT clan,admins FROM CODClans");
					ResultSet rs = ps.executeQuery();
					
					while (rs.next()) {
						if (rs.getString("clan").equals(clanName)) {
							Admins = MySQL.stringToList(MySQL.clobToString(rs.getClob("admins")));
							break;
						}
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			admins.put(clanName, Admins);
			return Admins;
		}

		ArrayList<String> newList = new ArrayList<String>();
		admins.put(clanName, newList);
		return newList;
	}

	public static void createClan(String ClanName, Player owner) {
		if (!(MySQL.mySQLenabled())) {
			int nextNum = 0;
			
			while (ClansFile.getData().get("Clans." + nextNum) != null) {
				nextNum++;
			}
			
			ArrayList<String> players = new ArrayList<String>();
			String uuid = "" + owner.getUniqueId();
			players.add(uuid);
			
			ClansFile.getData().set("Clans." + nextNum + ".Owner", uuid);
			
			clans.add(ClanName);
			clan.put(ClanName, players);
			
			saveClans();
		}else{
			try {
				Connection conn = MySQL.getConnection();
				String INSERT = "INSERT INTO CODClans (clan, owner) VALUES(?, ?) ON DUPLICATE KEY UPDATE owner=?";
				PreparedStatement ps = conn.prepareStatement(INSERT);
				
				ps.setString(1, ClanName);
				ps.setString(2, owner.getUniqueId().toString());
				ps.setString(3, owner.getUniqueId().toString());
				
				ArrayList<String> players = new ArrayList<String>();
				String uuid = "" + owner.getUniqueId();
				players.add(uuid);
				
				clans.add(ClanName);
				clan.put(ClanName, players);
				
				ps.executeUpdate();
				
				saveClans();
				
				ps.close();
				conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void addPlayer(String Clan, Player p) {
		if (clanExists(Clan)) {
			ArrayList<String> uuid = clan.get(Clan);
			uuid.add("" + p.getUniqueId());
			clan.put(Clan, uuid);
			
			saveClans();
		}
	}
	
	public static void addAdmin(String Clan, Player p) {
		if (clanExists(Clan)) {
			ArrayList<String> uuid = admins.get(Clan);
			uuid.add("" + p.getUniqueId());
			admins.put(Clan, uuid);
			
			saveClans();
		}
	}
	
	public static void removeAdmin(Player p, String clanName) {
		if (clanExists(clanName)) {
			ArrayList<String> newPlayers = new ArrayList<String>();
			newPlayers.addAll(getClanAdmins(clanName));
			if (newPlayers.contains("" + p.getUniqueId())) newPlayers.remove("" + p.getUniqueId());
			admins.put(clanName, newPlayers);
			saveClans();
		}
	}
	
	public static void removePlayer(Player p, String clanName) {
		if (clanExists(clanName)) {
			ArrayList<String> newPlayers = new ArrayList<String>();
			newPlayers.addAll(getClanMembers(clanName));
			if (newPlayers.contains("" + p.getUniqueId())) newPlayers.remove("" + p.getUniqueId());
			clan.put(clanName, newPlayers);
			saveClans();
		}
	}
	
	public static boolean clanExists(String clan) {
		if (!(MySQL.mySQLenabled())) {
			ArrayList<String> c = new ArrayList<String>();
			ClansFile.saveData();
			ClansFile.reloadData();
			
			int i = 0;
			while (ClansFile.getData().get("Clans." + i) != null) {
				c.add(ClansFile.getData().getString("Clans." + i + ".Name"));
				i++;
			}
			
			if (c.isEmpty()) {
				return false;
			}
			
			if (c.contains(clan)) {
				return true;
			}
			
			return false;
		}else{
			try {
				Connection conn = MySQL.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT clan FROM CODClans");
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					if (rs.getString("clan").equals(clan)) return true;
				}
				
				rs.close();
				ps.close();
				conn.close();
				
				return false;
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			return false;
		}
	}
	
	public static int getClanNumber(String clan) {
		if (!(MySQL.mySQLenabled())) {
			if (clanExists(clan)) {
				int i = 0;
				while (ClansFile.getData().get("Clans." + i) != null) {
					if (ClansFile.getData().getString("Clans." + i + ".Name").equals(clan)) return i;
					i++;
				}
				
				return -1;
			}
		}
	
		return -1;
	}
	
	public static String getOwner(String clanName) {
		/**
		 * Returns the UUID of the player
		 **/
		
	if (!(MySQL.mySQLenabled())) {
		saveClans();
		if ((clanExists(clanName)) && (getClanNumber(clanName) != -1)) {
			return (String) ClansFile.getData().get("Clans." + getClanNumber(clanName) + ".Owner");
		}
	}else{
		try {
			Connection conn = MySQL.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT clan,owner FROM CODClans");
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				if (rs.getString("clan").equals(clanName)) {
					return rs.getString("owner");
				}
			}
			
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
		return null;
	}
	
	public static void setOwner(Player p, String clanName) {
		saveClans();
		if (clanExists(clanName)) {
			if (!(MySQL.mySQLenabled())) {
				ClansFile.getData().set("Clans." + getClanNumber(clanName) + ".Owner", p.getUniqueId());
				ClansFile.saveData();
				ClansFile.reloadData();
				loadClans();
			}else{
				try {
					Connection conn = MySQL.getConnection();
					String INSERT = "INSERT INTO CODClans (clan, owner) VALUES(?, ?) ON DUPLICATE KEY UPDATE owner=?";
					PreparedStatement ps = conn.prepareStatement(INSERT);
					
					ps.setString(1, clanName);
					ps.setString(2, p.getUniqueId().toString());
					ps.setString(3, p.getUniqueId().toString());
					
					ps.executeUpdate();
					
					ps.close();
					conn.close();
					loadClans();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static boolean isOwner(Player p, String clanName) {
		saveClans();
		
		if (clanExists(clanName)) {
			String uuid = "" + p.getUniqueId();
			if (getOwner(clanName).equals(uuid)) return true;
			return false;
		}
		
		return false;
	}
	
	public static boolean belongsToClan(Player p, String clanName) {
		saveClans();
		
		if (clanExists(clanName)) {
			if (getClanMembers(clanName).contains("" + p.getUniqueId())) return true;
			return false;
		}
		
		return false;
	}
	
	public static boolean belongsToAClan(Player p) {
		saveClans();
		
		if (getClan(p) != null) return true;
		return false;
	}
	
	public static boolean isAdmin(String clan, Player p) {
		if (admins.get(clan) == null) return false;
		if (admins.get(clan).contains(p)) return true;
		return false;
	}
}
