package com.CentrumGuy.CodWarfare.Inventories;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Files.GunsFile;
import com.CentrumGuy.CodWarfare.Interface.ItemsAndInventories;
import com.CentrumGuy.CodWarfare.Utilities.GetNormalName;

public class Guns {
	
	public static ArrayList<ItemStack> guns = new ArrayList<ItemStack>();
	public static HashMap<ItemStack, ItemStack> ammo = new HashMap<ItemStack, ItemStack>();
	public static HashMap<ItemStack, Integer> cost = new HashMap<ItemStack, Integer>();
	public static HashMap<ItemStack, Integer> level = new HashMap<ItemStack, Integer>();
	public static HashMap<ItemStack, String> type = new HashMap<ItemStack, String>();
	public static Inventory tryGunsInventory = Bukkit.getServer().createInventory(null, 54, "Try Guns");
	public static Inventory tryPrimary = Bukkit.getServer().createInventory(null, 54, "Primary Guns");
	public static Inventory trySecondary = Bukkit.getServer().createInventory(null, 54, "Secondary Guns");
	public static ItemStack backTry = new ItemStack(Material.LAVA_BUCKET);
	public static ItemStack tryPrimaryItem = new ItemStack(Material.WOOD_HOE);
	public static ItemStack trySecondaryItem = new ItemStack(Material.WOOD_SPADE);
	public static ItemStack clearGun = new ItemStack(Material.EMERALD);

