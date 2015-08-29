package com.CentrumGuy.CodWarfare.SpecialWeapons;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import com.CentrumGuy.CodWarfare.Listeners;
import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.OtherLoadout.Perk;
import com.CentrumGuy.CodWarfare.OtherLoadout.PerkAPI;
import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;

public class Dogs {
    public static ItemStack Dogs = new ItemStack(Material.BONE);

    public static void setUp() {
        ItemMeta DogsItemMeta = Dogs.getItemMeta();
        DogsItemMeta.setDisplayName("§c§lDogs");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("§6RELEASE THE HOUNDS!");
        DogsItemMeta.setLore(lore);
        Dogs.setItemMeta(DogsItemMeta);
    }

    @SuppressWarnings("deprecation")
	public static void onEntitiyKill(EntityDeathEvent e) {
        if ((e.getEntity() instanceof Player) && (e.getEntity().getKiller() instanceof Player)) {
            Player k = (Player) e.getEntity().getKiller();
            Player p = (Player) e.getEntity();

            if ((Main.PlayingPlayers.contains(k)) && (Main.PlayingPlayers.contains(p))) {
                if ((Main.GameKillStreakScore.get(k.getName()).getScore() == 15) || ((Main.GameKillStreakScore.get(k.getName()).getScore() == 14) && (PerkAPI.getPerk(k) == Perk.HARDLINE))) {
                    k.getInventory().addItem(Dogs);
                    k.updateInventory();
                    k.sendMessage(Main.codSignature + "§c§lYou got Dogs. Right click to deploy!");
                }
            }
        }
    }

    public static void onInteract(PlayerInteractEvent e) {
        if (e.getItem() == null) return;
        if (!(e.getItem().getType() == Material.AIR)) return;
        if (e.getItem().equals(Dogs)) return;
        if ((!(e.getAction().equals(Action.RIGHT_CLICK_AIR))) && (!(e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) && (!(e.getAction().equals(Action.LEFT_CLICK_AIR))) && (!(e.getAction().equals(Action.LEFT_CLICK_BLOCK))))
            return;
        e.getPlayer().sendMessage(Main.codSignature + "§5You released the hounds");

        for (Player p : Main.PlayingPlayers) {
            p.sendMessage(Main.codSignature + "§4Take Cover!!! " + Listeners.getPrefix(p) + p.getName() + " §4Released the hounds!");

        }
        
        final Player p = e.getPlayer();
        p.setItemInHand(null);
        
        if (!(Main.PlayingPlayers.isEmpty())) {
            if (!(GetPlayersOnOtherTeam.get(p).isEmpty())) {
            	for (int i = 0 ; i < 5 ; i++) {
	            	Player pp = GetPlayersOnOtherTeam.get(p).get(new Random().nextInt(GetPlayersOnOtherTeam.get(p).size()));
	            	
	                Location loc = p.getLocation();
	                final Wolf w = p.getWorld().spawn(loc, Wolf.class);
	                
	                w.setMetadata("codAllowHit", new FixedMetadataValue(ThisPlugin.getPlugin(), w));
	                w.setAngry(true);
	                w.setAdult();
	                w.setOwner(p);
	                w.setCollarColor(DyeColor.BLUE);
	                w.setTarget(pp);
	                
	                BukkitRunnable br = new BukkitRunnable() {
	                	public void run() {
	                		w.remove();
	                	}
	                };
	                
	                br.runTaskLater(ThisPlugin.getPlugin(), 20 * 30);
            	}
            }else{
            	p.sendMessage(Main.codSignature + "§cThere needs to be 1 more player for this killsteak to work!");
            }
        }
    }
}