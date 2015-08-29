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

import com.CentrumGuy.CodWarfare.Files.KitFile;
import com.CentrumGuy.CodWarfare.MySQL.MySQL;

public class KitInventory {
	
	public static HashMap<Player, Inventory> kit = new HashMap<Player, Inventory>();
	
	public static void saveKit(final Player p) {
		if (kit.get(p) != null) {
			ItemStack five = kit.get(p).getItem(5);
			ItemStack six = kit.get(p).getItem(6);
			
			kit.get(p).setItem(5, null);
			kit.get(p).setItem(6, null);
			
			if (!(MySQL.mySQLenabled())) {
				KitFile.getData().set("Kit." + p.getUniqueId(), SaveAndLoad.toString(kit.get(p)));
				KitFile.saveData();
				KitFile.reloadData();
			}else{
				String INSERT = "INSERT INTO CODKits VALUES(?, ?) ON DUPLICATE KEY UPDATE list=?";
				try {
					Connection conn = MySQL.getConnection();
					PreparedStatement ps = conn.prepareStatement(INSERT);
					
					ps.setString(1, p.getUniqueId().toString());
					
					Clob clob = conn.createClob();
					ArrayList<String> list = SaveAndLoad.toString(kit.get(p));
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
			
			kit.get(p).setItem(5, five);
			kit.get(p).setItem(6, six);
		}else{
			return;
		}
	}
	
	public static Inventory getKit(Player p) {
			if (kit.get(p) != null) return kit.get(p);
			else return loadKit(p);
	}
	
	public static Inventory loadKit(Player p) {
		if (!(MySQL.mySQLenabled())) {
			if (KitFile.getData().getString("Kit." + p.getUniqueId()) != null && !KitFile.getData().getString("Kit." + p.getUniqueId()).isEmpty()) {
				Inventory inv = Bukkit.getServer().createInventory(p, 27, "Loadout");
				kit.put(p, SaveAndLoad.fromString(inv, KitFile.getData().getList("Kit." + p.getUniqueId())));
				return kit.get(p);
			}else{
				Inventory inv = Bukkit.getServer().createInventory(p, 27, "Loadout");
				kit.put(p, inv);
				return inv;
			}
		}else{
			try {
				Connection conn = MySQL.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT uuid,list FROM CODKits");
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
				
				if ((UUIDexists) && (!(isEmpty))) {
					Inventory inv = Bukkit.getServer().createInventory(p, 27, "Loadout");
					
					conn = MySQL.getConnection();
					ps = conn.prepareStatement("SELECT uuid,list FROM CODKits");
					result = ps.executeQuery();
					
					String stringList = "";
					
					while(result.next()) {
						String s = result.getString("uuid");
						if (s.equals(p.getUniqueId().toString())) {
							stringList = MySQL.clobToString(result.getClob("list"));
							break;
						}
					}
					
					ArrayList<String> kits = (ArrayList<String>) MySQL.stringToList(stringList);
					inv = SaveAndLoad.fromString(inv, kits);
					
					kit.put(p, inv);
					
					conn.close();
					ps.close();
					result.close();
					
					return inv;
				}else{
					Inventory inv = Bukkit.getServer().createInventory(p, 27, "Loadout");
					kit.put(p, inv);
					return inv;
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
}