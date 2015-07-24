package com.CentrumGuy.CodWarfare.Commands;

import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Arena.DeleteArena;
import com.CentrumGuy.CodWarfare.Files.ArenasFile;

public class DeleteArenaCommand {
	@SuppressWarnings("deprecation")
	public static void deleteArena(String ArenaName, Player p) {
		DeleteArena.deleteArena(ArenaName, p);
		ArenasFile.saveData();
		
		if (CreateArenaCommand.creatingArena.get(p) == true) {
			if (ArenaName.equals(CreateArenaCommand.arenaCreating.get(p))) {
				CreateArenaCommand.arenaCreating.put(p, null);
				p.getInventory().clear();
				p.getInventory().setContents(CreateArenaCommand.savedInventory.get(p));
				CreateArenaCommand.creatingArena.put(p, false);
				p.sendMessage(Main.codSignature + "§7You left arena creator mode");
				p.updateInventory();
			}
		}
	}
}