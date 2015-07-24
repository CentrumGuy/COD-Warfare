package com.CentrumGuy.CodWarfare.Utilities;

import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Main;

public class Prefix {

	public static void setDispName(Player p, String name) {
		Main.dispName.put(p, name);
	}
}
