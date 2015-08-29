package com.CentrumGuy.CodWarfare.Interface;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.Scoreboard;

import com.CentrumGuy.CodWarfare.Listeners;
import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Achievements.AchievementsAPI;
import com.CentrumGuy.CodWarfare.Arena.BaseArena;
import com.CentrumGuy.CodWarfare.Arena.Countdown;
import com.CentrumGuy.CodWarfare.Files.LobbyFile;
import com.CentrumGuy.CodWarfare.Inventories.AGPInventory;
import com.CentrumGuy.CodWarfare.Inventories.AGSInventory;
import com.CentrumGuy.CodWarfare.Inventories.Guns;
import com.CentrumGuy.CodWarfare.Inventories.KitInventory;
import com.CentrumGuy.CodWarfare.Inventories.ShopInventoryPrimary;
import com.CentrumGuy.CodWarfare.Inventories.ShopInventorySecondary;
import com.CentrumGuy.CodWarfare.Leveling.Exp;
import com.CentrumGuy.CodWarfare.Lobby.Lobby;
import com.CentrumGuy.CodWarfare.Utilities.Prefix;
import com.CentrumGuy.CodWarfare.Utilities.SendCoolMessages;
import com.CentrumGuy.extras.parkour.Parkour;

public class JoinCOD {
	
	public static HashMap<Player, Location> teleport = new HashMap<Player, Location>();
	public static HashMap<Player, Double> Health = new HashMap<Player, Double>();
	public static HashMap<Player, Integer> Food = new HashMap<Player, Integer>();
	public static HashMap<Player, ItemStack[]> inventory = new HashMap<Player, ItemStack[]>();
	public static HashMap<Player, ItemStack[]> ArmorContents = new HashMap<Player, ItemStack[]>();
	public static HashMap<Player, Float> PlayerExp = new HashMap<Player, Float>();
	public static HashMap<Player, Integer> Level = new HashMap<Player, Integer>();
	public static HashMap<Player, GameMode> gamemode = new HashMap<Player, GameMode>();
	public static HashMap<Player, Scoreboard> sb = new HashMap<Player, Scoreboard>();
	public static HashMap<Player, String> tabName = new HashMap<Player, String>();
	public static HashMap<Player, Collection<PotionEffect>> pEffects = new HashMap<Player, Collection<PotionEffect>>();
	public static HashMap<Player, Boolean> canFly = new HashMap<Player, Boolean>();
	public static HashMap<Player, Boolean> isFlying = new HashMap<Player, Boolean>();
	public static HashMap<Player, Float> speed = new HashMap<Player, Float>();

