package com.CentrumGuy.CodWarfare.SpecialWeapons;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.OtherLoadout.Perk;
import com.CentrumGuy.CodWarfare.OtherLoadout.PerkAPI;
import com.CentrumGuy.CodWarfare.ParticleEffects.ParticleEffect;
import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;
import com.CentrumGuy.CodWarfare.Utilities.Damage;

public class AirStrike {
	public static ItemStack Airstrike = new ItemStack(Material.SUGAR);
	
	public static void setUp() {
		ItemMeta EMPMeta = Airstrike.getItemMeta();
		EMPMeta.setDisplayName("§c§lAirstrike");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§6Use this fast! This will");
		lore.add("§6bomb everyone who is not");
		lore.add("§6under a roof");
		EMPMeta.setLore(lore);
		Airstrike.setItemMeta(EMPMeta);
	}
	
	@SuppressWarnings("deprecation")
	public static void onEntityKill(EntityDeathEvent e) {
		if ((e.getEntity() instanceof Player) && (e.getEntity().getKiller() instanceof Player)) {
			Player killer = (Player) e.getEntity().getKiller();
			Player p = (Player) e.getEntity();
			
			if ((Main.PlayingPlayers.contains(killer)) && (Main.PlayingPlayers.contains(p))) {
				if ((Main.GameKillStreakScore.get(killer.getName()).getScore() == 15) || ((Main.GameKillStreakScore.get(killer.getName()).getScore() == 14) && (PerkAPI.getPerk(killer) == Perk.HARDLINE))) {
					killer.getInventory().addItem(Airstrike);
					killer.updateInventory();
					killer.sendMessage(Main.codSignature + "§c§lYou got an Airstrike. Use it fast by right-clicking it!");
				}
			}
		}
	}
	
	public static void onInteract(PlayerInteractEvent e) {
		if (e.getItem() == null) return;
		if (e.getItem().getType() == Material.AIR) return;
		if (!(e.getItem().equals(Airstrike))) return;
		if ((!(e.getAction().equals(Action.RIGHT_CLICK_AIR))) && (!(e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) && (!(e.getAction().equals(Action.LEFT_CLICK_AIR))) && (!(e.getAction().equals(Action.LEFT_CLICK_BLOCK)))) return;
		
		e.getPlayer().sendMessage(Main.codSignature + "§aYou used your airstrike");
		
		for (Player p : Main.PlayingPlayers) {
			p.sendMessage(Main.codSignature + "§4§l" + e.getPlayer().getName() + " §c§llaunched an airstrike! TAKE COVER!!!");
		}
		
		final Player p = e.getPlayer();
		p.setItemInHand(null);
		
		BukkitRunnable br = new BukkitRunnable() {
			public void run() {
				if (!(Main.PlayingPlayers.isEmpty())) {
					if (!(GetPlayersOnOtherTeam.get(p).isEmpty())) {
						for (Player pp : GetPlayersOnOtherTeam.get(p)) {
							if (!(isUnderRoof(pp))) {
								ParticleEffect.EXPLOSION_LARGE.display((float) 0, (float) 0, (float) 0, 0, 3, pp.getLocation().add(0, 1, 0));
								pp.playSound(pp.getLocation(), Sound.EXPLODE, 20, 0);
								Damage.damage(p, pp, 200);
							}
						}
					}
				}
			}
		};

		br.runTaskLater(ThisPlugin.getPlugin(), 60L);
	}
	
	public static boolean isUnderRoof(Player p) {
		int x = p.getLocation().getBlockX();
		final int y = p.getLocation().getBlockY();
		int z = p.getLocation().getBlockZ();
		World world = p.getLocation().getWorld();
		
		for (int i = y ; i < world.getMaxHeight() ; i++) {
			Block block = world.getBlockAt(x, i, z);
			if ((block.getType().isSolid() && (block.getType()) != Material.BARRIER)) return true;
		}
		
		return false;
	}
}
