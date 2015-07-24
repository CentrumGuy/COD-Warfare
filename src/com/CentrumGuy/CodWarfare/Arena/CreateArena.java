package com.CentrumGuy.CodWarfare.Arena;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Commands.CreateArenaCommand;
import com.CentrumGuy.CodWarfare.Files.ArenasFile;
import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;
import com.CentrumGuy.CodWarfare.Utilities.Items;
import com.CentrumGuy.CodWarfare.Utilities.SendCoolMessages;

public class CreateArena {
		
	public static void createArena(String ArenaName, String ArenaType, final Player p) {
			if (!(ArenasFile.getData().contains("Arenas." + ArenaName))) {
				ArenasFile.getData().set("Arenas." + ArenaName + ".Type", ArenaType.toUpperCase());
				
				p.sendMessage(Main.codSignature + "§7You entered arena creator mode. Type §f/cod arenacreator§7 to leave");
				
				BukkitRunnable br = new BukkitRunnable() {
					public void run() {
						if (CreateArenaCommand.creatingArena.get(p) == true) {
							SendCoolMessages.sendOverActionBar(p, "§7You are in arena creator mode. Type §f/cod creator§7 to leave");
						}
					}
				};
				
				br.runTaskTimer(ThisPlugin.getPlugin(), 0L, 30L);
				
				CreateArenaCommand.creatingArena.put(p, true);
				CreateArenaCommand.arenaCreating.put(p, ArenaName);
				CreateArenaCommand.savedInventory.put(p, p.getInventory().getContents());
				p.getInventory().clear();
				
				p.sendMessage(Main.codSignature + "§bArena§3 " +  ArenaName + " §bwith type§3 " + ArenaType.toUpperCase() + " §bcreated!");
				
				if (ArenaType.equalsIgnoreCase("tdm") || ArenaType.equalsIgnoreCase("ctf") || ArenaType.equalsIgnoreCase("infect") || ArenaType.equalsIgnoreCase("kc")) {
					Items.setItem(p, CreateArenaCommand.redSpawnTool, 0);
				}else if (ArenaType.equalsIgnoreCase("gun") || ArenaType.equalsIgnoreCase("ffa") || ArenaType.equalsIgnoreCase("onein")) {
					if (ArenaType.equalsIgnoreCase("onein")) Items.setItem(p, CreateArenaCommand.oneinSpecTool, 0);
					else Items.setItem(p, CreateArenaCommand.ffaSpawnTool, 0);
				}
				
				if (ArenaType.equalsIgnoreCase("tdm") || ArenaType.equalsIgnoreCase("ctf") || ArenaType.equalsIgnoreCase("infect") || ArenaType.equalsIgnoreCase("kc")) {
					p.sendMessage(Main.codSignature + "§3Go to the §cred team's §3spawn and right-click with the stick");
					/*p.sendMessage(Main.codSignature + "§6Now set the §cred §6and §9blue §6spawns by typing:");
					p.sendMessage(Main.codSignature + "§7- §3/cod set§b " + ArenaName + " §3spawn §cred");
					p.sendMessage(Main.codSignature + "§7- §3/cod set§b " + ArenaName + " §3spawn §9blue");*/
				}else if (ArenaType.equalsIgnoreCase("gun") || ArenaType.equalsIgnoreCase("ffa") || ArenaType.equalsIgnoreCase("onein")) {
					if (ArenaType.equalsIgnoreCase("onein")) {
						if (getArena.getSpectatorSpawn(ArenaName) == null) {
							if (CreateArenaCommand.creatingArena.get(p) == false) {
								p.sendMessage(Main.codSignature + "§bNow set a spectator spawn by typing:");
								p.sendMessage(Main.codSignature + "§7- §3/cod set§e " + ArenaName + " §3spec");
								return;
							}else{
								p.getInventory().clear();
								Items.setItem(p, CreateArenaCommand.oneinSpecTool, 0);
								p.sendMessage(Main.codSignature + "§3Go to the §aspectator's §3spawn and right-click with the stick");
								return;
							}
						}
					}
					
					p.sendMessage(Main.codSignature+ "§3Add spawns by right-clicking with the stick");
					/*p.sendMessage(Main.codSignature + "§6Now set the spawns by typing:");
					p.sendMessage(Main.codSignature + "§7- §3/cod set§b " + ArenaName + " §3spawn §dnext");*/
				}
		}else{
			p.sendMessage(Main.codSignature + "§cArena§4 " + ArenaName + " §calready exists!");
		}
	}
}
