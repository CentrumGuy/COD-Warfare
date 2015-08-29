package com.CentrumGuy.CodWarfare;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Score;

import com.CentrumGuy.CODWeapons.Weapons.C4;
import com.CentrumGuy.CodWarfare.Updater.UpdateResult;
import com.CentrumGuy.CodWarfare.Updater.UpdateType;
import com.CentrumGuy.CodWarfare.Achievements.AchievementsAPI;
import com.CentrumGuy.CodWarfare.Arena.BaseArena;
import com.CentrumGuy.CodWarfare.Arena.CTFArena;
import com.CentrumGuy.CodWarfare.Arena.FFAArena;
import com.CentrumGuy.CodWarfare.Arena.GUNArena;
import com.CentrumGuy.CodWarfare.Arena.INFECTArena;
import com.CentrumGuy.CodWarfare.Arena.KillArena;
import com.CentrumGuy.CodWarfare.Arena.LeaveArena;
import com.CentrumGuy.CodWarfare.Arena.ONEINArena;
import com.CentrumGuy.CodWarfare.Arena.PickRandomArena;
import com.CentrumGuy.CodWarfare.Arena.TDMArena;
import com.CentrumGuy.CodWarfare.Arena.ZombieTeam;
import com.CentrumGuy.CodWarfare.Arena.getArena;
import com.CentrumGuy.CodWarfare.Clans.MainClan;
import com.CentrumGuy.CodWarfare.Commands.CreateArenaCommand;
import com.CentrumGuy.CodWarfare.Commands.CreateGunCommand;
import com.CentrumGuy.CodWarfare.Interface.EditLoadout;
import com.CentrumGuy.CodWarfare.Interface.ItemsAndInventories;
import com.CentrumGuy.CodWarfare.Interface.JoinCOD;
import com.CentrumGuy.CodWarfare.Interface.ResetPlayer;
import com.CentrumGuy.CodWarfare.Interface.Scores;
import com.CentrumGuy.CodWarfare.Inventories.AGPInventory;
import com.CentrumGuy.CodWarfare.Inventories.AGSInventory;
import com.CentrumGuy.CodWarfare.Inventories.Guns;
import com.CentrumGuy.CodWarfare.Inventories.KitInventory;
import com.CentrumGuy.CodWarfare.Inventories.ShopInventoryPrimary;
import com.CentrumGuy.CodWarfare.Inventories.ShopInventorySecondary;
import com.CentrumGuy.CodWarfare.Leveling.Exp;
import com.CentrumGuy.CodWarfare.Leveling.Level;
import com.CentrumGuy.CodWarfare.Lobby.Lobby;
import com.CentrumGuy.CodWarfare.OtherLoadout.Perk;
import com.CentrumGuy.CodWarfare.OtherLoadout.PerkAPI;
import com.CentrumGuy.CodWarfare.OtherLoadout.WeaponUtils;
import com.CentrumGuy.CodWarfare.Packets.WrapperPlayClientClientCommand;
import com.CentrumGuy.CodWarfare.ParticleEffects.ParticleEffect;
import com.CentrumGuy.CodWarfare.ParticleEffects.ParticleEffect.BlockData;
import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;
import com.CentrumGuy.CodWarfare.SpecialWeapons.AirStrike;
import com.CentrumGuy.CodWarfare.SpecialWeapons.Dogs;
import com.CentrumGuy.CodWarfare.SpecialWeapons.ElectroMagneticPulse;
import com.CentrumGuy.CodWarfare.SpecialWeapons.MoreAmmo;
import com.CentrumGuy.CodWarfare.SpecialWeapons.Nuke;
import com.CentrumGuy.CodWarfare.Utilities.DeathBloodEffect;
import com.CentrumGuy.CodWarfare.Utilities.GameCountdown;
import com.CentrumGuy.CodWarfare.Utilities.IChatMessage;
import com.CentrumGuy.CodWarfare.Utilities.PlaySounds;
import com.CentrumGuy.CodWarfare.Utilities.Rank;
import com.CentrumGuy.CodWarfare.Utilities.SendCoolMessages;
import com.CentrumGuy.CodWarfare.Utilities.SendUpdateInfo;
import com.CentrumGuy.CodWarfare.Utilities.damageUtils;
import com.CentrumGuy.CodWarfare.Utilities.feedTask;
import com.CentrumGuy.CodWarfare.Utilities.healTask;
import com.CentrumGuy.CodWarfare.Utilities.startingMatch;
import com.comphenix.protocol.wrappers.EnumWrappers.ClientCommand;

@SuppressWarnings("deprecation")
public class Listeners implements Listener {
	
	public static HashMap<Player, String> MessageFromBefore = new HashMap<Player, String>();
	public static HashMap<Player, Player> lastDamager = new HashMap<Player, Player>();
	public static ArrayList<Player> DontKill = new ArrayList<Player>();
	
	private static String getFileVersion(String version) {
		return StringUtils.remove(version, ThisPlugin.getPlugin().getName() + " v");
	}

	@EventHandler
	public void onJoin(final PlayerJoinEvent e) {
		final Player p = e.getPlayer();

		/*BukkitRunnable br = new BukkitRunnable() {
			public void run() {
				if (!Bukkit.getOnlinePlayers().isEmpty()) {
					Location loc = new Location(Bukkit.getServer().getWorld("NewSpawn"), 80, 72, 71);
					new ParticleText(ThisPlugin.getPlugin()).SpawnPhrase(loc, loc.add(0.5D, 2.0D, 0.5D), "_-_", ParticleEffect.REDSTONE, Direction.EAST, 1, 1.0D, 0);
				}
			}
		};
		
		br.runTaskTimer(ThisPlugin.getPlugin(), 5, 5);*/
		
		CreateArenaCommand.creatingArena.put(p, false);
		
		CreateGunCommand.gunBuilder.put(p, false);
		CreateGunCommand.gunBuilderStep.put(p, 0);
		
		if (p.isOp()) {
			if (Bukkit.getPluginManager().getPlugin("ProtocolLib") == null) {
				p.sendMessage(Main.codSignature + "§cFatal error occured. The plugin ProtocolLib is not installed. Please install the latest 1.8 build"
						+ " of ProtocolLib. There will be issues and errors with the plugin if ProtocolLib is not installed");
			}
		}
	
			if (ThisPlugin.getPlugin().getConfig().getBoolean("Updater") && p.isOp()) {
				if (ThisPlugin.getPlugin().getConfig().getBoolean("Updater")) {
					Updater updater = new Updater(ThisPlugin.getPlugin(), 71948, Main.file, UpdateType.NO_DOWNLOAD, false);
					if (updater.getResult() == UpdateResult.UPDATE_AVAILABLE) {
						p.sendMessage("§b§m================================================");
						p.sendMessage("§c§lCOD-Warfare Update:");
						p.sendMessage("");
						p.sendMessage("§6COD-Warfare §eversion " + getFileVersion(updater.getLatestName()) + " §6is now available for download.");
						p.sendMessage("§6If you would like to update your current version,");
						IChatMessage m = new IChatMessage("§eType, '/cod update' or ", "§4[click here]").addLoreLine("§bClick to update COD-Warfare").addCommand("/cod update");
						m.send(p);
						p.sendMessage("§b§m================================================");
					}
				}
			}
					
			for (Player pp : Bukkit.getOnlinePlayers()) {
				if (Main.dispName.get(pp) != null) p.setPlayerListName(Main.dispName.get(pp));
			}
			
			MainClan.invites.put(p, new ArrayList<String>());
			if (Main.header != null && Main.footer != null) {
				SendCoolMessages.TabHeaderAndFooter(Main.header, Main.footer, p);
			}else{
				SendCoolMessages.TabHeaderAndFooter("§e-§6-§e-§6-§e-§6-§e-§6-§e-§6-§e-§6-§e-", "§b-§3-§b-§3-§b-§3-§b-§3-§b-§3-§b-§3-§b-", p);
			}
			
			if (ThisPlugin.getPlugin().getConfig().getBoolean("ServerBased")) {
				BukkitRunnable br = new BukkitRunnable() {
					public void run() {
						JoinCOD.join(true, p, true);
					}
				};
				
				br.runTaskLater(ThisPlugin.getPlugin(), 3L);
			}
			
			Main.createGameBoard(p);
			Main.createLobbyBoard(p);
			if (Scores.scoresExist(p)) Scores.loadScores(p);
			
			lastDamager.put(p, null);
			
			AGPInventory.loadAGP(p);
			AGSInventory.loadAGS(p);
			
			ItemsAndInventories.setUpPlayer(p);
			ItemsAndInventories.setAvailableGuns(p);
			
			SendUpdateInfo.send(p);
			
			AchievementsAPI.setUpPlayer(p);
			AchievementsAPI.unlockJoinAchievements(p);
			
			if (Exp.ExpNow.get(p) == null) {
				Exp.ExpNow.put(p, 0);
			}
			
			if (Exp.NeededExpNow.get(p) == null) {
				Exp.NeededExpNow.put(p, 210);
			}
			
			if (Exp.NeededExpFromBefore.get(p) == null) {
				Exp.NeededExpFromBefore.put(p, 0);
			}
	}
	
