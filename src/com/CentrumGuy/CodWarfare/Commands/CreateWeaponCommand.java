package com.CentrumGuy.CodWarfare.Commands;

import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.OtherLoadout.Lethal;
import com.CentrumGuy.CodWarfare.OtherLoadout.Tactical;

public class CreateWeaponCommand {

	public static void createWeapon(String[] args, Player p) {
		if (args.length == 7) {
			String id = args[1];
			String data = args[2];
			String amount = args[3];
			String name = args[4];
			String cost = args[5];
			String type = args[6];
			
			int idNum = Integer.parseInt(id);
			byte dataNum = Byte.parseByte(data);
			int amountNum = Integer.parseInt(amount);
			int costNum = Integer.parseInt(cost);
			
			if (type.equalsIgnoreCase("tactical")) {
				Tactical.createTactical(idNum, dataNum, amountNum, name, costNum);
				p.sendMessage(Main.codSignature + "§aSuccessfully created tactical weapon:§2 " + name);
			}else if (type.equalsIgnoreCase("lethal")) {
				Lethal.createLethal(idNum, dataNum, amountNum, name, costNum);
				p.sendMessage(Main.codSignature + "§aSuccessfully created lethal weapon:§2 " + name);
			}else{
				p.sendMessage(Main.codSignature + "§cInvalid weapon type. Valid weapon types are §4lethal §cand §4tactical");
			}
		}else{
			p.sendMessage(Main.codSignature + "§cPlease type §4/cod createweapon [Item ID] [Item Data Value] [Amount] [Name] [Cost] [Type]");
		}
	}
}
