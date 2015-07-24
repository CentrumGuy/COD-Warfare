package com.CentrumGuy.CodWarfare.Commands;

import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Arena.GGgunAPI;
import com.CentrumGuy.CodWarfare.Arena.getArena;
import com.CentrumGuy.CodWarfare.Files.ArenasFile;

public class ArenaInfoCommand {

	public static void arenaInfo(String[] args, Player p) {
		if (args.length == 1) {
			p.sendMessage(Main.codSignature + "§cPlease type §4/cod arena [Arena Name]");
		}else if (args.length >= 2) {
			String ArenaName = args[1];
			
			if (ArenasFile.getData().contains("Arenas." + ArenaName)) {
				p.sendMessage("§eInformation for arena:§6 " + ArenaName);
				p.sendMessage(" §7- §3Arena type:§b " + getArena.getType(ArenaName));
					if (getArena.getEnabled(ArenaName)) {
						p.sendMessage(" §7- §3Enabled: §bTrue");
					}else{
						p.sendMessage(" §7- §3Enabled: §bFalse");
					}
					
				if (getArena.getType(ArenaName).equalsIgnoreCase("TDM") || getArena.getType(ArenaName).equalsIgnoreCase("CTF") || getArena.getType(ArenaName).equalsIgnoreCase("INFECT") || getArena.getType(ArenaName).equalsIgnoreCase("KC")) {
					if (getArena.getRedSpawn(ArenaName) == null) {
						p.sendMessage(" §7- §3Red spawn: §bNot set");
					}else{
						p.sendMessage(" §7- §3Red spawn: §bSet");
					}
					
					if (getArena.getBlueSpawn(ArenaName) == null) {
						p.sendMessage(" §7- §3Blue spawn: §bNot set");
					}else{
						p.sendMessage(" §7- §3Blue spawn: §bSet");
					}
					
					if (getArena.getType(ArenaName).equalsIgnoreCase("CTF")) {
						if (getArena.getFlagLocation("red", ArenaName) == null) {
							p.sendMessage(" §7- §3Red flag location: §bNot set");
						}else{
							p.sendMessage(" §7- §3Red flag location: §bSet");
						}
						
						if (getArena.getFlagLocation("blue", ArenaName) == null) {
							p.sendMessage(" §7- §3Blue flag location: §bNot set");
						}else{
							p.sendMessage(" §7- §3Blue flag location: §bSet");
						}
					}
				}else if (getArena.getType(ArenaName).equalsIgnoreCase("FFA") || getArena.getType(ArenaName).equalsIgnoreCase("GUN") || getArena.getType(ArenaName).equalsIgnoreCase("ONEIN")) {
					if (getArena.getFFASpawn(ArenaName, 2) == null) {
						p.sendMessage(" §7- §3At least two spawns set: §bFalse");
					}else{
						p.sendMessage(" §7- §3At least two spawns set: §bTrue");
					}
					
					if (getArena.getType(ArenaName).equalsIgnoreCase("ONEIN")) {
						if (getArena.getSpectatorSpawn(ArenaName) == null) {
							p.sendMessage(" §7- §3Spectator spawn: §bNot set");
						}else{
							p.sendMessage(" §7- §3Spectator spawn: §bSet");
						}
					}
					
					if (getArena.getType(ArenaName).equalsIgnoreCase("GUN")) {
						if (GGgunAPI.Guns.isEmpty()) {
							p.sendMessage(" §7- §3At least one gun game gun: §bFalse");
						}else{
							p.sendMessage(" §7- §3At least one gun game gun: §bTrue");
						}
					}
				}
			}else{
				p.sendMessage(Main.codSignature + "§cArena§4 " + ArenaName + " §cdoesn't exist");
			}
		}
	}
	
	public static boolean arenaReadyForEnable(String ArenaName) {
		if (ArenasFile.getData().contains("Arenas." + ArenaName)) {
			if (getArena.getEnabled(ArenaName) == true) return false;
			if (getArena.getType(ArenaName).equalsIgnoreCase("TDM") || getArena.getType(ArenaName).equalsIgnoreCase("CTF") || getArena.getType(ArenaName).equalsIgnoreCase("INFECT") || getArena.getType(ArenaName).equalsIgnoreCase("KC")) {
				if (getArena.getRedSpawn(ArenaName) == null) {
					return false;
				}
				
				if (getArena.getBlueSpawn(ArenaName) == null) {
					return false;
				}
				
				if (getArena.getType(ArenaName).equalsIgnoreCase("CTF")) {
					if (getArena.getFlagLocation("red", ArenaName) == null) {
						return false;
					}
					
					if (getArena.getFlagLocation("blue", ArenaName) == null) {
						return false;
					}
				}
			}else if (getArena.getType(ArenaName).equalsIgnoreCase("FFA") || getArena.getType(ArenaName).equalsIgnoreCase("GUN") || getArena.getType(ArenaName).equalsIgnoreCase("ONEIN")) {
				if (getArena.getFFASpawn(ArenaName, 1) == null) {
					return false;
				}
				
				if (getArena.getType(ArenaName).equalsIgnoreCase("ONEIN")) {
					if (getArena.getSpectatorSpawn(ArenaName) == null) {
						return false;
					}
				}
				
				if (getArena.getType(ArenaName).equalsIgnoreCase("GUN")) {
					if (GGgunAPI.Guns.isEmpty()) {
						return false;	
					}
				}
			}
		}else{
			return false;
		}
		
		return true;
	}
}