	@SuppressWarnings("deprecation")
	public static void join(boolean JoinMessage, final Player p, boolean login) {
		if (!(p.isOnline())) return;
		
		if (Main.WaitingPlayers.contains(p) || Main.PlayingPlayers.contains(p)) {
			if (JoinMessage) {
				p.sendMessage(Main.codSignature + "§cYou are already in COD-Warfare");
				return;
			}
		}
		
		if (((Main.WaitingPlayers.size() + Main.PlayingPlayers.size()) >= Main.max_Players) && (Main.max_Players != 0)) {
			p.sendMessage(Main.codSignature + "§cYou cannot join COD-Warfare because the maximum number of players has been reached");
			return;
		}
		
		if (JoinMessage) {
			SendCoolMessages.sendOverActionBar(p, "§a§lYou §2§lJoined §a§lCOD-Warfare");
			if (Main.extras) {
				if (!(Parkour.parkour.contains(p))) {
					teleport.put(p, p.getLocation());
					Food.put(p, p.getFoodLevel());
				}else{
					teleport.put(p, Parkour.loc.get(p));
					Food.put(p, Parkour.foodLevel.get(p));
				}
			}
			
			if (!(Main.extras)) {
				teleport.put(p, p.getLocation());
				Food.put(p, p.getFoodLevel());
			}
			
			
			Health.put(p, p.getHealth());
			inventory.put(p, p.getInventory().getContents());
			ArmorContents.put(p, p.getInventory().getArmorContents());
			PlayerExp.put(p, p.getExp());
			Level.put(p, p.getLevel());
			gamemode.put(p, p.getGameMode());
			sb.put(p, p.getScoreboard());
			pEffects.put(p, p.getActivePotionEffects());
			canFly.put(p, p.getAllowFlight());
			isFlying.put(p, p.isFlying());
			speed.put(p, p.getWalkSpeed());
		}
		
		if (checkNotNull(p) == false) {
			p.sendMessage(Main.codSignature + "§cYou need to have at least one available primary gun and one available secondary gun to join COD-Warfare");
			return;
		}
		
		for (int i = 0 ; i < p.getInventory().getSize() ; i++) {
			p.getInventory().setItem(i, new ItemStack(Material.AIR));
		}
		
		p.updateInventory();
		
        p.setWalkSpeed((float) 0.2);
		
		p.setAllowFlight(false);
		p.setFlying(false);
		
		p.setFoodLevel(20);
		
		Main.WaitingPlayers.add(p);
		
		if ((Main.WaitingPlayers.size() == Main.min_Players) && (Main.PlayingPlayers.isEmpty())) {
			Countdown.CancelLobby();
			Countdown.CancelGame();
			Countdown.ChangingLobbyTime = Countdown.LobbyTime;
			Countdown.StartLobbyCountdown();
		}else if (((Main.WaitingPlayers.size() + Main.PlayingPlayers.size()) < Main.min_Players)) {
			for (Player pp : Main.WaitingPlayers) {
				BaseArena.state = BaseArena.ArenaState.WAITING;
				if (JoinMessage) pp.sendMessage(Main.codSignature + "§a§l" + (Main.min_Players - Main.WaitingPlayers.size()) + " More players §2are needed to start the game");
			}
		}
		
		p.setLevel((Main.lobbyTime) - (1));
		
        ShopInventoryPrimary.loadPrimaryShop(p);
		ShopInventorySecondary.loadSecondaryShop(p);
		AGPInventory.loadAGP(p);
		AGSInventory.loadAGS(p);
		KitInventory.loadKit(p);
		
		for(PotionEffect effect : p.getActivePotionEffects()) {
		    p.removePotionEffect(effect.getType());
		}
		
		//Main.lobby.addPlayer(p);
		
		p.setMaxHealth(20);
		p.setHealth(20);
		
		Prefix.setDispName(p, "§e" + p.getName());
		for (Player pp : Bukkit.getOnlinePlayers()) {
			if (Main.dispName.get(pp) != null) pp.setPlayerListName(Main.dispName.get(pp));
		}
		
		SendCoolMessages.TabHeaderAndFooter("§e§lWaiting", "§6§lCOD-Warfare", p);
		
		if (Main.WaitingPlayers.contains(p) || Main.PlayingPlayers.contains(p)) {
			Player player = p;
			player.getInventory().clear();
			player.getInventory().setItem(0, Main.shoptool);
			if (Main.testGuns == true) player.getInventory().setItem(7, Main.tryGuns);
			player.getInventory().setItem(6, AchievementsAPI.getAchievementsItem(p));
			player.getInventory().setHeldItemSlot(4);
			if (!(login)) player.closeInventory();
			
			player.updateInventory();
			
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
			
			player.getInventory().setItem(5, HelpBook);
			//player.getInventory().setHeldItemSlot(6);
			//player.getInventory().setItemInHand(UpdateBook);
			//player.getInventory().setHeldItemSlot(0);
			
			if (JoinMessage) player.sendMessage(Main.codSignature + "§bYou joined COD-Warfare");
			if (JoinMessage) p.sendMessage(Main.codSignature + "§dGame will begin shortly.");
			
			if (Scores.scoresExist(player)) {
				Scores.loadScores(p);
				float precent = (((float) Exp.ExpNow.get(p)) / ((float) Exp.NeededExpNow.get(p)));
				p.setExp(precent);
				Main.setLobbyBoard(player);
			}
			
			if (!(LobbyFile.getData().getConfigurationSection("Lobby") == null)) {
				p.teleport(Lobby.getLobby());
			}else{
				p.teleport(p.getWorld().getSpawnLocation());
				
				if (p.isOp()) {
					p.sendMessage(Main.codSignature + "§cPlease set a lobby by typing §4/cod lobby set");
				}
			}
			
			Listeners.MessageFromBefore.put(player, "");
			
			Color c = Color.fromRGB(255, 255, 0);
			player.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
			player.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
			player.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
			player.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
			
			if (Exp.getExp(player) == null) {
				Exp.setExp(player, 0);
			}
			
			if (Exp.getNeededExp(player) == null) {
				Exp.setNeededExp(player, 210);
			}
	        
			if (!(login)) p.closeInventory();
	        
			AGPInventory.getAGP(p).setItem(49, ItemsAndInventories.backAG);
			AGSInventory.getAGS(p).setItem(49, ItemsAndInventories.backAG);
			
			ShopInventoryPrimary.getPrimaryShop(p).setItem(49, ItemsAndInventories.backShop);
			ShopInventorySecondary.getSecondaryShop(p).setItem(49, ItemsAndInventories.backShop);
			
			ItemsAndInventories.updateShop(p);
			
			Guns.loadGuns();
			
	        ReloadAG.load(p);
		}
	}
	
	private static ItemStack getColorArmor(Material m, Color c) {
		ItemStack i = new ItemStack(m , 1);
		LeatherArmorMeta meta = (LeatherArmorMeta) i.getItemMeta();
		meta.setColor(c);
		i.setItemMeta(meta);
		return i;
	}
	
	private static boolean checkNotNull(Player p) {
		FirstJoin.fJoin(p);
		
		AGPInventory.loadAGP(p);
		AGSInventory.loadAGS(p);
		
		Boolean b1 = false;
		Boolean b2 = false;
		
		ItemsAndInventories.setUpPlayer(p);
		ItemsAndInventories.setAvailableGuns(p);
		
		for (int i = 0 ; i < AGPInventory.getAGP(p).getSize() ; i++) {
			ItemStack item = AGPInventory.getAGP(p).getItem(i);
			
			if (item == null || item.getType() == Material.AIR) continue;
			if (item.equals(ItemsAndInventories.backAG)) continue;
			
				b1 = true;
		}
		
		for (int i = 0 ; i < AGSInventory.getAGS(p).getSize() ; i++) {
			ItemStack item = AGSInventory.getAGS(p).getItem(i);
			
			if (item == null || item.getType() == Material.AIR) continue;
			if (item.equals(ItemsAndInventories.backAG)) continue;
			
				b2 = true;
		}
		
		if (b1 == true) {
			if (b2 == true) {
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
}
