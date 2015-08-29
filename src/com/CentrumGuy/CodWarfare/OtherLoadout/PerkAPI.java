package com.CentrumGuy.CodWarfare.OtherLoadout;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Files.PerksFile;
import com.CentrumGuy.CodWarfare.Interface.ItemsAndInventories;
import com.CentrumGuy.CodWarfare.MySQL.MySQL;
import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;
import com.CentrumGuy.CodWarfare.Utilities.Items;

public class PerkAPI {
	public static HashMap<Player, Perk> perk = new HashMap<Player, Perk>();
	public static HashMap<Player, List<Perk>> ownedPerks = new HashMap<Player, List<Perk>>();
	
	/* Speed: Bread
	 * Marathon: Carrot
	 * Scavenger: Apple
	 * Hardline: Egg
	 * Down Arrow: Water Bucket
	 */
	
	public static ItemStack downArrow = Items.createItem(Material.WATER_BUCKET, 1, 0, "§6§lCurrent Perk");
	public static ItemStack noPerk = Items.createItem(Material.EMERALD, 1, 0, "§4§lNo Perk");
	
	public static ItemStack ownsSpeed = Items.createItem(Material.BREAD, 1, 0, "§e§l§nSpeed", new ArrayList<String>(Arrays.asList("", "§f§lCosts: §2§l" + getCost(Perk.SPEED) + " Credits", "§f§lPurchased: §a§lTrue", "§7When using this perk, you", "§7will have an extra speed", "§7boost during the game")));
	public static ItemStack ownsMarathon = Items.createItem(Material.CARROT_ITEM, 1, 0, "§e§l§nMarathon", new ArrayList<String>(Arrays.asList("", "§f§lCosts: §2§l" + getCost(Perk.MARATHON) + " Credits", "§f§lPurchased: §a§lTrue", "§7When using this perk, you", "§7will have unlimited hunger", "§7meaning you can run forever")));
	public static ItemStack ownsScavenger = Items.createItem(Material.APPLE, 1, 0, "§e§l§nScavenger", new ArrayList<String>(Arrays.asList("", "§f§lCosts: §2§l" + getCost(Perk.SCAVENGER) + " Credits", "§f§lPurchased: §a§lTrue", "§7When using this perk, you", "§7will receive 25% of your ammo", "§7back after you kill someone")));
	public static ItemStack ownsHardline = Items.createItem(Material.EGG, 1, 0, "§e§l§nHardline", new ArrayList<String>(Arrays.asList("", "§f§lCosts: §2§l" + getCost(Perk.HARDLINE) + " Credits", "§f§lPurchased: §a§lTrue", "§7When using this perk, you", "§7will get killstreak powerups", "§7by having one less kill than", "§7the usual amount. This means", "§7you will only have to get 4", "§7kills to get extra ammo")));
	
	public static ItemStack Speed = Items.createItem(Material.BREAD, 1, 0, "§e§l§nSpeed", new ArrayList<String>(Arrays.asList("", "§f§lCosts: §4§l" + getCost(Perk.SPEED) + " Credits", "§f§lPurchased: §c§lFalse", "§5» Click to purchase", "§7When using this perk, you", "§7will have an extra speed", "§7boost during the game")));
	public static ItemStack Marathon = Items.createItem(Material.CARROT_ITEM, 1, 0, "§e§l§nMarathon", new ArrayList<String>(Arrays.asList("", "§f§lCosts: §4§l" + getCost(Perk.MARATHON) + " Credits", "§f§lPurchased: §c§lFalse", "§5» Click to purchase", "§7When using this perk, you", "§7will have unlimited hunger", "§7meaning you can run forever")));
	public static ItemStack Scavenger = Items.createItem(Material.APPLE, 1, 0, "§e§l§nScavenger", new ArrayList<String>(Arrays.asList("", "§f§lCosts: §4§l" + getCost(Perk.SCAVENGER) + " Credits", "§f§lPurchased: §c§lFalse", "§5» Click to purchase", "§7When using this perk, you", "§7will receive 25% of your ammo", "§7back after you kill someone")));
	public static ItemStack Hardline = Items.createItem(Material.EGG, 1, 0, "§e§l§nHardline", new ArrayList<String>(Arrays.asList("", "§f§lCosts: §4§l" + getCost(Perk.HARDLINE) + " Credits", "§f§lPurchased: §c§lFalse", "§5» Click to purchase", "§7When using this perk, you", "§7will get killstreak powerups", "§7by having one less kill than", "§7the usual amount. This means", "§7you will only have to get 4", "§7kills to get extra ammo")));
	
