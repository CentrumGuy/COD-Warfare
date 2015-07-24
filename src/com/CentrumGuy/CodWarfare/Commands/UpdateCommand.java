package com.CentrumGuy.CodWarfare.Commands;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Updater;
import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;
import com.CentrumGuy.CodWarfare.Updater.UpdateResult;
import com.CentrumGuy.CodWarfare.Updater.UpdateType;
import com.CentrumGuy.CodWarfare.Utilities.IChatMessage;

public class UpdateCommand {
	
	private static HashMap<Player, Boolean> Update = new HashMap<Player, Boolean>();
	private static HashMap<Player, BukkitRunnable> task = new HashMap<Player, BukkitRunnable>();
	public static boolean Updated = false;
	
	  private static double getFileVersion(String version) {
		  version = StringUtils.remove(version, ThisPlugin.getPlugin().getName() + " v");
		  double d = Double.parseDouble(version);
		  return d;
	  }

	public static void update(final Player p, String[] args) {
		if (p.hasPermission("cod.update")) {
			if (Updated) {
				p.sendMessage(Main.codSignature + "§cThe newest version of COD-Warfare has already been downloaded. Please reload your server");
				return;
			}
			
			Updater up0 = new Updater(ThisPlugin.getPlugin(), 71948, Main.file, UpdateType.NO_DOWNLOAD, false);
			if (ThisPlugin.getPlugin().getConfig().getBoolean("Updater") == false || up0.getResult() == UpdateResult.DISABLED) {
				p.sendMessage(Main.codSignature + "§cThe updater is disabled");
				return;
			}
			
			if (Update.get(p) == null) {
				p.sendMessage(Main.codSignature + "§bChecking for update...");
				Updater up1 = new Updater(ThisPlugin.getPlugin(), 71948, Main.file, UpdateType.NO_DOWNLOAD, false);
				if (up1.getResult() == UpdateResult.UPDATE_AVAILABLE) {
					p.sendMessage("§b§m================================================");
					p.sendMessage("§a§lUpdate Found:");
					p.sendMessage("");
					p.sendMessage("§6COD-Warfare §eversion " + getFileVersion(up1.getLatestName()) + " §6is now available for download.");
					p.sendMessage("§6If you would like to update your current version,");
					IChatMessage m = new IChatMessage("", "§e[Click Here]").addLoreLine("§bClick to download the").addLoreLine("§bnewest version of COD").addCommand("/cod update");
					m.send(p);
					p.sendMessage("§6Or, type §e/cod update");
					p.sendMessage("§6You have 60 seconds to confirm the update");
					p.sendMessage("§b§m================================================");
					Update.put(p, true);
					BukkitRunnable BRtask = new BukkitRunnable() {
						public void run() {
							Update.put(p, false);
						}
					};
					
					BRtask.runTaskLater(ThisPlugin.getPlugin(), 20L * 60L);
					task.put(p, BRtask);
					return;
				}else{
					p.sendMessage(Main.codSignature + "§aCOD-Warfare is up to date");
					return;
				}
			}else{
				Update.put(p, false);
				task.put(p, null);
				Updater up2 = new Updater(ThisPlugin.getPlugin(), 71948, Main.file, UpdateType.NO_DOWNLOAD, false);
				if (up2.getResult() == UpdateResult.UPDATE_AVAILABLE || Updated == false) {
					p.sendMessage(Main.codSignature + "§bDownloading...");
					Updater up3 = new Updater(ThisPlugin.getPlugin(), 71948, Main.file, UpdateType.DEFAULT, true);
					if (up3.getResult() == UpdateResult.SUCCESS) {
						p.sendMessage(Main.codSignature + "§2COD-Warfare v" + getFileVersion(up3.getLatestName()) + " §aDownloaded!");
						Updated = true;
					}else{
						p.sendMessage(Main.codSignature + "§cThere was an issue downloading the latest version of COD-Warfare");
						return;
					}
				}else{
					p.sendMessage(Main.codSignature + "§cCOD-Warfare is already up to date");
					return;
				}
			}
		}else{
			p.sendMessage(Main.codSignature + "§cYou don't have permission to update COD-Warfare");
		}
	}
}
