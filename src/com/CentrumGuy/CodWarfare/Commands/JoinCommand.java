package com.CentrumGuy.CodWarfare.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Arena.JoinArena;

public class JoinCommand {
	
	@SuppressWarnings("deprecation")
	public static void JoinCOD(CommandSender s, String[] args) {
		if (args.length == 1) {
			
			if (s instanceof Player) {
				Player p = (Player) s;
				JoinArena.Join(p);
			}else{
				s.sendMessage(Main.codSignature + "§cYou must be a player to join COD-Warfare");
				return;
			}
			
		}else if (args.length == 2) {
			
			if (!(s.hasPermission("cod.joinother"))) {
				s.sendMessage(Main.codSignature + "§cYou do not have permission to make other players join");
				return;
			}
			
			String target = args[1];
			
			if (!(Bukkit.getPlayer(target) == null)) {
				Player pt = Bukkit.getPlayer(target);
				
				JoinArena.Join(pt);
			}else{
				s.sendMessage(Main.codSignature + "§cCouldn't find player§4 " + target);
			}
		}else{
			s.sendMessage(Main.codSignature + "§cPlease type §4/cod join [Player Name]");
		}
	}
}
