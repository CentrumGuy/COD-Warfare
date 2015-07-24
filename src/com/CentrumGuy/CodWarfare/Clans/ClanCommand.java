package com.CentrumGuy.CodWarfare.Clans;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Interface.Scores;

public class ClanCommand {

	@SuppressWarnings("deprecation")
	public static void onCommand(Player p, String[] args) {
		if (args[0].equalsIgnoreCase("clan")) {
			
			if (args.length < 2) {
				p.sendMessage("§4§lClan Commands:");
				p.sendMessage("§7 - §e/cod clan create §7- §bCreates a clan");
				p.sendMessage("§7 - §e/cod clan invite §7- §bInvites a player to your clan");
				p.sendMessage("§7 - §e/cod clan leave §7- §bLeaves a clan");
				p.sendMessage("§7 - §e/cod clan join §7- §bJoins a clan");
				p.sendMessage("§7 - §e/cod clan invites §7- §bShows you your clan invites");
				p.sendMessage("§7 - §e/cod clan delete §7- §bDeletes a clan");
				return;
			}
			
			String command = args[1];
			
			if (!(Main.WaitingPlayers.contains(p))) {
				if (Main.PlayingPlayers.contains(p)) {
					p.sendMessage(Main.codSignature + "§cCannot use clan commands while in-game");
					return;
				}else{
					p.sendMessage(Main.codSignature + "§cPlease join cod first: §4/cod join");
					return;
				}
			}
			
			if (command.equalsIgnoreCase("create")) {
				if (p.hasPermission("cod.clanCreate")) {
					if (!(Main.LobbyCreditsScore.get(p.getName()).getScore() >= Main.ClanCost)) {
						Scores.saveScores(p);
						Scores.loadScores(p);
						
						p.sendMessage(Main.codSignature + "§cYou don't have enough credits. This costs§4 " + Main.ClanCost + " §ccredits.");
						return;
					}
					
					if (MainClan.belongsToAClan(p)) {
						p.sendMessage(Main.codSignature + "§cPlease leave your current clan before creating another one");
						return;
					}
					
					if (args.length < 3) {
						p.sendMessage(Main.codSignature + "§cPlease type §4/cod clan create [Clan Name]");
					}else{	
						if (MainClan.clanExists(args[2])) {
							p.sendMessage(Main.codSignature + "§cThat clan already exists");
							return;
						}
						
						String clan = args[2];
						
						if (!StringUtils.isAlphanumeric(clan)) {
							p.sendMessage(Main.codSignature + "§cYour clan name can only contain alphanumeric characters");
							return;
						}
						
						if (clan.length() >= 9) {
							p.sendMessage(Main.codSignature + "§cYour clan name has to be less than 9 characters long");
							return;
						}
						
						MainClan.createClan(args[2], p);
						
						Main.LobbyCreditsScore.get(p.getName()).setScore((Main.LobbyCreditsScore.get(p.getName()).getScore()) - (Main.ClanCost));
						Main.GameCreditsScore.get(p.getName()).setScore((Main.LobbyCreditsScore.get(p.getName()).getScore()) - (Main.ClanCost));
						
						p.sendMessage(Main.codSignature + "§aCreated clan§2 " + args[2]);
					}
				}else{
					p.sendMessage(Main.codSignature + "§cYou don't have the necessary permissions");
				}
			}else if (command.equalsIgnoreCase("invite") || command.equalsIgnoreCase("inv")) {
				if (!(p.hasPermission("cod.clanInvite"))) {
					p.sendMessage(Main.codSignature + "§cYou don't have the necessary permissions");
					return;
				}
				
				if (!(MainClan.belongsToAClan(p))) {
					p.sendMessage(Main.codSignature + "§cYou don't belong to a clan");
					return;
				}
				
				if (MainClan.isOwner(p, MainClan.getClan(p))) {
					if (args.length < 3) {
						p.sendMessage(Main.codSignature + "§cPlease type §4/cod clan invite [Player Name]");
					}else{
						Player invite = Bukkit.getServer().getPlayer(args[2]);
						if (invite == null) {
							p.sendMessage(Main.codSignature + "§cCould not find player§4 " + args[2]);
						}else{
							if (invite.equals(p)) {
								p.sendMessage(Main.codSignature + "§cCannot invite yourself");
								return;
							}
							
							if (MainClan.getClan(invite) != null) {
								if (MainClan.getClan(invite).equals(MainClan.getClan(p))) {
									p.sendMessage(Main.codSignature + "§4" + invite.getName() + " §cis already part of your clan clan");
									return;
								}
							}
							
							MainClan.invites.get(invite).add(MainClan.getClan(p));
							invite.sendMessage(Main.codSignature + "§b§lYou have been invited to clan§3§l " + MainClan.getClan(p));
							p.sendMessage(Main.codSignature + "§aInvited player§2 " + invite.getName() + " §ato your clan!");
						}
					}
				}else{
					p.sendMessage(Main.codSignature + "§cYou are not the clan owner");
					return;
				}
			}else if (command.equalsIgnoreCase("leave") || command.equalsIgnoreCase("l")) {
				if (!(p.hasPermission("cod.clanLeave"))) {
					p.sendMessage(Main.codSignature + "§cYou don't have the necessary permissions");
					return;
				}
				
				if (!(MainClan.belongsToAClan(p))) {
					p.sendMessage(Main.codSignature + "§cYou don't belong to a clan");
					return;
				}
				
				if (MainClan.isOwner(p, MainClan.getClan(p))) {
					p.sendMessage(Main.codSignature + "§cCannot leave your own clan. Please delete it");
					return;
				}
				
				String clan = MainClan.getClan(p);
				
				MainClan.removePlayer(p, MainClan.getClan(p));
				p.sendMessage(Main.codSignature + "§aLeft clan§2 " + clan);
			}else if (command.equalsIgnoreCase("join") || command.equalsIgnoreCase("j")) {
				if (!(p.hasPermission("cod.clanJoin"))) {
					p.sendMessage(Main.codSignature + "§cYou don't have the necessary permissions");
					return;
				}
				
				if (args.length < 3) {
					p.sendMessage(Main.codSignature + "§cPlease type §4/cod clan join [Clan Name]");
					return;
				}
				
				String clan = args[2];
				
				if (!(MainClan.clanExists(clan))) {
					p.sendMessage(Main.codSignature + "§cThat clan does not exist");
					return;
				}
				
				if (MainClan.belongsToAClan(p)) {
					if (MainClan.getClan(p).equals(clan)) {
						p.sendMessage(Main.codSignature + "§cYou are already in this clan");
						return;
					}
					
					p.sendMessage(Main.codSignature + "§cPlease leave your current clan before joining another one");
					return;
				}
				
				if (!(MainClan.invites.get(p).contains(clan))) {
					p.sendMessage(Main.codSignature + "§cYou have not been invited to join this clan");
					return;
				}
				
				MainClan.addPlayer(clan, p);
				MainClan.invites.put(p, new ArrayList<String>());
				p.sendMessage(Main.codSignature + "§aYou have been added to clan§2 " + clan);
			}else if (command.equalsIgnoreCase("delete") || command.equalsIgnoreCase("del") || command.equalsIgnoreCase("remove")) {
				if (!(p.hasPermission("cod.clanDelete"))) {
					p.sendMessage(Main.codSignature + "§cYou don't have the necessary permissions");
					return;
				}
				
				if (!(MainClan.belongsToAClan(p))) {
					p.sendMessage(Main.codSignature + "§cYou don't belong to a clan");
					return;
				}
				
				if (!(MainClan.isOwner(p, MainClan.getClan(p)))) {
					p.sendMessage(Main.codSignature + "§cYou are not the clan owner");
					return;
				}
				
				String clan = MainClan.getClan(p);
				
				MainClan.deleteClan(MainClan.getClan(p));
				p.sendMessage(Main.codSignature + "§aDeleted clan§2 " + clan);
			}else if (command.equalsIgnoreCase("invites")) {
				if (!(p.hasPermission("cod.clanInvites"))) {
					p.sendMessage(Main.codSignature + "§cYou don't have the necessary permissions");
					return;
				}
				
				if (MainClan.invites.get(p).isEmpty()) {
					p.sendMessage(Main.codSignature + "§cYou have no clan invites");
					return;
				}
				
				p.sendMessage("§b§lYour clan invites:");
				
				for (int i = 0 ; i < MainClan.invites.get(p).size() ; i++) {
					String clan = MainClan.invites.get(p).get(i);
					p.sendMessage(" §7-§e " + clan);
				}
			}else{
				p.sendMessage(Main.codSignature + "§cInvalid clan command:§4 " + command);
			}
		}
	}
}
