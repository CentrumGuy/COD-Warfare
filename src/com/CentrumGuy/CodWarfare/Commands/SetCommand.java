package com.CentrumGuy.CodWarfare.Commands;

import java.util.ArrayList;

import org.bukkit.World;
import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Arena.GGgunAPI;
import com.CentrumGuy.CodWarfare.Arena.PickRandomArena;
import com.CentrumGuy.CodWarfare.Arena.getArena;
import com.CentrumGuy.CodWarfare.Files.ArenasFile;
import com.CentrumGuy.CodWarfare.Files.EnabledArenasFile;
import com.CentrumGuy.CodWarfare.Utilities.Items;

public class SetCommand {

	public static void Set(Player p, String[] args) {
		if (args.length <= 2) {
			p.sendMessage(Main.codSignature + "§cIncorrect usage. Please type §4/cod set [ArenaName] [SettingType] §cValid setting types are §7spawn, flag, spec, enabled, disabled");
		}else if (args.length > 2) {
			String ArenaName = args[1];
				if (ArenasFile.getData().contains("Arenas." + ArenaName)) {
					if (args[2].equalsIgnoreCase("spawn")) {
						SetSpawns(p, args, ArenaName);
					}else if (args[2].equalsIgnoreCase("flag")) {
						setFlagLocation(p, args, ArenaName);
					}else if (args[2].equalsIgnoreCase("spectator") || args[2].equalsIgnoreCase("spec")) {
						setSpectator(p, args, ArenaName);
					}else if (args[2].equalsIgnoreCase("enabled") || args[2].equalsIgnoreCase("enable")) {
						SetEnabled(p, args, ArenaName);
					}else if (args[2].equalsIgnoreCase("disabled") || args[2].equalsIgnoreCase("off")) {
						setDisabled(p, args, ArenaName);
					}else{
						p.sendMessage(Main.codSignature + "§cIncorrect usage. Please type §6/cod set <ArenaName> <SettingType> §cValid setting types are §7spawn, flag, spectator, enabled");
					}
			}else{
				p.sendMessage(Main.codSignature + "§cArena§4 " + ArenaName + " §cdoes not exist. This is case sensitive");
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private static void SetSpawns(Player p, String[] args, String ArenaName) {
					if (args.length == 4) {
						if (args[3].equalsIgnoreCase("blue")) {
							if (ArenasFile.getData().getString("Arenas." + ArenaName + ".Type").contentEquals("TDM") || ArenasFile.getData().getString("Arenas." + ArenaName + ".Type").contentEquals("INFECT") || ArenasFile.getData().getString("Arenas." + ArenaName + ".Type").contentEquals("CTF") || ArenasFile.getData().getString("Arenas." + ArenaName + ".Type").contentEquals("KC")) {
								World world = p.getLocation().getWorld();
								double X = p.getLocation().getX();
								double Y = p.getLocation().getY();
								double Z = p.getLocation().getZ();
								ArenasFile.getData().set("Arenas." + ArenaName + ".Spawns.Blue.World", world.getName());
								ArenasFile.getData().set("Arenas." + ArenaName + ".Spawns.Blue.X", X);
								ArenasFile.getData().set("Arenas." + ArenaName + ".Spawns.Blue.Y", Y);
								ArenasFile.getData().set("Arenas." + ArenaName + ".Spawns.Blue.Z", Z);
								ArenasFile.saveData();
								ArenasFile.reloadData();
								p.sendMessage(Main.codSignature + "§6Set §9blue §6spawn for arena§b " + ArenaName);
								
									if (ArenasFile.getData().getString("Arenas." + ArenaName + ".Type").contentEquals("CTF")) {
										if (getArena.getRedSpawn(ArenaName) != null) {
											if (getArena.getFlagLocation("red", ArenaName) == null) {
												if (CreateArenaCommand.creatingArena.get(p) != true) {
													p.sendMessage(Main.codSignature + "§bNow set the §cred §bflag location by typing:");
													p.sendMessage(Main.codSignature + "§7- §3/cod set§e " + ArenaName + " §3flag §cred");
												}else{
													p.getInventory().clear();
													Items.setItem(p, CreateArenaCommand.redFlagTool, 0);
													p.sendMessage(Main.codSignature + "§3Go to the §cred flag location§3 and right-click with the wool block");
												}
											}else if (getArena.getFlagLocation("blue", ArenaName) == null) {
												if (CreateArenaCommand.creatingArena.get(p) != true) {
													p.sendMessage(Main.codSignature + "§bNow set the §9blue §bflag location by typing:");
													p.sendMessage(Main.codSignature + "§7- §3/cod set§e " + ArenaName + " §3flag §9blue");
												}else{
													p.getInventory().clear();
													Items.setItem(p, CreateArenaCommand.blueFlagTool, 0);
													p.sendMessage(Main.codSignature + "§3Go to the §9blue flag location§3 and right-click with the wool block");
												}
											}
										}else{
											if (CreateArenaCommand.creatingArena.get(p) != true) {
												p.sendMessage(Main.codSignature + "§bNow set the §cred §bspawn by typing:");
												p.sendMessage(Main.codSignature + "§7- §3/cod set§e " + ArenaName + " §3spawn §cred");
											}else{
												p.getInventory().clear();
												Items.setItem(p, CreateArenaCommand.redSpawnTool, 0);
												p.sendMessage(Main.codSignature + "§3Go to the §cred team's §3spawn and right-click with the wool block");
											}
										}
									}else{
										if (getArena.getRedSpawn(ArenaName) == null) {
											if (CreateArenaCommand.creatingArena.get(p) != true) {
												p.sendMessage(Main.codSignature + "§bNow set the §cred §bspawn by typing:");
												p.sendMessage(Main.codSignature + "§7- §3/cod set§e " + ArenaName + " §3spawn §cred");
											}else{
												p.getInventory().clear();
												Items.setItem(p, CreateArenaCommand.redSpawnTool, 0);
												p.sendMessage(Main.codSignature + "§3Go to the §cred team's §3spawn and right-click with the wool block");
											}
										}
									}
									
									if (ArenaInfoCommand.arenaReadyForEnable(ArenaName)) {
										if (CreateArenaCommand.creatingArena.get(p) == true) {
											p.getInventory().clear();
											Items.setItem(p, CreateArenaCommand.enabledTool, 0);
											p.sendMessage(Main.codSignature + "§aRight click to enable the arena");
										}else{
											p.sendMessage(Main.codSignature + "§bYou can now §aenable the arena§b by typing:");
											p.sendMessage(Main.codSignature + "§7- §3/cod set§e " + ArenaName + " §3enabled");
										}
									}
							}else{
								p.sendMessage(Main.codSignature + "§cArena type must be §4TDM§c, §4INFECT§c, §4KC§c, or §4CTF");
							}
						}else if (args[3].equalsIgnoreCase("red")) {
							if (ArenasFile.getData().getString("Arenas." + ArenaName + ".Type").contentEquals("TDM") || ArenasFile.getData().getString("Arenas." + ArenaName + ".Type").contentEquals("INFECT") || ArenasFile.getData().getString("Arenas." + ArenaName + ".Type").contentEquals("CTF") || ArenasFile.getData().getString("Arenas." + ArenaName + ".Type").contentEquals("KC")) {
								World world = p.getLocation().getWorld();
								double X = p.getLocation().getX();
								double Y = p.getLocation().getY();
								double Z = p.getLocation().getZ();
								ArenasFile.getData().set("Arenas." + ArenaName + ".Spawns.Red.World", world.getName());
								ArenasFile.getData().set("Arenas." + ArenaName + ".Spawns.Red.X", X);
								ArenasFile.getData().set("Arenas." + ArenaName + ".Spawns.Red.Y", Y);
								ArenasFile.getData().set("Arenas." + ArenaName + ".Spawns.Red.Z", Z);
								ArenasFile.saveData();
								ArenasFile.reloadData();
								p.sendMessage(Main.codSignature + "§6Set §cred §6spawn for arena§b " + ArenaName);
								
								if (ArenasFile.getData().getString("Arenas." + ArenaName + ".Type").contentEquals("CTF")) {
									if (getArena.getBlueSpawn(ArenaName) != null) {
										if (getArena.getFlagLocation("red", ArenaName) == null) {
											if (CreateArenaCommand.creatingArena.get(p) != true) {
												p.sendMessage(Main.codSignature + "§bNow set the §cred §bflag location by typing:");
												p.sendMessage(Main.codSignature + "§7- §3/cod set§e " + ArenaName + " §3flag §cred");
											}else{
												p.getInventory().clear();
												Items.setItem(p, CreateArenaCommand.redFlagTool, 0);
												p.sendMessage(Main.codSignature + "§3Go to the §cred flag location§3 and right-click with the wool block");
											}
										}else if (getArena.getFlagLocation("blue", ArenaName) == null) {
											if (CreateArenaCommand.creatingArena.get(p) != true) {
												p.sendMessage(Main.codSignature + "§bNow set the §9blue §bflag location by typing:");
												p.sendMessage(Main.codSignature + "§7- §3/cod set§e " + ArenaName + " §3flag §9blue");
											}else{
												p.getInventory().clear();
												Items.setItem(p, CreateArenaCommand.blueFlagTool, 0);
												p.sendMessage(Main.codSignature + "§3Go to the §9blue flag location§3 and right-click with the wool block");
											}
										}
									}else{
										if (CreateArenaCommand.creatingArena.get(p) != true) {
											p.sendMessage(Main.codSignature + "§bNow set the §9blue §bspawn by typing:");
											p.sendMessage(Main.codSignature + "§7- §3/cod set§e " + ArenaName + " §3spawn §9blue");
										}else{
											p.getInventory().clear();
											Items.setItem(p, CreateArenaCommand.blueSpawnTool, 0);
											p.sendMessage(Main.codSignature + "§3Go to the §9blue team's §3spawn and right-click with the wool block");
										}
									}
								}else{
									if (getArena.getBlueSpawn(ArenaName) == null) {
										if (CreateArenaCommand.creatingArena.get(p) != true) {
											p.sendMessage(Main.codSignature + "§bNow set the §9blue §bspawn by typing:");
											p.sendMessage(Main.codSignature + "§7- §3/cod set§e " + ArenaName + " §3spawn §9blue");
										}else{
											p.getInventory().clear();
											Items.setItem(p, CreateArenaCommand.blueSpawnTool, 0);
											p.sendMessage(Main.codSignature + "§3Go to the §9blue team's §3spawn and right-click with the wool block");
										}
									}
								}
								
								if (ArenaInfoCommand.arenaReadyForEnable(ArenaName)) {
									if (CreateArenaCommand.creatingArena.get(p) == true) {
										p.getInventory().clear();
										Items.setItem(p, CreateArenaCommand.enabledTool, 0);
										p.sendMessage(Main.codSignature + "§aRight click to enable the arena");
									}else{
										p.sendMessage(Main.codSignature + "§bYou can now §aenable the arena§b by typing:");
										p.sendMessage(Main.codSignature + "§7- §3/cod set§e " + ArenaName + " §3enabled");
									}
								}
							}else{
								p.sendMessage(Main.codSignature + "§cArena type must be §4TDM§c, §4INFECT§c, §4KC§c, or §4CTF");
							}
						}else if (args[3].equalsIgnoreCase("next")) {
							if (ArenasFile.getData().contains("Arenas." + ArenaName + ".Spawns")) {
							
					        int highestNumber = 1;
					        
							while (ArenasFile.getData().get("Arenas." + ArenaName + ".Spawns." + highestNumber) != null)
					        {
					          highestNumber++;
					        }
							
								String world = p.getWorld().getName();
								double X = p.getLocation().getX();
								double Y = p.getLocation().getY();
								double Z = p.getLocation().getZ();
								
								ArenasFile.getData().set("Arenas." + ArenaName + ".Spawns." + highestNumber + ".World", world);
								ArenasFile.getData().set("Arenas." + ArenaName + ".Spawns." + highestNumber + ".X", X);
								ArenasFile.getData().set("Arenas." + ArenaName + ".Spawns." + highestNumber + ".Y", Y);
								ArenasFile.getData().set("Arenas." + ArenaName + ".Spawns." + highestNumber + ".Z", Z);
								ArenasFile.saveData();
								ArenasFile.reloadData();
								p.sendMessage(Main.codSignature + "§6Set spawn §b#" + highestNumber + " §6for arena§3 " + ArenaName);
							
								if (ArenasFile.getData().getString("Arenas." + ArenaName + ".Type").contentEquals("ONEIN")) {
									if (getArena.getSpectatorSpawn(ArenaName) == null) {
										if (CreateArenaCommand.creatingArena.get(p) != true) {
											p.sendMessage(Main.codSignature + "§bNow set a spectator spawn by typing:");
											p.sendMessage(Main.codSignature + "§7- §3/cod set§e " + ArenaName + " §3spec");
										}else{
											p.getInventory().clear();
											Items.setItem(p, CreateArenaCommand.oneinSpecTool, 8);
											p.sendMessage(Main.codSignature + "§3Go to the §aspectator's §3spawn and right-click with the wool block");
										}
									}
								}
							}else{
								String world = p.getWorld().getName();
								double X = p.getLocation().getX();
								double Y = p.getLocation().getY();
								double Z = p.getLocation().getZ();
								
								ArenasFile.getData().set("Arenas." + ArenaName + ".Spawns.1.World", world);
								ArenasFile.getData().set("Arenas." + ArenaName + ".Spawns.1.X", X);
								ArenasFile.getData().set("Arenas." + ArenaName + ".Spawns.1.Y", Y);
								ArenasFile.getData().set("Arenas." + ArenaName + ".Spawns.1.Z", Z);
								ArenasFile.saveData();
								ArenasFile.reloadData();
								p.sendMessage(Main.codSignature + "§6Set spawn §b#1 §6for arena§3 " + ArenaName);
								
									if (ArenasFile.getData().getString("Arenas." + ArenaName + ".Type").contentEquals("ONEIN")) {
										if (getArena.getSpectatorSpawn(ArenaName) == null) {
											p.sendMessage(Main.codSignature + "§bNow set a spectator spawn by typing:");
											p.sendMessage(Main.codSignature + "§7- §3/cod set§e " + ArenaName + " §3spec");
										}
									}
									
									if (ArenaInfoCommand.arenaReadyForEnable(ArenaName)) {
										if (CreateArenaCommand.creatingArena.get(p) == true) {
											p.getInventory().setItem(8, CreateArenaCommand.enabledTool);
											p.updateInventory();
											p.sendMessage(Main.codSignature + "§aRight click the blaze rod to enable the arena if you have added all of the spawns");
										}else{
											p.sendMessage(Main.codSignature + "§bYou can now §aenable the arena§b by typing:");
											p.sendMessage(Main.codSignature + "§7- §3/cod set§e " + ArenaName + " §3enabled");
										}
									}
							}
						}else{
							String spawnNumber = args[3];
							
							String world = p.getWorld().getName();
							double X = p.getLocation().getX();
							double Y = p.getLocation().getY();
							double Z = p.getLocation().getZ();
							
							if (ArenasFile.getData().contains("Arenas." + ArenaName + ".Spawns." + spawnNumber)) {
								ArenasFile.getData().set("Arenas." + ArenaName + ".Spawns." + spawnNumber + ".World", world);
								ArenasFile.getData().set("Arenas." + ArenaName + ".Spawns." + spawnNumber + ".X", X);
								ArenasFile.getData().set("Arenas." + ArenaName + ".Spawns." + spawnNumber + ".Y", Y);
								ArenasFile.getData().set("Arenas." + ArenaName + ".Spawns." + spawnNumber + ".Z", Z);
								ArenasFile.saveData();
								ArenasFile.reloadData();
								p.sendMessage(Main.codSignature + "§6Set spawn§b #" + spawnNumber + " §6to your current position");
							}else{
								p.sendMessage(Main.codSignature + "§cThere is no spawn§4 #" + spawnNumber);
							}
							
							if (ArenaInfoCommand.arenaReadyForEnable(ArenaName)) {
								if (CreateArenaCommand.creatingArena.get(p) == true) {
									p.getInventory().clear();
									Items.setItem(p, CreateArenaCommand.enabledTool, 0);
									p.sendMessage(Main.codSignature + "§aRight click to enable the arena");
								}else{
									p.sendMessage(Main.codSignature + "§bYou can now §aenable the arena§b by typing:");
									p.sendMessage(Main.codSignature + "§7- §3/cod set§e " + ArenaName + " §3enabled");
								}
							}
						}
					}else{
						p.sendMessage(Main.codSignature + "§cPlease make sure you are typing the command correctly:");
						p.sendMessage(" §7- §bType §3/cod set [Arena Name] spawn [§9blue§3|§cred§3] for TDM, CTF, KC, or INFECT");
						p.sendMessage(" §7- §bType §3/cod set [Arena Name] spawn next for GUN, ONEIN, or FFA");
					}
				}
	
	private static void SetEnabled(Player p, String[] args, String ArenaName) {
		
		if (!(getArena.getName(ArenaName).isEmpty() && getArena.getType(ArenaName).isEmpty())) {
			if (getArena.getType(ArenaName).equals("TDM") || getArena.getType(ArenaName).equals("INFECT") || getArena.getType(ArenaName).equals("CTF") || getArena.getType(ArenaName).equals("KC")) {
				if (ArenasFile.getData().contains("Arenas." + ArenaName + ".Spawns.Red") && ArenasFile.getData().contains("Arenas." + ArenaName + ".Spawns.Blue")) {
					if (getArena.getType(ArenaName).equals("CTF")) {
						if ((!(ArenasFile.getData().contains("Arenas." + ArenaName + ".Flags.Red"))) || (!(ArenasFile.getData().contains("Arenas." + ArenaName + ".Flags.Blue")))) {
							p.sendMessage(Main.codSignature + "§cA flag location is missing");
							return;
						}
					}
					
			        int highestNumber = 1;
			        
					while (EnabledArenasFile.getData().get("EnabledArenas." + highestNumber) != null) {
						if (EnabledArenasFile.getData().get("EnabledArenas." + highestNumber).equals(ArenaName)) {
							p.sendMessage(Main.codSignature + "§cArena§4 " + ArenaName + " §cis already enabled!");
							return;
						}
						highestNumber++;
			        }
					
					if (getArena.getType(ArenaName).equalsIgnoreCase("GUN")) {
						if (GGgunAPI.Guns.isEmpty()) {
							p.sendMessage(Main.codSignature + "§cAt least one gun-game gun needs to be created");
							return;
						}
					}
					
					EnabledArenasFile.getData().set("EnabledArenas." + highestNumber, ArenaName);
					EnabledArenasFile.saveData();
					EnabledArenasFile.reloadData();
					p.sendMessage(Main.codSignature + "§aArena§2 " + ArenaName + " §aenabled!");
					return;
					
				}else{
					p.sendMessage(Main.codSignature + "§cA spawn needs to be set");
					return;
				}
			}else if (getArena.getType(ArenaName).equals("FFA") || getArena.getType(ArenaName).equals("GUN") || getArena.getType(ArenaName).equals("ONEIN")) {
				if (ArenasFile.getData().contains("Arenas." + ArenaName + ".Spawns")) {
					if (getArena.getType(ArenaName).equals("ONEIN")) {
						if (!(ArenasFile.getData().contains("Arenas." + ArenaName + ".Spectator.Spawn"))) {
							p.sendMessage(Main.codSignature + "§cA spectator spawn is missing");
							return;
						}
					}
					
					if (getArena.getType(ArenaName).equals("GUN")) {
						if (GGgunAPI.Guns.isEmpty()) {
							p.sendMessage(Main.codSignature + "§cGun game guns are missing. Please create some by typing §4/cod creategggun");
							return;
						}
					}
					
			        int highestNumber = 1;
			        
					while (EnabledArenasFile.getData().get("EnabledArenas." + highestNumber) != null)
			        {
			          highestNumber++;
			        }
					
					EnabledArenasFile.getData().set("EnabledArenas." + highestNumber, ArenaName);
					EnabledArenasFile.saveData();
					EnabledArenasFile.reloadData();
					p.sendMessage(Main.codSignature + "§aArena§2 " + ArenaName + " §aenabled!");
					return;
					
				}else{
					p.sendMessage(Main.codSignature + "§cPlease set a spawn");
					return;
				}
			}else{
				return;
			}
		}
	}
	
	private static void setFlagLocation(Player p, String[] args, String ArenaName) {
		if (args.length == 4) {
			if (args[3].equalsIgnoreCase("blue")) {
				if (ArenasFile.getData().getString("Arenas." + ArenaName + ".Type").contentEquals("CTF")) {
					World world = p.getLocation().getWorld();
					double X = p.getLocation().getBlockX() + 0.5;
					double Y = p.getLocation().getBlockY() + 1;
					double Z = p.getLocation().getBlockZ() + 0.5;
					ArenasFile.getData().set("Arenas." + ArenaName + ".Flags.Blue.World", world.getName());
					ArenasFile.getData().set("Arenas." + ArenaName + ".Flags.Blue.X", X);
					ArenasFile.getData().set("Arenas." + ArenaName + ".Flags.Blue.Y", Y);
					ArenasFile.getData().set("Arenas." + ArenaName + ".Flags.Blue.Z", Z);
					ArenasFile.saveData();
					ArenasFile.reloadData();
					p.sendMessage(Main.codSignature + "§6Set §9blue §6flag location for arena§b " + ArenaName);
					
					if (getArena.getFlagLocation("red", ArenaName) == null) {
						if (CreateArenaCommand.creatingArena.get(p) != true) {
							p.sendMessage(Main.codSignature + "§bNow set the §cred §bflag location by typing:");
							p.sendMessage(Main.codSignature + "§7- §3/cod set§e " + ArenaName + " §3flag §cred");
						}else{
							p.getInventory().clear();
							Items.setItem(p, CreateArenaCommand.redFlagTool, 0);
							p.sendMessage(Main.codSignature + "§3Go to the §cred flag location§3 and right-click with the wool block");
						}
					}
				}else{
					p.sendMessage(Main.codSignature + "§cArena type must be §4CTF");
				}
				
				if (ArenaInfoCommand.arenaReadyForEnable(ArenaName)) {
					if (CreateArenaCommand.creatingArena.get(p) == true) {
						p.getInventory().clear();
						Items.setItem(p, CreateArenaCommand.enabledTool, 0);
						p.sendMessage(Main.codSignature + "§aRight click to enable the arena");
					}else{
						p.sendMessage(Main.codSignature + "§bYou can now §aenable the arena§b by typing:");
						p.sendMessage(Main.codSignature + "§7- §3/cod set§e " + ArenaName + " §3enabled");
					}
				}
			}else if (args[3].equalsIgnoreCase("red")) {
				if (ArenasFile.getData().getString("Arenas." + ArenaName + ".Type").contentEquals("CTF")) {
					World world = p.getLocation().getWorld();
					double X = p.getLocation().getBlockX() + 0.5;
					double Y = p.getLocation().getBlockY() + 1;
					double Z = p.getLocation().getBlockZ() + 0.5;
					ArenasFile.getData().set("Arenas." + ArenaName + ".Flags.Red.World", world.getName());
					ArenasFile.getData().set("Arenas." + ArenaName + ".Flags.Red.X", X);
					ArenasFile.getData().set("Arenas." + ArenaName + ".Flags.Red.Y", Y);
					ArenasFile.getData().set("Arenas." + ArenaName + ".Flags.Red.Z", Z);
					ArenasFile.saveData();
					ArenasFile.reloadData();
					p.sendMessage(Main.codSignature + "§6Set §cred §6flag location for arena§b " + ArenaName);
					
					if (getArena.getFlagLocation("blue", ArenaName) == null) {
						if (CreateArenaCommand.creatingArena.get(p) != true) {
							p.sendMessage(Main.codSignature + "§bNow set the §9blue §bflag location by typing:");
							p.sendMessage(Main.codSignature + "§7- §3/cod set§e " + ArenaName + " §3flag §9blue");
						}else{
							p.getInventory().clear();
							Items.setItem(p, CreateArenaCommand.blueFlagTool, 0);
							p.sendMessage(Main.codSignature + "§3Go to the §9blue flag location§3 and right-click with the wool block");
						}
					}
				}else{
					p.sendMessage(Main.codSignature + "§cArena type must be §4CTF");
				}
				
				if (ArenaInfoCommand.arenaReadyForEnable(ArenaName)) {
					if (CreateArenaCommand.creatingArena.get(p) == true) {
						p.getInventory().clear();
						Items.setItem(p, CreateArenaCommand.enabledTool, 0);
						p.sendMessage(Main.codSignature + "§aRight click to enable the arena");
					}else{
						p.sendMessage(Main.codSignature + "§bYou can now §aenable the arena§b by typing:");
						p.sendMessage(Main.codSignature + "§7- §3/cod set§e " + ArenaName + " §3enabled");
					}
				}
			}
		}
	}
	
	private static void setSpectator(Player p, String[] args, String ArenaName) {
		if (args.length == 3) {
			if (args[2].equalsIgnoreCase("spec") || args[2].equalsIgnoreCase("spectator")) {
				if (ArenasFile.getData().getString("Arenas." + ArenaName + ".Type").contentEquals("ONEIN")) {
					World world = p.getLocation().getWorld();
					double X = p.getLocation().getBlockX();
					double Y = p.getLocation().getBlockY();
					double Z = p.getLocation().getBlockZ();
					ArenasFile.getData().set("Arenas." + ArenaName + ".Spectator.Spawn.World", world.getName());
					ArenasFile.getData().set("Arenas." + ArenaName + ".Spectator.Spawn.X", X);
					ArenasFile.getData().set("Arenas." + ArenaName + ".Spectator.Spawn.Y", Y);
					ArenasFile.getData().set("Arenas." + ArenaName + ".Spectator.Spawn.Z", Z);
					ArenasFile.saveData();
					ArenasFile.reloadData();
					p.sendMessage(Main.codSignature + "§6Set spectator location for arena§b " + ArenaName);
					
					if (!(ArenasFile.getData().contains("Arenas." + ArenaName + ".Spawns.1"))) {
						if (CreateArenaCommand.creatingArena.get(p) != true) {
							p.sendMessage(Main.codSignature + "§bNow add §dspawns §bby typing:");
							p.sendMessage(Main.codSignature + "§7- §3/cod set§e " + ArenaName + " §3spawn next");
						}else{
							p.getInventory().clear();
							Items.setItem(p, CreateArenaCommand.ffaSpawnTool, 0);
							p.sendMessage(Main.codSignature+ "§3Add spawns by right-clicking with the wool block");
						}
					}
					
					if (ArenaInfoCommand.arenaReadyForEnable(ArenaName)) {
						if (CreateArenaCommand.creatingArena.get(p) == true) {
							p.getInventory().clear();
							Items.setItem(p, CreateArenaCommand.enabledTool, 0);
							p.sendMessage(Main.codSignature + "§aRight click to enable the arena");
						}else{
							p.sendMessage(Main.codSignature + "§bYou can now §aenable the arena§b by typing:");
							p.sendMessage(Main.codSignature + "§7- §3/cod set§e " + ArenaName + " §3enabled");
						}
					}
				}else{
					p.sendMessage(Main.codSignature + "§cArena type must be §4ONEIN");
				}
			}
		}
	}
	
	private static void setDisabled(Player p, String[] args, String Arena) {
		if (args.length == 3) {
			if (args[2].equals("disabled") || args[2].equals("off")) {
				ArrayList<String> enabledArenas = new ArrayList<String>();
				
				int nextInt = 1;
				
				while (EnabledArenasFile.getData().get("EnabledArenas." + nextInt) != null) {
					enabledArenas.add(EnabledArenasFile.getData().getString("EnabledArenas." + nextInt));
					nextInt++;
				}
				
				if (enabledArenas.contains(Arena)) {
					enabledArenas.remove(Arena);
				
					EnabledArenasFile.getData().set("EnabledArenas", null);
					
					int highestNum = 1;
					
					if (!(enabledArenas.isEmpty())) {
						for (int i = 0 ; i < enabledArenas.size() ; i++) {
							EnabledArenasFile.getData().set("EnabledArenas." + highestNum, enabledArenas.get(i));
							highestNum++;
						}
					}
					
					EnabledArenasFile.saveData();
					EnabledArenasFile.reloadData();
					
					p.sendMessage(Main.codSignature + "§aArena§2 " + Arena + " §adisabled");
					
					PickRandomArena.UpcomingArena = null;
					
					ReloadCommand.reloadWaitingPlayers(Main.codSignature + "§cYou left due to an arena disable");
					ReloadCommand.reloadPlayingPlayers(Main.codSignature + "§cYou left due to an arena disable");
				}else{
					p.sendMessage(Main.codSignature + "§cArena§4 " + Arena + " §cis not enabled");
				}
			}
		}
	}
}