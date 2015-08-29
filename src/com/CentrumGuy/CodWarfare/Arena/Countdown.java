package com.CentrumGuy.CodWarfare.Arena;

import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Arena.BaseArena.ArenaState;
import com.CentrumGuy.CodWarfare.Interface.ItemsAndInventories;
import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;
import com.CentrumGuy.CodWarfare.Utilities.GameVersion;
import com.CentrumGuy.CodWarfare.Utilities.Language;
import com.CentrumGuy.CodWarfare.Utilities.SendCoolMessages;
import com.CentrumGuy.extras.parkour.Parkour;

public class Countdown {
	
	public static int LobbyTime = Main.lobbyTime;
	public static int GameTime = Main.gameTime;
	
	public static int ChangingLobbyTime = LobbyTime;
	public static int ChangingGameTime = GameTime;
	
	private static int ID1 = -1;
	private static int ID2 = -1;
	
	public static String ArenaType;
	
	public static void StartLobbyCountdown () {
		if (BaseArena.state == ArenaState.WAITING) {
			ChangingLobbyTime = LobbyTime;
		BaseArena.state = ArenaState.COUNTDOWN;
		
		for (World w : Bukkit.getServer().getWorlds()) {
			for (Entity e : w.getEntities()) {
				if (e.hasMetadata("codredflag") || e.hasMetadata("codblueflag")) e.remove();
			}
		}
		
		if (PickRandomArena.UpcomingArena == null) PickRandomArena.PickArena();
		
		if (getArena.getType(PickRandomArena.UpcomingArena) == null) {
			for (Player p : Main.WaitingPlayers) {
				LeaveArena.Leave(p, false, false, true);
				
				p.sendMessage(Language.getString("noArenas", true));
			}
			
			Main.WaitingPlayers.clear();
			
			return;
		}
		
		if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("TDM")) {
			BaseArena.type = BaseArena.ArenaType.TDM;
			ArenaType = "TDM";
		}else if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("CTF")) {
			BaseArena.type = BaseArena.ArenaType.CTF;
			ArenaType = "CTF";
		}else if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("INFECT")) {
			BaseArena.type = BaseArena.ArenaType.INFECT;
			ArenaType = "Infected";
		}else if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("GUN")) {
			BaseArena.type = BaseArena.ArenaType.GUN;
			ArenaType = "Gun Game";
		}else if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("ONEIN")) {
			BaseArena.type = BaseArena.ArenaType.ONEIN;
			ArenaType = "One In The Chamber";
		}else if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("FFA")) {
			BaseArena.type = BaseArena.ArenaType.FFA;
			ArenaType = "FFA";
		}else if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("KC")) {
			BaseArena.type = BaseArena.ArenaType.KC;
			ArenaType = "Kill Confirmed";
		}
		
		ItemsAndInventories.setInformation(PickRandomArena.UpcomingArena, ArenaType);
		
		ID1 = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ThisPlugin.getPlugin(), new Runnable(){
			public void run(){
		    	ChangingLobbyTime--;
				for(int i = 0; i < Main.WaitingPlayers.size(); i++){
				    Player pp = Main.WaitingPlayers.get(i);
				    if (ChangingLobbyTime == 50) {
				    	pp.sendMessage(Language.getString("nextArena1", false));
				    	pp.sendMessage(Language.getString("nextArena2", false) + PickRandomArena.UpcomingArena);
				    	pp.sendMessage(Language.getString("nextArena3", false) + ArenaType);
				    	pp.sendMessage(Language.getString("nextArena4", false));
				    	pp.setLevel(ChangingLobbyTime);
				    }
				    if (ChangingLobbyTime == 30) {
				    	pp.sendMessage(Language.getString("nextArena1", false));
				    	pp.sendMessage(Language.getString("nextArena2", false) + PickRandomArena.UpcomingArena);
				    	pp.sendMessage(Language.getString("nextArena3", false) + ArenaType);
				    	pp.sendMessage(Language.getString("nextArena4", false));
				    	SendCoolMessages.sendTitle(pp, "§6", 10, 20, 10);
				    	SendCoolMessages.sendSubTitle(pp, "§d§l" + ChangingLobbyTime + " Seconds", 10, 20, 10);
				    	pp.setLevel(ChangingLobbyTime);
				    }
				    if (ChangingLobbyTime == 15) {
				    	SendCoolMessages.sendTitle(pp, "§6", 10, 20, 10);
				    	SendCoolMessages.sendSubTitle(pp, "§d§l" + ChangingLobbyTime + " Seconds", 10, 20, 10);
				    	if (!(GameVersion.above47(pp))) {
				    		pp.sendMessage(Main.codSignature + "§d§l" + ChangingLobbyTime + " Seconds");
				    	}
				    	pp.setLevel(ChangingLobbyTime);
				    }
				    if (ChangingLobbyTime == 10) {
				    	pp.sendMessage(Language.getString("nextArena1", false));
				    	pp.sendMessage(Language.getString("nextArena2", false) + PickRandomArena.UpcomingArena);
				    	pp.sendMessage(Language.getString("nextArena3", false) + ArenaType);
				    	pp.sendMessage(Language.getString("nextArena4", false));
				    	pp.setLevel(ChangingLobbyTime);
				    }
				    if (ChangingLobbyTime > 10) {
				    	pp.setLevel(ChangingLobbyTime);
				    }else if (ChangingLobbyTime >= 7 && ChangingLobbyTime < 11) {
				    	SendCoolMessages.sendTitle(pp, "§6", 5, 10, 5);
				    	SendCoolMessages.sendSubTitle(pp, "§a§l" + ChangingLobbyTime + " Seconds", 5, 10, 5);
				    	if (!(GameVersion.above47(pp))) {
				    		pp.sendMessage(Main.codSignature + "§a§l" + ChangingLobbyTime + " Seconds");
				    	}
				    	pp.setLevel(ChangingLobbyTime);
				    }else if (ChangingLobbyTime >= 4 && ChangingLobbyTime < 7) {
				    	SendCoolMessages.sendTitle(pp, "§6", 5, 10, 5);
				    	SendCoolMessages.sendSubTitle(pp, "§e§l" + ChangingLobbyTime + " Seconds", 5, 10, 5);
				    	if (!(GameVersion.above47(pp))) {
				    		pp.sendMessage(Main.codSignature + "§e§l" + ChangingLobbyTime + " Seconds");
				    	}
				    	pp.setLevel(ChangingLobbyTime);
			    	}else if (ChangingLobbyTime > 1 && ChangingLobbyTime < 4) {
			    		SendCoolMessages.sendTitle(pp, "§6", 5, 10, 5);
			    		SendCoolMessages.sendSubTitle(pp, "§4§l" + ChangingLobbyTime + " Seconds", 5, 10, 5);
				    	if (!(GameVersion.above47(pp))) {
				    		pp.sendMessage(Main.codSignature + "§4§l" + ChangingLobbyTime + " Seconds");
				    	}
			    		pp.setLevel(ChangingLobbyTime);
				    }else if (ChangingLobbyTime == 1) {
				    	SendCoolMessages.sendTitle(pp, "§6", 5, 10, 5);
				    	SendCoolMessages.sendSubTitle(pp, "§4§l" + ChangingLobbyTime + " Second", 5, 10, 5);
				    	if (!(GameVersion.above47(pp))) {
				    		pp.sendMessage(Main.codSignature + "§4§l" + ChangingLobbyTime + " Second");
				    	}
				    	pp.setLevel(ChangingLobbyTime);
				    }else if (ChangingLobbyTime < 1) {
				    	pp.setLevel(ChangingLobbyTime);
						if (ID1 != -1) {
							Bukkit.getScheduler().cancelTask(ID1);
								ChangingLobbyTime = LobbyTime;
								ChangingGameTime = GameTime;
								BaseArena.state = ArenaState.STARTED;
								StartGameCountdown();
							}
				    	}
					}
				}
			}, 0L, 1L * 20L);
		}
	}
	
	public static void CancelLobby() {
		if (ID1 != -1) Bukkit.getScheduler().cancelTask(ID1);
			ChangingLobbyTime = LobbyTime;
			ChangingGameTime = GameTime;
			BaseArena.state = ArenaState.WAITING;
	}
	
	@SuppressWarnings("deprecation")
	public static void StartGameCountdown () {
		if (BaseArena.state == ArenaState.STARTED) {
			
			for (Player p : Main.WaitingPlayers) {
				if (Main.extras) {
					Parkour.leaveParkour(p, true, false);
				}
				p.setGameMode(GameMode.SURVIVAL);
				
				SendCoolMessages.clearTitleAndSubtitle(p);
				SendCoolMessages.resetTitleAndSubtitle(p);
			}
			
			Collections.shuffle(Main.WaitingPlayers);
			
			try {
				if (BaseArena.type == BaseArena.ArenaType.TDM) {	
					TDMArena.assignTeam(PickRandomArena.UpcomingArena);
					TDMArena.startTDM(PickRandomArena.UpcomingArena);
				}else if (BaseArena.type == BaseArena.ArenaType.CTF) {
					CTFArena.assignTeam(PickRandomArena.UpcomingArena);
					CTFArena.startCTF(PickRandomArena.UpcomingArena);
				}else if (BaseArena.type == BaseArena.ArenaType.INFECT) {
					INFECTArena.assignTeam();
					INFECTArena.StartINFECTArena(PickRandomArena.UpcomingArena);
				}else if (BaseArena.type == BaseArena.ArenaType.GUN) {
					GUNArena.startGUN(PickRandomArena.UpcomingArena);
				}else if (BaseArena.type == BaseArena.ArenaType.ONEIN) {
					ONEINArena.startONEIN(PickRandomArena.UpcomingArena);
				}else if (BaseArena.type == BaseArena.ArenaType.FFA) {
					FFAArena.startFFA(PickRandomArena.UpcomingArena);
				}else if (BaseArena.type == BaseArena.ArenaType.KC) {
					KillArena.assignTeam(PickRandomArena.UpcomingArena);
					KillArena.startKC(PickRandomArena.UpcomingArena);
				}
			
			}catch (Exception e) {
				if (!Main.PlayingPlayers.isEmpty()) {
					for (Player p : Main.PlayingPlayers) {
						LeaveArena.Leave(p, false, true, true);
						p.sendMessage(Main.codSignature + "§cYou left COD-Warfare because there was a problem starting the match");
					}
				}
				
				if (!Main.WaitingPlayers.isEmpty()) {
					for (Player p : Main.WaitingPlayers) {
						LeaveArena.Leave(p, false, true, true);
						p.sendMessage(Main.codSignature + "§cYou left COD-Warfare because there was a problem starting the match");
					}
				}
				
				if (!Bukkit.getServer().getOnlinePlayers().isEmpty()) {
					for (Player p : Bukkit.getServer().getOnlinePlayers()) {
						if (p.isOp()) {
							p.sendMessage(Main.codSignature + "§cThere was a problem starting the match at the arena §4" + PickRandomArena.UpcomingArena + "§c. "
									+ "This is probably because the spawn locations or their world haven't been loaded on the server. Please reset the spawn locations "
									+ "for this arena.");
						}
					}
				}
			}
			
			for (Player p : Main.PlayingPlayers) {
				p.updateInventory();
			}
			
			PickRandomArena.PickArena();
			
			if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("TDM")) {
				ArenaType = "TDM";
			}else if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("CTF")) {
				ArenaType = "CTF";
			}else if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("INFECT")) {
				ArenaType = "Infected";
			}else if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("GUN")) {
				ArenaType = "Gun Game";
			}else if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("ONEIN")) {
				ArenaType = "One In The Chamber";
			}else if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("FFA")) {
				ArenaType = "FFA";
			}else if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("KC")) {
				ArenaType = "Kill Confirmed";
			}
			
			ChangingGameTime = GameTime;
			
			ItemsAndInventories.setInformation(PickRandomArena.UpcomingArena, ArenaType);
		ID2 = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ThisPlugin.getPlugin(), new Runnable(){
			public void run(){
		    	ChangingGameTime--;
		    	
		    if (!(Main.PlayingPlayers.isEmpty())) {
				for(int i = 0; i < Main.WaitingPlayers.size(); i++){
				    Player wp = Main.WaitingPlayers.get(i);
				    if (ChangingGameTime == 60) {
				    	SendCoolMessages.sendTitle(wp, "§6", 10, 20, 10);
				    	SendCoolMessages.sendSubTitle(wp, "§7§l1 Minute", 10, 20, 10);
				    	if (!(GameVersion.above47(wp))) {
				    		wp.sendMessage(Main.codSignature + "§7§l1 Minute");
				    	}
				    }
				    if (ChangingGameTime == 30) {
				    	SendCoolMessages.sendTitle(wp, "§6", 10, 20, 10);
				    	SendCoolMessages.sendSubTitle(wp, "§d§l" + ChangingGameTime + " Seconds", 10, 20, 10);
				    	if (!(GameVersion.above47(wp))) {
				    		wp.sendMessage(Main.codSignature + "§d§l" + ChangingGameTime + " Seconds");
				    	}
				    }
				    if (ChangingGameTime == 15) {
				    	SendCoolMessages.sendTitle(wp, "§6", 10, 20, 10);
				    	SendCoolMessages.sendSubTitle(wp, "§d§l" + ChangingGameTime + " Seconds", 10, 20, 10);
				    	if (!(GameVersion.above47(wp))) {
				    		wp.sendMessage(Main.codSignature + "§d§l" + ChangingGameTime + " Seconds");
				    	}
				    }
				    if (ChangingGameTime > 10) {
				    	wp.setLevel(ChangingGameTime);
				    }else if (ChangingGameTime > 6 && ChangingGameTime <= 10) {
					    SendCoolMessages.sendTitle(wp, "§6", 5, 10, 5);
					    SendCoolMessages.sendSubTitle(wp, "§a§l" + ChangingGameTime + " Seconds", 5, 10, 5);
				    	if (!(GameVersion.above47(wp))) {
				    		wp.sendMessage(Main.codSignature + "§a§l" + ChangingGameTime + " Seconds");
				    	}
					    wp.setLevel(ChangingGameTime);
				    }else if (ChangingGameTime > 3 && ChangingGameTime <= 6) {
					    SendCoolMessages.sendTitle(wp, "§6", 5, 10, 5);
					    SendCoolMessages.sendSubTitle(wp, "§6§l" + ChangingGameTime + " Seconds", 5, 10, 5);
				    	if (!(GameVersion.above47(wp))) {
				    		wp.sendMessage(Main.codSignature + "§6§l" + ChangingGameTime + " Seconds");
				    	}
					    wp.setLevel(ChangingGameTime);
				    }else if (ChangingGameTime > 1 && ChangingGameTime <= 3) {
					    SendCoolMessages.sendTitle(wp, "§6", 5, 10, 5);
					    SendCoolMessages.sendSubTitle(wp, "§4§l" + ChangingGameTime + " Seconds", 5, 10, 5);
				    	if (!(GameVersion.above47(wp))) {
				    		wp.sendMessage(Main.codSignature + "§4§l" + ChangingGameTime + " Seconds");
				    	}
					    wp.setLevel(ChangingGameTime);
				    }else if (ChangingGameTime == 1) {
					    SendCoolMessages.sendTitle(wp, "§6", 5, 10, 5);
					    SendCoolMessages.sendSubTitle(wp, "§4§l" + ChangingGameTime + " Second", 5, 10, 5);
				    	if (!(GameVersion.above47(wp))) {
				    		wp.sendMessage(Main.codSignature + "§4§l" + ChangingGameTime + " Second");
				    	}
					    wp.setLevel(ChangingGameTime);
				    }else if (ChangingGameTime < 1) {
					    SendCoolMessages.sendTitle(wp, Language.getString("gameOver", false), 10, 20, 10);
					    SendCoolMessages.sendSubTitle(wp, Language.getString("startingNewGame", false), 10, 20, 10);
				    	if (!(GameVersion.above47(wp))) {
				    		wp.sendMessage(Language.getString("gameOver", true));
				    		wp.sendMessage(Language.getString("startingNewGame", true));
				    	}
				    	wp.setLevel(ChangingGameTime);
				    }
				}
		    	
				for(int i = 0; i < Main.PlayingPlayers.size(); i++) {
				    Player pp = Main.PlayingPlayers.get(i);
					
				    if (ChangingGameTime > 10) {
				    	pp.setLevel(ChangingGameTime);
				    }
				    
					if (ChangingGameTime == 60) {
				    	SendCoolMessages.sendTitle(pp, "§6", 10, 20, 10);
				    	SendCoolMessages.sendSubTitle(pp, "§7§l1 Minute", 10, 20, 10);
				    	if (!(GameVersion.above47(pp))) {
				    		pp.sendMessage(Main.codSignature + "§7§l1 Minute");
				    	}
				    }
				    if (ChangingGameTime == 30) {
				    	SendCoolMessages.sendTitle(pp, "§6", 10, 20, 10);
				    	SendCoolMessages.sendSubTitle(pp, "§d§l" + ChangingGameTime + " Seconds", 10, 20, 10);
				    	if (!(GameVersion.above47(pp))) {
				    		pp.sendMessage(Main.codSignature + "§d§l" + ChangingGameTime + " Seconds");
				    	}
				    }
				    if (ChangingGameTime == 15) {
				    	SendCoolMessages.sendTitle(pp, "§6", 10, 20, 10);
				    	SendCoolMessages.sendSubTitle(pp, "§d§l" + ChangingGameTime + " Seconds", 10, 20, 10);
				    	if (!(GameVersion.above47(pp))) {
				    		pp.sendMessage(Main.codSignature + "§d§l" + ChangingGameTime + " Seconds");
				    	}
				    }
				    
				    if (ChangingGameTime > 6 && ChangingGameTime <= 10) {
					    SendCoolMessages.sendTitle(pp, "§6", 5, 10, 5);
					    SendCoolMessages.sendSubTitle(pp, "§a§l" + ChangingGameTime + " Seconds", 5, 10, 5);
				    	if (!(GameVersion.above47(pp))) {
				    		pp.sendMessage(Main.codSignature + "§a§l" + ChangingGameTime + " Seconds");
				    	}
					    pp.setLevel(ChangingGameTime);
				    }else if (ChangingGameTime > 3 && ChangingGameTime <= 6) {
					    SendCoolMessages.sendTitle(pp, "§6", 5, 10, 5);
					    SendCoolMessages.sendSubTitle(pp, "§6§l" + ChangingGameTime + " Seconds", 5, 10, 5);
				    	if (!(GameVersion.above47(pp))) {
				    		pp.sendMessage(Main.codSignature + "§6§l" + ChangingGameTime + " Seconds");
				    	}
					    pp.setLevel(ChangingGameTime);
				    }else if (ChangingGameTime > 1 && ChangingGameTime <= 3) {
					    SendCoolMessages.sendTitle(pp, "§6", 5, 10, 5);
					    SendCoolMessages.sendSubTitle(pp, "§4§l" + ChangingGameTime + " Seconds", 5, 10, 5);
				    	if (!(GameVersion.above47(pp))) {
				    		pp.sendMessage(Main.codSignature + "§4§l" + ChangingGameTime + " Seconds");
				    	}
					    pp.setLevel(ChangingGameTime);
				    }else if (ChangingGameTime == 1) {
					    SendCoolMessages.sendTitle(pp, "§6", 5, 10, 5);
					    SendCoolMessages.sendSubTitle(pp, "§4§l" + ChangingGameTime + " Second", 5, 10, 5);
				    	if (!(GameVersion.above47(pp))) {
				    		pp.sendMessage(Main.codSignature + "§4§l" + ChangingGameTime + " Seconds");
				    	}
					    pp.setLevel(ChangingGameTime);
				    }else if (ChangingGameTime < 1) {
					    SendCoolMessages.sendTitle(pp, Language.getString("gameOver", false), 10, 20, 10);
					    SendCoolMessages.sendSubTitle(pp, Language.getString("startingNewGame", false), 10, 20, 10);
				    	if (!(GameVersion.above47(pp))) {
				    		pp.sendMessage(Language.getString("gameOver", true));
				    		pp.sendMessage(Language.getString("startingNewGame", true));
				    	}
				    	pp.setLevel(ChangingGameTime);
				    }
				}
				    if (ChangingGameTime < 1) {
				    	StopGameCountdown.endGame();
				    }
				
				} else {
					if (ID2 != -1) {
					Bukkit.getScheduler().cancelTask(ID2);
						ChangingLobbyTime = LobbyTime;
						ChangingGameTime = GameTime;
						BaseArena.state = ArenaState.COUNTDOWN;
						CancelLobby();
						stopGame();
					}
				}
			}
		}, 0L, 1L * 20L);
	}
}
	
	public static void stopGame() {
	if (ID2 != -1) {
		Bukkit.getScheduler().cancelTask(ID2);
			BaseArena.state = BaseArena.ArenaState.WAITING;
			ChangingLobbyTime = LobbyTime;
			ChangingGameTime = GameTime;
			CancelLobby();
		}
	}
	
	public static void CancelGame() {
		if (ID2 != -1) Bukkit.getScheduler().cancelTask(ID2);
			ChangingLobbyTime = LobbyTime;
			ChangingGameTime = GameTime;
	}
}