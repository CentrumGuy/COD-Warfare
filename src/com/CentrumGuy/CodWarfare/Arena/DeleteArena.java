package com.CentrumGuy.CodWarfare.Arena;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Files.ArenasFile;
import com.CentrumGuy.CodWarfare.Files.EnabledArenasFile;

public class DeleteArena {

	public static void deleteArena(String ArenaName, Player p) {
		if (ArenasFile.getData().contains("Arenas." + ArenaName)) {
			ArenasFile.getData().set("Arenas." + ArenaName, null);
			
			ArrayList<String> enabledArenas = new ArrayList<String>();
			
			int nextInt = 1;
			
			while (EnabledArenasFile.getData().get("EnabledArenas." + nextInt) != null) {
				enabledArenas.add(EnabledArenasFile.getData().getString("EnabledArenas." + nextInt));
				nextInt++;
	        }
			
			EnabledArenasFile.getData().set("EnabledArenas", null);
			
			if (enabledArenas.contains(ArenaName)) enabledArenas.remove(ArenaName);
			
			int highestNum = 1;
			
			if (!(enabledArenas.isEmpty())) {
				for (int i = 0 ; i < enabledArenas.size() ; i++) {
					EnabledArenasFile.getData().set("EnabledArenas." + highestNum, enabledArenas.get(i));
					highestNum++;
				}
			}
			
			EnabledArenasFile.saveData();
			EnabledArenasFile.reloadData();
			
			p.sendMessage(Main.codSignature + "§5Arena §6" + ArenaName + " §5deleted!");
		}else{
			p.sendMessage(Main.codSignature + "§cArena §4" + ArenaName + " §cdoes not exist");
		}
	}
}
