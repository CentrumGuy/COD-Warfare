package com.CentrumGuy.CodWarfare.SpecialWeapons;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Arena.BaseArena;
import com.CentrumGuy.CodWarfare.Arena.CTFArena;
import com.CentrumGuy.CodWarfare.Arena.INFECTArena;
import com.CentrumGuy.CodWarfare.Arena.KillArena;
import com.CentrumGuy.CodWarfare.Arena.TDMArena;

public class GetPlayersOnOtherTeam {

	public static ArrayList<Player> get(Player p) {
		if (Main.WaitingPlayers.contains(p)) return new ArrayList<Player>();
		if (BaseArena.type == BaseArena.ArenaType.TDM) {
			if (TDMArena.RedTeam.contains(p)) {
				return TDMArena.BlueTeam;
			}else if (TDMArena.BlueTeam.contains(p)) {
				return TDMArena.RedTeam;
			}
		}else if (BaseArena.type == BaseArena.ArenaType.CTF) {
			if (CTFArena.RedTeam.contains(p)) {
				return CTFArena.BlueTeam;
			}else if (CTFArena.BlueTeam.contains(p)) {
				return CTFArena.RedTeam;
			}
		}else if (BaseArena.type == BaseArena.ArenaType.INFECT) {
			if (INFECTArena.Zombies.contains(p)) {
				return INFECTArena.Blue;
			}else if (INFECTArena.Blue.contains(p)) {
				return INFECTArena.Zombies;
			}
		}else if (BaseArena.type == BaseArena.ArenaType.KC) {
			if (KillArena.RedTeam.contains(p)) {
				return KillArena.BlueTeam;
			}else if (KillArena.BlueTeam.contains(p)) {
				return KillArena.RedTeam;
			}
		}else{
			ArrayList<Player> players = new ArrayList<Player>();
			players.addAll(Main.PlayingPlayers);
			if (players.contains(p)) players.remove(p);
			return players;
		}
		
		return new ArrayList<Player>();
	}
}
