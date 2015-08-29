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

public class ShopInventoryPrimary {
	
	public static HashMap<Player, Inventory> Pshop = new HashMap<Player, Inventory>();
	
	public static void savePrimaryShop(final Player p) {
		if (Pshop.get(p) != null) {
			for (int i = 0 ; i < Pshop.get(p).getSize() ; i++) {
				ItemStack item = Pshop.get(p).getItem(i);
				if (item == null) continue;
				if (item.equals(ItemsAndInventories.backShop)) {
					Pshop.get(p).setItem(i, null);
				}
			}
				
		
		if (!(MySQL.mySQLenabled())) {
			ShopFile.getData().set("PrimaryShops." + p.getUniqueId(), SaveAndLoad.toString(Pshop.get(p)));
			ShopFile.saveData();
			ShopFile.reloadData();
		}else{
			String INSERT = "INSERT INTO CODPrimaryShop VALUES(?, ?) ON DUPLICATE KEY UPDATE list=?";
			try {
				Connection conn = MySQL.getConnection();
				PreparedStatement ps = conn.prepareStatement(INSERT);
				
				ps.setString(1, p.getUniqueId().toString());
				
				Clob clob = conn.createClob();
				ArrayList<String> list = SaveAndLoad.toString(Pshop.get(p));
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
				
			Pshop.get(p).setItem(49, ItemsAndInventories.backShop);
		}else{
			return;
		}
	}
	
	public static Inventory getPrimaryShop(Player p) {
			if (Pshop.get(p) != null) return Pshop.get(p);
			else return loadPrimaryShop(p);
	}
	
	public static Inventory loadPrimaryShop(Player p) {
		if (!(MySQL.mySQLenabled())) {
			if (ShopFile.getData().getString("PrimaryShops." + p.getUniqueId()) != null && !ShopFile.getData().getString("PrimaryShops." + p.getUniqueId()).isEmpty()) {
				Inventory inv = Bukkit.getServer().createInventory(p, 54, "Buy Primary Guns");
				inv = SaveAndLoad.fromString(inv, ShopFile.getData().getList("PrimaryShops." + p.getUniqueId()));
				
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
				
				Pshop.put(p, inv);
				Pshop.get(p).setItem(49, ItemsAndInventories.backShop);
				
				return inv;
			} else {
				Inventory inv = Bukkit.getServer().createInventory(p, 54, "Buy Primary Guns");
				Pshop.put(p, inv);
				Pshop.get(p).setItem(49, ItemsAndInventories.backShop);
				
				return inv;
			}
		}else{
			try {
				Connection conn = MySQL.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT uuid,list FROM CODPrimaryShop");
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
					Inventory inv = Bukkit.getServer().createInventory(p, 54, "Buy Primary Guns");
					
					conn = MySQL.getConnection();
					ps = conn.prepareStatement("SELECT uuid,list FROM CODPrimaryShop");
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
					
					Pshop.put(p, inv);
					Pshop.get(p).setItem(49, ItemsAndInventories.backShop);
					
					conn.close();
					ps.close();
					result.close();
					
					return inv;
				}else{
					Inventory inv = Bukkit.getServer().createInventory(p, 54, "Buy Primary Guns");
					Pshop.put(p, inv);
					Pshop.get(p).setItem(49, ItemsAndInventories.backShop);
					
					return inv;
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
}