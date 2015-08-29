package com.CentrumGuy.CodWarfare.Arena;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Score;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Arena.BaseArena.ArenaState;
import com.CentrumGuy.CodWarfare.Interface.ResetPlayer;
import com.CentrumGuy.CodWarfare.OtherLoadout.WeaponUtils;
import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;
import com.CentrumGuy.CodWarfare.Utilities.GameVersion;
import com.CentrumGuy.CodWarfare.Utilities.SendCoolMessages;

public class StopGameCountdown {

	public static void endGame() {
    	Countdown.CancelGame();
		Countdown.stopGame();
		BaseArena.state = BaseArena.ArenaState.ENDING;
			for (Player pp : Main.PlayingPlayers) {
				SendCoolMessages.sendTitle(pp, "§6§lGAME OVER!", 10, 20, 10);
			    SendCoolMessages.sendSubTitle(pp, "§d§lStarting New Game...", 10, 20, 10);
		    	if (!(GameVersion.above47(pp))) {
		    		pp.sendMessage(Main.codSignature + "§6§lGAME OVER!");
		    		pp.sendMessage(Main.codSignature + "§d§lStarting New Game...");
		    	}
		    	
		    	if (!(Main.invincible.contains(pp))) Main.invincible.add(pp);
		    	pp.setWalkSpeed((float) 0.2);
		    }
			
	    	for (int i = 0 ; i < Main.WaitingPlayers.size() ; i++) {
	    		Player wp = Main.WaitingPlayers.get(i);
	    		
	    		SendCoolMessages.sendTitle(wp, "§6§lGAME OVER!", 10, 20, 10);
			    SendCoolMessages.sendSubTitle(wp, "§d§lStarting New Game...", 10, 20, 10);
		    	if (!(GameVersion.above47(wp))) {
		    		wp.sendMessage(Main.codSignature + "§6§lGAME OVER!");
		    		wp.sendMessage(Main.codSignature + "§d§lStarting New Game...");
		    	}
	    	}
	    	
	    	for (Player pp : Main.PlayingPlayers) {
	    		if ((!(getArena.getType(PickRandomArena.CurrentArena).equalsIgnoreCase("ONEIN"))) && (!(getArena.getType(PickRandomArena.CurrentArena).equalsIgnoreCase("GUN")))) {
	    			WeaponUtils.clearWeapons(pp);
	    		}
	    	}
	    	
	    	for (World w : Bukkit.getServer().getWorlds()) {
	    		for (Entity e : w.getEntities()) {
	    			if (e.hasMetadata("codAllowHit")) e.remove();
	    		}
	    	}
	    	
	    	if (getArena.getType(PickRandomArena.CurrentArena).equalsIgnoreCase("CTF")) {
	    		CTFArena.endCTF(PickRandomArena.CurrentArena);
	    		for (Player p : Main.PlayingPlayers) {
					if (CTFArena.holdingBlueFlag.get(p) != null && CTFArena.holdingBlueFlag.get(p) == true) {
						CTFArena.returnFlag("blue", PickRandomArena.CurrentArena);
		        		for (Player pp : Main.PlayingPlayers) {
		        			pp.sendMessage(Main.codSignature + "§c" + p.getName() + " §6dropped the §9blue §6flag");
		        		}
						CTFArena.holdingBlueFlag.put(p, false);
					}else if (CTFArena.holdingRedFlag.get(p) != null && CTFArena.holdingRedFlag.get(p) == true) {
						CTFArena.returnFlag("red", PickRandomArena.CurrentArena);
		        		for (Player pp : Main.PlayingPlayers) {
		        			pp.sendMessage(Main.codSignature + "§9" + p.getName() + " §6dropped the §cred §6flag");
		        		}
	        			CTFArena.holdingRedFlag.put(p, false);
					}
	    		}
	    		
				CTFArena.removeFlags(PickRandomArena.CurrentArena);
	    	}else if (getArena.getType(PickRandomArena.CurrentArena).equalsIgnoreCase("INFECT")) {
	    		INFECTArena.endInfect();
	    	}else if (getArena.getType(PickRandomArena.CurrentArena).equalsIgnoreCase("FFA")) {
	    		FFAArena.endFFA();
	    	}else if (getArena.getType(PickRandomArena.CurrentArena).equalsIgnoreCase("GUN")) {
	    		GUNArena.endGUN();
	    	}else if (getArena.getType(PickRandomArena.CurrentArena).equalsIgnoreCase("TDM")) {
	    		TDMArena.endTDM();
	    	}else if (getArena.getType(PickRandomArena.CurrentArena).equalsIgnoreCase("ONEIN")) {
	    		ONEINArena.sendEndMessage();
	    	}else if (getArena.getType(PickRandomArena.CurrentArena).equalsIgnoreCase("KC")) {
	    		KillArena.endKC();
	    	}
	    	
	    	for (Player p : Main.PlayingPlayers) {
	    		p.setLevel(0);
	    		p.getInventory().clear();
	    		
	    		for(PotionEffect effect : p.getActivePotionEffects()) {
	    		    p.removePotionEffect(effect.getType());
	    		}
	    	}
	    	
	    	for (Player p : Main.WaitingPlayers) {
	    		p.setLevel(0);
	    	}
	    	
	    	BukkitRunnable br = new BukkitRunnable() {
	    		public void run() {
		    		Main.invincible.clear();
			    	if (getArena.getType(PickRandomArena.CurrentArena).equalsIgnoreCase("ONEIN")) {
			    		ONEINArena.endONEIN();
			    	}
			    	for (int i = 0 ; i < Main.PlayingPlayers.size() ; i++) {
			    		Player pp = Main.PlayingPlayers.get(i);
			    		
				    	if (getArena.getType(PickRandomArena.CurrentArena).equalsIgnoreCase("ONEIN")) {
				    		ONEINArena.Lives.put(pp, null);
				    	}

				    		
			    			
			    	        Score GameKillStreakScore = Main.GameKillStreakScore.get(pp.getName());
			    	        Score highestKS = Main.highestKillstreak.get(pp.getName());
			    	        
			    	        if (highestKS.getScore() < GameKillStreakScore.getScore()) {
			    	        	highestKS.setScore(GameKillStreakScore.getScore());
			    	        	pp.sendMessage(Main.codSignature + "§b§lNew highest kill streak:§6§l " + GameKillStreakScore.getScore());
			    	        }
							
							ResetPlayer.reset(pp);
							pp.setSprinting(false);
			    	}
			    	
			    	Main.PlayingPlayers.clear();
			    	
	    			BaseArena.state = ArenaState.WAITING;
			    	
					if ((Main.WaitingPlayers.size() + Main.PlayingPlayers.size()) >= Main.min_Players) {
						Countdown.StartLobbyCountdown();
					}else{
						if ((!Main.WaitingPlayers.isEmpty()) && ((Main.WaitingPlayers.size() + Main.PlayingPlayers.size()) < Main.min_Players)) {
							for (Player p : Main.WaitingPlayers) {
								p.sendMessage(Main.codSignature + "§a§l" + (Main.min_Players - (Main.WaitingPlayers.size() + Main.PlayingPlayers.size())) + " More players §2are needed to start the game");
								p.setLevel(Main.lobbyTime);
							}
						}
						
						if ((!Main.PlayingPlayers.isEmpty()) && ((Main.WaitingPlayers.size() + Main.PlayingPlayers.size()) < Main.min_Players)) {
							for (Player p : Main.PlayingPlayers) {
								p.sendMessage(Main.codSignature + "§a§l" + (Main.min_Players - (Main.WaitingPlayers.size() + Main.PlayingPlayers.size())) + " More players §2are needed to start the game");
								p.setLevel(Main.lobbyTime);
							}
						}
					}
	    	}
	    };
	    
	    br.runTaskLater(ThisPlugin.getPlugin(), 5 * 20);
	}
}
