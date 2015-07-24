package com.CentrumGuy.CodWarfare.Clans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.CentrumGuy.CodWarfare.Files.ClansFile;

public class MainClan {
	
	public static ArrayList<String> clans = new ArrayList<String>();
	public static HashMap<String, ArrayList<String>> clan = new HashMap<String, ArrayList<String>>();
	public static HashMap<Player, ArrayList<String>> invites = new HashMap<Player, ArrayList<String>>();
	
	public static void setUp(Plugin p) {
		ClansFile.setup(p);
		loadClans();
	}
	
	public static void loadClans() {
		clans.clear();
		clan.clear();
		
		int i = 0;
		while (ClansFile.getData().get("Clans." + i) != null) {
			clans.add(ClansFile.getData().getString("Clans." + i + ".Name"));
			getClanMembers(ClansFile.getData().getString("Clans." + i + ".Name"));
			i++;
		}
	}
	
	public static void saveClans() {
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
			ClansFile.getData().set(("Clans." + i + ".Owner"), Owner.get(i));
		}
		
		ClansFile.saveData();
		ClansFile.reloadData();
		loadClans();
	}
	
	public static boolean deleteClan(String ClanName) {
		
		if (getClanNumber(ClanName) != -1) {
			if (clans.contains(ClanName)) clans.remove(ClanName);
			if (clan.get(ClanName) != null) clan.put(ClanName, null);
			
			ClansFile.getData().set("Clans", null);
			
			saveClans();
			
			return true;
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
			ArrayList<String> players = (ArrayList<String>) ClansFile.getData().getStringList("Clans." + getClanNumber(clanName) + ".Players");
			clan.put(clanName, players);
			return players;
		}

		ArrayList<String> newList = new ArrayList<String>();
		clan.put(clanName, newList);
		return newList;
	}

	public static void createClan(String ClanName, Player owner) {
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
	}
	
	public static void addPlayer(String Clan, Player p) {
		if (getClanNumber(Clan) != -1) {
			ArrayList<String> uuid = clan.get(Clan);
			uuid.add("" + p.getUniqueId());
			clan.put(Clan, uuid);
			
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
	}
	
	public static int getClanNumber(String clan) {
		if (clanExists(clan)) {
			int i = 0;
			while (ClansFile.getData().get("Clans." + i) != null) {
				if (ClansFile.getData().getString("Clans." + i + ".Name").equals(clan)) return i;
			}
			
			return -1;
		}
		
		return -1;
	}
	
	public static String getOwner(String clanName) {
		/**
		 * Returns the UUID of the player
		 **/
		
		saveClans();
		if ((clanExists(clanName)) && (getClanNumber(clanName) != -1)) {
			return (String) ClansFile.getData().get("Clans." + getClanNumber(clanName) + ".Owner");
		}
		
		return null;
	}
	
	public static void setOwner(Player p, String clanName) {
		saveClans();
		if ((clanExists(clanName))) {
			ClansFile.getData().set("Clans." + getClanNumber(clanName) + ".Owner", p.getUniqueId());
			ClansFile.saveData();
			ClansFile.reloadData();
			loadClans();
		}
	}
	
	public static boolean isOwner(Player p, String clanName) {
		saveClans();
		
		if ((clanExists(clanName)) && (getClanNumber(clanName) != -1)) {
			String uuid = "" + p.getUniqueId();
			if (getOwner(clanName).equals(uuid)) return true;
			return false;
		}
		
		return false;
	}
	
	public static boolean belongsToClan(Player p, String clanName) {
		saveClans();
		
		if ((clanExists(clanName))) {
			if (getClanMembers(clanName).contains(p.getUniqueId())) return true;
			return false;
		}
		
		return false;
	}
	
	public static boolean belongsToAClan(Player p) {
		saveClans();
		
		if (getClan(p) != null) return true;
		return false;
	}
}
