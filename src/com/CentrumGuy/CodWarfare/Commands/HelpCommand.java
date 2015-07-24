package com.CentrumGuy.CodWarfare.Commands;

import org.bukkit.command.CommandSender;

import com.CentrumGuy.CodWarfare.Main;

public class HelpCommand {
	
	public static void Help(CommandSender p, String[] args) {
		if (args.length == 1) {
			p.sendMessage("§4§m |   »* |   >»  §r  §6[ §cCOD-Warfare Help §d· §e1/5§d· §6]  §4§m  «<   | *«   | §r");
			p.sendMessage(" §8- §3/cod help §7- §bShows the help menu");
			p.sendMessage(" §8- §3/cod create §7- §bCreates an arena");
			p.sendMessage(" §8- §3/cod delete §7- §bDeletes an arena");
			p.sendMessage(" §8- §3/cod arena §7- §bShows information about an arena");
			p.sendMessage(" §8- §3/cod info §7- §bShows information about the plugin");
		}else if (args.length >= 2) {
			if (args[1].equalsIgnoreCase("1") || args[1].equalsIgnoreCase("0")) {
				p.sendMessage("§4§m |   »* |   >»  §r  §6[ §cCOD-Warfare Help §d· §e1/6§d· §6]  §4§m  «<   | *«   | §r");
				p.sendMessage(" §8- §3/cod help §7- §bShows the help menu");
				p.sendMessage(" §8- §3/cod create §7- §bCreates an arena");
				p.sendMessage(" §8- §3/cod delete §7- §bDeletes an arena");
				p.sendMessage(" §8- §3/cod arena §7- §bGives information about an arena");
				p.sendMessage(" §8- §3/cod info §7- §bShows information about the plugin");
			}else if (args[1].equalsIgnoreCase("2")) {
				p.sendMessage("§4§m |   »* |   >»  §r  §6[ §cCOD-Warfare Help §d· §e2/6§d· §6]  §4§m  «<   | *«   | §r");
				p.sendMessage(" §8- §3/cod set §7- §bEdit an arena");
				p.sendMessage(" §8- §3/cod menu §7- §bOpens the Gun Menu");
				p.sendMessage(" §8- §3/cod guns §7- §bList all your available guns");
				p.sendMessage(" §8- §3/cod pack §7- §bGet the link to the resource pack");
				p.sendMessage(" §8- §3/cod creategun §7- §bCreate a gun");
			}else if (args[1].equalsIgnoreCase("3")) {
				p.sendMessage("§4§m |   »* |   >»  §r  §6[ §cCOD-Warfare Help §d· §e3/6§d· §6]  §4§m  «<   | *«   | §r");
				p.sendMessage(" §8- §3/cod profile §7- §bOpens a player's cod profile");
				p.sendMessage(" §8- §3/cod reload §7- §bReloads the cod-warfare plugin");
				p.sendMessage(" §8- §3/cod join §7- §bJoins cod-warfare");
				p.sendMessage(" §8- §3/cod leave §7- §bLeaves cod-warfare");
				p.sendMessage(" §8- §3/cod lobby §7- §bTeleports you to the cod-war lobby");
			}else if (args[1].equalsIgnoreCase("4")) {
				p.sendMessage("§4§m |   »* |   >»  §r  §6[ §cCOD-Warfare Help §d· §e4/6§d· §6]  §4§m  «<   | *«   | §r");
				p.sendMessage(" §8- §3/cod creategggun §7- §bCreate a gun for gun game");
				p.sendMessage(" §8- §3/cod xp §7- §bShows your xp information");
				p.sendMessage(" §8- §3/cod givegun §7- §bGive a gun to a player");
				p.sendMessage(" §8- §3/cod delgun §7- §bDeletes a gun");
				p.sendMessage(" §8- §3/cod credits §7- §bModifies a player's credits");
			}else if (args[1].equalsIgnoreCase("5")) {
				p.sendMessage("§4§m |   »* |   >»  §r  §6[ §cCOD-Warfare Help §d· §e5/6§d· §6]  §4§m  «<   | *«   | §r");
				p.sendMessage(" §8- §3/cod clan §7- §bThe main command for clans");
				p.sendMessage(" §8- §3/cod list §7- §bShows you a list of enabled arenas");
				p.sendMessage(" §8- §3/cod update §7- §bUpdates COD-Warfare");
				p.sendMessage(" §8- §3/cod createweapon §7- §bCreates a lethal or a tactical weapon");
				p.sendMessage(" §8- §3/cod arenacreator §7- §bLeaves arena creator mode");
			}else if (args[1].equalsIgnoreCase("6")) {
				p.sendMessage("§4§m |   »* |   >»  §r  §6[ §cCOD-Warfare Help §d· §e6/6§d· §6]  §4§m  «<   | *«   | §r");
				p.sendMessage(" §8- §3/cod guncreator §7- §bEasy way to create guns");
				p.sendMessage(" §8- §3/cod nextarena §7- §bSet the next arena in the game");
				p.sendMessage(" §8- §3/cod start §7- §bForce start the match");
			}else{
				p.sendMessage(Main.codSignature + "§cInvalid help page§4 " + args[1]);
			}
		}
	}
	
}
