package com.CentrumGuy.CodWarfare.Arena;

import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Main;

public class BaseArena {

	public enum ArenaState {
		WAITING, COUNTDOWN, STARTED, ENDING
	}
	
	public enum ArenaType {
		TDM, GUN, FFA, INFECT, ONEIN, CTF, KC
	}
	
	public static ArenaType type;
	
	public ArenaType getType() {
		return type;
	}
	
	public static ArenaState state;
	
	public ArenaState getState() {
		return state;
	}
	
	public Player[] getPlayingPlayers(){
		return Main.PlayingPlayers.toArray(new Player[Main.PlayingPlayers.size()]);
	}
	
	public Player[] getWaitingPlayers(){
		return Main.WaitingPlayers.toArray(new Player[Main.WaitingPlayers.size()]);
	}
	
	public boolean hasPlayingPlayer(Player p){
		return Main.PlayingPlayers.contains(p);
	}
	
	public boolean hasWaitingPlayer(Player p){
		return Main.WaitingPlayers.contains(p);
	}
	
	public void addWaitingPlayer(Player p) {
		Main.WaitingPlayers.add(p);
	}
	
	public void addPlayingPlayer(Player p) {
		Main.PlayingPlayers.add(p);
	}
}
