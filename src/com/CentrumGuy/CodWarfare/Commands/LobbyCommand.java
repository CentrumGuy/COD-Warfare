package com.CentrumGuy.CodWarfare.Commands;

import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Arena.StopGameCountdown;
import com.CentrumGuy.CodWarfare.Files.LobbyFile;
import com.CentrumGuy.CodWarfare.Interface.ResetPlayer;
import com.CentrumGuy.CodWarfare.Lobby.Lobby;

public class LobbyCommand {

	public static void SetLobby(Player p) {
		LobbyFile.getData().set("Lobby.World", p.getWorld().getName());
		LobbyFile.getData().set("Lobby.X", p.getLocation().getX());
		LobbyFile.getData().set("Lobby.Y", p.getLocation().getY());
		LobbyFile.getData().set("Lobby.Z", p.getLocation().getZ());
		LobbyFile.getData().set("Lobby.Pitch", p.getLocation().getPitch());
		LobbyFile.getData().set("Lobby.Yaw", p.getLocation().getYaw());
		LobbyFile.saveData();
		LobbyFile.reloadData();
		p.sendMessage(Main.codSignature + "§aLobby set!");
	}
	
	public static void tpLobby(Player p) {
		if (Main.PlayingPlayers.contains(p)) {
			Main.PlayingPlayers.remove(p);
			ResetPlayer.reset(p);
			
			if ((Main.PlayingPlayers.isEmpty()) && (!(Main.WaitingPlayers.isEmpty()))) {
				StopGameCountdown.endGame();
			}
		}
		
		if (Lobby.getLobby() != null) {
			p.teleport(Lobby.getLobby());
		}else{
			p.sendMessage(Main.codSignature + "§cThere is no lobby set for this server. Set one by typing§4 /cod lobby set");
		}
	}
	
}