	@EventHandler 
	public void onPickupItem(PlayerPickupItemEvent e) {
		if (e.getItem().hasMetadata("codredflag") || e.getItem().hasMetadata("codblueflag")) {
			e.setCancelled(true);
		}
		
		KillArena.ItemPickupEvent(e);
		
		if (Main.WaitingPlayers.contains(e.getPlayer()) || Main.PlayingPlayers.contains(e.getPlayer())) {
			if (e.getItem().hasMetadata("codredflag") || e.getItem().hasMetadata("codblueflag")) {
				CTFArena.captureFlag(e);
				return;
			}
			
		if (e.getItem().hasMetadata("CODnoPickup")) e.setCancelled(true);
			
			if (!(e.getPlayer().hasPermission("cod.pickupitem"))) {
				e.setCancelled(true);
			}
		}
		
		if (CreateArenaCommand.creatingArena.get(e.getPlayer()) == true) {
			e.setCancelled(true);
		}
		
		if (CreateGunCommand.gunBuilder.get(e.getPlayer()) == true) {
			if (CreateGunCommand.gunBuilderStep.get(e.getPlayer()) >= 7) {
				e.setCancelled(true);
				return;
			}
		}
	}
	@EventHandler 
	public void onDropItem(PlayerDropItemEvent e) {
		if (Main.WaitingPlayers.contains(e.getPlayer()) || Main.PlayingPlayers.contains(e.getPlayer())) {
			if (!(e.getPlayer().hasPermission("cod.dropitem"))) {
				e.setCancelled(true);
			}
		}
		
		if (CreateArenaCommand.creatingArena.get(e.getPlayer()) == true) {
			e.setCancelled(true);
		}
		
		if (CreateGunCommand.gunBuilder.get(e.getPlayer()) == true) {
			if (CreateGunCommand.gunBuilderStep.get(e.getPlayer()) >= 7) {
				e.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler 
	public void onQuit(PlayerQuitEvent e) {
		final Player p = e.getPlayer();
		
		LeaveArena.Leave(p, false, true, true);
		
		WeaponUtils.clearWeapons(p);
		
		saveFiles(p);
		
		CreateGunCommand.endGunCreation(p, true);
		
		if (CreateArenaCommand.creatingArena.get(p) == true) {
			CreateArenaCommand.arenaCreating.put(p, null);
			p.getInventory().clear();
			p.getInventory().setContents(CreateArenaCommand.savedInventory.get(p));
			CreateArenaCommand.creatingArena.put(p, false);
			p.sendMessage(Main.codSignature + "§7You left arena creator mode");
			p.updateInventory();
		}
	}

	private static void saveFiles(final Player p) {
		ShopInventoryPrimary.savePrimaryShop(p);
		ShopInventorySecondary.saveSecondaryShop(p);
		AGPInventory.saveAGP(p);
		AGSInventory.saveAGS(p);
		KitInventory.saveKit(p);
		Scores.saveScores(p);
	}
	
	public static boolean shouldCancelInvClick(Inventory inv, Player p) {
		if (inv.getType().equals(InventoryType.CRAFTING)) {
			return true;
		}else if (inv.equals(ItemsAndInventories.MainInventory)) {
			return true;
		}else if (inv.equals(ItemsAndInventories.ShopMainMenu.get(p))) {
			return true;
		}else if (inv.equals(ItemsAndInventories.ClassSelection.get(p))) {
			return true;
		}else if (inv.equals(ItemsAndInventories.lethals.get(p))) {
			return true;
		}else if (inv.equals(ItemsAndInventories.tacticals.get(p))) {
			return true;
		}else if (inv.equals(ItemsAndInventories.perks.get(p))) {
			return true;
		}else if (inv.equals(ShopInventoryPrimary.Pshop.get(p))) {
			return true;
		}else if (inv.equals(ShopInventorySecondary.Sshop.get(p))) {
			return true;
		}else if (inv.equals(AGPInventory.availablePrimaryGuns.get(p))) {
			return true;
		}else if (inv.equals(AGSInventory.availableSecondaryGuns.get(p))) {
			return true;
		}else if (inv.equals(Guns.tryGunsInventory)) {
			return true;
		}else if (inv.equals(Guns.tryPrimary)) {
			return true;
		}else if (inv.equals(Guns.trySecondary)) {
			return true;
		}else if (inv.equals(AchievementsAPI.achievementsInv.get(p))) {
			return true;
		}
		
		return false;
	}

	@EventHandler 
	public void onInventoryClick(InventoryClickEvent e) {
	if (!(e.getWhoClicked() instanceof Player)) return;
		Player p = (Player) e.getWhoClicked();
		
		if (Main.PlayingPlayers.contains(p) || Main.WaitingPlayers.contains(p)) {
			if (shouldCancelInvClick(e.getInventory(), p)) {
				e.setCancelled(true);
			}
		}
		
		if (e.getCurrentItem() == null) return;
		if (e.getCurrentItem().getType() == Material.AIR) return;
		
		if (e.getCurrentItem() != null && (e.getCurrentItem().equals(CreateArenaCommand.redSpawnTool) || e.getCurrentItem().equals(CreateArenaCommand.blueSpawnTool) || e.getCurrentItem().equals(CreateArenaCommand.redFlagTool) || e.getCurrentItem().equals(CreateArenaCommand.blueFlagTool)
				|| e.getCurrentItem().equals(CreateArenaCommand.oneinSpecTool) || e.getCurrentItem().equals(CreateArenaCommand.ffaSpawnTool) || e.getCurrentItem().equals(CreateArenaCommand.enabledTool))) {
			
			e.setCancelled(true);;
			return;
		}
		
		if (CreateGunCommand.gunBuilder.get(p) == true) {
			CreateGunCommand.onInventoryClick(e);
			return;
		}
	if (Main.WaitingPlayers.contains(p) || Main.PlayingPlayers.contains(p)) {
		if (e.getInventory().equals(ShopInventoryPrimary.getPrimaryShop(p)) || e.getInventory().equals(ShopInventorySecondary.getSecondaryShop(p))) EditLoadout.buyGun(e);
		if (e.getInventory().equals(AGPInventory.getAGP(p)) || e.getInventory().equals(AGSInventory.getAGS(p))) EditLoadout.selectGun(e);
		if (e.getInventory().equals(ItemsAndInventories.tacticals.get(p)) || e.getInventory().equals(ItemsAndInventories.lethals.get(p))) EditLoadout.buyWeapon(e);
		if (e.getInventory().equals(Guns.tryGunsInventory) || e.getInventory().equals(Guns.tryPrimary) || e.getInventory().equals(Guns.trySecondary)) {
			e.setCancelled(true);
			Guns.getTryGun(e);
			return;
		}
		
		if (e.getInventory().equals(ItemsAndInventories.ClassSelection.get(p))) {
			e.setCancelled(true);
			
			if (e.getSlot() == 35) {
				e.setCancelled(true);
				p.openInventory(ItemsAndInventories.perks.get(p));
			}
		}
		
		if (e.getCurrentItem() == null) {
			e.setCancelled(true);
			return;
		}else if (e.getCurrentItem().equals(ItemsAndInventories.none)) {
			if (e.getSlot() == 31) {
				e.setCancelled(true);
				p.openInventory(ItemsAndInventories.lethals.get(p));
			}else if (e.getSlot() == 33) {
				e.setCancelled(true);
				p.openInventory(ItemsAndInventories.tacticals.get(p));
			}
		}else if (e.getInventory().equals(ItemsAndInventories.perks.get(p))) {
			if (e.getCurrentItem().equals(PerkAPI.noPerk)) {
				PerkAPI.setPerk(p, Perk.NO_PERK);
				p.sendMessage(Main.codSignature + "§cSet your perk to nothing");
			}else if (e.getCurrentItem().equals(PerkAPI.Speed)) {
				if (Main.LobbyCreditsScore.get(p.getName()).getScore() >= PerkAPI.getCost(Perk.SPEED)) {
					PerkAPI.unlockPerk(p, Perk.SPEED);
					Main.LobbyCreditsScore.get(p.getName()).setScore(Main.LobbyCreditsScore.get(p.getName()).getScore() - PerkAPI.getCost(Perk.SPEED));
				}else{
					p.sendMessage(Main.codSignature + "§cYou do not have credits to buy this perk");
				}
			}else if (e.getCurrentItem().equals(PerkAPI.Marathon)) {
				if (Main.LobbyCreditsScore.get(p.getName()).getScore() >= PerkAPI.getCost(Perk.MARATHON)) {
					PerkAPI.unlockPerk(p, Perk.MARATHON);
					Main.LobbyCreditsScore.get(p.getName()).setScore(Main.LobbyCreditsScore.get(p.getName()).getScore() - PerkAPI.getCost(Perk.MARATHON));
				}else{
					p.sendMessage(Main.codSignature + "§cYou do not have credits to buy this perk");
				}
			}else if (e.getCurrentItem().equals(PerkAPI.Scavenger)) {
				if (Main.LobbyCreditsScore.get(p.getName()).getScore() >= PerkAPI.getCost(Perk.SCAVENGER)) {
					PerkAPI.unlockPerk(p, Perk.SCAVENGER);
					Main.LobbyCreditsScore.get(p.getName()).setScore(Main.LobbyCreditsScore.get(p.getName()).getScore() - PerkAPI.getCost(Perk.SCAVENGER));
				}else{
					p.sendMessage(Main.codSignature + "§cYou do not have credits to buy this perk");
				}
			}else if (e.getCurrentItem().equals(PerkAPI.Hardline)) {
				if (Main.LobbyCreditsScore.get(p.getName()).getScore() >= PerkAPI.getCost(Perk.HARDLINE)) {
					PerkAPI.unlockPerk(p, Perk.HARDLINE);
					Main.LobbyCreditsScore.get(p.getName()).setScore(Main.LobbyCreditsScore.get(p.getName()).getScore() - PerkAPI.getCost(Perk.HARDLINE));
				}else{
					p.sendMessage(Main.codSignature + "§cYou do not have credits to buy this perk");
				}
			}else if (e.getCurrentItem().equals(PerkAPI.ownsSpeed)) {
				PerkAPI.setPerk(p, Perk.SPEED);
				p.sendMessage(Main.codSignature + "§aSet perk to §2Speed");
			}else if (e.getCurrentItem().equals(PerkAPI.ownsMarathon)) {
				PerkAPI.setPerk(p, Perk.MARATHON);
				p.sendMessage(Main.codSignature + "§aSet perk to §2Marathon");
			}else if (e.getCurrentItem().equals(PerkAPI.ownsScavenger)) {
				PerkAPI.setPerk(p, Perk.SCAVENGER);
				p.sendMessage(Main.codSignature + "§aSet perk to §2Scavenger");
			}else if (e.getCurrentItem().equals(PerkAPI.ownsHardline)) {
				PerkAPI.setPerk(p, Perk.HARDLINE);
				p.sendMessage(Main.codSignature + "§aSet perk to §2Hardline");
			}else if (e.getCurrentItem().equals(ItemsAndInventories.backAG)) {
				e.setCancelled(true);
				p.closeInventory();
				p.openInventory(ItemsAndInventories.ClassSelection.get(p));
			}
			e.setCancelled(true);
		}else if (e.getCurrentItem().equals(ItemsAndInventories.selectionItem)) {
			e.setCancelled(true);
			p.openInventory(ItemsAndInventories.ClassSelection.get(p));
			return;
		}else if (e.getCurrentItem().equals(ItemsAndInventories.shopItem)) {
			e.setCancelled(true);
			p.openInventory(ItemsAndInventories.ShopMainMenu.get(p));
			return;
		}else if (e.getCurrentItem().equals(ItemsAndInventories.buyPGuns)) {
			e.setCancelled(true);
			p.openInventory(ShopInventoryPrimary.getPrimaryShop((Player) p));
			return;
		}else if (e.getCurrentItem().equals(ItemsAndInventories.buySGuns)) {
			e.setCancelled(true);
			p.openInventory(ShopInventorySecondary.getSecondaryShop((Player) p));
			return;
		}else if (e.getCurrentItem().equals(ItemsAndInventories.exit)) {
			e.setCancelled(true);
			p.closeInventory();
			Bukkit.getPlayer(p.getName()).sendMessage(Main.codSignature + "§dYou closed the menu");
			return;
		}else if (e.getCurrentItem().equals(ItemsAndInventories.PGun.get(p))) {
			e.setCancelled(true);
			p.openInventory(AGPInventory.getAGP((Player) p));
			return;
		}else if (e.getCurrentItem().equals(ItemsAndInventories.SGun.get(p))) {
			e.setCancelled(true);
			p.openInventory(AGSInventory.getAGS((Player) p));
			return;
		}else if (e.getCurrentItem().equals(ItemsAndInventories.backArrow)) {
			e.setCancelled(true);
			p.openInventory(ItemsAndInventories.MainInventory);
			return;
		}else if (e.getCurrentItem().equals(Main.shoptool)){
				if (!p.getGameMode().equals(GameMode.CREATIVE)) {
					e.setCancelled(true);
						p.closeInventory();
						p.openInventory(ItemsAndInventories.MainInventory);
						return;
					}
				return;
		}else if (p.getGameMode().equals(GameMode.CREATIVE)) {
			return;
		}else{
			if (e.getInventory().getType().equals(InventoryType.CRAFTING)) {
				e.setCancelled(true);
				return;
			}else if (e.getInventory().equals(ItemsAndInventories.MainInventory)) {
				e.setCancelled(true);
				return;
			}else if (e.getInventory().equals(ItemsAndInventories.ShopMainMenu.get(p))) {
				e.setCancelled(true);
				return;
			}else if (e.getInventory().equals(ItemsAndInventories.ClassSelection.get(p))) {
				e.setCancelled(true);
				return;
			}else if (e.getInventory().equals(ItemsAndInventories.lethals.get(p))) {
				e.setCancelled(true);
				return;
			}else if (e.getInventory().equals(ItemsAndInventories.tacticals.get(p))) {
				e.setCancelled(true);
				return;
			}else if (e.getInventory().equals(ItemsAndInventories.perks.get(p))) {
				e.setCancelled(true);
				return;
			}else if (e.getInventory().equals(ShopInventoryPrimary.Pshop.get(p))) {
				e.setCancelled(true);
				return;
			}else if (e.getInventory().equals(ShopInventorySecondary.Sshop.get(p))) {
				e.setCancelled(true);
				return;
			}else if (e.getInventory().equals(AGPInventory.availablePrimaryGuns.get(p))) {
				e.setCancelled(true);
				return;
			}else if (e.getInventory().equals(AGSInventory.availableSecondaryGuns.get(p))) {
				e.setCancelled(true);
				return;
			}else if (e.getInventory().equals(Guns.tryGunsInventory)) {
				e.setCancelled(true);
				return;
			}else if (e.getInventory().equals(Guns.tryPrimary)) {
				e.setCancelled(true);
				return;
			}else if (e.getInventory().equals(Guns.trySecondary)) {
				e.setCancelled(true);
				return;
			}else if (e.getInventory().equals(AchievementsAPI.achievementsInv.get(p))) {
				e.setCancelled(true);
				return;
			}
		}
	}
	return;
}

	@EventHandler 
	public void onBreakBlock(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (Main.WaitingPlayers.contains(e.getPlayer()) || Main.PlayingPlayers.contains(e.getPlayer())) {
			if (!p.hasPermission("cod.terrainedit")) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler 
	public void onPlaceBlock(BlockPlaceEvent e) {
	Player p = e.getPlayer();
	if (Main.WaitingPlayers.contains(e.getPlayer()) || Main.PlayingPlayers.contains(e.getPlayer())) {
		if (e.getItemInHand().getItemMeta().equals(C4.getC4Block().getItemMeta()) && (Main.weapons)) return;
		if (!p.hasPermission("cod.terrainedit")) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler 
	public void onEntityDeath(EntityDeathEvent e) {
	    if (e.getEntity() instanceof Player) {
	    	if (Main.PlayingPlayers.contains(e.getEntity()) || Main.WaitingPlayers.contains(e.getEntity())) {
	    		e.getDrops().clear();
	    		Player p = (Player) e.getEntity();
	    		
	    		p.getInventory().clear();
	    	}
	    	
		    if (e.getEntity().getKiller() instanceof Player) {
		    	if (!(e.getEntity().equals(e.getEntity().getKiller()))) {
			        Player Victim = (Player) e.getEntity();
			        Player Killer = (Player) e.getEntity().getKiller();
			        if (Main.PlayingPlayers.contains(Victim)) {
			        	Victim.setTotalExperience(0);
			        }
					if (Main.PlayingPlayers.contains(Victim)) {
						if (Main.PlayingPlayers.contains(Killer)) {
							Score KillerLobbyKills = Main.LobbyKillsScore.get(Killer.getName());
							Score KillerGameCredits = Main.GameCreditsScore.get(Killer.getName());
							Score KillerLobbyCredits = Main.LobbyCreditsScore.get(Killer.getName());
			        
							KillerLobbyKills.setScore(KillerLobbyKills.getScore() + 1);
							KillerGameCredits.setScore(KillerGameCredits.getScore() + 1);
							KillerLobbyCredits.setScore(KillerLobbyCredits.getScore() + 1);
			        
							//=====================================================================
							
							Score KillerGameKills = Main.GameKillsScore.get(Killer.getName());
			        	
							KillerGameKills.setScore(KillerGameKills.getScore() + 1);
							
							//=====================================================================
							
							Score KillerGameKillStreak = Main.GameKillStreakScore.get(Killer.getName());
				        	
							KillerGameKillStreak.setScore(KillerGameKillStreak.getScore() + 1);
			        
							Exp.addExp(Killer, 30);
							
							Score GameLevelScore = Main.GameLevelScore.get(Killer.getName());
							GameLevelScore.setScore(Level.getLevel(Killer));
							
							Score LobbyLevelScore = Main.LobbyLevelScore.get(Killer.getName());
							LobbyLevelScore.setScore(Level.getLevel(Killer));
							
							if (Main.dispName.get(Killer) != null) Killer.setPlayerListName(Main.dispName.get(Killer));
							if (Main.dispName.get(Victim) != null) Victim.setPlayerListName(Main.dispName.get(Victim));
							
							Killer.sendMessage(Main.codSignature + "§3You killed " + Victim.getPlayerListName() + " §3«═» §b+30 §3XP  §b+1 §3Credits");
							Victim.sendMessage(Main.codSignature + Killer.getPlayerListName() + " §3killed you");
							SendCoolMessages.sendTitle(Victim, "§6", 20, 40, 20);
							SendCoolMessages.sendSubTitle(Victim, "§bKilled by§e " + Killer.getPlayerListName(), 20, 40, 20);
							if (BaseArena.type != BaseArena.ArenaType.GUN) {
								SendCoolMessages.sendOverActionBar(Killer, "§eYou killed§d " + Victim.getPlayerListName());
							}
			        
							if (BaseArena.type == BaseArena.ArenaType.ONEIN) {
								ONEINArena.onKill(Killer);
							}
							
							if (BaseArena.type == BaseArena.ArenaType.FFA) {
								FFAArena.onKill(Killer);
							}
							
							if (BaseArena.type == BaseArena.ArenaType.GUN) {
								GUNArena.onKill(Killer);
							}
							
							if (BaseArena.type == BaseArena.ArenaType.TDM) {
								TDMArena.changeScore(Victim);
							}
							
							Scores.saveScores(Killer);
							
							ElectroMagneticPulse.onEntityKill(e);
							AirStrike.onEntityKill(e);
							MoreAmmo.onEntityKill(e);
							Dogs.onEntitiyKill(e);
							Nuke.onEntityKill(e);
							
							PerkAPI.onPlayerKillPlayer(Killer);
							
							AchievementsAPI.unlockMyFirstKill(Killer);
							AchievementsAPI.unlockNoob(Victim);
							AchievementsAPI.unlockPro(Killer);
							AchievementsAPI.unlockMaster(Killer);
							
							if (startingMatch.firstBlood == null) {
								Player firstBlood = Killer;
								startingMatch.firstBlood = Killer;
								
								firstBlood.sendMessage(Main.codSignature + "§cYou got §4first blood");
								AchievementsAPI.unlockFirstBlood(Killer);
							}
							
								}
							}
		    			}
		    		}
		    	}
		    }
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			if (e.getDamager() instanceof Player) {
				final Player damager = (Player) e.getDamager();
				Player damaged = (Player) e.getEntity();
				
				if (Main.WaitingPlayers.contains(damaged) || Main.invincible.contains(damaged)) {
					e.setCancelled(true);
					return;
				}
				
				if (Main.PlayingPlayers.contains(damager)) {
					if (Main.PlayingPlayers.contains(damaged)) {
						
						if (damager.equals(damaged)) {
							e.setCancelled(true);
							return;
						}
						
						if (BaseArena.type == BaseArena.ArenaType.TDM) {
							if (TDMArena.RedTeam.contains(damager) && TDMArena.RedTeam.contains(damaged)) {
								e.setCancelled(true);
								return;
							}
							if (TDMArena.BlueTeam.contains(damager) && TDMArena.BlueTeam.contains(damaged)) {
								e.setCancelled(true);
								return;
							}
						}else if (BaseArena.type == BaseArena.ArenaType.KC) {
							if (KillArena.RedTeam.contains(damager) && KillArena.RedTeam.contains(damaged)) {
								e.setCancelled(true);
								return;
							}
							if (KillArena.BlueTeam.contains(damager) && KillArena.BlueTeam.contains(damaged)) {
								e.setCancelled(true);
								return;
							}
						}else if (BaseArena.type == BaseArena.ArenaType.CTF) {
							if (CTFArena.RedTeam.contains(damager) && CTFArena.RedTeam.contains(damaged)) {
								e.setCancelled(true);
								return;
							}
							if (CTFArena.BlueTeam.contains(damager) && CTFArena.BlueTeam.contains(damaged)) {
								e.setCancelled(true);
								return;
							}
						}else if (BaseArena.type == BaseArena.ArenaType.INFECT) {
							if (INFECTArena.Zombies.contains(damager) && INFECTArena.Zombies.contains(damaged)) {
								e.setCancelled(true);
								return;
							}
							if (INFECTArena.Blue.contains(damager) && INFECTArena.Blue.contains(damaged)) {
								e.setCancelled(true);
								return;
							}
							if (INFECTArena.Zombies.contains(damager)) {
								e.setDamage(99999);
								
								if (INFECTArena.firstZombie.equals(damager)) {
									damager.getInventory().setHelmet(new ItemStack(Material.SKULL_ITEM, 1));
								}else{
									damager.getInventory().setHelmet(new ItemStack(Material.SKULL_ITEM, 1, (byte) 2));
								}
								
								damager.updateInventory();
								return;
							}
						}else if (BaseArena.type == BaseArena.ArenaType.ONEIN) {
							if (ONEINArena.specContains(damager)) {
								e.setCancelled(true);
								return;
							}
							if (ONEINArena.specContains(damaged)) {
								e.setCancelled(true);
								return;
							}
							
							e.setDamage(99999);
							return;
						}
						
						if (damager.getItemInHand().getType() == Material.IRON_SWORD) {
							damager.getItemInHand().setDurability((short) -1);
						}else if (damager.getItemInHand().getType().equals(Material.AIR)) {
							e.setDamage(7);
						}
						
						if (!(e.isCancelled())) {
							lastDamager.put(damaged, damager);
						}
					}
				}else if (Main.WaitingPlayers.contains(damager)) {
						e.setCancelled(true);
				}else{
					return;
				}
			}else if (e.getDamager() instanceof Projectile) {
					Projectile p = (Projectile) e.getDamager();
					if (!(p.getShooter() instanceof Player)) return;
					Player damager = (Player) p.getShooter();
					Player damaged = (Player) e.getEntity();
					
					if (damager.equals(damaged)) {
						e.setCancelled(true);
						return;
					}
					
					if (Main.PlayingPlayers.contains(damager)) {
						if (Main.PlayingPlayers.contains(damaged)) {
							if (BaseArena.type == BaseArena.ArenaType.TDM) {
								if (TDMArena.RedTeam.contains(damager) && TDMArena.RedTeam.contains(damaged)) {
									e.setCancelled(true);
									return;
								}
								if (TDMArena.BlueTeam.contains(damager) && TDMArena.BlueTeam.contains(damaged)) {
									e.setCancelled(true);
									return;
								}
							}else if (BaseArena.type == BaseArena.ArenaType.KC) {
								if (KillArena.RedTeam.contains(damager) && KillArena.RedTeam.contains(damaged)) {
									e.setCancelled(true);
									return;
								}
								if (KillArena.BlueTeam.contains(damager) && KillArena.BlueTeam.contains(damaged)) {
									e.setCancelled(true);
									return;
								}
							}else if (BaseArena.type == BaseArena.ArenaType.CTF) {
								if (CTFArena.RedTeam.contains(damager) && CTFArena.RedTeam.contains(damaged)) {
									e.setCancelled(true);
									return;
								}
								if (CTFArena.BlueTeam.contains(damager) && CTFArena.BlueTeam.contains(damaged)) {
									e.setCancelled(true);
									return;
								}
							}else if (BaseArena.type == BaseArena.ArenaType.INFECT) {
								if (INFECTArena.Zombies.contains(damager) && INFECTArena.Zombies.contains(damaged)) {
									e.setCancelled(true);
									return;
								}
								if (INFECTArena.Blue.contains(damager) && INFECTArena.Blue.contains(damaged)) {
									e.setCancelled(true);
									return;
								}
								if (INFECTArena.Zombies.contains(damager)) {
									e.setDamage(99999);
									return;
								}
							}else if (BaseArena.type == BaseArena.ArenaType.ONEIN) {
								if (ONEINArena.specContains(damager)) {
									e.setCancelled(true);
									return;
								}
								if (ONEINArena.specContains(damaged)) {
									e.setCancelled(true);
									return;
								}
								
								e.setDamage(99999);
								return;
							}
							
							if (!(e.isCancelled())) {
								lastDamager.put(damaged, damager);
							}
						}
					}
			}else if (e.getDamager() instanceof Wolf) {
				Wolf w = (Wolf) e.getDamager();
				if (w.hasMetadata("codAllowHit")) {
					if ((w.getOwner() instanceof Player) && (e.getEntity() instanceof Player)) {
						Player p = (Player) w.getOwner();
						Player damaged = (Player) e.getEntity();
						if (Main.PlayingPlayers.contains(p) && Main.PlayingPlayers.contains(damaged)) {
							e.setCancelled(true);
							damageUtils.damage(p, damaged, 15);
						}
					}
				}
			}
		}else{
			if (e.getDamager() instanceof Player) {
				Player p = (Player) e.getDamager();
				if (Main.WaitingPlayers.contains(p) || Main.PlayingPlayers.contains(p)) {
					if (!(e.getEntity().hasMetadata("codAllowHit"))) e.setCancelled(true);
				}
			}else if (e.getDamager() instanceof Projectile) {
				Projectile projectile = (Projectile) e.getDamager();
				Player p = (Player) projectile.getShooter();
				if (Main.WaitingPlayers.contains(p) || Main.PlayingPlayers.contains(p)) {
					if (!(e.getEntity().hasMetadata("codAllowHit"))) e.setCancelled(true);
				}
			}
		}
	}
	
	public static boolean shouldDamage(Player p1, Player p2) {
		Boolean shouldDamage = true;
		if (p1 == null || p2 == null) {
			shouldDamage = false;
		}else if (BaseArena.type == BaseArena.ArenaType.TDM) {
			if (TDMArena.RedTeam.contains(p1) && TDMArena.RedTeam.contains(p2)) {
				shouldDamage = false;
			}
			if (TDMArena.BlueTeam.contains(p1) && TDMArena.BlueTeam.contains(p2)) {
				shouldDamage = false;
			}
		}else if (BaseArena.type == BaseArena.ArenaType.KC) {
			if (KillArena.RedTeam.contains(p1) && KillArena.RedTeam.contains(p2)) {
				shouldDamage = false;
			}
			if (KillArena.BlueTeam.contains(p1) && KillArena.BlueTeam.contains(p2)) {
				shouldDamage = false;
			}
		}else if (BaseArena.type == BaseArena.ArenaType.CTF) {
			if (CTFArena.RedTeam.contains(p1) && CTFArena.RedTeam.contains(p2)) {
				shouldDamage = false;
			}
			if (CTFArena.BlueTeam.contains(p1) && CTFArena.BlueTeam.contains(p2)) {
				shouldDamage = false;
			}
		}else if (BaseArena.type == BaseArena.ArenaType.INFECT) {
			if (INFECTArena.Zombies.contains(p1) && INFECTArena.Zombies.contains(p2)) {
				shouldDamage = false;
			}
			if (INFECTArena.Blue.contains(p1) && INFECTArena.Blue.contains(p2)) {
				shouldDamage = false;
			}
		}else if (BaseArena.type == BaseArena.ArenaType.ONEIN) {
			if (ONEINArena.specContains(p1)) {
				shouldDamage = false;
			}
			if (ONEINArena.specContains(p2)) {
				shouldDamage = false;
			}
		}else if (p1.equals(p2)) {
			shouldDamage = false;
		}else if ((!(Main.PlayingPlayers.contains(p1))) || (!(Main.PlayingPlayers.contains(p2)))) {
			shouldDamage = false;
		}else if (Bukkit.getPlayer(p1.getName()) == null || Bukkit.getPlayer(p2.getName()) == null) {
			shouldDamage = false;
		}else if (p1.isDead() || p2.isDead()) {
			shouldDamage = false;
		}
		
		return shouldDamage;
	}
	
	@EventHandler 
	public void onPlayerdeath(PlayerDeathEvent e) {
		Player p = (Player) e.getEntity();
		if (DontKill.contains(p)) return;
		if (healTask.task.get(p) != null) healTask.task.get(p).cancel();
		if (healTask.task.get(p) != null) healTask.task.put(p, null);
		if (Main.PlayingPlayers.contains(p)) {
		e.setDeathMessage(null);
		
        Score PlayerLobbyDeaths = Main.LobbyDeathsScore.get(p.getName());
        
        PlayerLobbyDeaths.setScore(PlayerLobbyDeaths.getScore() + 1);
        
        //============================================================
        
        Score PlayerGameDeaths = Main.GameDeathsScore.get(p.getName());
        
        PlayerGameDeaths.setScore(PlayerGameDeaths.getScore() + 1);
        
        //============================================================
        
        Score GameKillStreakScore = Main.GameKillStreakScore.get(p.getName());
        Score highestKS = Main.highestKillstreak.get(p.getName());
        
        if (highestKS.getScore() < GameKillStreakScore.getScore()) {
        	highestKS.setScore(GameKillStreakScore.getScore());
        	p.sendMessage(Main.codSignature + "§b§lNew highest kill streak:§6§l " + GameKillStreakScore.getScore());
        }        
        	GameKillStreakScore.setScore(0);
		}
		
		Scores.saveScores(p);
		
		DontKill.add(p);
		
		if (BaseArena.type == BaseArena.ArenaType.KC) {
			KillArena.dropItem(e);
		}
		
		if (Main.PlayingPlayers.contains(p) && getArena.getType(PickRandomArena.CurrentArena).equals("CTF")) {
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
	}
	
	public static String getPrefix(Player p) {
		if (Main.WaitingPlayers.contains(p)) {
			return "§e";
		}else if (Main.PlayingPlayers.contains(p)) {
			if (getArena.getType(PickRandomArena.CurrentArena).equals("TDM")) {
				if (TDMArena.BlueTeam.contains(p)) return "§9";
				if (TDMArena.RedTeam.contains(p)) return "§c";
				return null;
			}else if (getArena.getType(PickRandomArena.CurrentArena).equals("KC")) {
				if (KillArena.BlueTeam.contains(p)) return "§9";
				if (KillArena.RedTeam.contains(p)) return "§c";
				return null;
			}else if (getArena.getType(PickRandomArena.CurrentArena).equals("CTF")) {
				if (CTFArena.BlueTeam.contains(p)) return "§9";
				if (CTFArena.RedTeam.contains(p)) return "§c";
				return null;
			}else if (getArena.getType(PickRandomArena.CurrentArena).equals("INFECT")) {
				if (INFECTArena.Blue.contains(p)) {
					return "§9";
				}else if (INFECTArena.Zombies.contains(p)) {
					return "§c";
				}else if (p.getPlayerListName().startsWith("§c")) {
					return "§c";
				}else if (p.getPlayerListName().startsWith("§9")) {
					return "§9";
				}else if (INFECTArena.team.get(p) == ZombieTeam.ZOMBIE) {
					return "§c";
				}else if (INFECTArena.team.get(p) == ZombieTeam.BLUE) {
					return "§9";
				}
				
				return null;
			}else{
				return "§d";
			}
		}else{
			return "";
		}
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onSendMessage(PlayerChatEvent e) {		
		if (CreateGunCommand.gunBuilder.get(e.getPlayer())) {
			CreateGunCommand.onChat(e);
			return;
		}
		if (Main.WaitingPlayers.contains(e.getPlayer()) || Main.PlayingPlayers.contains(e.getPlayer())) {			
			if (Main.dispName.get(e.getPlayer()) != null) e.getPlayer().setPlayerListName(Main.dispName.get(e.getPlayer()));
			if (MainClan.belongsToAClan(e.getPlayer())) {
				e.setFormat("§3[§b" + MainClan.getClan(e.getPlayer()) + "§3] " + getPrefix(e.getPlayer()) + e.getPlayer().getName() + " §b»§7 " + e.getMessage());
			}else{
				e.setFormat(Rank.getRankPrefix(Rank.getRank(e.getPlayer())) + getPrefix(e.getPlayer()) + e.getPlayer().getName() + " §b»§7 " + e.getMessage());
			}
			
			String Message = e.getMessage().toLowerCase();
			String normMessage = e.getMessage();
			
			Message = StringUtils.remove(Message, '.');
			Message = StringUtils.remove(Message, ' ');
			Message = StringUtils.remove(Message, '?');
			Message = StringUtils.remove(Message, '!');
			Message = StringUtils.remove(Message, '|');
			Message = StringUtils.remove(Message, '/');
			Message = StringUtils.remove(Message, ':');
			Message = StringUtils.remove(Message, ';');
			Message = StringUtils.remove(Message, '_');
			Message = StringUtils.remove(Message, '-');
			Message = StringUtils.remove(Message, '*');
			Message = StringUtils.remove(Message, '>');
			Message = StringUtils.remove(Message, '<');
			Message = StringUtils.remove(Message, '^');
			Message = StringUtils.remove(Message, '(');
			Message = StringUtils.remove(Message, ')');
			Message = StringUtils.remove(Message, '[');
			Message = StringUtils.remove(Message, ']');
			Message = StringUtils.remove(Message, '{');
			Message = StringUtils.remove(Message, '}');
			Message = Message + " ";
		
		String newMessage = Message;
		
		if (!(newMessage.equalsIgnoreCase(MessageFromBefore.get(e.getPlayer()))) && !(Message.equalsIgnoreCase(MessageFromBefore.get(e.getPlayer())))) {
			MessageFromBefore.put(e.getPlayer(), Message);
		}else{
			if (!(newMessage.contains("bitch") || newMessage.contains("fuck") || newMessage.contains("cunt") || newMessage.contains("dick") || newMessage.contains("pussy"))) {
				if (e.getPlayer().hasPermission("cod.allowedSpam")) return;
				if (Main.spamDetector) {
					e.getPlayer().sendMessage(Main.codSignature + "§c§lPlease don't repeat messages!");
					e.setCancelled(true);
				}
			}
		}
			if (newMessage.contains("bitch") || newMessage.contains("fuck") || newMessage.contains("cunt") || newMessage.contains("dick") || newMessage.contains("pussy")) {
				if (e.getPlayer().hasPermission("cod.allowedSpam")) return;
				if (Main.spamDetector) {
					e.setCancelled(true);
					e.getPlayer().sendMessage(Main.codSignature + "§c§lPlease do not use bad language!");
					Bukkit.getServer().getConsoleSender().sendMessage(Main.codSignature + "§c" + e.getPlayer().getName() + " said " + normMessage);
				}
			}
		}
		
		if (ONEINArena.specContains(e.getPlayer())) {
			e.setMessage("§8" + e.getMessage());
		}
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (e.getCause() == DamageCause.FIRE_TICK || e.getCause() == DamageCause.FIRE) {
				if (Main.WaitingPlayers.contains(p) || Main.invincible.contains(p) || ONEINArena.specContains(p)) {
					e.setCancelled(true);
					p.setFireTicks(-20);
					return;
				}
			}
			
			if (CreateArenaCommand.creatingArena.get(p) == true) {
				e.setCancelled(true);
				return;
			}
			
			if (CreateGunCommand.gunBuilder.get(p) == true) {
				e.setCancelled(true);
				return;
			}
			
			if (Main.WaitingPlayers.contains(p)) {
				e.setCancelled(true);
				if (e.getCause().equals(DamageCause.VOID)) p.teleport(Lobby.getLobby());
				return;
			}
			
			if (Main.invincible.contains(p)) {
				e.setCancelled(true);
				return;
			}
			
			if (getArena.getType(PickRandomArena.CurrentArena) == null) return;
			if (getArena.getType(PickRandomArena.CurrentArena).equalsIgnoreCase("ONEIN")) {
				if (ONEINArena.specContains(p)) {
					e.setCancelled(true);
					if (e.getCause().equals(DamageCause.VOID)) p.teleport(getArena.getSpectatorSpawn(PickRandomArena.CurrentArena));
					return;
				}
			}
			
			if (Main.PlayingPlayers.contains(p) && (BaseArena.type == BaseArena.ArenaType.ONEIN)) {
				if (e.getCause().equals(DamageCause.PROJECTILE)) {
					e.setDamage(200);
					return;
				}
			}
			
			if (!(p.getInventory().getArmorContents() == null)) {
			if (!(p.getInventory().getHelmet() == null)) {
					p.getInventory().getHelmet().setDurability((short) 0);
				}
			if (!(p.getInventory().getChestplate() == null)) {
					p.getInventory().getChestplate().setDurability((short) 0);
				}
			if (!(p.getInventory().getLeggings() == null)) {
					p.getInventory().getLeggings().setDurability((short) 0);
				}
			if (!(p.getInventory().getBoots() == null)) {
					p.getInventory().getBoots().setDurability((short) 0);
				}
			}
			
			if (Main.PlayingPlayers.contains(p)) {
				healTask.startHealTask(p);
			}
			
			if (e.getDamage() >= p.getHealth()) {
				if ((shouldDamage(p, lastDamager.get(p)))) {
					if (!(e.isCancelled())) {
						if ((!(e.getCause() == DamageCause.ENTITY_ATTACK)) && (!(e.getCause() == DamageCause.PROJECTILE))) {
	                        e.setCancelled(true);
			                EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(lastDamager.get(p), p, DamageCause.ENTITY_ATTACK, 20);
	                        Bukkit.getServer().getPluginManager().callEvent(event);
	                        if (event.isCancelled()) return;
			                p.setLastDamageCause((EntityDamageEvent) event);
			                p.setHealth(1);
			                if (Bukkit.getPlayer(lastDamager.get(p).getName()) != null) p.damage(200, lastDamager.get(p));
	                        return;
						}
					}
				}
			}
		}
	}
	
	public final void WatermelonHit(final Location loc) {
  		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
			  @Override
			public void run() {
				  ParticleEffect.BLOCK_CRACK.display(new BlockData(Material.MELON_BLOCK, (byte) 0), (float) 0.5, (float) 0.5, (float) 0.5, (float) 0, 300, loc.add(0.5, 0.5, 0.5));
				  loc.getBlock().setType(Material.MELON_BLOCK);
			}
		}, 60L);
  	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onRespawn(PlayerRespawnEvent e) {
		final Player p = e.getPlayer();
		
		if (DontKill.contains(p)) DontKill.remove(p);
		
		if (Main.PlayingPlayers.contains(p)) {
			if (getArena.getType(PickRandomArena.CurrentArena).equals("TDM")) {
				e.setRespawnLocation(TDMArena.getSpawn(p));
			}else if (getArena.getType(PickRandomArena.CurrentArena).equals("KC")) {
				e.setRespawnLocation(KillArena.getSpawn(p));
			}else if (getArena.getType(PickRandomArena.CurrentArena).equals("CTF")) {
				e.setRespawnLocation(CTFArena.getSpawn(p));
			}else if (getArena.getType(PickRandomArena.CurrentArena).equals("INFECT")) {
				e.setRespawnLocation(INFECTArena.getSpawn(p));
			}else if (getArena.getType(PickRandomArena.CurrentArena).equals("FFA")) {
				e.setRespawnLocation(FFAArena.getSpawn(p));
			}else if (getArena.getType(PickRandomArena.CurrentArena).equals("ONEIN")) {
				e.setRespawnLocation(ONEINArena.getSpawn(p));
			}else if (getArena.getType(PickRandomArena.CurrentArena).equals("GUN")) {
				e.setRespawnLocation(GUNArena.getSpawn(p));
			}
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
				public void run() {
					if ((!(p.isOnline())) && (!(Main.PlayingPlayers.contains(p)))) {
						if (Main.WaitingPlayers.contains(p)) {
							ResetPlayer.reset(p);
							if (Main.PlayingPlayers.contains(p)) Main.PlayingPlayers.remove(p);
						}
						return;
					}
					//if ((!(Main.WaitingPlayers.contains(p))) || (!(Main.PlayingPlayers.contains(p)))) return;
					
					PlaySounds.playStartGameSound(p);
					
					float precent = (((float) Exp.ExpNow.get(p)) / ((float) Exp.NeededExpNow.get(p)));
					p.setExp(precent);
					
			        if (getArena.getType(PickRandomArena.CurrentArena).equalsIgnoreCase("TDM")) {
			        	TDMArena.SpawnPlayer(p);
			        }else if (getArena.getType(PickRandomArena.CurrentArena).equalsIgnoreCase("KC")) {
			        	KillArena.SpawnPlayer(p);
			        }else if (getArena.getType(PickRandomArena.CurrentArena).equalsIgnoreCase("CTF")) {
			        	CTFArena.SpawnPlayer(p);
			        	
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
			        }else if (getArena.getType(PickRandomArena.CurrentArena).equalsIgnoreCase("INFECT")) {
			        	INFECTArena.spawnPlayer(p, PickRandomArena.CurrentArena);
			        }else if (getArena.getType(PickRandomArena.CurrentArena).equalsIgnoreCase("FFA")) {
			        	FFAArena.respawnPlayer(p);
			        }else if (getArena.getType(PickRandomArena.CurrentArena).equalsIgnoreCase("ONEIN")) {
			        	ONEINArena.respawnPlayer(p, PickRandomArena.CurrentArena);
			        }else if (getArena.getType(PickRandomArena.CurrentArena).equalsIgnoreCase("GUN")) {
			        	GUNArena.respawnPlayer(p);
			        }else{
			        	return;
			        }
			        
			        PerkAPI.onRespawn(p);
				}
			}, 10L);
		}else if (Main.WaitingPlayers.contains(p)) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
				public void run() {
					ResetPlayer.reset(p);
					if (Main.PlayingPlayers.contains(p)) Main.PlayingPlayers.remove(p);
					return;
				}
			}, 10L);
		}
	}
  
