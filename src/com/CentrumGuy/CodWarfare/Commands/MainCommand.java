package com.CentrumGuy.CodWarfare.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Clans.ClanCommand;
import com.CentrumGuy.CodWarfare.Files.LobbyFile;
import com.CentrumGuy.CodWarfare.Interface.ItemsAndInventories;

public class MainCommand implements CommandExecutor{

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		CommandSender player = sender;
			if (args.length == 0) {	
					player.sendMessage("§b§lCOD-Warfare Info:");
					player.sendMessage(" §7- §3COD-Warfare Developers: §eCentrumGuy, Mr_Rhetorical, xx_Dev_xx, and ShadowwizardMC");
					player.sendMessage(" §7- §3Plugin version:§b " + Main.version);
					player.sendMessage(" §7- §3Plugin Link: §cdev.bukkit.org/bukkit-plugins/call-of-duty/");
					player.sendMessage(" §7- §3Type §c/cod help §3to start");
					
					}else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("load")) {
						
						if (!(player.hasPermission("cod.reload"))) {
							player.sendMessage(Main.codSignature + "§cYou do not have permission to use this command");
							return true;
						}
						
						ReloadCommand.Reload(sender, args);
				
					}else if (args[0].equalsIgnoreCase("help")) {							
							HelpCommand.Help(player, args);
					}else if (args[0].equalsIgnoreCase("givegun") || args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("send"))	{
						
						if (!(player.hasPermission("cod.givegun"))) {
							player.sendMessage(Main.codSignature + "§cYou do not have permission to use this command");
							return true;
						}
						
							GiveGunCommand.give(player, args);
					}else if (args[0].equalsIgnoreCase("join") || args[0].equalsIgnoreCase("j")) {	
											
						if (!(sender.hasPermission("cod.join"))) {
							sender.sendMessage(Main.codSignature + "§cYou do not have permission to use this command");
							return true;
						}
						
						JoinCommand.JoinCOD(sender, args);
					}else if (args[0].equalsIgnoreCase("leave") || args[0].equalsIgnoreCase("l")) {
						
						if (!(sender.hasPermission("cod.leave"))) {
							sender.sendMessage(Main.codSignature + "§cYou do not have permission to use this command");
							return true;
						}
						
						LeaveCommand.LeaveCOD(sender, args);
					}else if (args[0].equalsIgnoreCase("credits") || args[0].equalsIgnoreCase("cred")) {
						if (args.length < 2) {
							sender.sendMessage(Main.codSignature + "§cPlease type §4/cod credits [set|add|take] [Player Name] [Amount]");
							return true;
						}
						
						if (args[1].equalsIgnoreCase("add")) {
							CreditsCommand.addCredits(sender, args);
						}else if (args[1].equalsIgnoreCase("take")) {
							CreditsCommand.takeCredits(sender, args);
						}else if (args[1].equalsIgnoreCase("set")) {
							CreditsCommand.setCredits(sender, args);
						}else{
							sender.sendMessage(Main.codSignature + "§cPlease type §4/cod credits [set|add|take] [Player Name] [Amount]");
							return true;
						}
					}else if (args[0].equalsIgnoreCase("authors") || args[0].equalsIgnoreCase("author")) {
						AuthorsCommand.author(sender);
					}else{
						if (sender instanceof Player) {			
							Player p = (Player) sender;
					if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("createarena") || args[0].equalsIgnoreCase("make") || args[0].equalsIgnoreCase("makearena")) {
						
						if (!(p.hasPermission("cod.createarena"))) {
							p.sendMessage(Main.codSignature + "§cYou do not have permission to use this command");
							return true;
						}
						
						if (CreateArenaCommand.creatingArena.get(p) == true) {
							p.sendMessage(Main.codSignature + "§cYou are already in the middle of creating an arena");
							return true;
						}
						
						if (CreateGunCommand.gunBuilder.get(p) == true) {
							p.sendMessage(Main.codSignature + "§cPlease finish creating your gun first");
							return true;
						}
						
						if (args.length == 3) {
							CreateArenaCommand.createArena(args[1], args[2], p);
						}else if (args.length < 3) {
							p.sendMessage(Main.codSignature + "§cPlease type §4/cod create [Arena Name] [Arena Type]");
						}else if (args.length > 3) {
							p.sendMessage(Main.codSignature + "§cPlease type §4/cod create [Arena Name] [Arena Type]");
						}
						
					}else if (args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("deletearena") || args[0].equalsIgnoreCase("removearena")) {
						
						if (!(p.hasPermission("cod.deletearena"))) {
							p.sendMessage(Main.codSignature + "§cYou do not have permission to use this command");
							return true;
						}
						
						if (args.length == 2) {
							DeleteArenaCommand.deleteArena(args[1], p);
						}else if (args.length < 2) {
							p.sendMessage(Main.codSignature + "§cPlease type §4/cod delete [Arena Name]");
						}else if (args.length > 2) {
							p.sendMessage(Main.codSignature + "§cPlease type §4/cod delete [Arena Name]");
						}
						
					}else if (args[0].equalsIgnoreCase("infoarena") || args[0].equalsIgnoreCase("arena") || args[0].equalsIgnoreCase("arenainfo") || args[0].equalsIgnoreCase("arenainformation") || args[0].equalsIgnoreCase("informationarena")) {	
						
						if (!(p.hasPermission("cod.infoarena"))) {
							p.sendMessage(Main.codSignature + "§cYou do not have permission to use this command");
							return true;
						}
						
						ArenaInfoCommand.arenaInfo(args, p);
					}else if (args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("information")) {
						
						player.sendMessage("§b§lCOD-Warfare Info:");
						player.sendMessage(" §7- §3Plugin created by §eCentrumGuy");
						player.sendMessage(" §7- §3Plugin Link: §cdev.bukkit.org/bukkit-plugins/call-of-duty/");
						player.sendMessage(" §7- §3Type §c/cod help §3to start");
						
					}else if (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("edit")) {
						
						if (!(p.hasPermission("cod.set"))) {
							p.sendMessage(Main.codSignature + "§cYou do not have permission to use this command");
							return true;
						}
						
						SetCommand.Set(p, args);
					}else if (args[0].equalsIgnoreCase("shop") || args[0].equalsIgnoreCase("gunmenu") || args[0].equalsIgnoreCase("menu")) {

						if (!(p.hasPermission("cod.openshop"))) {
							p.sendMessage(Main.codSignature + "§cYou do not have permission to use this command");
							return true;
						}
						
						if (Main.WaitingPlayers.contains(p)) {
							p.openInventory(ItemsAndInventories.MainInventory);
							p.sendMessage(Main.codSignature + "§aYou opened the Gun Menu!");
						}else if (Main.PlayingPlayers.contains(p)) {
							p.sendMessage(Main.codSignature + "§cYou may not open the gun menu while in-game");
						}else{
							p.sendMessage(Main.codSignature + "§cYou must be playing COD-Warfare to open the gun menu. Do §4/cod join");
						}
						
					}else if (args[0].equalsIgnoreCase("createGun")) {
						
						if (!(p.hasPermission("cod.creategun"))) {
							p.sendMessage(Main.codSignature + "§cYou do not have permission to use this command");
							return true;
						}
						
						CreateGunCommand.createGun(p, args);
					}else if (args[0].equalsIgnoreCase("gunbuilder") || args[0].equalsIgnoreCase("buildgun") || args[0].equalsIgnoreCase("autogun") || args[0].equalsIgnoreCase("autoguncreate") || args[0].equalsIgnoreCase("guncreator")) {
						if (!(p.hasPermission("cod.guncreator"))) {
							p.sendMessage(Main.codSignature + "§cYou do not have permission to use this command");
							return true;
						}
						
						if (CreateGunCommand.gunBuilder.get(p)) {
							CreateGunCommand.endGunCreation(p, true);
						}else{
							CreateGunCommand.createAutoGun(p);
						}
					}else if (args[0].equalsIgnoreCase("createGGGun") || args[0].equalsIgnoreCase("createGunGameGun")) {
						
						if (!(p.hasPermission("cod.creategungamegun"))) {
							p.sendMessage(Main.codSignature + "§cYou do not have permission to use this command");
							return true;
						}
						
						CreateGunGameGun.createGunGameGun(p, args);
					}else if (args[0].equalsIgnoreCase("guns") || args[0].equalsIgnoreCase("myguns")) {
						
						if (!(p.hasPermission("cod.gunlist"))) {
							p.sendMessage(Main.codSignature + "§cYou do not have permission to use this command");
							return true;
						}
						
						MyGunsCommand.myGuns(p);
					}else if (args[0].equalsIgnoreCase("pack") || args[0].equalsIgnoreCase("texturepack") || args[0].equalsIgnoreCase("texture") || args[0].equalsIgnoreCase("resource") || args[0].equalsIgnoreCase("resourcepack")) {
						
						if (!(p.hasPermission("cod.rpack"))) {
							p.sendMessage(Main.codSignature + "§cYou do not have permission to use this command");
							return true;
						}
						
						ResourcePackCommand.sendLink(p);
					}else if (args[0].equalsIgnoreCase("score") || args[0].equalsIgnoreCase("profile")) {
						
						if (!(p.hasPermission("cod.profile"))) {
							p.sendMessage(Main.codSignature + "§cYou do not have permission to use this command");
							return true;
						}
						
						ProfileCommand.show(p, args);
					}else if (args[0].equalsIgnoreCase("delgun") || args[0].equalsIgnoreCase("deletegun")) {
						
						if (!(p.hasPermission("cod.delgun"))) {
							p.sendMessage(Main.codSignature + "§cYou do not have permission to use this command");
							return true;
						}
						
						DeleteGunCommand.deleteGun(args, p);
					}else if (args[0].equalsIgnoreCase("exp") || args[0].equalsIgnoreCase("xp") || args[0].equalsIgnoreCase("experience")) {
						
						if (!(p.hasPermission("cod.xp"))) {
							p.sendMessage(Main.codSignature + "§cYou do not have permission to use this command");
							return true;
						}
						
						XPCommand.XP(p);
					}else if (args[0].equalsIgnoreCase("clan")) {
						ClanCommand.onCommand(p, args);
					}else if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("arenas") || args[0].equalsIgnoreCase("arenalist") || args[0].equalsIgnoreCase("arenaslist")) {
						ArenaListCommand.listEnabledArenas(p, args);
					}else if (args[0].equalsIgnoreCase("update")) {
						UpdateCommand.update(p, args);
					}else if (args[0].equalsIgnoreCase("createweapon")) {
						CreateWeaponCommand.createWeapon(args, p);
					}else if (args[0].equalsIgnoreCase("arenacreator") || args[0].equalsIgnoreCase("creator")) {
						if (p.hasPermission("cod.arenacreator")) {
							if (CreateArenaCommand.creatingArena.get(p) == true) {
								CreateArenaCommand.arenaCreating.put(p, null);
								p.getInventory().clear();
								p.getInventory().setContents(CreateArenaCommand.savedInventory.get(p));
								CreateArenaCommand.creatingArena.put(p, false);
								p.sendMessage(Main.codSignature + "§7You left arena creator mode");
								p.updateInventory();
							}else{
								p.sendMessage(Main.codSignature + "§cYou are not in arena creator mode");
								return true;
							}
						}else{
							p.sendMessage(Main.codSignature + "§cYou do not have permission to use this command");
							return true;
						}
					}else if (args[0].equalsIgnoreCase("setnextarena") || args[0].equalsIgnoreCase("nextarena") || args[0].equalsIgnoreCase("upcomingarena") || args[0].equalsIgnoreCase("setupcomingarena")) {
						if (p.hasPermission("cod.setNextArena")) {
							SetNextArenaCommand.setNextArena(p, args);
						}else{
							p.sendMessage(Main.codSignature + "§cYou do not have permission to use this command");
							return true;
						}
					}else if (args[0].equalsIgnoreCase("start") || args[0].equalsIgnoreCase("startmatch")) {
						if (p.hasPermission("cod.forceStart")) {
							ForceStartCommand.forceStart(p, args);
						}else{
							p.sendMessage(Main.codSignature + "§cYou do not have permission to use this command");
							return true;
						}
					}else if (args[0].equalsIgnoreCase("lobby") || args[0].equalsIgnoreCase("lobbyspawn") || args[0].equalsIgnoreCase("spawn")) {
						
						if (args.length == 1) {
							
							if (!(p.hasPermission("cod.lobbytp"))) {
								p.sendMessage(Main.codSignature + "§cYou do not have permission to use this command");
								return true;
							}
							
						LobbyCommand.tpLobby(p);
						
						if (!(LobbyFile.getData().getConfigurationSection("Lobby") == null)) {
							p.sendMessage(Main.codSignature + "§bTeleported to COD-Warfare lobby");
						}
						
						}else if (args.length == 2) {
							if (args[1].equalsIgnoreCase("set")) {
								
								if (!(p.hasPermission("cod.lobbyset"))) {
									p.sendMessage(Main.codSignature + "§cYou do not have permission to use this command");
									return true;
								}
								
								LobbyCommand.SetLobby(p);
							}else if (args[1].equalsIgnoreCase("tp") || args[1].equalsIgnoreCase("teleport")) {
								
								if (!(p.hasPermission("cod.lobbytp"))) {
									p.sendMessage(Main.codSignature + "§cYou do not have permission to use this command");
									return true;
								}
								
								LobbyCommand.tpLobby(p);
								if (!(LobbyFile.getData().getConfigurationSection("Lobby") == null)) {
									p.sendMessage(Main.codSignature + "§bTeleported to COD-Warfare lobby");
								}
							}else{
								p.sendMessage(Main.codSignature + "§4 " + args[1] + " §cis not a lobby command. Try doing §4/cod lobby §cor §4/cod lobby set");
							}
						}else{
							p.sendMessage(Main.codSignature + "§cToo many arguments. Try doing §4/cod lobby §cor §4/cod lobby set");
						}
						
					}else{
						p.sendMessage(Main.codSignature + "§cUnknown command §4/" + label + " " + args[0]);
					}
		}else{
			sender.sendMessage(Main.codSignature + "§cYou must be a player to preform this command");
			}
		}
		return true;
	}
}
