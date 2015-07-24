package com.CentrumGuy.CodWarfare.Commands;

import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Files.EnabledArenasFile;

public class ArenaListCommand {

	public static void listEnabledArenas(Player p, String[] args) {
		if (!(p.hasPermission("cod.arenasList"))) {
			p.sendMessage(Main.codSignature + "§cYou do not have the necessary permissions");
			return;
		}
		
		/*if (ArenasFile.getData().getStringList("Arenas").isEmpty()) {
			p.sendMessage(Main.codSignature + "§cThere are no enabled arenas");
			return;
		}else{
			ArrayList<String> arenas = (ArrayList<String>) ArenasFile.getData().getStringList("Arenas");
			
			ArrayList<String> enabledArenas = new ArrayList<String>();
			int nextInt = 1;
			while (EnabledArenasFile.getData().getString("EnabledArenas." + nextInt) != null) {
				enabledArenas.add(EnabledArenasFile.getData().getString("EnabledArenas." + nextInt));
				nextInt++;
			}
			
			p.sendMessage("§b§lArenas:");
			
			for (int i = 0 ; i < arenas.size() ; i++) {
				String arena = arenas.get(i);
				if (enabledArenas.contains(arena)) {
					p.sendMessage("§7- §2" + arena + " §7» §a§oenabled");
				}else{
					p.sendMessage("§7- §4" + arena + " §7» §c§odisabled");
				}
			}
		}*/
		
		if (EnabledArenasFile.getData().getString("EnabledArenas.1") == null) {
			p.sendMessage(Main.codSignature + "§cThere are no enabled arenas");
			return;
		}else{
			p.sendMessage("§a§lEnabled Arenas:");
			
			int nextInt = 1;
			while (EnabledArenasFile.getData().getString("EnabledArenas." + nextInt) != null) {
				p.sendMessage(" §7-§e " + EnabledArenasFile.getData().getString("EnabledArenas." + nextInt));
				nextInt++;
			}
		}
	}
}
