package com.CentrumGuy.CodWarfare.Commands;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Arena.CreateArena;
import com.CentrumGuy.CodWarfare.Files.ArenasFile;
import com.CentrumGuy.CodWarfare.Utilities.Items;

public class CreateArenaCommand {
	public static HashMap<Player, Boolean> creatingArena = new HashMap<Player, Boolean>();
	public static HashMap<Player, ItemStack[]> savedInventory = new HashMap<Player, ItemStack[]>();
	public static ItemStack redSpawnTool = Items.createItem(Material.WOOL, 1, 14, "§6» §3Right click to set §cred spawn §6«");
	public static ItemStack blueSpawnTool = Items.createItem(Material.WOOL, 1, 11, "§6» §3Right click to set §9blue spawn §6«");
	public static ItemStack ffaSpawnTool = Items.createItem(Material.WOOL, 1, 2, "§6» §3Right click to add §dspawn §6«");
	
	public static ItemStack redFlagTool = Items.createItem(Material.WOOL, 1, 14, "§6» §3Right click to add §cred flag spawn §6«");
	public static ItemStack blueFlagTool = Items.createItem(Material.WOOL, 1, 11, "§6» §3Right click to add §9blue flag spawn §6«");
	
	public static ItemStack oneinSpecTool = Items.createItem(Material.WOOL, 1, 5, "§6» §3Right click to add §aspectator spawn §6«");
	
	public static ItemStack enabledTool = Items.createItem(Material.WOOL, 1, 5, "§6» §eRight click to enable the arena §6«");
	
	public static HashMap<Player, String> arenaCreating = new HashMap<Player, String>();
	
	public static void createArena(String ArenaName, String ArenaType, Player p) {
		if (ArenaType.equalsIgnoreCase("tdm") || ArenaType.equalsIgnoreCase("infect") || ArenaType.equalsIgnoreCase("gun") || ArenaType.equalsIgnoreCase("ffa") || ArenaType.equalsIgnoreCase("onein") || ArenaType.equalsIgnoreCase("CTF") || ArenaType.equalsIgnoreCase("KC")) {
			
			if (Main.prefixGM) {
				if (ArenaType.equalsIgnoreCase("tdm")) ArenaName = ArenaName + "_TDM";
				if (ArenaType.equalsIgnoreCase("infect")) ArenaName = ArenaName + "_INF";
				if (ArenaType.equalsIgnoreCase("gun")) ArenaName = ArenaName + "_GUN";
				if (ArenaType.equalsIgnoreCase("ffa")) ArenaName = ArenaName + "_FFA";
				if (ArenaType.equalsIgnoreCase("onein")) ArenaName = ArenaName + "_OITC";
				if (ArenaType.equalsIgnoreCase("ctf")) ArenaName = ArenaName + "_CTF";
				if (ArenaType.equalsIgnoreCase("kc")) ArenaName = ArenaName + "_KC";
			}
			
			CreateArena.createArena(ArenaName, ArenaType.toUpperCase(), p);
			
			ArenasFile.saveData();
		}else{
			p.sendMessage(Main.codSignature + "§cArena type§4 " + ArenaType + " §cis invalid");
			p.sendMessage("§6Valid arent types are§b tdm§6, §binfect§6, §bctf§6, §bffa§6, §bgun§6, §bonein§6, §bkc");
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (creatingArena.get(p) == true) {
			if ((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
				if (p.getItemInHand() == null) return;
				e.setCancelled(true);
				
				if (e.getItem().equals(redSpawnTool)) {
					p.chat("/cod set " + arenaCreating.get(p) + " spawn red");
				}else if (e.getItem().equals(blueSpawnTool)) {
					p.chat("/cod set " + arenaCreating.get(p) + " spawn blue");
				}else if (e.getItem().equals(ffaSpawnTool)) {
					p.chat("/cod set " + arenaCreating.get(p) + " spawn next");
				}else if (e.getItem().equals(redFlagTool)) {
					p.chat("/cod set " + arenaCreating.get(p) + " flag red");
				}else if (e.getItem().equals(blueFlagTool)) {
					p.chat("/cod set " + arenaCreating.get(p) + " flag blue");
				}else if (e.getItem().equals(oneinSpecTool)) {
					p.chat("/cod set " + arenaCreating.get(p) + " spec");
				}else if (e.getItem().equals(enabledTool)) {
					p.chat("/cod set " + arenaCreating.get(p) + " enabled");
					p.getInventory().clear();
					p.getInventory().setContents(savedInventory.get(p));
					arenaCreating.put(p, null);
					creatingArena.put(p, false);
					p.sendMessage(Main.codSignature + "§7You left arena creator mode");
					p.updateInventory();
				}
			}
		}
	}
}