	public static void setPerk(Player p, Perk name) {
		if (ownsPerk(p, name)) {
			perk.put(p, name);
			setPerkArrow(p);
			
			savePerk(p);
			
			if (name == Perk.SPEED) {
				ItemsAndInventories.ClassSelection.get(p).setItem(35, Items.createItem(Speed.getType(), 1, 0, "§a§lSpeed", new ArrayList<String>(Arrays.asList("§2Click to change"))));
			}else if (name == Perk.MARATHON) {
				ItemsAndInventories.ClassSelection.get(p).setItem(35, Items.createItem(Marathon.getType(), 1, 0, "§a§lMarathon", new ArrayList<String>(Arrays.asList("§2Click to change"))));
			}else if (name == Perk.SCAVENGER) {
				ItemsAndInventories.ClassSelection.get(p).setItem(35, Items.createItem(Scavenger.getType(), 1, 0, "§a§lScavenger", new ArrayList<String>(Arrays.asList("§2Click to change"))));
			}else if (name == Perk.HARDLINE) {
				ItemsAndInventories.ClassSelection.get(p).setItem(35, Items.createItem(Hardline.getType(), 1, 0, "§a§lHardline", new ArrayList<String>(Arrays.asList("§2Click to change"))));
			}else if (name == Perk.NO_PERK) {
				ItemsAndInventories.ClassSelection.get(p).setItem(35, ItemsAndInventories.none);
			}
		}
	}
	
	public static Perk getPerk(Player p) {
		if (perk.get(p) == null) setPerk(p, Perk.NO_PERK);
		return perk.get(p);
	}
	
