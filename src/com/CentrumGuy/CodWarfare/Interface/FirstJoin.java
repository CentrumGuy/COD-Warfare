package com.CentrumGuy.CodWarfare.Interface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.CentrumGuy.CodWarfare.Files.JoinedCODFile;
import com.CentrumGuy.CodWarfare.Inventories.AGPInventory;
import com.CentrumGuy.CodWarfare.Inventories.AGSInventory;
import com.CentrumGuy.CodWarfare.Leveling.Level;
import com.CentrumGuy.CodWarfare.MySQL.MySQL;

public class FirstJoin {

	public static void fJoin(Player p) {
		List<String> JoinedPlayers = new ArrayList<String>();
		if (!(MySQL.mySQLenabled())) {
			JoinedPlayers = JoinedCODFile.getData().getStringList("JoinedPlayers");
			if (JoinedPlayers.contains("" + p.getUniqueId())) return;
		}else{
			try {
				Connection conn = MySQL.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT players FROM CODJoined");
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					JoinedPlayers.add(rs.getString("players"));
				}
				
				if (JoinedPlayers.contains(p.getUniqueId().toString())) return;
				
				rs.close();
				ps.close();
				conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		Scores.loadScores(p);
		Level.resetLevel(p);
		
		if ((!(agpNull(p))) && (!(agsNull(p)))) {
			if (!(MySQL.mySQLenabled())) {
				JoinedPlayers.add("" + p.getUniqueId());
				JoinedCODFile.getData().set("JoinedPlayers", JoinedPlayers);
				JoinedCODFile.saveData();
				JoinedCODFile.reloadData();
			}else{
				try {
					Connection conn = MySQL.getConnection();
					String INSERT = "INSERT INTO CODJoined VALUES(?) ON DUPLICATE KEY UPDATE players=?";
					PreparedStatement ps = conn.prepareStatement(INSERT);
					
					ps.setString(1, p.getUniqueId().toString());
					ps.setString(2, p.getUniqueId().toString());
					
					ps.executeUpdate();
					
					ps.close();
					conn.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static boolean agpNull(Player p) {
		for (int i = 0 ; i < AGPInventory.getAGP(p).getSize() ; i++) {
			ItemStack item = AGPInventory.getAGP(p).getItem(i);
			if (item == null) continue;
			if (item.equals(ItemsAndInventories.backAG)) continue;
			
			return false;
		}
		
		return true;
	}
	
	public static boolean agsNull(Player p) {
		for (int i = 0 ; i < AGSInventory.getAGS(p).getSize() ; i++) {
			ItemStack item = AGSInventory.getAGS(p).getItem(i);
			if (item == null) continue;
			if (item.equals(ItemsAndInventories.backAG)) continue;
			
			return false;
		}
		
		return true;
	}
}