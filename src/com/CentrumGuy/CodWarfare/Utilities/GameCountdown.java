package com.CentrumGuy.CodWarfare.Utilities;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Note;
import org.bukkit.Note.Tone;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.OtherLoadout.PerkAPI;
import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;
import com.CentrumGuy.CodWarfare.Utilities.SendCoolMessages;

public class GameCountdown {
	
	public static double countdownTime = 10;
	public static ArrayList<Player> countdownPlayers = new ArrayList<Player>();

	public static void startCountdown(final Player p, final Location teleport, final String Message1, final String Message2) {
		if (Main.extraCountdown) {
			for (Player pp : Main.PlayingPlayers) {
				if (pp != p && !(pp.equals(p))) p.hidePlayer(pp);
			}
			
			p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, -100));
			p.setWalkSpeed(0);
			Main.invincible.add(p);
			p.teleport(teleport);
			Main.noHungerLoss.add(p);
			if (!(countdownPlayers.contains(p))) countdownPlayers.add(p);
			p.setGameMode(GameMode.SURVIVAL);
			
			
			BukkitRunnable br = new BukkitRunnable() {
				double timeLeft = countdownTime;
				
					public void run() {
						
						if (Main.PlayingPlayers.contains(p)) {
							DecimalFormat df = new DecimalFormat("#.#");
							
							if (timeLeft >= 2) {
								PlaySounds.playCountdownSound(p, Note.natural(0, Tone.A));
							}else if (timeLeft == 1) {
								PlaySounds.playCountdownSound(p, Note.natural(1, Tone.A));
							}
							
							if (timeLeft > 9) {
								SendCoolMessages.sendOverActionBar(p, "§b§lTime Remaining: §7██████████ §4§l" + df.format(timeLeft) + " Seconds");
						    	if (!(GameVersion.above47(p))) {
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("§b§lTime Remaining: §7██████████ §4§l" + df.format(timeLeft) + " Seconds");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    	}
							}else if (timeLeft <= 9 && timeLeft > 8) {
								SendCoolMessages.sendOverActionBar(p, "§b§lTime Remaining: §2█§7█████████ §4§l" + df.format(timeLeft) + " Seconds");
						    	if (!(GameVersion.above47(p))) {
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("§b§lTime Remaining: §2█§7█████████ §4§l" + df.format(timeLeft) + " Seconds");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    	}
							} else if (timeLeft <= 8 && timeLeft > 7) {
								SendCoolMessages.sendOverActionBar(p, "§b§lTime Remaining: §2██§7████████ §4§l" + df.format(timeLeft) + " Seconds");
						    	if (!(GameVersion.above47(p))) {
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("§b§lTime Remaining: §2██§7████████ §4§l" + df.format(timeLeft) + " Seconds");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    	}
							} else if (timeLeft <= 7 && timeLeft > 6) {
								SendCoolMessages.sendOverActionBar(p, "§b§lTime Remaining: §2██§a█§7███████ §4§l" + df.format(timeLeft) + " Seconds");
						    	if (!(GameVersion.above47(p))) {
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("§b§lTime Remaining: §2██§a█§7███████ §4§l" + df.format(timeLeft) + " Seconds");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    	}
							} else if (timeLeft <= 6 && timeLeft > 5) {
								SendCoolMessages.sendOverActionBar(p, "§b§lTime Remaining: §2██§a██§7██████ §6§l" + df.format(timeLeft) + " Seconds");
						    	if (!(GameVersion.above47(p))) {
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("§b§lTime Remaining: §2██§a██§7██████ §6§l" + df.format(timeLeft) + " Seconds");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    	}
							} else if (timeLeft <= 5 && timeLeft > 4) {
								SendCoolMessages.sendOverActionBar(p, "§b§lTime Remaining: §2██§a██§e█§7█████ §6§l" + df.format(timeLeft) + " Seconds");
						    	if (!(GameVersion.above47(p))) {
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("§b§lTime Remaining: §2██§a██§e█§7█████ §6§l" + df.format(timeLeft) + " Seconds");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    	}
							} else if (timeLeft <= 4 && timeLeft > 3) {
								SendCoolMessages.sendOverActionBar(p, "§b§lTime Remaining: §2██§a██§e██§7████ §6§l" + df.format(timeLeft) + " Seconds");
						    	if (!(GameVersion.above47(p))) {
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("§b§lTime Remaining: §2██§a██§e██§7████ §6§l" + df.format(timeLeft) + " Seconds");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    	}
							} else if (timeLeft <= 3 && timeLeft > 2) {
								SendCoolMessages.sendOverActionBar(p, "§b§lTime Remaining: §2██§a██§e██§c█§7███ §2§l" + df.format(timeLeft) + " Seconds");
						    	if (!(GameVersion.above47(p))) {
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("§b§lTime Remaining: §2██§a██§e██§c█§7███ §2§l" + df.format(timeLeft) + " Seconds");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    	}
							} else if (timeLeft <= 2 && timeLeft > 1) {
								SendCoolMessages.sendOverActionBar(p, "§b§lTime Remaining: §2██§a██§e██§c██§7██ §2§l" + df.format(timeLeft) + " Seconds");
						    	if (!(GameVersion.above47(p))) {
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("§b§lTime Remaining: §2██§a██§e██§c██§7██ §2§l" + df.format(timeLeft) + " Seconds");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    	}
							} else if (timeLeft <= 1 && timeLeft > 0.1) {
								SendCoolMessages.sendOverActionBar(p, "§b§lTime Remaining: §2██§a██§e██§c██§4█§7█ §2§l" + df.format(timeLeft) + " Seconds");
						    	if (!(GameVersion.above47(p))) {
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("§b§lTime Remaining: §2██§a██§e██§c██§4█§7█ §2§l" + df.format(timeLeft) + " Seconds");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    	}
							} else if (timeLeft <= 0.1) {
								SendCoolMessages.sendOverActionBar(p, "§b§lTime Remaining: §2██§a██§e██§c██§4██ §2§l" + df.format(timeLeft) + " Seconds");
						    	if (!(GameVersion.above47(p))) {
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("§b§lTime Remaining: §2██§a██§e██§c██§4██ §2§l" + df.format(timeLeft) + " Seconds");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    		p.sendMessage("");
						    	}
						    	
								cancel();
								
								p.removePotionEffect(PotionEffectType.JUMP);
								p.setWalkSpeed((float) 0.2);
								PlaySounds.playStartGameSound(p);
								
					    		if (Message2 != null) p.sendMessage(Message2);
					    		
					    		String msg2Changed = StringUtils.remove(Message2, Main.codSignature);
								
								SendCoolMessages.sendTitle(p, Message1, 5, 40, 20);
					    		if (msg2Changed != null) SendCoolMessages.sendSubTitle(p, msg2Changed, 5, 40, 20);
								
					    		if (!(GameVersion.above47(p))) {
					    			p.sendMessage(Message1);
					    		}
								
								Main.invincible.remove(p);
								
								if (Main.noHungerLoss.contains(p)) Main.noHungerLoss.remove(p);
								
								ShowPlayer.showPlayer(p);
								
								p.setSprinting(false);
								
								PerkAPI.onStartOfMatch();
								
								if (Main.PlayingPlayers.contains(p)) {
					                if(p.hasPermission("cod.exoboost")){
					                    p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1200, 2));
					                }else{
					                    if(Main.WaitingPlayers.contains(p)){
					                        p.removePotionEffect(PotionEffectType.JUMP);
					                    }
					                }
					            }
								
								if (countdownPlayers.contains(p)) countdownPlayers.remove(p);
							}
							
							timeLeft = timeLeft - 1;
					}else{
						cancel();
					}
				}
			};
			br.runTaskTimer(ThisPlugin.getPlugin(), 20L, 20L);
		}else{
			if (!(GameVersion.above47(p))) {
				if (Message1 != null) p.sendMessage(Message1);
				if (Message2 != null) p.sendMessage(Message2);
			}else{
				SendCoolMessages.clearTitleAndSubtitle(p);
				SendCoolMessages.resetTitleAndSubtitle(p);
				if (Message1 != null) {
					SendCoolMessages.sendTitle(p, Message1, 15, 30, 15);
				}else{
					SendCoolMessages.sendTitle(p, "§6", 15, 30, 15);
				}
				
				if (Message2 != null) p.sendMessage(Message2);
				PlaySounds.playStartGameSound(p);
			}
			
			PerkAPI.onStartOfMatch();
			if (Main.PlayingPlayers.contains(p)) {
                if(p.hasPermission("cod.exoboost")){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1200, 2));
                }else{
                    if(Main.WaitingPlayers.contains(p)){
                        p.removePotionEffect(PotionEffectType.JUMP);
                    }
                }
            }
		}
	}
}
