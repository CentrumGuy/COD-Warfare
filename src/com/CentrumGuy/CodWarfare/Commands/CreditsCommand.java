package com.CentrumGuy.CodWarfare.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Interface.Scores;

public class CreditsCommand {

	@SuppressWarnings("deprecation")
	public static void addCredits(CommandSender p, String[] args) {
		if (!(p.hasPermission("cod.creditsAdd"))) {
			p.sendMessage(Main.codSignature + "§cYou do not have the necessary permissions");
			return;
		}
		
		if (args.length != 4) {
			p.sendMessage(Main.codSignature + "§cPlease type §4/cod credits add [Player Name] [Amount]");
			return;
		}
		
		Player pp = Bukkit.getServer().getPlayer(args[2]);
		
		if (pp == null) {
			p.sendMessage(Main.codSignature + "§cPlayer§4 " + args[2] + " §ccannot be found");
			return;
		}
		
		Scores.saveScores(pp);
		Scores.loadScores(pp);
		
		Main.LobbyCreditsScore.get(pp.getName()).setScore((Main.LobbyCreditsScore.get(pp.getName()).getScore()) + (Integer.parseInt(args[3])));
		Main.GameCreditsScore.get(pp.getName()).setScore(Main.LobbyCreditsScore.get(pp.getName()).getScore());
		
		if (p.equals(pp)) {
			p.sendMessage(Main.codSignature + "§aYour credits score is now§2 " + Main.LobbyCreditsScore.get(pp.getName()).getScore());
		}else{
			p.sendMessage(Main.codSignature + "§2" + pp.getName() + "'s §acredits score is now§2 " + Main.LobbyCreditsScore.get(pp.getName()).getScore());
			pp.sendMessage(Main.codSignature + "§2" + p.getName() + " §aadded§2 " + args[3] + " §acredits to your total credits score");
		}
		
		Scores.saveScores(pp);
		Scores.loadScores(pp);
		return;
	}
	
	@SuppressWarnings("deprecation")
	public static void takeCredits(CommandSender p, String[] args) {
		if (!(p.hasPermission("cod.creditsTake"))) {
			p.sendMessage(Main.codSignature + "§cYou do not have the necessary permissions");
			return;
		}
		
		if (args.length != 4) {
			p.sendMessage(Main.codSignature + "§cPlease type §4/cod credits take [Player Name] [Amount]");
			return;
		}
		
		Player pp = Bukkit.getServer().getPlayer(args[2]);
		
		if (pp == null) {
			p.sendMessage(Main.codSignature + "§cPlayer§4 " + args[2] + " §ccannot be found");
			return;
		}
		
		Scores.saveScores(pp);
		Scores.loadScores(pp);
		
		Main.LobbyCreditsScore.get(pp.getName()).setScore((Main.LobbyCreditsScore.get(pp.getName()).getScore()) - (Integer.parseInt(args[3])));
		Main.GameCreditsScore.get(pp.getName()).setScore(Main.LobbyCreditsScore.get(pp.getName()).getScore());
		
		if (p.equals(pp)) {
			p.sendMessage(Main.codSignature + "§aYour credits score is now§2 " + Main.LobbyCreditsScore.get(pp.getName()).getScore());
		}else{
			p.sendMessage(Main.codSignature + "§2" + pp.getName() + "'s §acredits score is now§2 " + Main.LobbyCreditsScore.get(pp.getName()).getScore());
			pp.sendMessage(Main.codSignature + "§2" + p.getName() + " §atook§2 " + args[3] + " §acredits from your total credits score");
		}
		
		Scores.saveScores(pp);
		Scores.loadScores(pp);
		return;
	}
	
	@SuppressWarnings("deprecation")
	public static void setCredits(CommandSender p, String[] args) {
		if (!(p.hasPermission("cod.creditsSet"))) {
			p.sendMessage(Main.codSignature + "§cYou do not have the necessary permissions");
			return;
		}
		
		if (args.length != 4) {
			p.sendMessage(Main.codSignature + "§cPlease type §4/cod credits set [Player Name] [Amount]");
			return;
		}
		
		Player pp = Bukkit.getServer().getPlayer(args[2]);
		
		if (pp == null) {
			p.sendMessage(Main.codSignature + "§cPlayer§4 " + args[2] + " §ccannot be found");
			return;
		}
		
		Scores.saveScores(pp);
		Scores.loadScores(pp);
		
		Main.LobbyCreditsScore.get(pp.getName()).setScore(Integer.parseInt(args[3]));
		Main.GameCreditsScore.get(pp.getName()).setScore(Main.LobbyCreditsScore.get(pp.getName()).getScore());
		
		if (p.equals(pp)) {
			p.sendMessage(Main.codSignature + "§aYour credits score is now§2 " + Main.LobbyCreditsScore.get(pp.getName()).getScore());
		}else{
			p.sendMessage(Main.codSignature + "§2" + pp.getName() + "'s §acredits score is now§2 " + Main.LobbyCreditsScore.get(pp.getName()).getScore());
			pp.sendMessage(Main.codSignature + "§2" + p.getName() + " §aset your credits score to§2 " + Main.LobbyCreditsScore.get(pp.getName()).getScore());
		}
		
		Scores.saveScores(pp);
		Scores.loadScores(pp);
		return;
	}
	
}
