package com.CentrumGuy.CodWarfare.Utilities;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.CentrumGuy.CodWarfare.ParticleEffects.ParticleEffect;
import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;

public class DeathBloodEffect {

	public static void displayDeathBlood(Player p) {
		for (int i = 0 ; i < 5 ; i++) {
			ItemStack redDust = new ItemStack(Material.INK_SACK, 1, (byte) 1);
			ItemMeta redDustMeta = redDust.getItemMeta();
			redDustMeta.setDisplayName("" + i);
			redDust.setItemMeta(redDustMeta);
			
			Location loc = p.getEyeLocation();
			loc.setY(loc.getY() - 0.5);
			final Item redstoneItem = p.getWorld().dropItem(loc, redDust);
			double X = Math.random() * 0.40 - 0.20;
			double Z = Math.random() * 0.40 - 0.20;
			Vector v = redstoneItem.getLocation().toVector();
			redstoneItem.setVelocity(v.normalize().setX(X).setZ(Z));
			
			redstoneItem.setMetadata("CODnoPickup", new FixedMetadataValue(ThisPlugin.getPlugin(), redstoneItem));
			
			BukkitRunnable br1 = new BukkitRunnable() {
				public void run() {
					if (!Bukkit.getOnlinePlayers().isEmpty()) ParticleEffect.REDSTONE.display((float) 0.01, (float) 0.01, (float) 0.01, 0, 10, redstoneItem.getLocation());
					redstoneItem.remove();
				}
			};
			
			int time = new Random().nextInt(/*Max*/4 - /*Min*/2 + 1) + 2;
			br1.runTaskLater(ThisPlugin.getPlugin(), 20 * time);
			
			//================================================================================
			
			ItemStack redDye = new ItemStack(Material.INK_SACK, 1, (byte) 1);
			ItemMeta redDyeMeta = redDye.getItemMeta();
			redDyeMeta.setDisplayName("" + i);
			redDust.setItemMeta(redDyeMeta);
			
			final Item redDyeItem = p.getWorld().dropItem(loc, redDye);
			X = Math.random() * 0.40 - 0.20;
			Z = Math.random() * 0.40 - 0.20;
			v = redDyeItem.getLocation().toVector();
			redDyeItem.setVelocity(v.normalize().setX(X).setZ(Z));
			
			redDyeItem.setMetadata("CODnoPickup", new FixedMetadataValue(ThisPlugin.getPlugin(), redDyeItem));
			
			BukkitRunnable br2 = new BukkitRunnable() {
				public void run() {
					if (!Bukkit.getOnlinePlayers().isEmpty()) ParticleEffect.REDSTONE.display((float) 0.01, (float) 0.01, (float) 0.01, 0, 10, redDyeItem.getLocation());
					redDyeItem.remove();
				}
			};
			
			time = new Random().nextInt(/*Max*/4 - /*Min*/2 + 1) + 2;
			br2.runTaskLater(ThisPlugin.getPlugin(), 20 * time);
		}
	}
}
