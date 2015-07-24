package com.CentrumGuy.CodWarfare.OtherLoadout;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.CentrumGuy.CodWarfare.Files.WeaponsFile;
import com.CentrumGuy.CodWarfare.Interface.ItemsAndInventories;
import com.CentrumGuy.CodWarfare.Utilities.GetNormalName;

public class Lethal {
	public static ArrayList<ItemStack> lethals = new ArrayList<ItemStack>();
	public static HashMap<ItemStack, Integer> cost = new HashMap<ItemStack, Integer>();

	@SuppressWarnings("deprecation")
	public static void createLethal(Integer id, Byte DataValue, Integer Amount, String Name, Integer Cost) {
		ItemStack lethal = new ItemStack(id, Amount, DataValue);
		ItemMeta tacMeta = lethal.getItemMeta();
		tacMeta.setDisplayName("§e" + GetNormalName.get(Name));
		lethal.setItemMeta(tacMeta);
		
		int nextInt = 0;
		while (getData().get("Lethals." + nextInt) != null) {
			nextInt++;
		}
		
		getData().set("Lethals." + nextInt + ".ItemID", id);
		getData().set("Lethals." + nextInt + ".DataValue", DataValue);
		getData().set("Lethals." + nextInt + ".Amount", Amount);
		getData().set("Lethals." + nextInt + ".Name", tacMeta.getDisplayName());
		getData().set("Lethals." + nextInt + ".Cost", Cost);
		
		WeaponsFile.saveData();
		WeaponsFile.reloadData();
		
		lethals.add(lethal);
		cost.put(lethal, Cost);
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			ItemsAndInventories.setWeaponInvs(p, ItemsAndInventories.WeaponType.LETHAL);
		}
	}
	
	public static Integer getCost(ItemStack lethal) {
		return cost.get(lethal);
	}
	
	private static FileConfiguration getData() {
		return WeaponsFile.getData();
	}
	
	@SuppressWarnings("deprecation")
	public static void loadLethals() {
		lethals.clear();
		cost.clear();
		
		int nextInt = 0;
		while (getData().get("Lethals." + nextInt) != null) {
			ItemStack lethal = new ItemStack(getData().getInt("Lethals." + nextInt + ".ItemID"), getData().getInt("Lethals." + nextInt + ".Amount"), (byte) getData().getInt("Lethals." + nextInt + ".DataValue"));
			ItemMeta tacMeta = lethal.getItemMeta();
			tacMeta.setDisplayName(getData().getString("Lethals." + nextInt + ".Name"));
			lethal.setItemMeta(tacMeta);
			
			lethals.add(lethal);
			cost.put(lethal, getData().getInt("Lethals." + nextInt + ".Cost"));
			nextInt++;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void saveLethals() {
		getData().set("Lethals", null);
		for (int i = 0 ; i < lethals.size() ; i++) {
			ItemStack tac = lethals.get(i);
			if (!tac.hasItemMeta()) continue;
			if (!tac.getItemMeta().hasDisplayName()) continue;
			
			getData().set("Lethals." + i + ".ItemID", tac.getTypeId());
			getData().set("Lethals." + i + ".DataValue", tac.getData().getData());
			getData().set("Lethals." + i + ".Amount", tac.getAmount());
			getData().set("Lethals." + i + ".Name", tac.getItemMeta().getDisplayName());
			getData().set("Lethals." + i + ".Cost", getCost(tac));
		}
		
		WeaponsFile.saveData();
		WeaponsFile.reloadData();
	}
}