	public static void savePerk(Player p) {
		if (!(MySQL.mySQLenabled())) {
			PerksFile.getData().set("Perks.CurrentlyUsing." + p.getUniqueId(), "" + perk.get(p));
			PerksFile.saveData();
			PerksFile.reloadData();
		}else{
			try {
				Connection conn = MySQL.getConnection();
				String INSERT = "INSERT INTO CODPerks (uuid, currentperk) VALUES(?, ?) ON DUPLICATE KEY UPDATE currentperk=?";
				PreparedStatement ps = conn.prepareStatement(INSERT);
				ps.setString(1, p.getUniqueId().toString());
				ps.setString(2, "" + perk.get(p));
				ps.setString(3, "" + perk.get(p));
				
				ps.executeUpdate();
				
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void loadPerk(Player p) {
		if (!(MySQL.mySQLenabled())) {
			String name = PerksFile.getData().getString("Perks.CurrentlyUsing." + p.getUniqueId());
			
			if (name != null) {
				perk.put(p, getPerk(name));
			}else{
				perk.put(p, Perk.NO_PERK);
			}
		}else{
			try {
				Connection conn = MySQL.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT uuid,currentperk FROM CODPerks");
				ResultSet result = ps.executeQuery();
				
				while (result.next()) {
					if (result.getString("uuid").equals(p.getUniqueId().toString())) {
						if ((result.getString("currentperk") == null) || (result.getString("currentperk").equals(""))) {
							perk.put(p, Perk.NO_PERK);
						}else{
							perk.put(p, getPerk(result.getString("currentperk")));
						}
						
						break;
					}
				}
				
				result.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Perk getPerk(String name) {
		if (Perk.valueOf(name) != null) {
			return Perk.valueOf(name);
		}else{
			return Perk.NO_PERK;
		}
	}
	
	public static void loadOwnedPerks(Player p) {
		if (!(MySQL.mySQLenabled())) {
			List<String> perkNames = PerksFile.getData().getStringList("Perks.OwnedPerks." + p.getUniqueId());
			ownedPerks.put(p, new ArrayList<Perk>());
			
			for (int i = 0 ; i < perkNames.size() ; i++) {
				String name = perkNames.get(i);
				if (getPerk(name) == Perk.NO_PERK) continue;
				
				ownedPerks.get(p).add(getPerk(name));
			}
		}else{
			try {
				Connection conn = MySQL.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT uuid,list FROM CODPerks");
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					if (rs.getString("uuid").equals(p.getUniqueId().toString())) {
						ArrayList<String> perkNames = MySQL.stringToList(MySQL.clobToString(rs.getClob("list")));
						if ((perkNames == null) || (perkNames.isEmpty())) {
							ownedPerks.put(p, new ArrayList<Perk>());
						}else{
							ownedPerks.put(p, new ArrayList<Perk>());
							
							for (int i = 0 ; i < perkNames.size() ; i++) {
								String name = perkNames.get(i);
								if (getPerk(name) == Perk.NO_PERK) continue;
								
								ownedPerks.get(p).add(getPerk(name));
							}
						}
						
						break;
					}
				}
				
				if (ownedPerks.get(p) == null) {
					ownedPerks.put(p, new ArrayList<Perk>());
				}
				
				rs.close();
				ps.close();
				conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void saveOwnedPerks(Player p) {	
		if (!(MySQL.mySQLenabled())) {
			List<String> perkNames = new ArrayList<String>();
			for (Perk perk : ownedPerks.get(p)) {
				perkNames.add("" + perk);
			}
			
			PerksFile.getData().set("Perks.OwnedPerks." + p.getUniqueId(), perkNames);
			PerksFile.saveData();
			PerksFile.reloadData();
		}else{
			try {
				Connection conn = MySQL.getConnection();
				String INSERT = "INSERT INTO CODPerks (uuid, list) VALUES(?, ?) ON DUPLICATE KEY UPDATE list=?";
				PreparedStatement ps = conn.prepareStatement(INSERT);
				ps.setString(1, p.getUniqueId().toString());
				
				ArrayList<String> perkNames = new ArrayList<String>();
				for (Perk perk : ownedPerks.get(p)) {
					perkNames.add("" + perk);
				}
				
				Clob clob = MySQL.stringToClob(MySQL.listToString(perkNames), conn);
				ps.setClob(2, clob);
				ps.setClob(3, clob);
				
				ps.executeUpdate();
				
				ps.close();
				conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean ownsPerk(Player p, Perk name) {
		if (name == Perk.NO_PERK) return true;
		loadOwnedPerks(p);
		if (ownedPerks.get(p).contains(name)) return true;
		return false;
	}
	
	public static boolean isUsingPerk(Player p) {
		if (perk.get(p) != null && perk.get(p) != Perk.NO_PERK) return true;
		return false;
	}
	
	public static int getCost(Perk perk) {
		if (ThisPlugin.getPlugin().getConfig().get("Perks.Cost." + perk) != null) return ThisPlugin.getPlugin().getConfig().getInt("Perks.Cost." + perk);
		return 0;
	}
	
	@SuppressWarnings("deprecation")
	public static void setPerkArrow(Player p) {
		Inventory inv = ItemsAndInventories.perks.get(p);
		
		inv.setItem(9, new ItemStack(Material.AIR));
		inv.setItem(11, new ItemStack(Material.AIR));
		inv.setItem(13, new ItemStack(Material.AIR));
		inv.setItem(15, new ItemStack(Material.AIR));
		inv.setItem(17, new ItemStack(Material.AIR));
		
		if (PerkAPI.isUsingPerk(p)) {
			if (PerkAPI.getPerk(p) == Perk.SPEED) inv.setItem(9, downArrow);
			if (PerkAPI.getPerk(p) == Perk.MARATHON) inv.setItem(11, downArrow);
			if (PerkAPI.getPerk(p) == Perk.SCAVENGER) inv.setItem(15, downArrow);
			if (PerkAPI.getPerk(p) == Perk.HARDLINE) inv.setItem(17, downArrow);
		}else{
			inv.setItem(13, downArrow);
		}
		
		p.updateInventory();
	}
	
	public static void unlockPerk(Player p, Perk name) {
		List<Perk> perks = ownedPerks.get(p);
		Inventory inv = ItemsAndInventories.perks.get(p);
		
		if (name == Perk.SPEED) {
			if (!(perks.contains(Perk.SPEED))) {
				perks.add(Perk.SPEED);
				inv.setItem(18, ownsSpeed);
				saveOwnedPerks(p);
				p.sendMessage(Main.codSignature + "§aSuccessfully purchased §2Speed Perk");
				
				if (ownsPerk(p, Perk.SPEED)) {
					setPerk(p, name);
				}
			}
		}else if (name == Perk.MARATHON) {
			if (!(perks.contains(Perk.MARATHON))) {
				perks.add(Perk.MARATHON);
				inv.setItem(20, ownsMarathon);
				saveOwnedPerks(p);
				p.sendMessage(Main.codSignature + "§aSuccessfully purchased §2Marathon Perk");
				
				if (ownsPerk(p, Perk.MARATHON)) {
					setPerk(p, name);
				}
			}
		}else if (name == Perk.SCAVENGER) {
			if (!(perks.contains(Perk.SCAVENGER))) {
				perks.add(Perk.SCAVENGER);
				inv.setItem(24, ownsScavenger);
				saveOwnedPerks(p);
				p.sendMessage(Main.codSignature + "§aSuccessfully purchased §2Scavenger Perk");
				
				if (ownsPerk(p, Perk.SCAVENGER)) {
					setPerk(p, name);
				}
			}
		}else if (name == Perk.HARDLINE) {
			if (!(perks.contains(Perk.HARDLINE))) {
				perks.add(Perk.HARDLINE);
				inv.setItem(26, ownsHardline);
				saveOwnedPerks(p);
				p.sendMessage(Main.codSignature + "§aSuccessfully purchased §2Hardline Perk");
				
				if (ownsPerk(p, Perk.HARDLINE)) {
					setPerk(p, name);
				}
			}
		}
	}
	
	//Events:
	public static void onStartOfMatch() {
		for (Player pp : Main.PlayingPlayers) {
			if (getPerk(pp) == Perk.SPEED) {
				pp.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 180000, 1));
			}
		}
	}
	
	public static void onRespawn(Player p) {
		if (getPerk(p) == Perk.SPEED) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 180000, 1));
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void onPlayerKillPlayer(Player killer) {
		if (getPerk(killer) == Perk.SCAVENGER) {
			if (Main.PlayingPlayers.contains(killer)) {
				if (killer.getInventory().getItem(19) != null) {
					ItemStack ammoPrimary = killer.getInventory().getItem(19);
					int Primary25 = ammoPrimary.getAmount() / 4;
					if (Primary25 <= 0) return;
					killer.getInventory().addItem(Items.createItem(ammoPrimary.getType(), Primary25, ammoPrimary.getData().getData(), ammoPrimary.getItemMeta().getDisplayName()));
				}
				
				if (killer.getInventory().getItem(25) != null) {
					ItemStack ammoSecondary = killer.getInventory().getItem(25);
					int Secondary25 = ammoSecondary.getAmount() / 4;
					if (Secondary25 <= 0) return;
					killer.getInventory().addItem(Items.createItem(ammoSecondary.getType(), Secondary25, ammoSecondary.getData().getData(), ammoSecondary.getItemMeta().getDisplayName()));
				}
				
				if (killer.getInventory().getItem(8) != null) {
					ItemStack ammoFFA = killer.getInventory().getItem(8);
					int FFA25 = ammoFFA.getAmount() / 4;
					if (FFA25 <= 0) return;
					killer.getInventory().addItem(Items.createItem(ammoFFA.getType(), FFA25, ammoFFA.getData().getData(), ammoFFA.getItemMeta().getDisplayName()));
				}
			}
		}
	}
}
