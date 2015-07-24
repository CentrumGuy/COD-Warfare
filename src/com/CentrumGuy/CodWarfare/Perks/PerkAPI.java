package com.CentrumGuy.CodWarfare.Perks;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class PerkAPI {
	public static HashMap<Player, Perk> Perk = new HashMap<Player, Perk>();
	
	public static void setPerk(Player p, Perk perk) {
		Perk.put(p, perk);
	}
	
	public static void removePerk(Player p, Perk perk) {
		Perk.put(p, null);
	}
}
