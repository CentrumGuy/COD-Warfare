package com.CentrumGuy.CodWarfare.Interface;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Files.ScoresFile;
import com.CentrumGuy.CodWarfare.Leveling.Exp;
import com.CentrumGuy.CodWarfare.Leveling.Level;

public class Scores {

	public static void loadScores(Player p) {
			if (Main.GameScoreboard.get(p.getName()) == null) Main.createGameBoard(p);
			if (Main.LobbyScoreboard.get(p.getName()) == null) Main.createLobbyBoard(p);
			if (ScoresFile.getData().get("Scores." + p.getUniqueId()) == null) return;
			
			if (ScoresFile.getData().get("Scores." + p.getUniqueId() + ".Credits") != null) Main.LobbyCreditsScore.get(p.getName()).setScore(ScoresFile.getData().getInt("Scores." + p.getUniqueId() + ".Credits"));
			if (ScoresFile.getData().get("Scores." + p.getUniqueId() + ".Level") != null) Main.LobbyLevelScore.get(p.getName()).setScore(ScoresFile.getData().getInt("Scores." + p.getUniqueId() + ".Level"));
			if (ScoresFile.getData().get("Scores." + p.getUniqueId() + ".Kills") != null) Main.LobbyKillsScore.get(p.getName()).setScore(ScoresFile.getData().getInt("Scores." + p.getUniqueId() + ".Kills"));
			if (ScoresFile.getData().get("Scores." + p.getUniqueId() + ".Deaths") != null) Main.LobbyDeathsScore.get(p.getName()).setScore(ScoresFile.getData().getInt("Scores." + p.getUniqueId() + ".Deaths"));
			if (ScoresFile.getData().get("Scores." + p.getUniqueId() + ".HighestKillStreak") != null) Main.highestKillstreak.get(p.getName()).setScore(ScoresFile.getData().getInt("Scores." + p.getUniqueId() + ".HighestKillStreak"));
			
			if (ScoresFile.getData().get("Scores." + p.getUniqueId() + ".Level") != null) Level.setLevel(p, ScoresFile.getData().getInt("Scores." + p.getUniqueId() + ".Level"));
			
			if (ScoresFile.getData().get("Scores." + p.getUniqueId() + ".Exp") != null) Exp.ExpNow.put(p, ScoresFile.getData().getInt("Scores." + p.getUniqueId() + ".Exp"));
			if (ScoresFile.getData().get("Scores." + p.getUniqueId() + ".NeededExp") != null) Exp.NeededExpNow.put(p, ScoresFile.getData().getInt("Scores." + p.getUniqueId() + ".NeededExp"));
			if (ScoresFile.getData().get("Scores." + p.getUniqueId() + ".NeededExpBefore") != null) Exp.NeededExpFromBefore.put(p, ScoresFile.getData().getInt("Scores." + p.getUniqueId() + ".NeededExpBefore"));
	}
	
	public static void saveScores(Player p) {
		if (Main.LobbyCreditsScore.get(p.getName()) != null) ScoresFile.getData().set("Scores." + p.getUniqueId() + ".Credits", Main.LobbyCreditsScore.get(p.getName()).getScore());
		if (Main.LobbyLevelScore.get(p.getName()) != null) ScoresFile.getData().set("Scores." + p.getUniqueId() + ".Level", Main.LobbyLevelScore.get(p.getName()).getScore());
		if (Main.LobbyKillsScore.get(p.getName()) != null) ScoresFile.getData().set("Scores." + p.getUniqueId() + ".Kills", Main.LobbyKillsScore.get(p.getName()).getScore());
		if (Main.LobbyDeathsScore.get(p.getName()) != null) ScoresFile.getData().set("Scores." + p.getUniqueId() + ".Deaths", Main.LobbyDeathsScore.get(p.getName()).getScore());
		
		if ((Main.LobbyDeathsScore.get(p.getName()) != null) && (Main.highestKillstreak.get(p.getName()) != null)) {
	        Score GameKillStreakScore = Main.GameKillStreakScore.get(p.getName());
	        Score highestKS = Main.highestKillstreak.get(p.getName());
	        
	        if (GameKillStreakScore != null) {
	        	if (highestKS.getScore() < GameKillStreakScore.getScore()) {
	        		highestKS.setScore(GameKillStreakScore.getScore());
	        	}
	        }
		}
		
		if (Main.highestKillstreak.get(p.getName()) != null) ScoresFile.getData().set("Scores." + p.getUniqueId() + ".HighestKillStreak", Main.highestKillstreak.get(p.getName()).getScore());
		if (Exp.ExpNow.get(p) != null) ScoresFile.getData().set("Scores." + p.getUniqueId() + ".Exp", Exp.getExp(p));
		if (Exp.NeededExpNow.get(p) != null) ScoresFile.getData().set("Scores." + p.getUniqueId() + ".NeededExp", Exp.getNeededExp(p));
		if (Exp.NeededExpFromBefore.get(p) != null) ScoresFile.getData().set("Scores." + p.getUniqueId() + ".NeededExpBefore", Exp.getNeededExpFromBefore(p));
		
		ScoresFile.saveData();
		ScoresFile.reloadData();
	}
	
}