	@SuppressWarnings("deprecation")
	public static void loadGuns() {
		int nextNum = 0;
		while(!(GunsFile.getData().get("Guns." + nextNum) == null)) {
			nextNum++;
		}
		
		for (int i = 0 ; i < tryPrimary.getSize() ; i++) {
			tryPrimary.setItem(i, null);
		}
		
		for (int i = 0 ; i < trySecondary.getSize() ; i++) {
			tryPrimary.setItem(i, null);
		}
		
		ItemMeta pMeta = tryPrimaryItem.getItemMeta();
		ArrayList<String> pLore = new ArrayList<String>();
		pMeta.setDisplayName("§6§lTry Primary Guns");
		pLore.add("§7Click this item to");
		pLore.add("§7try primary guns");
		pMeta.setLore(pLore);
		tryPrimaryItem.setItemMeta(pMeta);
		
		ItemMeta sMeta = trySecondaryItem.getItemMeta();
		ArrayList<String> sLore = new ArrayList<String>();
		sMeta.setDisplayName("§6§lTry Secondary Guns");
		sLore.add("§7Click this item to");
		sLore.add("§7try secondary guns");
		sMeta.setLore(sLore);
		trySecondaryItem.setItemMeta(sMeta);
		
		ItemMeta backMeta = backTry.getItemMeta();
		ArrayList<String> backLore = new ArrayList<String>();
		backMeta.setDisplayName("§7§l« Back");
		backLore.add("§7Back to trying guns");
		backMeta.setLore(backLore);
		backTry.setItemMeta(backMeta);
		
		ItemMeta clearMeta = clearGun.getItemMeta();
		ArrayList<String> clearLore = new ArrayList<String>();
		clearMeta.setDisplayName("§c§lClear Gun");
		clearLore.add("§eClick to remove your current");
		clearLore.add("§etest gun");
		clearMeta.setLore(clearLore);
		clearGun.setItemMeta(clearMeta);
		
		guns = null;
		ammo = null;
		cost = null;
		level = null;
		type = null;
		
		guns = new ArrayList<ItemStack>();
		ammo = new HashMap<ItemStack, ItemStack>();
		cost = new HashMap<ItemStack, Integer>();
		level = new HashMap<ItemStack, Integer>();
		type = new HashMap<ItemStack, String>();
		
		guns.clear();
		ammo.clear();
		cost.clear();
		level.clear();
		type.clear();
		
		for (int i = 0 ; i < nextNum ; i++) {
			ItemStack gun = new ItemStack(GunsFile.getData().getInt("Guns." + i + ".Gun.GunItemID"), 1, (byte) GunsFile.getData().getInt("Guns." + i + ".Gun.GunData"));
			gun = new ItemStack(GunsFile.getData().getInt("Guns." + i + ".Gun.GunItemID"), 1, (byte) GunsFile.getData().getInt("Guns." + i + ".Gun.GunData"));
			ItemMeta gunMeta = gun.getItemMeta();
			gunMeta.setDisplayName("§e" + GunsFile.getData().getString("Guns." + i + ".Gun.GunName"));
			gun.setItemMeta(gunMeta);
			
			ItemStack gunAmmo = new ItemStack(GunsFile.getData().getInt("Guns." + i + ".Ammo.AmmoItemID"), GunsFile.getData().getInt("Guns." + i + ".Ammo.Amount"), (byte) GunsFile.getData().getInt("Guns." + i + ".Ammo.AmmoData"));
			ItemMeta ammoMeta = gunAmmo.getItemMeta();
			ammoMeta.setDisplayName("§e" + GunsFile.getData().getString("Guns." + i + ".Ammo.AmmoName"));
			gunAmmo.setItemMeta(ammoMeta);
			
			int gunCost = GunsFile.getData().getInt("Guns." + i + ".Cost");
			int gunLevel = GunsFile.getData().getInt("Guns." + i + ".LevelUnlock");
			String gunType = GunsFile.getData().getString("Guns." + i + ".Type");
			
			guns.add(gun);
			ammo.put(gun, gunAmmo);
			cost.put(gun, gunCost);
			level.put(gun, gunLevel);
			type.put(gun, gunType);
		}
		
		for (int i = 0 ; i < guns.size() ; i++) {
			ItemStack gun = guns.get(i);
			
			ItemStack tryGun = new ItemStack(gun.getType(), 1, gun.getData().getData());
			ItemMeta tryMeta = tryGun.getItemMeta();
			tryMeta.setDisplayName(gun.getItemMeta().getDisplayName());
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("§7Test gun");
			if (getLevel(gun) != -1) {
				lore.add("§8- Unlocked at level:§3 " + getLevel(gun));
			}else{
				lore.add("§8- Cannot be unlocked with a level");
			}
			if (getCost(gun) != 0) {
				lore.add("§8- Costs§3 " + getCost(gun) + " §8credits");
			}else{
				lore.add("§8- This gun is §3Free");
			}
			tryMeta.setLore(lore);
			tryGun.setItemMeta(tryMeta);
			
			String gunType = getType(gun);
			
			if (gunType.equalsIgnoreCase("Primary")) {
				if (!(tryPrimary.contains(tryGun))) tryPrimary.addItem(tryGun);
			}else if (gunType.equalsIgnoreCase("Secondary")) {
				if (!(trySecondary.contains(tryGun))) trySecondary.addItem(tryGun);
			}else{
				continue;
			}
		}
		
		tryGunsInventory.setItem(19, tryPrimaryItem);
		tryGunsInventory.setItem(25, trySecondaryItem);
		
		tryGunsInventory.setItem(48, ItemsAndInventories.exit);
		tryGunsInventory.setItem(49, clearGun);
		tryGunsInventory.setItem(50, ItemsAndInventories.exit);
		
		
		tryPrimary.setItem(49, backTry);
		trySecondary.setItem(49, backTry);
	}
	
