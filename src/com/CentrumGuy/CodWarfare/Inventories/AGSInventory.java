package com.CentrumGuy.CodWarfare.Inventories;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.CentrumGuy.CodWarfare.Files.AvailableGunsFile;
import com.CentrumGuy.CodWarfare.Interface.ItemsAndInventories;
import com.CentrumGuy.CodWarfare.MySQL.MySQL;

public class AGSInventory {
	
	public static HashMap<Player, Inventory> availableSecondaryGuns = new HashMap<Player, Inventory>();
	
	public static void saveAGS(final Player p) {
		if (availableSecondaryGuns.get(p) != null) {
			for (int i = 0 ; i < availableSecondaryGuns.get(p).getSize() ; i++) {
				ItemStack item = availableSecondaryGuns.get(p).getItem(i);
				if (item == null) continue;
				if (item.equals(ItemsAndInventories.backAG)) {
					availableSecondaryGuns.get(p).setItem(i, null);
				}
			}
				
		if (!(MySQL.mySQLenabled())) {
			AvailableGunsFile.getData().set("AvailableSecondaryGuns." + p.getUniqueId(), SaveAndLoad.toString(availableSecondaryGuns.get(p)));
			AvailableGunsFile.saveData();
			AvailableGunsFile.reloadData();
		}else{
			String INSERT = "INSERT INTO CODASG VALUES(?, ?) ON DUPLICATE KEY UPDATE list=?";
			try {
				Connection conn = MySQL.getConnection();
				PreparedStatement ps = conn.prepareStatement(INSERT);
				
				ps.setString(1, p.getUniqueId().toString());
				
				Clob clob = conn.createClob();
				ArrayList<String> list = SaveAndLoad.toString(availableSecondaryGuns.get(p));
				clob.setString(1, MySQL.listToString(list));
				ps.setClob(2, clob);
				ps.setClob(3, clob);
				
				ps.executeUpdate();
				ps.close();
				conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
				
			availableSecondaryGuns.get(p).setItem(49, ItemsAndInventories.backAG);
		}else{
			return;
		}
	}
	
	public static Inventory getAGS(Player p) {
			if (availableSecondaryGuns.get(p) != null) return availableSecondaryGuns.get(p);
			else return loadAGS(p);
	}
	
	public static Inventory loadAGS(Player p) {
		if (!(MySQL.mySQLenabled())) {
			if (AvailableGunsFile.getData().getString("AvailableSecondaryGuns." + p.getUniqueId()) != null && !AvailableGunsFile.getData().getString("AvailableSecondaryGuns." + p.getUniqueId()).isEmpty()) {
				Inventory inv = Bukkit.getServer().createInventory(p, 54, "Available Secondary Guns");
				availableSecondaryGuns.put(p, SaveAndLoad.fromString(inv, AvailableGunsFile.getData().getList("AvailableSecondaryGuns." + p.getUniqueId())));
				return SaveAndLoad.fromString(inv, AvailableGunsFile.getData().getList("AvailableSecondaryGuns." + p.getUniqueId()));
			} else {
				Inventory inv = Bukkit.getServer().createInventory(p, 54, "Available Secondary Guns");
				availableSecondaryGuns.put(p, inv);
				
				return inv;
			}
		}else{
			try {
				Connection conn = MySQL.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT uuid,list FROM CODASG");
				ResultSet result = ps.executeQuery();
				
				boolean UUIDexists = false;
				boolean isEmpty = false;
				
				while(result.next()) {
					String s = result.getString("uuid");
					if (s.equals(p.getUniqueId().toString())) {
						UUIDexists = true;
						String stringList = MySQL.clobToString(result.getClob("list"));
						if (stringList == null || stringList.equals("")) isEmpty = true;
						break;
					}
				}
				
				conn.close();
				ps.close();
				result.close();
				
				if (UUIDexists && (!(isEmpty))) {
					Inventory inv = Bukkit.getServer().createInventory(p, 54, "Available Secondary Guns");
					
					conn = MySQL.getConnection();
					ps = conn.prepareStatement("SELECT uuid,list FROM CODASG");
					result = ps.executeQuery();
					
					String stringList = "";
					
					while(result.next()) {
						String s = result.getString("uuid");
						if (s.equals(p.getUniqueId().toString())) {
							stringList = MySQL.clobToString(result.getClob("list"));
							break;
						}
					}
					
					ArrayList<String> guns = (ArrayList<String>) MySQL.stringToList(stringList);
					inv = SaveAndLoad.fromString(inv, guns);
					
					availableSecondaryGuns.put(p, inv);
					availableSecondaryGuns.get(p).setItem(49, ItemsAndInventories.backAG);
					
					conn.close();
					ps.close();
					result.close();
					
					return inv;
				}else{
					Inventory inv = Bukkit.getServer().createInventory(p, 54, "Available Secondary Guns");
					availableSecondaryGuns.put(p, inv);
					return inv;
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
}