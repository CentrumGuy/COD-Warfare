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
import org.bukkit.inventory.meta.ItemMeta;

import com.CentrumGuy.CodWarfare.Files.ShopFile;
import com.CentrumGuy.CodWarfare.Interface.ItemsAndInventories;
import com.CentrumGuy.CodWarfare.MySQL.MySQL;

public class ShopInventorySecondary {
	
	public static HashMap<Player, Inventory> Sshop = new HashMap<Player, Inventory>();
	
	public static void saveSecondaryShop(final Player p) {
		if (Sshop.get(p) != null) {
			for (int i = 0 ; i < Sshop.get(p).getSize() ; i++) {
				ItemStack item = Sshop.get(p).getItem(i);
				if (item == null) continue;
				if (item.equals(ItemsAndInventories.backShop)) {
					Sshop.get(p).setItem(i, null);
				}
			}
			
			if (!(MySQL.mySQLenabled())) {
				ShopFile.getData().set("SecondaryShops." + p.getUniqueId(), SaveAndLoad.toString(Sshop.get(p)));
				ShopFile.saveData();
				ShopFile.reloadData();
			}else{
				String INSERT = "INSERT INTO CODSecondaryShop VALUES(?, ?) ON DUPLICATE KEY UPDATE list=?";
				try {
					Connection conn = MySQL.getConnection();
					PreparedStatement ps = conn.prepareStatement(INSERT);
					
					ps.setString(1, p.getUniqueId().toString());
					
					Clob clob = conn.createClob();
					ArrayList<String> list = SaveAndLoad.toString(Sshop.get(p));
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
				
			Sshop.get(p).setItem(49, ItemsAndInventories.backShop);
		}else{
			return;
		}
	}
	
	public static Inventory getSecondaryShop(Player p) {
			if (Sshop.get(p) != null) return Sshop.get(p);
			else return loadSecondaryShop(p);
	}
	
	public static Inventory loadSecondaryShop(Player p) {
		if (!(MySQL.mySQLenabled())) {
			if (ShopFile.getData().getString("SecondaryShops." + p.getUniqueId()) != null && !ShopFile.getData().getString("SecondaryShops." + p.getUniqueId()).isEmpty()) {
				Inventory inv = Bukkit.getServer().createInventory(p, 54, "Buy Secondary Guns");
				inv = SaveAndLoad.fromString(inv, ShopFile.getData().getList("SecondaryShops." + p.getUniqueId()));
				
				for (int i = 0 ; i < inv.getSize() ; i++) {
					ItemStack item = inv.getItem(i);
					if (item == null) continue;
					if (item.equals(ItemsAndInventories.backShop)) continue;
					
					ArrayList<String> lore = new ArrayList<String>();
					ItemMeta im = item.getItemMeta();
					lore.add("§7This gun costs§6 " + Guns.getCost(item) + " §7credits");
					im.setLore(lore);
					item.setItemMeta(im);
					
					inv.setItem(i, item);
				}
				
				Sshop.put(p, inv);
				Sshop.get(p).setItem(49, ItemsAndInventories.backShop);
				
				return inv;
			} else {
				Inventory inv = Bukkit.getServer().createInventory(p, 54, "Buy Secondary Guns");
				Sshop.put(p, inv);
				Sshop.get(p).setItem(49, ItemsAndInventories.backShop);
				
				return inv;
			}
		}else{
			try {
				Connection conn = MySQL.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT uuid,list FROM CODSecondaryShop");
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
					Inventory inv = Bukkit.getServer().createInventory(p, 54, "Buy Secondary Guns");
					
					conn = MySQL.getConnection();
					ps = conn.prepareStatement("SELECT uuid,list FROM CODSecondaryShop");
					result = ps.executeQuery();
					
					String stringList = "";
					
					while(result.next()) {
						String s = result.getString("uuid");
						if (s.equals(p.getUniqueId().toString())) {
							stringList = MySQL.clobToString(result.getClob("list"));
							break;
						}
					}
					
					ArrayList<String> shop = (ArrayList<String>) MySQL.stringToList(stringList);
					
					inv = SaveAndLoad.fromString(inv, shop);
					
					for (int i = 0 ; i < inv.getSize() ; i++) {
						ItemStack item = inv.getItem(i);
						if (item == null) continue;
						if (item.equals(ItemsAndInventories.backShop)) continue;
						
						ArrayList<String> lore = new ArrayList<String>();
						ItemMeta im = item.getItemMeta();
						lore.add("§7This gun costs§6 " + Guns.getCost(item) + " §7credits");
						im.setLore(lore);
						item.setItemMeta(im);
						
						inv.setItem(i, item);
					}
					
					Sshop.put(p, inv);
					Sshop.get(p).setItem(49, ItemsAndInventories.backShop);
					
					conn.close();
					ps.close();
					result.close();
					
					return inv;
				}else{
					Inventory inv = Bukkit.getServer().createInventory(p, 54, "Buy Secondary Guns");
					Sshop.put(p, inv);
					Sshop.get(p).setItem(49, ItemsAndInventories.backShop);
					
					return inv;
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
}