	@SuppressWarnings("deprecation")
	public static void getTryGun(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player)) return;
		if (e.getCurrentItem() == null) return;
		ItemStack item = e.getCurrentItem();
		Player p = (Player) e.getWhoClicked();
		
		if (Main.WaitingPlayers.contains(p)) {
			
			if (item.equals(ItemsAndInventories.exit)) {
				e.setCancelled(true);
				p.closeInventory();
				p.sendMessage(Main.codSignature + "§dYou closed your inventory");
				return;
			}
			
			if (item.equals(clearGun)) {
				e.setCancelled(true);
				p.getInventory().setItem(1, new ItemStack(Material.AIR));
				p.getInventory().setItem(2, new ItemStack(Material.AIR));
				p.closeInventory();
				p.sendMessage(Main.codSignature + "§cRemoved guns");
				return;
			}
			
			if (item.equals(tryPrimaryItem)) {
				e.setCancelled(true);
				p.openInventory(tryPrimary);
				return;
			}
			
			if (item.equals(trySecondaryItem)) {
				e.setCancelled(true);
				p.openInventory(trySecondary);
				return;
			}
			
			if (item.equals(backTry)) {
				e.setCancelled(true);
				p.openInventory(tryGunsInventory);
				return;
			}
			
			ItemStack gun = item;
			
			if (!(gun.hasItemMeta())) return;
			if (!(gun.getItemMeta().hasDisplayName())) return;
			if (!(gun.getItemMeta().hasLore())) return;
			
			ItemStack real = new ItemStack(gun.getType(), 1, gun.getData().getData());
			ItemMeta realMeta = real.getItemMeta();
			realMeta.setDisplayName(gun.getItemMeta().getDisplayName());
			real.setItemMeta(realMeta);
			
			ArrayList<String> lore = (ArrayList<String>) gun.getItemMeta().getLore();
			if (lore.get(0).equals("§7Test gun")) {
				e.setCancelled(true);
				
				if (!(Main.CrackShot)) {
					p.getInventory().setItem(1, real);
				}else{
					p.getInventory().setItem(1, Main.CrackShotAPI.generateWeapon(GetNormalName.get(real.getItemMeta().getDisplayName())));
				}
				
				p.getInventory().setItem(2, ammo.get(real));
				
				p.closeInventory();
				p.sendMessage(Main.codSignature + "§7You received the test gun:§e " + real.getItemMeta().getDisplayName());
			}else{
				return;
			}
		}else if (Main.PlayingPlayers.contains(p)) {
			e.setCancelled(true);
			p.sendMessage(Main.codSignature + "§cYou may not try guns while in the middle of a game");
		}else{
			e.setCancelled(true);
			p.sendMessage(Main.codSignature + "§cPlease join COD-Warfare first by doing §4/cod join");
		}
	}
	
	public static ItemStack getAmmo(ItemStack gun) {
		return ammo.get(gun);
	}
	
	public static Integer getCost(ItemStack gun) {
		return cost.get(gun);
	}
	
	public static Integer getLevel(ItemStack gun) {
		return level.get(gun);
	}
	
	public static String getType(ItemStack gun) {
		return type.get(gun);
	}
	
	@SuppressWarnings("deprecation")
	public static void saveGun(ItemStack Gun, Integer GunData, ItemStack Ammo, Integer AmmoData, Integer Cost, Integer Level, String Type) {
		int nextNum = 0;
		while(!(GunsFile.getData().get("Guns." + nextNum) == null)) {
			nextNum++;
		}
		
		GunsFile.getData().set("Guns." + nextNum + ".Gun.GunItemID", Gun.getTypeId());
		GunsFile.getData().set("Guns." + nextNum + ".Gun.GunData", GunData);
		GunsFile.getData().set("Guns." + nextNum + ".Gun.GunName", Gun.getItemMeta().getDisplayName());
		
		GunsFile.getData().set("Guns." + nextNum + ".Ammo.AmmoItemID", Ammo.getTypeId());
		GunsFile.getData().set("Guns." + nextNum + ".Ammo.AmmoData", AmmoData);
		GunsFile.getData().set("Guns." + nextNum + ".Ammo.Amount", Ammo.getAmount());
		GunsFile.getData().set("Guns." + nextNum + ".Ammo.AmmoName", Ammo.getItemMeta().getDisplayName());
		
		GunsFile.getData().set("Guns." + nextNum + ".Cost", Cost);
		GunsFile.getData().set("Guns." + nextNum + ".LevelUnlock", Level);
		GunsFile.getData().set("Guns." + nextNum + ".Type", Type);
		
		GunsFile.saveData();
		
		loadGuns();
		
	}
	
}
