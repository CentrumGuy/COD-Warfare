package com.CentrumGuy.CodWarfare.Commands;

import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Arena.BaseArena;
import com.CentrumGuy.CodWarfare.Arena.Countdown;

public class ForceStartCommand {
	public static void forceStart(Player p, String[] args) {
		if (BaseArena.state == BaseArena.ArenaState.WAITING && Main.PlayingPlayers.isEmpty()) {
			if ((!Main.WaitingPlayers.isEmpty())) {
				Countdown.StartLobbyCountdown();
				Countdown.ChangingLobbyTime = 1;
				p.sendMessage(Main.codSignature + "§aYou force-started the match");
			}else{
				p.sendMessage(Main.codSignature + "§cThere is no one in COD");
			}
		}
		
		if (BaseArena.state == BaseArena.ArenaState.COUNTDOWN && Main.PlayingPlayers.isEmpty()) {
			if ((!Main.WaitingPlayers.isEmpty())) {
				Countdown.ChangingLobbyTime = 1;
				p.sendMessage(Main.codSignature + "§aYou force-started the match");
			}else{
				p.sendMessage(Main.codSignature + "§cThere is no one in COD");
			}
		}else{
			p.sendMessage(Main.codSignature + "§cA game is already in progress");
		}
	}	
}
