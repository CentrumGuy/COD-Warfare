package com.CentrumGuy.CodWarfare.Lobby;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.CentrumGuy.CodWarfare.Files.LobbyFile;

public class Lobby {

	public static Location getLobby() {
		Location lobby = null;
		if (!(LobbyFile.getData().getString("Lobby.World") == null)) {
			World lobbyWorld = Bukkit.getServer().getWorld(LobbyFile.getData().getString("Lobby.World"));
		
			double X = LobbyFile.getData().getDouble("Lobby.X");
			double Y = LobbyFile.getData().getDouble("Lobby.Y");
			double Z = LobbyFile.getData().getDouble("Lobby.Z");
			String Pitch1 = LobbyFile.getData().getString("Lobby.Pitch");
			String Yaw1 = LobbyFile.getData().getString("Lobby.Yaw");
		
			float Pitch = Float.parseFloat(Pitch1);
			float Yaw = Float.parseFloat(Yaw1);
		
			lobby = new Location(lobbyWorld, X, Y, Z, Yaw, Pitch);
			
			}
		return lobby;
	}
}
