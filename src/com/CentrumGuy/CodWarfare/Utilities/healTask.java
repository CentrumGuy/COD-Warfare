package com.CentrumGuy.CodWarfare.Utilities;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;

public class healTask {
	public static HashMap<Player, BukkitRunnable> task = new HashMap<Player, BukkitRunnable>();
	
	public static void startHealTask(final Player p) {
		if (task.get(p) != null) return;
		BukkitRunnable br = new BukkitRunnable() {
			public void run() {
				if (Main.PlayingPlayers.contains(p) && p.getHealth() < 20) {
					if (p.isOnline() && (!(p.isDead()))) {
						if (p.getHealth() > 0 && p.getHealth() < 20) {
							double health = p.getHealth() + 1;
							if ((!(health <= 20)) || (!(health > 0))) {
								task.put(p, null);
								cancel();
							}else{
								p.setHealth(health);
							}
						}else{
							task.put(p, null);
							cancel();
						}
					}else{
						task.put(p, null);
						cancel();
					}
				}else{
					task.put(p, null);
					cancel();
				}
			}
		};
		
		br.runTaskTimer(ThisPlugin.getPlugin(), 20L, 20L);
		task.put(p, br);
	}
}
