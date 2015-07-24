package com.CentrumGuy.CodWarfare.Utilities;

import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;

public class Rank {

	public static String getRank(Player p) {
		int nextInt = 0;
		String rank = "";
		while (nextInt <= Main.LobbyLevelScore.get(p.getName()).getScore()) {
			if (ThisPlugin.getPlugin().getConfig().getString("COD-Ranks." + nextInt) == null) {
				nextInt++;
				continue;
			}
			rank = ThisPlugin.getPlugin().getConfig().getString("COD-Ranks." + nextInt);
			nextInt++;
		}
		
		rank = ColorCodes.change(rank, '&');
		return rank;
	}
	
	public static String getRankPrefix(String rank) {
		if (rank.equals("")) return "";
		String pref = "§2[§a" + rank + "§2]§r ";
		return pref;
	}
}
