package com.CentrumGuy.CodWarfare.Utilities;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;

public class feedTask {
	
	private static HashMap<Player, BukkitRunnable> fillTask = new HashMap<Player, BukkitRunnable>();
	private static HashMap<Player, BukkitRunnable> emptyTask = new HashMap<Player, BukkitRunnable>();

	public static void startFeedTask(PlayerToggleSprintEvent e) {
		if (Main.WaitingPlayers.contains(e.getPlayer()) || Main.PlayingPlayers.contains(e.getPlayer())) {
			final Player p = e.getPlayer();
			if (Main.noHungerLoss.contains(p)) return;
			
			if (e.isSprinting()) {
				if (fillTask.get(p) != null) {
					fillTask.get(p).cancel();
					fillTask.put(p, null);
				}
				
				emptyFoodBar(p);
			}
			
			if (!(e.isSprinting())) {
				if (emptyTask.get(p) != null) {
					emptyTask.get(p).cancel();
					emptyTask.put(p, null);
				}
				
				fillFoodBar(p);
			}
		}
	}
	
	private static void emptyFoodBar(final Player p) {
		BukkitRunnable br = new BukkitRunnable() {
			public void run() {
				if ((Main.WaitingPlayers.contains(p) || Main.PlayingPlayers.contains(p)) && p.isSprinting()) {
					if (Main.noHungerLoss.contains(p)) return;
					if (p.getFoodLevel() >= 0) {
						p.setFoodLevel(p.getFoodLevel() - 1);
					}else{
						cancel();
					}
				}else{
					cancel();
					fillFoodBar(p);
				}
			}
		};
		
		br.runTaskTimer(ThisPlugin.getPlugin(), 20L, 20L);
		
		emptyTask.put(p, br);
	}
	
	private static void fillFoodBar(final Player p) {
		BukkitRunnable br = new BukkitRunnable() {
			public void run() {
				if ((Main.WaitingPlayers.contains(p) || Main.PlayingPlayers.contains(p)) && !p.isSprinting()) {
					if (Main.noHungerLoss.contains(p)) return;
					if (!(p.getFoodLevel() >= 20)) {
						if (p.getFoodLevel() < 20) {
							p.setFoodLevel(p.getFoodLevel() + 2);
						}else{
							cancel();
						}
					}else{
						cancel();
					}
				}else{
					cancel();
				}
			}
		};
		
		br.runTaskTimer(ThisPlugin.getPlugin(), 20L, 20L);
		
		fillTask.put(p, br);
	}
}
