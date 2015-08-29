package com.CentrumGuy.CodWarfare.SpecialWeapons;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.CentrumGuy.CodWarfare.Listeners;
import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Arena.CTFArena;
import com.CentrumGuy.CodWarfare.Arena.KillArena;
import com.CentrumGuy.CodWarfare.Arena.StopGameCountdown;
import com.CentrumGuy.CodWarfare.Arena.TDMArena;
import com.CentrumGuy.CodWarfare.OtherLoadout.Perk;
import com.CentrumGuy.CodWarfare.OtherLoadout.PerkAPI;
import com.CentrumGuy.CodWarfare.ParticleEffects.ParticleEffect;
import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;
import com.CentrumGuy.CodWarfare.Utilities.Damage;

/**
 * Created by Tate on 15-08-05.
 */

public class Nuke {
    public static ItemStack Nuke = new ItemStack(Material.TNT);

    public static void setUp() {
        ItemMeta NukeItemMeta = Nuke.getItemMeta();
        NukeItemMeta.setDisplayName("§cNuke");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("§b§lYou have a NUKE! This will end the game thus killing all players and will make your team win.");
        NukeItemMeta.setLore(lore);
        Nuke.setItemMeta(NukeItemMeta);
    }
    
    @SuppressWarnings("deprecation")
	public static void onEntityKill(EntityDeathEvent e) {
        if ((e.getEntity() instanceof Player) && (e.getEntity().getKiller() instanceof Player)) {
            Player k = e.getEntity().getKiller();
            Player p = (Player) e.getEntity();

            if ((Main.PlayingPlayers.contains(k)) && (Main.PlayingPlayers.contains(p))) {
                if ((Main.GameKillStreakScore.get(k.getName()).getScore() == 30) || ((Main.GameKillStreakScore.get(k.getName()).getScore() == 29) && (PerkAPI.getPerk(k) == Perk.HARDLINE))) {
                    k.getInventory().addItem(Nuke);
                    k.updateInventory();
                    k.sendMessage(Main.codSignature + "§c§lYou got a Nuke. Right click to launch!");
                }
            }
        }
    }
    
    public static void onInteract(PlayerInteractEvent e) {
        if (e.getItem() == null) return;
        if (e.getItem().getType() == Material.AIR) return;
        if (!(e.getItem().equals(Nuke))) return;
        if ((!(e.getAction().equals(Action.RIGHT_CLICK_AIR))) && (!(e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) && (!(e.getAction().equals(Action.LEFT_CLICK_AIR))) && (!(e.getAction().equals(Action.LEFT_CLICK_BLOCK)))) return;

        e.getPlayer().sendMessage(Main.codSignature + "§a§lYou used your nuke");

        for (Player p : Main.PlayingPlayers) {
            p.sendMessage(Main.codSignature + Listeners.getPrefix(p) + "§l" + e.getPlayer().getName() + " §c§llaunched a NUKE!");
        }

        final Player p = e.getPlayer();
        p.setItemInHand(null);

        BukkitRunnable br = new BukkitRunnable() {
            @Override
			public void run() {
                if (!(Main.PlayingPlayers.isEmpty())) {
                    if (!(GetPlayersOnOtherTeam.get(p).isEmpty())) {
                        for (Player pp : GetPlayersOnOtherTeam.get(p)) {
                            ParticleEffect.EXPLOSION_LARGE.display(0, 0, 0, 0, 3, pp.getLocation().add(0, 1, 0));
                            pp.playSound(pp.getLocation(), Sound.EXPLODE, 1F, 1F);
                            Damage.damage(p, pp, 1000);
                        }
                    }
                }
            }
        };
        
        BukkitRunnable br2 = new BukkitRunnable() {
        	public void run() {
		        if (TDMArena.BlueTeam.contains(p)) {
		            StopGameCountdown.endGame();
		        }else if (TDMArena.RedTeam.contains(p)) {
		            StopGameCountdown.endGame();
		        }else if (CTFArena.BlueTeam.contains(p)) {
		            StopGameCountdown.endGame();
		        }else if (CTFArena.RedTeam.contains(p)) {
		            StopGameCountdown.endGame();
		        }else if (KillArena.BlueTeam.contains(p)) {
		        	StopGameCountdown.endGame();
		        }else if (KillArena.RedTeam.contains(p)) {
		            StopGameCountdown.endGame();
		        }
        	}
        };

        br.runTaskLater(ThisPlugin.getPlugin(), 60L);
        br2.runTaskLater(ThisPlugin.getPlugin(), 100L);
    }
}
