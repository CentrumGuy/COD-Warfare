package com.CentrumGuy.CodWarfare.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Arena.LeaveArena;

public class LeaveCommand {
	
	@SuppressWarnings("deprecation")
	public static void LeaveCOD(CommandSender sender, String[] args) {
		if (args.length == 1) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				LeaveArena.Leave(p, true, true, true);
			}else{
				sender.sendMessage(Main.codSignature + "§cYou must be a player to leave COD-Warfare");
			}
		}else if (args.length == 2) {
			
			if (!(sender.hasPermission("cod.leaveother"))) {
				sender.sendMessage(Main.codSignature + "§cYou do not have permission to make other players leave");
				return;
			}
			
			String target = args[1];
			
			if (!(Bukkit.getPlayer(target) == null)) {
				Player pt = Bukkit.getPlayer(target);
				
				LeaveArena.Leave(pt, true, true, true);
			}else{
				sender.sendMessage(Main.codSignature + "§cCouldn't find player§4 " + target);
			}
		}else{
			sender.sendMessage(Main.codSignature + "§cPlease type §4/cod leave [Player Name]");
		}
	}
}