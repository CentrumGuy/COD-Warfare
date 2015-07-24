package com.CentrumGuy.CodWarfare.Commands;

import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Arena.BaseArena;
import com.CentrumGuy.CodWarfare.Arena.Countdown;
import com.CentrumGuy.CodWarfare.Arena.PickRandomArena;
import com.CentrumGuy.CodWarfare.Arena.getArena;
import com.CentrumGuy.CodWarfare.Interface.ItemsAndInventories;

public class SetNextArenaCommand {

	public static void setNextArena(Player p, String[] args) {
		if (args.length <= 1) {
			p.sendMessage(Main.codSignature + "§cPlease type §4/cod nextarena [Arena Name]");
			return;
		}
		
		String arenaName = args[1];
		if (getArena.getName(arenaName) != null) {
			PickRandomArena.UpcomingArena = arenaName;
			
			if (BaseArena.state == BaseArena.ArenaState.COUNTDOWN) {
				if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("TDM")) {
					BaseArena.type = BaseArena.ArenaType.TDM;
					Countdown.ArenaType = "TDM";
				}else if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("CTF")) {
					BaseArena.type = BaseArena.ArenaType.CTF;
					Countdown.ArenaType = "CTF";
				}else if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("INFECT")) {
					BaseArena.type = BaseArena.ArenaType.INFECT;
					Countdown.ArenaType = "Infected";
				}else if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("GUN")) {
					BaseArena.type = BaseArena.ArenaType.GUN;
					Countdown.ArenaType = "Gun Game";
				}else if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("ONEIN")) {
					BaseArena.type = BaseArena.ArenaType.ONEIN;
					Countdown.ArenaType = "One In The Chamber";
				}else if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("FFA")) {
					BaseArena.type = BaseArena.ArenaType.FFA;
					Countdown.ArenaType = "FFA";
				}else if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("KC")) {
					BaseArena.type = BaseArena.ArenaType.KC;
					Countdown.ArenaType = "Kill Confirmed";
				}
			}else{
				if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("TDM")) {
					Countdown.ArenaType = "TDM";
				}else if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("CTF")) {
					Countdown.ArenaType = "CTF";
				}else if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("INFECT")) {
					Countdown.ArenaType = "Infected";
				}else if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("GUN")) {
					Countdown.ArenaType = "Gun Game";
				}else if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("ONEIN")) {
					Countdown.ArenaType = "One In The Chamber";
				}else if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("FFA")) {
					Countdown.ArenaType = "FFA";
				}else if (getArena.getType(PickRandomArena.UpcomingArena).equalsIgnoreCase("KC")) {
					Countdown.ArenaType = "Kill Confirmed";
				}
			}
			
			ItemsAndInventories.setInformation(PickRandomArena.UpcomingArena, Countdown.ArenaType);
			
			p.sendMessage(Main.codSignature + "§aSet upcoming arena to §2" + arenaName);
		}else{
			p.sendMessage(Main.codSignature + "§cNo arena exists with the name §4" + arenaName + "§c. This is case-sensitive");
		}
	}
	
}
