package com.CentrumGuy.CodWarfare.Commands;

import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Leveling.Exp;

public class XPCommand {

	public static void XP(Player p) {
		p.sendMessage("§a§lYour Experience:");
		p.sendMessage("§7 - §3Needed XP for next level:§b " + Exp.getNeededExp(p));
		p.sendMessage("§7 - §3Current XP:§b " + Exp.getExp(p));
	}
}
