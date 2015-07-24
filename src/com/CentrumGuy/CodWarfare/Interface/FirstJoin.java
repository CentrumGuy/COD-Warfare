package com.CentrumGuy.CodWarfare.Interface;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.CentrumGuy.CodWarfare.Files.JoinedCODFile;
import com.CentrumGuy.CodWarfare.Inventories.AGPInventory;
import com.CentrumGuy.CodWarfare.Inventories.AGSInventory;
import com.CentrumGuy.CodWarfare.Leveling.Level;

public class FirstJoin {

	public static void fJoin(Player p) {
		List<String> JoinedPlayers = JoinedCODFile.getData().getStringList("JoinedPlayers");
		if (JoinedPlayers.contains("" + p.getUniqueId())) return;
		
		Scores.loadScores(p);
		
		Level.resetLevel(p);
		
		if ((!(agpNull(p))) && (!(agsNull(p)))) {
			JoinedPlayers.add("" + p.getUniqueId());
			JoinedCODFile.getData().set("JoinedPlayers", JoinedPlayers);
			
			JoinedCODFile.saveData();
			JoinedCODFile.reloadData();
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