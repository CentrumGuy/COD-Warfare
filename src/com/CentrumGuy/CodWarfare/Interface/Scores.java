package com.CentrumGuy.CodWarfare.Interface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Files.ScoresFile;
import com.CentrumGuy.CodWarfare.Leveling.Exp;
import com.CentrumGuy.CodWarfare.Leveling.Level;
import com.CentrumGuy.CodWarfare.MySQL.MySQL;

public class Scores {

	public static void loadScores(Player p) {
		if (!(MySQL.mySQLenabled())) {
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
		}else{
			try {
				if (Main.GameScoreboard.get(p.getName()) == null) Main.createGameBoard(p);
				if (Main.LobbyScoreboard.get(p.getName()) == null) Main.createLobbyBoard(p);
				
				Connection conn = MySQL.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM CODScores");
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					if (rs.getString("uuid").equals(p.getUniqueId().toString())) {
						if (rs.getObject("Credits") != null) Main.LobbyCreditsScore.get(p.getName()).setScore(rs.getInt("Credits"));
						if (rs.getObject("Level") != null) {
							final int level = rs.getInt("Level");
							Main.LobbyLevelScore.get(p.getName()).setScore(level);
							Level.setLevel(p, level);
						}
						if (rs.getObject("Kills") != null) Main.LobbyKillsScore.get(p.getName()).setScore(rs.getInt("Kills"));
						if (rs.getObject("Deaths") != null) Main.LobbyDeathsScore.get(p.getName()).setScore(rs.getInt("Deaths"));
						if (rs.getObject("HighestKillStreak") != null) Main.highestKillstreak.get(p.getName()).setScore(rs.getInt("HighestKillStreak"));
						
						if (rs.getObject("Exp") != null) Exp.ExpNow.put(p, rs.getInt("Exp"));
						if (rs.getObject("NeededExp") != null) Exp.NeededExpNow.put(p, rs.getInt("NeededExp"));
						if (rs.getObject("NeededExpBefore") != null) Exp.NeededExpFromBefore.put(p, rs.getInt("NeededExpBefore"));
						break;
					}
				}
				
				rs.close();
				ps.close();
				conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void saveScores(Player p) {
		if (!(MySQL.mySQLenabled())) {
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
		}else{
			try {
				Connection conn = MySQL.getConnection();
				String INSERT = "INSERT INTO CODScores VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE Credits=?, Level=?, Kills=?, Deaths=?"
						+ ", HighestKillStreak=?, Exp=?, NeededExp=?, NeededExpBefore=?";
				PreparedStatement ps = conn.prepareStatement(INSERT);
				
				ps.setString(1, p.getUniqueId().toString());
				
				ps.setInt(2, Main.LobbyCreditsScore.get(p.getName()).getScore());
				ps.setInt(10, Main.LobbyCreditsScore.get(p.getName()).getScore());
				
				ps.setInt(3, Main.LobbyLevelScore.get(p.getName()).getScore());
				ps.setInt(11, Main.LobbyLevelScore.get(p.getName()).getScore());
				
				ps.setInt(4, Main.LobbyKillsScore.get(p.getName()).getScore());
				ps.setInt(12, Main.LobbyKillsScore.get(p.getName()).getScore());
				
				ps.setInt(5, Main.LobbyDeathsScore.get(p.getName()).getScore());
				ps.setInt(13, Main.LobbyDeathsScore.get(p.getName()).getScore());
				
				ps.setInt(6, Main.highestKillstreak.get(p.getName()).getScore());
				ps.setInt(14, Main.highestKillstreak.get(p.getName()).getScore());
				
				ps.setInt(7, Exp.getExp(p));
				ps.setInt(15, Exp.getExp(p));
				
				ps.setInt(8, Exp.getNeededExp(p));
				ps.setInt(16, Exp.getNeededExp(p));
				
				ps.setInt(9, Exp.getNeededExpFromBefore(p));
				ps.setInt(17, Exp.getNeededExpFromBefore(p));
				
				ps.executeUpdate();
				
				ps.close();
				conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean scoresExist(Player p) {
		if (!(MySQL.mySQLenabled())) {
			return ScoresFile.getData().contains("Scores." + p.getUniqueId());
		}else{
			try {
				Connection conn = MySQL.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT uuid FROM CODScores");
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					if (rs.getString("uuid").equals(p.getUniqueId().toString())) return true;
				}
				
				rs.close();
				ps.close();
				conn.close();
				
				return false;
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			return false;
		}
	}
}
