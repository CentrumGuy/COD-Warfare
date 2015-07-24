package com.CentrumGuy.CodWarfare.Interface;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Arena.CTFArena;
import com.CentrumGuy.CodWarfare.Arena.PickRandomArena;
import com.CentrumGuy.CodWarfare.Arena.getArena;
import com.CentrumGuy.CodWarfare.Files.LobbyFile;
import com.CentrumGuy.CodWarfare.Files.ScoresFile;
import com.CentrumGuy.CodWarfare.Inventories.AGPInventory;
import com.CentrumGuy.CodWarfare.Inventories.AGSInventory;
import com.CentrumGuy.CodWarfare.Inventories.Guns;
import com.CentrumGuy.CodWarfare.Inventories.ShopInventoryPrimary;
import com.CentrumGuy.CodWarfare.Inventories.ShopInventorySecondary;
import com.CentrumGuy.CodWarfare.Leveling.Exp;
import com.CentrumGuy.CodWarfare.Lobby.Lobby;
import com.CentrumGuy.CodWarfare.Utilities.Prefix;
import com.CentrumGuy.CodWarfare.Utilities.SendCoolMessages;
import com.CentrumGuy.extras.parkour.Parkour;

public class ResetPlayer {

	@SuppressWarnings("deprecation")
	public static void reset(Player p) {
		p.getInventory().clear();
		p.updateInventory();
		
		if (Main.extras) Parkour.leaveParkour(p, false, false);
		
		Scores.saveScores(p);
		if (getArena.getType(PickRandomArena.CurrentArena).equals("CTF")) {
			if (CTFArena.holdingBlueFlag.get(p) != null && CTFArena.holdingBlueFlag.get(p) == true) {
				CTFArena.returnFlag("blue", PickRandomArena.CurrentArena);
        		for (Player pp : Main.PlayingPlayers) {
        			pp.sendMessage(Main.codSignature + "§c" + p.getName() + " §6dropped the §9blue §6flag");
        		}
				CTFArena.holdingBlueFlag.put(p, false);
			}else if (CTFArena.holdingRedFlag.get(p) != null && CTFArena.holdingRedFlag.get(p) == true) {
				CTFArena.returnFlag("red", PickRandomArena.CurrentArena);
        		for (Player pp : Main.PlayingPlayers) {
        			pp.sendMessage(Main.codSignature + "§9" + p.getName() + " §6dropped the §cred §6flag");
        		}
    			CTFArena.holdingRedFlag.put(p, false);
			}
		}
		
		p.setAllowFlight(false);
		p.setFlying(false);
		
		p.setFoodLevel(20);
		
		p.setMaxHealth(20);
		p.setHealth(20);
		
		for(PotionEffect effect : p.getActivePotionEffects()) {
		    p.removePotionEffect(effect.getType());
		}
		
        ShopInventoryPrimary.loadPrimaryShop(p);
		ShopInventorySecondary.loadSecondaryShop(p);
		AGPInventory.loadAGP(p);
		AGSInventory.loadAGS(p);
		//KitInventory.loadKit(p);
		
		if (!(Main.WaitingPlayers.contains(p))) Main.WaitingPlayers.add(p);
		
		Prefix.setDispName(p, "§e" + p.getName());
		for (Player pp : Bukkit.getOnlinePlayers()) {
			if (Main.dispName.get(pp) != null) pp.setPlayerListName(Main.dispName.get(pp));
		}
		
		p.getInventory().setItem(0, Main.shoptool);
		if (Main.testGuns == true) p.getInventory().setItem(6, Main.tryGuns);
		p.getInventory().setHeldItemSlot(4);
		p.closeInventory();
		
		ItemStack HelpBook;
		
		HelpBook = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta hbm = (BookMeta) HelpBook.getItemMeta();
		hbm.setPages(Arrays.asList("§4§lContents\n\n§21. §cTeam Death Match\n§22. §cCapture The Flag\n§23. §cFree For All\n§24. §cInfected\n§25. §cOne In The Chamber\n§26. §cGun Game\n§27. §cKill Confirmed\n§28. §cBasics"
				, "§4§lTeam Death Match\n§d§m------------------\n§6In this game, a §cred\n§6team and a §9blue §6team will fight one another. The team with the most kills at the end wins."
				, "§4§lCapture The Flag\n§d§m------------------\n§6In this game, a §cred\n§6team and a §9blue §6team will try to capture each other's flags. To capture the flag, stand near the other teams flag and run back to your team's flag. The team with the most captures at the end wins."
				, "§4§lFree For All\n§d§m------------------\n§6In this game, §dall \n§6players will kill each other. First player to 21 kills or player with the most kills at the end of the game, wins the game."
				, "§4§lInfected\n§d§m------------------\n§6In this game, a single\n§czombie §6spawns and tries to infect the §9survivors§6 by killing them. If all §9survivors§6 are infected, the §czombies§6 win."
				, "§4§lOne In The Chamber\n§d§m------------------\n§6In this game, §dyou §6have three lives. You start with a single bullet and get more as you kill more people. Remember, one hit kill!"
				, "§4§lGun Game\n§d§m------------------\n§6In this game, §dyou §6start\nout with the worst gun. Guns get better as you kill more people."
				, "§4§lKill Confirmed\n§d§m------------------\n§6In this game, there is a §cred\n§6team and a §9blue §6team. When players die they drop their teams's tag. To get points pick up the other team's tags. To prevent the other team from getting points, pick up the your team's tags."
				, "§4§lBasics\n§d§m------------------\n§6For every kill, you get one credit and thirty experience. Rankup levels by getting more experience. Type §c/cod\nxp§6 to view your experience. Unlock guns as you rank up levels, and buy them with your credits."
				, "§4§lEnjoy\n§d§m------------------\n§5I am not afraid of an army of lions led by a sheep; I am afraid of an army of sheep led by a lion.\n\n§2~Alexander The Great"));
		hbm.setAuthor("§4COD-Warfare");
		hbm.setTitle("§3How to Play§r");
		HelpBook.setItemMeta(hbm);
		
		p.setGameMode(GameMode.SURVIVAL);
		
		ItemStack UpdateBook;
		
		UpdateBook = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta ubm = (BookMeta) UpdateBook.getItemMeta();
		ubm.addPage("§4§lTHIS PLUGIN IS IN PROGRESS" , "§6§lIT STILL HASN'T BEEN RELEASED YET");
		ubm.setAuthor("§4COD-WARFARE");
		ubm.setTitle("§e§lUPDATE INFO§r");
		UpdateBook.setItemMeta(ubm);
		
		p.updateInventory();
		
		p.getInventory().setItem(5, HelpBook);
		//player.getInventory().setHeldItemSlot(6);
		//player.getInventory().setItemInHand(UpdateBook);
		//player.getInventory().setHeldItemSlot(0);
		
		if (ScoresFile.getData().contains("Scores." + p.getUniqueId())) {
			Scores.saveScores(p);
			Scores.loadScores(p);
			float precent = (((float) Exp.ExpNow.get(p)) / ((float) Exp.NeededExpNow.get(p)));
			p.setExp(precent);
			Main.setLobbyBoard(p);
		}
		
		if (!(LobbyFile.getData().getConfigurationSection("Lobby") == null)) {
			p.teleport(Lobby.getLobby());
		}else if (p.isOp()) {
			p.sendMessage(Main.codSignature + "§cPlease set a lobby by typing §4/cod lobby set");
		}
		
		Color c = Color.fromRGB(255, 255, 0);
		p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
		p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
		p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
		p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
		
		p.closeInventory();
        
		AGPInventory.getAGP(p).setItem(49, ItemsAndInventories.backAG);
		AGSInventory.getAGS(p).setItem(49, ItemsAndInventories.backAG);
		
		ShopInventoryPrimary.getPrimaryShop(p).setItem(49, ItemsAndInventories.backShop);
		ShopInventorySecondary.getSecondaryShop(p).setItem(49, ItemsAndInventories.backShop);
		
		ItemsAndInventories.updateShop(p);
		
		Guns.loadGuns();
		
        ReloadAG.load(p);
        
        SendCoolMessages.TabHeaderAndFooter("§e§lWaiting", "§6§lCOD-Warfare", p);
        
        p.setWalkSpeed((float) 0.2);
        
        //Main.lobby.addPlayer(p);
	}
	
	private static ItemStack getColorArmor(Material m, Color c) {
		ItemStack i = new ItemStack(m , 1);
		LeatherArmorMeta meta = (LeatherArmorMeta) i.getItemMeta();
		meta.setColor(c);
		i.setItemMeta(meta);
		return i;
	}
}