  	@EventHandler
    public void DisableRespawnScreen(final PlayerDeathEvent e){
	  	final Player p = e.getEntity();
	  	
	  	p.getInventory().clear();
	  	
  		if (Main.PlayingPlayers.contains(e.getEntity())) {
  			e.setDeathMessage(null);
			DeathBloodEffect.displayDeathBlood((Player) e.getEntity());
  		}
  		
	  Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
		public void run() {
		  	  
			  if (Main.WaitingPlayers.contains(p) || Main.PlayingPlayers.contains(p)) {
			        /*PacketPlayInClientCommand in = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN); // Gets the packet class
			        EntityPlayer cPlayer = ((CraftPlayer)p).getHandle(); // Gets the EntityPlayer class
			        cPlayer.playerConnection.a(in); // Handles the rest of it*/
				  
				  	/*try {
				  		Object nmsPlayer = p.getClass().getMethod("getHandle").invoke(p);
				  		Object packet = Class.forName(nmsPlayer.getClass().getPackage().getName() + ".PacketPlayInClientCommand").newInstance();
				  		Class<?> enumClass = Class.forName(nmsPlayer.getClass().getPackage().getName() + ".EnumClientCommand");
					   
				  		for(Object ob : enumClass.getEnumConstants()){
				  			if(ob.toString().equals("PERFORM_RESPAWN")){
				  				packet = packet.getClass().getConstructor(enumClass).newInstance(ob);
				  			}
				  		}
					   
				  		Object con = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
				  		con.getClass().getMethod("a", packet.getClass()).invoke(con, packet);
				  	} catch(Throwable t){
				  		t.printStackTrace();
					}*/
				  
				  	WrapperPlayClientClientCommand packet = new WrapperPlayClientClientCommand();
				  	packet.setCommand(ClientCommand.PERFORM_RESPAWN);
				  	packet.recievePacket(p);
			  }
		  }
	  }, 10L);
  }
  	
  	@EventHandler
  	public void onPlayerSprint(PlayerToggleSprintEvent e) {
  		if (PerkAPI.getPerk(e.getPlayer()) == Perk.MARATHON) return;
  		feedTask.startFeedTask(e);
  	}
  	
  //THE FOLLOWING TEXT IS REFERRING TO THE INTERACT EVENT//	 
  	
	@EventHandler
	public void onPlayerInteract (PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (CreateGunCommand.gunBuilder.get(p) == true) {
			CreateGunCommand.onInteract(e);
			return;
		}
		
		if (e.getItem() != null && e.getItem().equals(ElectroMagneticPulse.EMP)) {
			ElectroMagneticPulse.onInteract(e);
			return;
		}
		
		if (e.getItem() != null && e.getItem().equals(Dogs.Dogs)) {
			Dogs.onInteract(e);
		}
		
		if (e.getItem() != null && e.getItem().equals(Nuke.Nuke)) {
			Nuke.onInteract(e);
		}
		
		if (e.getItem() != null && e.getItem().equals(AirStrike.Airstrike)) {
			AirStrike.onInteract(e);
		}
		
		if (e.getItem() != null && (e.getItem().equals(CreateArenaCommand.redSpawnTool) || e.getItem().equals(CreateArenaCommand.blueSpawnTool) || e.getItem().equals(CreateArenaCommand.redFlagTool) || e.getItem().equals(CreateArenaCommand.blueFlagTool)
				|| e.getItem().equals(CreateArenaCommand.oneinSpecTool) || e.getItem().equals(CreateArenaCommand.ffaSpawnTool) || e.getItem().equals(CreateArenaCommand.enabledTool))) {
			
			CreateArenaCommand.onInteract(e);
			return;
		}
		
		if (p.getItemInHand().getType() == Material.ENDER_PEARL || p.getItemInHand().getType() == Material.SNOW_BALL) {
			if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (Main.WaitingPlayers.contains(p) || Main.PlayingPlayers.contains(p)) {
					e.setCancelled(true);
					p.updateInventory();
				}
			}
		}
		
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null && (e.getClickedBlock().getType() == Material.SIGN_POST || e.getClickedBlock().getType() == Material.WALL_SIGN)) {
			Sign s = (Sign) e.getClickedBlock().getState();
				if (s.getLine(0).equalsIgnoreCase("§b§m═════════") && s.getLine(3).equalsIgnoreCase("§b§m═════════") && s.getLine(1).equalsIgnoreCase("§4§lCOD-Warfare")) {
					if (s.getLine(2).equalsIgnoreCase("§a§lJoin")) {
						e.setCancelled(true);
						JoinCOD.join(true, p, false);
					}else if (s.getLine(2).equalsIgnoreCase("§6§lLeave")) {
						e.setCancelled(true);
						LeaveArena.Leave(p, true, true, true);
					}else if (s.getLine(2).equalsIgnoreCase("§3§lMenu")) {
						e.setCancelled(true);
						if (Main.WaitingPlayers.contains(p)) {
							p.openInventory(ItemsAndInventories.MainInventory);
						}else if (Main.PlayingPlayers.contains(p)) {
							p.sendMessage(Main.codSignature + "§cCannot open Gun Menu while playing COD-Warfare");
						}else{
							p.sendMessage(Main.codSignature + "§cPlease join COD-Warfare first by doing §4/cod join");
					}
				}
			}
				return;
		}else if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR) && p.getItemInHand().equals(Main.tryGuns)) {
			e.setCancelled(true);
			p.openInventory(Guns.tryGunsInventory);
			return;
		}else if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR)) {
			if (e.getItem() == null || e.getItem().getType() == Material.AIR) return;
			if (e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasDisplayName() && e.getItem().getItemMeta().getDisplayName().equals("§c§lAchievements")) {
				e.setCancelled(true);
				p.openInventory(AchievementsAPI.achievementsInv.get(p));
				return;
			}
		}
		
		if (Main.WaitingPlayers.contains(e.getPlayer()) || Main.PlayingPlayers.contains(e.getPlayer())) {
			
			if (ONEINArena.specContains(p)) {
				e.setCancelled(true);
				return;
			}
			
			Material Beacon = Material.BEACON;
			Material EnderChest = Material.ENDER_CHEST;
			Material Chest = Material.CHEST;
			Material Bed = Material.BED_BLOCK;
			Material TrappedChest = Material.TRAPPED_CHEST;
			Material CraftingTable = Material.WORKBENCH;
			Material Furnace = Material.FURNACE;
			Material Anvil = Material.ANVIL;
			Material EnchantmentTable = Material.ENCHANTMENT_TABLE;
			Material EndPortal = Material.ENDER_PORTAL_FRAME;
			Material Frame = Material.ITEM_FRAME;
			Material Painting = Material.PAINTING;
			Material TNT = Material.TNT;
			Material Dispencer = Material.DISPENSER;
			Material Dropper = Material.DROPPER;
			Material Comparator_off = Material.REDSTONE_COMPARATOR_OFF;
			Material Comparator_on = Material.REDSTONE_COMPARATOR_ON;
			Material Repeater_off = Material.DIODE_BLOCK_OFF;
			Material Repeater_on = Material.DIODE_BLOCK_ON;
			Material Hopper = Material.HOPPER;
			Material BrewingStand = Material.BREWING_STAND;
			Material NoteBlock = Material.NOTE_BLOCK;
			Material JukeBox = Material.JUKEBOX;
			Material grass = Material.GRASS;
			Material dirt = Material.DIRT;
			Material fire = Material.FIRE;
			
			if (p.getItemInHand().getType() == Material.WOOD_HOE || p.getItemInHand().getType() == Material.STONE_HOE || p.getItemInHand().getType() == Material.IRON_HOE || p.getItemInHand().getType() == Material.GOLD_HOE || p.getItemInHand().getType() == Material.DIAMOND_HOE) {
				if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					if (e.getClickedBlock().getType() == grass || e.getClickedBlock().getType() == dirt) e.setCancelled(true);
				}
			}
			
			if ((p.getItemInHand().equals(Main.shoptool))) {
				if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				e.setCancelled(true);
				p.openInventory(ItemsAndInventories.MainInventory);
				return;
			}
		}else{
				
				if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
					return;
				}
				
				if (e.getClickedBlock().getType() == Material.MELON_BLOCK) {
					if (e.getClickedBlock().getLocation().getBlockX() == 475 && e.getClickedBlock().getLocation().getBlockY() == 38 && e.getClickedBlock().getLocation().getBlockZ() == 327) {
						if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
					
						byte data = 0;
						e.setCancelled(true);
						ParticleEffect.BLOCK_CRACK.display(new BlockData(Material.REDSTONE_BLOCK, data), (float) 0.5, (float) 0.5, (float) 0.5, (float) 0, 300, e.getClickedBlock().getLocation().add(0.5, 0.5, 0.5));
						//ParticleEffect.displayBlockCrack(e.getClickedBlock().getLocation().add(0.5, 0.5, 0.5), 152, data, 0, 0, 0, 999);
						p.playSound(p.getLocation(), Sound.NOTE_PIANO, 10, 1);
						p.sendMessage(Main.codSignature + "§6You punched the melon. Good job. There is a truck leaving on the road ahead. Don't miss it. It's going to the training base");
						e.getClickedBlock().getLocation().getBlock().setType(Material.AIR);
						WatermelonHit(e.getClickedBlock().getLocation());
						return;
						}
					}
				}else if (!(p.getGameMode().equals(GameMode.CREATIVE))) {
					
					Material ClickedBlock = e.getClickedBlock().getType();
					
					if (ClickedBlock == Beacon || ClickedBlock == EnderChest || ClickedBlock == Chest || 
							ClickedBlock == Bed || ClickedBlock == TrappedChest || ClickedBlock == CraftingTable || 
								ClickedBlock == Furnace || ClickedBlock == Anvil || ClickedBlock == EnchantmentTable || 
									ClickedBlock == EndPortal || ClickedBlock == Frame || ClickedBlock == Painting || 
										ClickedBlock == TNT || ClickedBlock == Dispencer || ClickedBlock == Dropper || 
											ClickedBlock == Comparator_off || ClickedBlock == Comparator_on || ClickedBlock == Repeater_off || 
												ClickedBlock == Repeater_on || ClickedBlock == Hopper || ClickedBlock == BrewingStand ||
													ClickedBlock == NoteBlock || ClickedBlock == JukeBox)  {
						
						e.setCancelled(true);
						return;
				}else if (e.getClickedBlock().getRelative(BlockFace.UP).getType() == fire) {
					if ((e.getBlockFace() == BlockFace.UP) && (e.getAction() == Action.LEFT_CLICK_BLOCK)) {
						if (!(p.hasPermission("cod.terrainedit"))) {
							e.setCancelled(true);
							e.getClickedBlock().getRelative(BlockFace.UP).setType(Material.FIRE);
						}
					}
				}
			}else{
				return;
			}
		}
	}else{
		return;
	}
}
	
	@EventHandler
	public void onItemDespawn(ItemDespawnEvent e) {
		if (e.getEntity().hasMetadata("codnodespawn")) e.setCancelled(true);
		if (e.getEntity().hasMetadata("codredflag")) e.setCancelled(true);
		if (e.getEntity().hasMetadata("codblueflag")) e.setCancelled(true);
		if (e.getEntity().hasMetadata("codRedTag")) e.setCancelled(true);
		if (e.getEntity().hasMetadata("codBlueTag")) e.setCancelled(true);
	}
	
	@EventHandler
	public void itemFrame(HangingBreakByEntityEvent e) {
		if(e.getRemover() instanceof Player) {
			Player p = (Player) e.getRemover();
			if (Main.WaitingPlayers.contains(p) || Main.PlayingPlayers.contains(p)) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		String[] line = e.getLines();
		
        if (line[0].equalsIgnoreCase("[COD]")) {
        	if (StringUtils.containsIgnoreCase(line[1], "Join")) {
				e.setLine(0, "§b§m═════════");
				e.setLine(1, "§4§lCOD-Warfare");
				e.setLine(2, "§a§lJoin");
				e.setLine(3, "§b§m═════════");
        	}else if (StringUtils.containsIgnoreCase(line[1], "Leave")) {
				e.setLine(0, "§b§m═════════");
				e.setLine(1, "§4§lCOD-Warfare");
				e.setLine(2, "§6§lLeave");
				e.setLine(3, "§b§m═════════");
        	}else if (StringUtils.containsIgnoreCase(line[1], "Menu")) {
				e.setLine(0, "§b§m═════════");
				e.setLine(1, "§4§lCOD-Warfare");
				e.setLine(2, "§3§lMenu");
				e.setLine(3, "§b§m═════════");
        	}
        }
	}
	
	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent e) {
		if (Main.PlayingPlayers.contains(e.getPlayer()) || Main.WaitingPlayers.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
		
		if (e.getRightClicked() instanceof Villager) {
			Villager v = (Villager) e.getRightClicked();
			if (v.getCustomName().equals("Try Guns") || v.getCustomName().equals("§b§lTry Guns")) {
				if (Main.WaitingPlayers.contains(e.getPlayer())) {
					Player p = e.getPlayer();
					v.setCustomName("§b§lTry Guns");
					v.setCustomNameVisible(true);
					p.openInventory(Guns.tryGunsInventory);
				}else{
					e.setCancelled(true);
					e.getPlayer().sendMessage(Main.codSignature + "§cYou may only try guns when in COD-Warfare");
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerWalk(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (GameCountdown.countdownPlayers.contains(p)) {
			if ((e.getFrom().getX() != e.getTo().getX()) || (e.getFrom().getY() != e.getTo().getY()) || (e.getFrom().getZ() != e.getTo().getZ())) {
				e.setTo(new Location(e.getFrom().getWorld(), e.getFrom().getX(), e.getFrom().getY(), e.getFrom().getZ(), e.getTo().getYaw(), e.getTo().getPitch()));
			}
		}
		if (Main.WaitingPlayers.contains(e.getPlayer())) {
			if (e.getPlayer().getLocation().getWorld().getName().equals("NewSpawn")) {
				if ((p.getLocation().getX() >= 116 && p.getLocation().getX() <= 124) && (p.getLocation().getZ() >= 60 && p.getLocation().getZ() <= 62) && (p.getLocation().getY() >= 63 && p.getLocation().getY() <= 69)) {
					p.getInventory().setItem(1, null);
					p.getInventory().setItem(2, null);
				}
			}
		}
	}
	
	@EventHandler
	public void onHungerLoss(FoodLevelChangeEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (Main.noHungerLoss.contains(p)) e.setCancelled(true);
			
			if (Main.PlayingPlayers.contains(p)) {
				if (PerkAPI.getPerk(p) == Perk.MARATHON) {
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		if (Main.blockCMDs == false) return;
			if (Main.PlayingPlayers.contains(e.getPlayer()) || Main.WaitingPlayers.contains(e.getPlayer())) {
			
			String cmd1 = "/cod";
			String cmd2 = "/callofduty";
			String cmd3 = "/codwar";
			String cmd4 = "/codwarfare";
			String cmd5 = "/warfare";
			String cmd6 = "/callofdutywarfare";
			String cmd7 = "/callofdutywar";
			
			if ((!(e.getMessage().startsWith(cmd1))) && (!(e.getMessage().startsWith(cmd2))) && (!(e.getMessage().startsWith(cmd3))) && (!(e.getMessage().startsWith(cmd4))) &&
				(!(e.getMessage().startsWith(cmd5))) && (!(e.getMessage().startsWith(cmd6))) && (!(e.getMessage().startsWith(cmd7)))) {
				
					boolean cmdInList = false;
				
					if (!(Main.cmdList.isEmpty())) {
						for(int i = 0 ; i < Main.cmdList.size() ; i++) {
							String s = Main.cmdList.get(i);
							if (!(s.startsWith("/"))) s = "/" + s;
							
							if (e.getMessage().startsWith(s)) {
								cmdInList = true;
								return;
							}
						}
					}
						
					if (cmdInList) return;
					
					Player p = e.getPlayer();
					
					if (p.hasPermission("cod.allowCommands")) return;
					
					e.setCancelled(true);
					p.sendMessage(Main.codSignature + "§cYou may not use this command while playing COD");
			}
		}
	}
	
    @EventHandler
    public void pRespawn(final PlayerRespawnEvent e) {
        final Player p = e.getPlayer();
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
            @Override
            public void run() {
            if(Main.PlayingPlayers.contains(p)) {
            	if (Main.exoJump) {
	                if(p.hasPermission("cod.exojump")) {
	                    p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1200, 2));
	                }else{
	                    if(Main.WaitingPlayers.contains(e.getPlayer())) {
	                        p.removePotionEffect(PotionEffectType.JUMP);
	                    }
	                }
            	}
            }
            }
        });
    }
    
    @EventHandler
	public void onDeath(PlayerDeathEvent e) {
    	if (!(ThisPlugin.getPlugin().getConfig().getBoolean("FireworksOnDeath"))) return;
		Player p = e.getEntity();
		if (Main.PlayingPlayers.contains(p)) {
			Firework f = (Firework) e.getEntity().getLocation().getWorld().spawn(e.getEntity().getLocation(), Firework.class);
			FireworkMeta fm = f.getFireworkMeta();
			
			if (KillArena.RedTeam.contains(p)) {
				fm.addEffect(FireworkEffect.builder().flicker(false)
					.trail(true)
					.with(FireworkEffect.Type.BALL_LARGE)
					.withColor(Color.RED)
					.build());
				fm.setPower(2);
				f.setFireworkMeta(fm);
			}else if (KillArena.BlueTeam.contains(p)) {
				fm.addEffect(FireworkEffect.builder().flicker(false)
					.trail(true)
					.with(FireworkEffect.Type.BALL_LARGE)
					.withColor(Color.BLUE)
					.build());
				fm.setPower(2);
				f.setFireworkMeta(fm);
			}else if (CTFArena.RedTeam.contains(p)) {
				fm.addEffect(FireworkEffect.builder().flicker(false)
					.trail(true)
					.with(FireworkEffect.Type.BALL_LARGE)
					.withColor(Color.RED)
					.build());
				fm.setPower(2);
				f.setFireworkMeta(fm);
			}else if (CTFArena.BlueTeam.contains(p)) {
				fm.addEffect(FireworkEffect.builder().flicker(false)
					.trail(true)
					.with(FireworkEffect.Type.BALL_LARGE)
					.withColor(Color.BLUE)
					.build());
				fm.setPower(2);
				f.setFireworkMeta(fm);
			}else if (INFECTArena.Zombies.contains(p)) {
				fm.addEffect(FireworkEffect.builder().flicker(false)
					.trail(true)
					.with(FireworkEffect.Type.BALL_LARGE)
					.withColor(Color.RED)
					.build());
				fm.setPower(2);
				f.setFireworkMeta(fm);
			}else if (INFECTArena.Blue.contains(p)) {
				fm.addEffect(FireworkEffect.builder().flicker(false)
						.trail(true)
						.with(FireworkEffect.Type.BALL_LARGE)
						.withColor(Color.RED)
						.build());
				fm.setPower(2);
				f.setFireworkMeta(fm);
			}else if (TDMArena.RedTeam.contains(p)) {
				fm.addEffect(FireworkEffect.builder().flicker(false)
					.trail(true)
					.with(FireworkEffect.Type.BALL_LARGE)
					.withColor(Color.RED)
					.build());
				fm.setPower(3);
				f.setFireworkMeta(fm);
			}else if (TDMArena.BlueTeam.contains(p)) {
				fm.addEffect(FireworkEffect.builder().flicker(false)
					.trail(true)
					.with(FireworkEffect.Type.BALL_LARGE)
					.withColor(Color.BLUE)
					.build());
				fm.setPower(2);
				f.setFireworkMeta(fm);
			}
		}
	}
}