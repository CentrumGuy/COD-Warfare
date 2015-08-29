package com.CentrumGuy.CodWarfare.Arena;

import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Interface.JoinCOD;

public class JoinArena {

	public static void Join (Player p) {
		if (Main.PlayingPlayers.contains(p) || Main.WaitingPlayers.contains(p)) {
			
			p.sendMessage(Main.codSignature + "§cYou are already in COD-Warfare");
			return;
			
		}else{
			
			JoinCOD.join(true, p, false);
		}
	}
}
