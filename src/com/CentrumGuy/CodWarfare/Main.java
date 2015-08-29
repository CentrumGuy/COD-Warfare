package com.CentrumGuy.CodWarfare;
	
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.CentrumGuy.CodWarfare.Updater.UpdateResult;
import com.CentrumGuy.CodWarfare.Updater.UpdateType;
import com.CentrumGuy.CodWarfare.Achievements.AchievementsAPI;
import com.CentrumGuy.CodWarfare.Arena.BaseArena;
import com.CentrumGuy.CodWarfare.Arena.BaseArena.ArenaState;
import com.CentrumGuy.CodWarfare.Arena.GGgunAPI;
import com.CentrumGuy.CodWarfare.Arena.LeaveArena;
import com.CentrumGuy.CodWarfare.Clans.MainClan;
import com.CentrumGuy.CodWarfare.Commands.CreateArenaCommand;
import com.CentrumGuy.CodWarfare.Commands.CreateGunCommand;
import com.CentrumGuy.CodWarfare.Commands.MainCommand;
import com.CentrumGuy.CodWarfare.Files.AchievementsFile;
import com.CentrumGuy.CodWarfare.Files.ArenasFile;
import com.CentrumGuy.CodWarfare.Files.AvailableGunsFile;
import com.CentrumGuy.CodWarfare.Files.ClansFile;
import com.CentrumGuy.CodWarfare.Files.EnabledArenasFile;
import com.CentrumGuy.CodWarfare.Files.GunGameFile;
import com.CentrumGuy.CodWarfare.Files.GunsFile;
import com.CentrumGuy.CodWarfare.Files.JoinedCODFile;
import com.CentrumGuy.CodWarfare.Files.KitFile;
import com.CentrumGuy.CodWarfare.Files.LangFile;
import com.CentrumGuy.CodWarfare.Files.LobbyFile;
import com.CentrumGuy.CodWarfare.Files.PerksFile;
import com.CentrumGuy.CodWarfare.Files.ScoresFile;
import com.CentrumGuy.CodWarfare.Files.ShopFile;
import com.CentrumGuy.CodWarfare.Files.WeaponsFile;
import com.CentrumGuy.CodWarfare.Interface.ItemsAndInventories;
import com.CentrumGuy.CodWarfare.Interface.JoinCOD;
import com.CentrumGuy.CodWarfare.Interface.Scores;
import com.CentrumGuy.CodWarfare.Inventories.AGPInventory;
import com.CentrumGuy.CodWarfare.Inventories.AGSInventory;
import com.CentrumGuy.CodWarfare.Inventories.Guns;
import com.CentrumGuy.CodWarfare.Inventories.KitInventory;
import com.CentrumGuy.CodWarfare.Inventories.ShopInventoryPrimary;
import com.CentrumGuy.CodWarfare.Inventories.ShopInventorySecondary;
import com.CentrumGuy.CodWarfare.MySQL.MySQL;
import com.CentrumGuy.CodWarfare.OtherLoadout.Lethal;
import com.CentrumGuy.CodWarfare.OtherLoadout.Tactical;
import com.CentrumGuy.CodWarfare.OtherLoadout.WeaponUtils;
import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;
import com.CentrumGuy.CodWarfare.SpecialWeapons.AirStrike;
import com.CentrumGuy.CodWarfare.SpecialWeapons.Dogs;
import com.CentrumGuy.CodWarfare.SpecialWeapons.ElectroMagneticPulse;
import com.CentrumGuy.CodWarfare.SpecialWeapons.Nuke;
import com.CentrumGuy.CodWarfare.Utilities.ColorCodes;
import com.CentrumGuy.CodWarfare.Utilities.IChatMessage;
import com.CentrumGuy.CodWarfare.Utilities.SendCoolMessages;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.shampaggon.crackshot.CSUtility;

	/**
	 * @author CentrumGuy
	 * @category Bukkit Plugin
	 * If you are seeing this message and do not have
	 * permission from CentrumGuy to use or modify this plugin's code,
	 * please ask CentrumGuy in a private message whether you can use this
	 * code. This is a warning. If you don't have permission you will face
	 * legal consequences. This is your last warning. Please stop now!
	 */
	
	/* 
	 * TODO:
	 * Add guns (Ray)
	 * Give guns and set level through command
	 * Pass deaths as player-kill-player
	 * 
	 * Subject due to change...
	 * 
	 */
	
	public class Main extends JavaPlugin {
		
		public static String version = "v4.0.0";
		
		  public static Location Lobby;
		  public static ItemStack UpdateBook;
		  public static ItemStack shoptool;
		  public static ItemStack knife;
		  public static ItemStack tryGuns;
		  
		  public static ArrayList<Player> PlayingPlayers;
		  public static ArrayList<Player> WaitingPlayers;
		  public static ArrayList<Player> invincible = new ArrayList<Player>();
		  public static ArrayList<Player> noHungerLoss = new ArrayList<Player>();
		  
		  public static String codSignature = "§8§l[§4§lCOD-War§8§l]§r ";
		  
		  public static HashMap<String, Scoreboard> LobbyScoreboard = new HashMap<String, Scoreboard>();
		  public static HashMap<String, Scoreboard> GameScoreboard = new HashMap<String, Scoreboard>();
		  public static HashMap<String, Score> highestKillstreak = new HashMap<String, Score>();
		  public static HashMap<String, Score> LobbyLevelScore = new HashMap<String, Score>();
		  public static HashMap<String, Score> LobbyCreditsScore = new HashMap<String, Score>();
		  public static HashMap<String, Score> GameLevelScore = new HashMap<String, Score>();
		  public static HashMap<String, Score> GameCreditsScore = new HashMap<String, Score>();
		  public static HashMap<String, Score> LobbyKillsScore = new HashMap<String, Score>();
		  public static HashMap<String, Score> LobbyDeathsScore = new HashMap<String, Score>();
		  public static HashMap<String, Score> GameKillsScore = new HashMap<String, Score>();
		  public static HashMap<String, Score> GameDeathsScore = new HashMap<String, Score>();
		  public static HashMap<String, Score> GameKillStreakScore = new HashMap<String, Score>();
		  
		  public static boolean extraCountdown;
		  public static int gameTime = 270;
		  public static int lobbyTime = 70;
		  public static boolean testGuns = true;
		  public static String header = "&b" + Bukkit.getServer().getName();
		  public static String footer = "&e" + "Running §6COD-Warfare";
		  public static boolean ONEINspectate = false;
		  public static boolean CrackShot = false;
		  public static int ClanCost = 700;
		  public static int min_Players = 2;
		  public static int max_Players = 0;
		  public static boolean spamDetector = false;
		  public static boolean blockCMDs = false;
		  public static List<String> cmdList = new ArrayList<String>();
		  public static boolean exoJump = true;
		  public static boolean weapons = true;
		  public static boolean prefixGM = false;
		  
		  public static CSUtility CrackShotAPI = null;
		  
		  public static boolean extras;
		  public static boolean mySQL;
		  
		  /*public static CODTeam red;
		  public static CODTeam blue;
		  public static CODTeam purple;
		  public static CODTeam lobby;*/
		  
		  public static boolean updateAvailable = false;
		  
		  public static File file = null;
		  
		  public static HashMap<Player, String> dispName = new HashMap<Player, String>();

		  private static String getFileVersion(String version) {
			  version = StringUtils.remove(version, ThisPlugin.getPlugin().getName() + " v");
			  version = StringUtils.remove(version, " ");
			  return version;
		  }
		  
		  @SuppressWarnings("unused")
		  private ProtocolManager protocolManager;
		  
		  public void onLoad() {
		      protocolManager = ProtocolLibrary.getProtocolManager();
		  }
		  
		@Override
		public void onEnable() {
			mySQL = getConfig().getBoolean("MySQL.Enabled");
			MySQL.mySQL = mySQL;
			
			version = getDescription().getVersion();
				
			file = this.getFile();
			  
			getServer().getMessenger().registerOutgoingPluginChannel(this, "MC|RPack");
			
			ThisPlugin.getPlugin().saveDefaultConfig();
			ThisPlugin.getPlugin().reloadConfig();
			
			Bukkit.getConsoleSender().sendMessage("COD-Warfare version " + version + " enabled");
			  
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
				public void run() {
					ConsoleCommandSender cs = Bukkit.getConsoleSender();
					cs.sendMessage("§b§l=================== §a§lCOD-Warfare Information §b§l===================");
					cs.sendMessage("§b» §eCOD-Warfare version:§c COD-Warfare v" + version);
					if (getConfig().getBoolean("Updater")) {
						Updater updater = new Updater(ThisPlugin.getPlugin(), 71948, getFile(), UpdateType.NO_DOWNLOAD, false);
						if (updater.getResult() == UpdateResult.UPDATE_AVAILABLE) {
							cs.sendMessage("§b» §cCOD-Warfare §cv" + getFileVersion(updater.getLatestName()) + "§e is now available for download");
							cs.sendMessage("§b» §eDownload it at:");
							cs.sendMessage("§b  §9" + updater.getLatestFileLink());
						}else{
							cs.sendMessage("§b» §eYou have the latest version of COD-Warfare");
							cs.sendMessage("§b» §eNo update available");
						}
					}else{
						cs.sendMessage("§b» §eCould not check for update because updater is disabled");
					}
					cs.sendMessage("§a§l«»«»«»«»«»«»«»«»«»«»§c§l License And Agreement §a§l§m«»«»«»«»«»«»«»«»«»«»");
					cs.sendMessage("§e§l1.§d Do not modify, edit, change, or alter this plugin's code");
					cs.sendMessage("§e§l2.§d Do not redistribute or claim this plugin as your work");
					cs.sendMessage("§e§l3.§d Do not use or copy this plugin's code");
					cs.sendMessage("§e§l4.§d Do not decompile the plugin");
					cs.sendMessage("§b§l===============================================================");
				}
			}, 1L);
			
			Bukkit.getServer().getPluginManager().registerEvents(new Listeners(), this);
			
			PlayingPlayers = new ArrayList <Player>();
			WaitingPlayers = new ArrayList <Player>();
			
			knife = new ItemStack(Material.IRON_SWORD);
			ItemMeta knifeMeta = knife.getItemMeta();
			knifeMeta.setDisplayName("§eKnife");
			knife.setItemMeta(knifeMeta);
			
			shoptool = new ItemStack(Material.EYE_OF_ENDER);
			ItemMeta shopmeta = shoptool.getItemMeta();
			ArrayList<String> shoplore = new ArrayList<String>();
			shopmeta.setDisplayName("§4§lGun Menu");
			shoplore.add(ChatColor.GOLD + "When clicked, this item will");
			shoplore.add(ChatColor.GOLD + "open your gun menu");
			shopmeta.setLore(shoplore);
			shoptool.setItemMeta(shopmeta);
			
			tryGuns = new ItemStack(Material.CHEST);
			ItemMeta tryMeta = tryGuns.getItemMeta();
			ArrayList<String> tryLore = new ArrayList<String>();
			tryMeta.setDisplayName("§6§lTry Guns");
			tryLore.add("§eClick this item to try");
			tryLore.add("§eguns");
			tryMeta.setLore(tryLore);
			tryGuns.setItemMeta(tryMeta);
			
			MainCommand cmd = new MainCommand();
			getCommand("cod").setExecutor(cmd);
			getCommand("callofduty").setExecutor(cmd);
			getCommand("codwar").setExecutor(cmd);
			getCommand("codwarfare").setExecutor(cmd);
			getCommand("warfare").setExecutor(cmd);
			getCommand("callofdutywarfare").setExecutor(cmd);
			getCommand("callofdutywar").setExecutor(cmd);
			
			ArenasFile.setup(this);
			LobbyFile.setup(this);
			EnabledArenasFile.setup(this);
			ShopFile.setup(this);
			KitFile.setup(this);
			AvailableGunsFile.setup(this);
			GunsFile.setup(this);
			GunGameFile.setup(this);
			JoinedCODFile.setup(this);
			ScoresFile.setup(this);
			LangFile.setup(this);
			ClansFile.setup(this);
			WeaponsFile.setup(this);
			PerksFile.setup(this);
			AchievementsFile.setup(this);
			
			MainClan.setUp(this);
			
			BaseArena.state = ArenaState.WAITING;
			
			ItemsAndInventories.setUp();
			AchievementsAPI.createAchievements();
			
			GGgunAPI.loadGuns();
			Tactical.loadTacticals();
			Lethal.loadLethals();
			
			extraCountdown = ThisPlugin.getPlugin().getConfig().getBoolean("extraCountdown");
			gameTime = ThisPlugin.getPlugin().getConfig().getInt("gameTime");
			gameTime = gameTime + 1;
			lobbyTime = ThisPlugin.getPlugin().getConfig().getInt("lobbyTime");
			lobbyTime = lobbyTime + 1;
			testGuns = ThisPlugin.getPlugin().getConfig().getBoolean("tryGuns");
			min_Players = ThisPlugin.getPlugin().getConfig().getInt("Min-Players");
			max_Players = ThisPlugin.getPlugin().getConfig().getInt("Max-Players");
			spamDetector = ThisPlugin.getPlugin().getConfig().getBoolean("SpamDetector");
			exoJump = ThisPlugin.getPlugin().getConfig().getBoolean("ExoJump");
			prefixGM = ThisPlugin.getPlugin().getConfig().getBoolean("PrefixGamemode");
			
			String h = ThisPlugin.getPlugin().getConfig().getString("Header");
			String f = ThisPlugin.getPlugin().getConfig().getString("Footer");
			
			h = ColorCodes.change(h, '&');
			f = ColorCodes.change(f, '&');
			
			header = h;
			footer = f;
			
			ONEINspectate = ThisPlugin.getPlugin().getConfig().getBoolean("ONEINspectate");
			
			blockCMDs = ThisPlugin.getPlugin().getConfig().getBoolean("DisableCommands");
			cmdList = ThisPlugin.getPlugin().getConfig().getStringList("CmdList");
			
			if (Bukkit.getServer().getPluginManager().getPlugin("CrackShot") != null) {
				CrackShot = true;
			}else{
				CrackShot = false;
			}
			
			if (Bukkit.getServer().getPluginManager().getPlugin("CODWeapons") != null) {
				weapons = true;
			}else{
				weapons = false;
			}
			
			if (CrackShot) {
				CrackShotAPI = new CSUtility();
			}
			
			if (Bukkit.getPluginManager().getPlugin("extras") == null) {
				extras = false;
			}else{
				extras = true;
			}
			
		    /*TeamManager teamManager = new TeamManager();
		    red = teamManager.registerTeam("red");
		    red.setPrefix(ChatColor.RED + "");
		    
		    blue = teamManager.registerTeam("blue");
		    blue.setPrefix(ChatColor.BLUE + "");
		    
		    purple = teamManager.registerTeam("purple");
		    purple.setPrefix(ChatColor.LIGHT_PURPLE + "");
		    
		    lobby = teamManager.registerTeam("lobby");
		    lobby.setPrefix(ChatColor.YELLOW + "");*/
		    
		    ClanCost = ThisPlugin.getPlugin().getConfig().getInt("ClanCost");
		    
		    ElectroMagneticPulse.setUp();
		    Dogs.setUp();
		    AirStrike.setUp();
		    Nuke.setUp();
		    
		    Guns.loadGuns();
		    
		    if (!(Bukkit.getServer().getOnlinePlayers().isEmpty())) {
		    	for (final Player p : Bukkit.getServer().getOnlinePlayers()) {
		    		CreateArenaCommand.creatingArena.put(p, false);
		    		
		    		CreateGunCommand.gunBuilder.put(p, false);
		    		CreateGunCommand.gunBuilderStep.put(p, 0);
		    		
		    		if (ThisPlugin.getPlugin().getConfig().getBoolean("Updater") && p.isOp()) {
		    			
		    			if (ThisPlugin.getPlugin().getConfig().getBoolean("Updater")) {
		    				Updater updater = new Updater(ThisPlugin.getPlugin(), 71948, Main.file, UpdateType.NO_DOWNLOAD, false);
		    				if (updater.getResult() == UpdateResult.UPDATE_AVAILABLE) {
		    					p.sendMessage("§b§m================================================");
		    					p.sendMessage("§c§lCOD-Warfare Update:");
		    					p.sendMessage("");
		    					p.sendMessage("§6COD-Warfare §eversion " + getFileVersion(updater.getLatestName()) + " §6is now available for download.");
		    					p.sendMessage("§6If you would like to update your current version,");
		    					IChatMessage m = new IChatMessage("§eType, '/cod update' or ", "§4[click here]").addLoreLine("§bClick to update COD-Warfare").addCommand("/cod update");
		    					m.send(p);
		    					p.sendMessage("§b§m================================================");
		    				}
		    			}
		    		}
		    		
		    		for (Player pp : Bukkit.getOnlinePlayers()) {
		    			if (Main.dispName.get(pp) != null) pp.setPlayerListName(Main.dispName.get(pp));
		    		}
		    		
		    		MainClan.invites.put(p, new ArrayList<String>());
		    		SendCoolMessages.TabHeaderAndFooter(Main.header, Main.footer, p);
		    		
					if (ThisPlugin.getPlugin().getConfig().getBoolean("ServerBased")) {
						BukkitRunnable br = new BukkitRunnable() {
							public void run() {
								JoinCOD.join(true, p, false);
							}
						};
						
						br.runTaskLater(ThisPlugin.getPlugin(), 3L);
					}
		    		
		    		Main.createGameBoard(p);
		    		Main.createLobbyBoard(p);
		    		if (Scores.scoresExist(p)) Scores.loadScores(p);
		    		
		    		Listeners.lastDamager.put(p, null);
		    		
		    		AGPInventory.loadAGP(p);
		    		AGSInventory.loadAGS(p);
		    		
					ItemsAndInventories.setUpPlayer(p);
					ItemsAndInventories.setAvailableGuns(p);
		    		
					AchievementsAPI.setUpPlayer(p);
					AchievementsAPI.unlockJoinAchievements(p);
		    	}
		    }
		}
	
		@SuppressWarnings("deprecation")
		public static void createLobbyBoard(Player p){
			  ScoreboardManager manager = Bukkit.getScoreboardManager();
			  Scoreboard board = manager.getNewScoreboard();
			   
			  Objective objective = board.registerNewObjective("LobbyBoard", "dummy");
			   
			  objective.setDisplaySlot(DisplaySlot.SIDEBAR);
			  objective.setDisplayName("§4§lYour Scores");
			   
			  Score Level = objective.getScore(Bukkit.getOfflinePlayer("§b§lLevel:"));
			  Score Credits = objective.getScore(Bukkit.getOfflinePlayer("§b§lCredits:"));
			  Score LobbyKills = objective.getScore(Bukkit.getOfflinePlayer("§b§lKills:"));
			  Score LobbyDeaths = objective.getScore(Bukkit.getOfflinePlayer("§b§lDeaths:"));
			  Score highestKS = objective.getScore(Bukkit.getOfflinePlayer("§b§lHighest KS:"));
			   
			  Level.setScore(1);
			  Credits.setScore(0);
			  LobbyKills.setScore(0);
			  LobbyDeaths.setScore(0);
			  highestKS.setScore(0);
			   
			  if (LobbyLevelScore.get(p.getName()) == null) LobbyLevelScore.put(p.getName(), Level);
			  if (LobbyCreditsScore.get(p.getName()) == null) LobbyCreditsScore.put(p.getName(), Credits);
			  if (LobbyKillsScore.get(p.getName()) == null) LobbyKillsScore.put(p.getName(), LobbyKills);
			  if (LobbyDeathsScore.get(p.getName()) == null) LobbyDeathsScore.put(p.getName(), LobbyDeaths);
			  if (highestKillstreak.get(p.getName()) == null) highestKillstreak.put(p.getName(), highestKS);
			   
			  if (LobbyScoreboard.get(p.getName()) == null) LobbyScoreboard.put(p.getName(), board);
			  
		  }
		
			@SuppressWarnings("deprecation")
			@Override
			public void onDisable() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					LeaveArena.Leave(p, false, true, false);
					
					ShopInventoryPrimary.savePrimaryShop(p);
					ShopInventorySecondary.saveSecondaryShop(p);
					AGPInventory.saveAGP(p);
					AGSInventory.saveAGS(p);
					KitInventory.saveKit(p);
					Scores.saveScores(p);
					
					WeaponUtils.clearWeapons(p);
					
					CreateGunCommand.endGunCreation(p, true);
					
					for (int i = 0 ; i < Bukkit.getServer().getWorlds().size() ; i++) {
						World w = Bukkit.getServer().getWorlds().get(i);
						for (int ii = 0 ; ii < w.getEntities().size() ; ii++) {
							Entity e = w.getEntities().get(ii);
							if (e.hasMetadata("CODnoPickup") || e.hasMetadata("codredflag") || e.hasMetadata("codblueflag") || e.hasMetadata("codRedTag") || e.hasMetadata("codBlueTag") || e.hasMetadata("codAllowHit")) e.remove();
						}
					}
					
					if (CreateArenaCommand.creatingArena.get(p) == true) {
						CreateArenaCommand.arenaCreating.put(p, null);
						p.getInventory().clear();
						p.getInventory().setContents(CreateArenaCommand.savedInventory.get(p));
						CreateArenaCommand.creatingArena.put(p, false);
						p.sendMessage(Main.codSignature + "§7You left arena creator mode");
						p.updateInventory();
					}
				}
			}
		  
			@SuppressWarnings("deprecation")
			public static void createGameBoard(final Player p){
				  final ScoreboardManager manager = Bukkit.getScoreboardManager();
				  
				  Bukkit.getServer().getScheduler().runTask(ThisPlugin.getPlugin(), new Runnable() {
						@Override
						public void run() {
							Scoreboard board = manager.getNewScoreboard();
				   
							Objective objective = board.registerNewObjective("GameBoard", "dummy");
				   
							objective.setDisplaySlot(DisplaySlot.SIDEBAR);
							objective.setDisplayName("§4§lYour Scores");
				   
							Score Level = objective.getScore(Bukkit.getOfflinePlayer("§b§lLevel:"));
							Score Credits = objective.getScore(Bukkit.getOfflinePlayer("§b§lCredits:"));
							Score GameKills = objective.getScore(Bukkit.getOfflinePlayer("§b§lKills:"));
							Score GameDeaths = objective.getScore(Bukkit.getOfflinePlayer("§b§lDeaths:"));
							Score KillStreak = objective.getScore(Bukkit.getOfflinePlayer("§b§lKill Streak:"));
				   
							Level.setScore(1);
							Credits.setScore(0);
				  			GameKills.setScore(0);
				  			GameDeaths.setScore(0);
				  			KillStreak.setScore(0);
				   
				  			if (GameLevelScore.get(p.getName()) == null) GameLevelScore.put(p.getName(), Level);
				  			if (GameCreditsScore.get(p.getName()) == null) GameCreditsScore.put(p.getName(), Credits);
				  			if (GameKillsScore.get(p.getName()) == null) GameKillsScore.put(p.getName(), GameKills);
				  			if (GameDeathsScore.get(p.getName()) == null) GameDeathsScore.put(p.getName(), GameDeaths);
				  			if (GameKillStreakScore.get(p.getName()) == null) GameKillStreakScore.put(p.getName(), KillStreak);
				   
				  			if (GameScoreboard.get(p.getName()) == null) GameScoreboard.put(p.getName(), board);
						}
					});
			  }
		  
		  public static void setGameBoard(final Player p){
			  if (GameScoreboard.get(p.getName()) == null) {
				  createGameBoard(p);
			  }
			  
			  p.setScoreboard(GameScoreboard.get(p.getName()));
			  
			  GameKillsScore.get(p.getName()).setScore(0);
			  GameDeathsScore.get(p.getName()).setScore(0);
			  GameKillStreakScore.get(p.getName()).setScore(0);
				  
			  Score level = GameLevelScore.get(p.getName());
			  level.setScore(LobbyLevelScore.get(p.getName()).getScore());
			  
			  Score credits = GameCreditsScore.get(p.getName());
			  credits.setScore(LobbyCreditsScore.get(p.getName()).getScore());
		  }
			
		  public static void setLobbyBoard(Player p) {
			  	Scores.saveScores(p);
			  	Scores.loadScores(p);
				p.setScoreboard(LobbyScoreboard.get(p.getName()));
		  }
	}