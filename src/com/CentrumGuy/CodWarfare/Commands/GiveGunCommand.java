package com.CentrumGuy.CodWarfare.Commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Inventories.AGPInventory;
import com.CentrumGuy.CodWarfare.Inventories.AGSInventory;
import com.CentrumGuy.CodWarfare.Inventories.Guns;

public class GiveGunCommand {

	@SuppressWarnings("deprecation")
	public static void give(CommandSender p, String[] args) {
		if (args.length >= 3) {
			String playerReceiverName = args[1];
			String gunName = "";
			
			for (int i = 2 ; i < args.length ; i++) {
				if (i == 2) {
					gunName = gunName + "" + args[i];
				}else{
					gunName = gunName + " " + args[i];
				}
			}
			
			gunName = StringUtils.remove(gunName, "§e");
			gunName = "§e" + gunName;
			
			if (!(checkIfGunsContains(gunName))) {
				gunName = StringUtils.remove(gunName, "§e");
				p.sendMessage(Main.codSignature + "§cNo gun found with the name§4 " + gunName);
				return;
			}
			
			if (Bukkit.getServer().getPlayer(playerReceiverName) == null) {
				p.sendMessage(Main.codSignature + "§cCould not find player§4 " + playerReceiverName);				
				return;
			}else{
				Player receiver = Bukkit.getServer().getPlayer(playerReceiverName);
				
				for (int i = 0 ; i < Guns.guns.size() ; i++) {
					if (!(Guns.guns.get(i).hasItemMeta())) continue;
					if (!(Guns.guns.get(i).getItemMeta().hasDisplayName())) continue;
					
					ItemStack gun = Guns.guns.get(i);
					
					if (gun.getItemMeta().getDisplayName().equals(gunName)) {
						if (Guns.getType(gun).equalsIgnoreCase("Primary")) {
							if (!(AGPInventory.getAGP(receiver).contains(gun))) {
								AGPInventory.getAGP(receiver).addItem(gun);
							}else{
								gunName = StringUtils.remove(gunName, "§e");
								p.sendMessage(Main.codSignature + "§c" + receiver.getName() + " §calready has the gun§4 " + gunName);
								return;
							}
						}else if (Guns.getType(gun).equalsIgnoreCase("Secondary")) {
							if (!(AGSInventory.getAGS(receiver).contains(gun))) {
								AGSInventory.getAGS(receiver).addItem(gun);
							}else{
								gunName = StringUtils.remove(gunName, "§e");
								p.sendMessage(Main.codSignature + "§c" + receiver.getName() + " §calready has the gun§4 " + gunName);
								return;
							}
						}else{
							continue;
						}
					}
				}
				
				AGPInventory.saveAGP(receiver);
				AGPInventory.loadAGP(receiver);
				
				AGSInventory.saveAGS(receiver);
				AGSInventory.loadAGS(receiver);
				
				p.sendMessage(Main.codSignature + "§aGave gun: §e" + gunName);
				receiver.sendMessage(Main.codSignature + "§aYou received a gun: " + gunName);
			}
 		}else{
 			p.sendMessage(Main.codSignature + "§cPlease type §4/cod givegun [Player Name] [Gun Name]");
 		}
	}
	
	private static boolean checkIfGunsContains(String gunName) {
		for (int i = 0 ; i < Guns.guns.size() ; i++) {
			if (!(Guns.guns.get(i).hasItemMeta())) continue;
			if (!(Guns.guns.get(i).getItemMeta().hasDisplayName())) continue;
			
			ItemStack gun = Guns.guns.get(i);
			
			if (gun.getItemMeta().getDisplayName().equals(gunName)) {
				return true;
			}
		}
		
		return false;
	}
}
