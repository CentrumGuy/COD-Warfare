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

public class Tactical {
	public static ArrayList<ItemStack> tacticals = new ArrayList<ItemStack>();
	public static HashMap<ItemStack, Integer> cost = new HashMap<ItemStack, Integer>();

	@SuppressWarnings("deprecation")
	public static void createTactical(Integer id, Byte DataValue, Integer Amount, String Name, Integer Cost) {
		ItemStack tactical = new ItemStack(id, Amount, DataValue);
		ItemMeta tacMeta = tactical.getItemMeta();
		tacMeta.setDisplayName("§e" + GetNormalName.get(Name));
		tactical.setItemMeta(tacMeta);
		
		int nextInt = 0;
		while (getData().get("Tacticals." + nextInt) != null) {
			nextInt++;
		}
		
		getData().set("Tacticals." + nextInt + ".ItemID", id);
		getData().set("Tacticals." + nextInt + ".DataValue", DataValue);
		getData().set("Tacticals." + nextInt + ".Amount", Amount);
		getData().set("Tacticals." + nextInt + ".Name", tacMeta.getDisplayName());
		getData().set("Tacticals." + nextInt + ".Cost", Cost);
		
		WeaponsFile.saveData();
		WeaponsFile.reloadData();
		
		tacticals.add(tactical);
		cost.put(tactical, Cost);
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			ItemsAndInventories.setWeaponInvs(p, ItemsAndInventories.WeaponType.TACTICAL);
		}
	}
	
	public static Integer getCost(ItemStack tactical) {
		return cost.get(tactical);
	}
	
	private static FileConfiguration getData() {
		return WeaponsFile.getData();
	}
	
	@SuppressWarnings("deprecation")
	public static void loadTacticals() {
		tacticals.clear();
		cost.clear();
		
		int nextInt = 0;
		while (getData().get("Tacticals." + nextInt) != null) {
			ItemStack tactical = new ItemStack(getData().getInt("Tacticals." + nextInt + ".ItemID"), getData().getInt("Tacticals." + nextInt + ".Amount"), (byte) getData().getInt("Tacticals." + nextInt + ".DataValue"));
			ItemMeta tacMeta = tactical.getItemMeta();
			tacMeta.setDisplayName(getData().getString("Tacticals." + nextInt + ".Name"));
			tactical.setItemMeta(tacMeta);
			
			tacticals.add(tactical);
			cost.put(tactical, getData().getInt("Tacticals." + nextInt + ".Cost"));
			nextInt++;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void saveTacticals() {
		getData().set("Tacticals", null);
		for (int i = 0 ; i < tacticals.size() ; i++) {
			ItemStack tac = tacticals.get(i);
			if (!tac.hasItemMeta()) continue;
			if (!tac.getItemMeta().hasDisplayName()) continue;
			
			getData().set("Tacticals." + i + ".ItemID", tac.getTypeId());
			getData().set("Tacticals." + i + ".DataValue", tac.getData().getData());
			getData().set("Tacticals." + i + ".Amount", tac.getAmount());
			getData().set("Tacticals." + i + ".Name", tac.getItemMeta().getDisplayName());
			getData().set("Tacticals." + i + ".Cost", getCost(tac));
		}
		
		WeaponsFile.saveData();
		WeaponsFile.reloadData();
	}
}
