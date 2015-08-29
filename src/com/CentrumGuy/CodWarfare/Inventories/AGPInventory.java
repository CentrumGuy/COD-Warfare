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

public class AGPInventory {
	
	public static HashMap<Player, Inventory> availablePrimaryGuns = new HashMap<Player, Inventory>();
	
	public static void saveAGP(final Player p) {
		if (availablePrimaryGuns.get(p) != null) {
			for (int i = 0 ; i < availablePrimaryGuns.get(p).getSize() ; i++) {
				if (availablePrimaryGuns.get(p).getItem(i) == null) continue;
				ItemStack item = availablePrimaryGuns.get(p).getItem(i);
				if (item.equals(ItemsAndInventories.backAG)) {
					availablePrimaryGuns.get(p).setItem(i, null);
				}else{
					continue;
				}
			}
			
			if (!(MySQL.mySQLenabled())) {
				AvailableGunsFile.getData().set("AvailablePrimaryGuns." + p.getUniqueId(), SaveAndLoad.toString(availablePrimaryGuns.get(p)));
				AvailableGunsFile.saveData();
				AvailableGunsFile.reloadData();
			}else{
				String INSERT = "INSERT INTO CODAPG VALUES(?, ?) ON DUPLICATE KEY UPDATE list=?";
				try {
					Connection conn = MySQL.getConnection();
					PreparedStatement ps = conn.prepareStatement(INSERT);

					ps.setString(1, p.getUniqueId().toString());
					
					Clob clob = conn.createClob();
					ArrayList<String> list = SaveAndLoad.toString(availablePrimaryGuns.get(p));
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
			
			availablePrimaryGuns.get(p).setItem(49, ItemsAndInventories.backAG);
		}else{
			return;
		}
	}
	
	public static Inventory getAGP(Player p) {
			if (availablePrimaryGuns.get(p) != null) return availablePrimaryGuns.get(p);
			else return loadAGP(p);
	}
	
	public static Inventory loadAGP(Player p) {
		if (!(MySQL.mySQLenabled())) {
			if (AvailableGunsFile.getData().getString("AvailablePrimaryGuns." + p.getUniqueId()) != null && !AvailableGunsFile.getData().getString("AvailablePrimaryGuns." + p.getUniqueId()).isEmpty()) {
				Inventory inv = Bukkit.getServer().createInventory(p, 54, "Available Primary Guns");
				availablePrimaryGuns.put(p, SaveAndLoad.fromString(inv, AvailableGunsFile.getData().getList("AvailablePrimaryGuns." + p.getUniqueId())));
				availablePrimaryGuns.get(p).setItem(49, ItemsAndInventories.backAG);
				return availablePrimaryGuns.get(p);
			}else{
				Inventory inv = Bukkit.getServer().createInventory(p, 54, "Available Primary Guns");
				availablePrimaryGuns.put(p, inv);
				return inv;
			}
		}else{
			try {
				Connection conn = MySQL.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT uuid,list FROM CODAPG");
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
					Inventory inv = Bukkit.getServer().createInventory(p, 54, "Available Primary Guns");
					
					conn = MySQL.getConnection();
					ps = conn.prepareStatement("SELECT uuid,list FROM CODAPG");
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
					
					availablePrimaryGuns.put(p, inv);
					availablePrimaryGuns.get(p).setItem(49, ItemsAndInventories.backAG);
					
					conn.close();
					ps.close();
					result.close();
					
					return inv;
				}else{
					Inventory inv = Bukkit.getServer().createInventory(p, 54, "Available Primary Guns");
					availablePrimaryGuns.put(p, inv);
					return inv;
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
}