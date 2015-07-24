package com.CentrumGuy.CodWarfare.Commands;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Main;

public class ProfileCommand {

	@SuppressWarnings("deprecation")
	public static void show(Player p, String[] args) {
		if (args.length == 1) {
			if (Main.LobbyScoreboard.get(p.getName()) == null) {
				p.sendMessage(Main.codSignature + "§cCannot get your profile because you never played COD-Warfare");
			}
			p.sendMessage("");
			p.sendMessage("");
			p.sendMessage("");
			p.sendMessage("§7║ §f§lProfile:§3§l " + p.getName());
			p.sendMessage("§7║");
			p.sendMessage("§7║     §b§lTotal Kills:§a§l " + Main.LobbyKillsScore.get(p.getName()).getScore() + "     §b§lTotal Deaths:§a§l " + Main.LobbyDeathsScore.get(p.getName()).getScore());
			p.sendMessage("§7║     §b§lLevel:§a§l " + Main.LobbyLevelScore.get(p.getName()).getScore() + "             §b§lHighest Kill Streak:§a§l " + Main.highestKillstreak.get(p.getName()).getScore());
			
			DecimalFormat df = new DecimalFormat("#.##");
			
			float kdr = ((float) Main.LobbyKillsScore.get(p.getName()).getScore()) / ((float) Main.LobbyDeathsScore.get(p.getName()).getScore());
			
			if (Main.LobbyDeathsScore.get(p.getName()).getScore() == 0) {
				p.sendMessage("§7║     §b§lKDR:§a§l " + Main.LobbyKillsScore.get(p.getName()).getScore());
			}else if (Main.LobbyKillsScore.get(p.getName()).getScore() == 0) {
				p.sendMessage("§7║     §b§lKDR:§a§l " + Main.LobbyKillsScore.get(p.getName()).getScore());
			}else{
				p.sendMessage("§7║     §b§lKDR:§a§l " + df.format(kdr));
			}
			
			p.sendMessage("§7║");
			p.sendMessage("§7╚§7§l════════════════════════════");
		}else{
			if (!(p.hasPermission("cod.profileother"))) {
				p.sendMessage(Main.codSignature + "§cYou do not have permission see other player's profile");
				return;
			}
			
			String playerName = args[1];
			
			if (Bukkit.getServer().getPlayer(playerName) == null) {
				p.sendMessage(Main.codSignature + "§cPlayer§4 " + playerName + " §ccannot be found");
			}else{
				Player pp = Bukkit.getServer().getPlayer(playerName);
				if (Main.LobbyScoreboard.get(pp.getName()) == null) {
					p.sendMessage(Main.codSignature + "§cCannot get this player's profile because they never played COD-Warfare");
				}
				
				p.sendMessage("");
				p.sendMessage("");
				p.sendMessage("");
				p.sendMessage("§7║ §f§lProfile:§3§l " + pp.getName());
				p.sendMessage("§7║");
				p.sendMessage("§7║     §b§lTotal Kills:§a§l " + Main.LobbyKillsScore.get(pp.getName()).getScore() + "     §b§lTotal Deaths:§a§l " + Main.LobbyDeathsScore.get(pp.getName()).getScore());
				p.sendMessage("§7║     §b§lLevel:§a§l " + Main.LobbyLevelScore.get(pp.getName()).getScore() + "             §b§lHighest Kill Streak:§a§l " + Main.highestKillstreak.get(pp.getName()).getScore());
				
				DecimalFormat df = new DecimalFormat("#.##");
				
				float kdr = ((float) Main.LobbyKillsScore.get(pp.getName()).getScore()) / ((float) Main.LobbyDeathsScore.get(pp.getName()).getScore());
				
				if (Main.LobbyDeathsScore.get(pp.getName()).getScore() == 0) {
					p.sendMessage("§7║     §b§lKDR:§a§l " + Main.LobbyKillsScore.get(pp.getName()).getScore());
				}else if (Main.LobbyKillsScore.get(pp.getName()).getScore() == 0) {
					p.sendMessage("§7║     §b§lKDR:§a§l " + Main.LobbyKillsScore.get(pp.getName()).getScore());
				}else{
					p.sendMessage("§7║     §b§lKDR:§a§l " + df.format(kdr));
				}
				
				p.sendMessage("§7║");
				p.sendMessage("§7╚§7§l════════════════════════════");
			}
		}
	}
}
