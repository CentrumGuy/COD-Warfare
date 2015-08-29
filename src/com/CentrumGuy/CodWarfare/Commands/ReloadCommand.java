package com.CentrumGuy.CodWarfare.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Arena.Countdown;
import com.CentrumGuy.CodWarfare.Arena.LeaveArena;
import com.CentrumGuy.CodWarfare.Arena.PickRandomArena;
import com.CentrumGuy.CodWarfare.Clans.MainClan;
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
import com.CentrumGuy.CodWarfare.Interface.Scores;
import com.CentrumGuy.CodWarfare.Inventories.Guns;
import com.CentrumGuy.CodWarfare.OtherLoadout.Lethal;
import com.CentrumGuy.CodWarfare.OtherLoadout.Tactical;
import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;

public class ReloadCommand {

	public static void reloadPlayingPlayers(String message) {
	
	if (Main.PlayingPlayers.isEmpty()) return;
	for(int i = 0; i < Main.PlayingPlayers.size(); i++){
	    Player pp = Main.PlayingPlayers.get(i);
	    pp.sendMessage(message);
	    LeaveArena.Leave(pp, false, false, true);
	    Scores.saveScores(pp);
	}
	
	Main.PlayingPlayers.clear();
}
	
	public static void reloadWaitingPlayers(String message) {
		
	if (Main.WaitingPlayers.isEmpty()) return;
	for(int i = 0; i < Main.WaitingPlayers.size(); i++){
	    Player wp = Main.WaitingPlayers.get(i);
	    wp.sendMessage(message);
	    LeaveArena.Leave(wp, false, false, true);
	    Scores.saveScores(wp);
	}
	
	Main.WaitingPlayers.clear();
}
	
	public static void Reload(CommandSender p, String[] args) {
		
		if (args.length == 1) {
			reloadPlayingPlayers(Main.codSignature + "§cYou left COD-Warfare due to a restart of the plugin");
			reloadWaitingPlayers(Main.codSignature + "§cYou left COD-Warfare due to a restart of the plugin");
			Main.WaitingPlayers.clear();
			Main.PlayingPlayers.clear();
			
			Countdown.CancelGame();
			Countdown.CancelLobby();
			
			PickRandomArena.CurrentArena = null;
			PickRandomArena.UpcomingArena = null;
			
			ArenasFile.setup(ThisPlugin.getPlugin());
			LobbyFile.setup(ThisPlugin.getPlugin());
			EnabledArenasFile.setup(ThisPlugin.getPlugin());
			ShopFile.setup(ThisPlugin.getPlugin());
			KitFile.setup(ThisPlugin.getPlugin());
			AvailableGunsFile.setup(ThisPlugin.getPlugin());
			GunsFile.setup(ThisPlugin.getPlugin());
			GunGameFile.setup(ThisPlugin.getPlugin());
			JoinedCODFile.setup(ThisPlugin.getPlugin());
			ScoresFile.setup(ThisPlugin.getPlugin());
			LangFile.setup(ThisPlugin.getPlugin());
			ClansFile.setup(ThisPlugin.getPlugin());
			WeaponsFile.setup(ThisPlugin.getPlugin());
			PerksFile.setup(ThisPlugin.getPlugin());
			AchievementsFile.setup(ThisPlugin.getPlugin());
			
			ThisPlugin.getPlugin().reloadConfig();
			EnabledArenasFile.reloadData();
			ArenasFile.reloadData();
			LobbyFile.reloadData();
			ShopFile.reloadData();
			AvailableGunsFile.reloadData();
			KitFile.reloadData();
			GunsFile.reloadData();
			GunGameFile.reloadData();
			ScoresFile.reloadData();
			JoinedCODFile.reloadData();
			LangFile.reloadData();
			ClansFile.reloadData();
			WeaponsFile.reloadData();
			PerksFile.reloadData();
			AchievementsFile.reloadData();
			ThisPlugin.getPlugin().reloadConfig();
			
			MainClan.setUp(ThisPlugin.getPlugin());
			
			Guns.loadGuns();
			Tactical.loadTacticals();
			Lethal.loadLethals();
			
			Bukkit.getServer().getPluginManager().disablePlugin(ThisPlugin.getPlugin());
			Bukkit.getServer().getPluginManager().enablePlugin(ThisPlugin.getPlugin());
			
			p.sendMessage(Main.codSignature + "§aCOD-Warfare§2 v" + Main.version + " §ahas been reloaded");
		}else{
			p.sendMessage(Main.codSignature + "§cToo many arguments. Please type §4/cod reload");
		}
		
	}
	
}
