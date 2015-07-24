package com.CentrumGuy.CodWarfare.Plugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class ThisPlugin {

	public static Plugin getPlugin() {
		return Bukkit.getServer().getPluginManager().getPlugin("COD");
	}
}
