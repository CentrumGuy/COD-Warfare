package com.CentrumGuy.CodWarfare.Utilities;

import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;

public class SendUpdateInfo {
	public static void send(Player p) {
		if (p.isOp()) {
			if (!(ThisPlugin.getPlugin().getConfig().getString("Version").equals(Main.version))) {
				
				p.sendMessage("§e§lCOD-Warfare version " + Main.version);
				p.sendMessage("§6§m=====================================================");
				p.sendMessage("§cAdditions in this update:");
				/*p.sendMessage("§7- §3Added a new dogs killstreak reward when a player gets a killstreak");
				p.sendMessage("§3     of 15");
				p.sendMessage("§7- §3Changed airstrike killstreak to 20 instead of 15");*/
				p.sendMessage("§7- §bAdded MySQL Support");
				p.sendMessage("§7- §6Added Achievements");
				p.sendMessage("§7- §6Added Clan Admins");
				p.sendMessage("§7- §3Fixed a lot of bugs and glitches");
				p.sendMessage("§6§m=====================================================");
				
				ThisPlugin.getPlugin().getConfig().set("Version", Main.version);
				ThisPlugin.getPlugin().saveConfig();
				ThisPlugin.getPlugin().reloadConfig();
			}
		}
	}
}
