package com.CentrumGuy.CodWarfare.Commands;

import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;
import com.CentrumGuy.CodWarfare.Utilities.IChatMessage;

public class ResourcePackCommand {
	
	private static String getLink(String link) {
		if (link.startsWith("http")) return link;
		else link = "http://" + link;
		return link;
	}

	public static void sendLink(Player p) {
		if (ThisPlugin.getPlugin().getConfig().getString("ResourceLink").equalsIgnoreCase("none")) {
			p.sendMessage(Main.codSignature + "§cThis server does not have a Call of Duty resource pack");
		}else{
			IChatMessage message = new IChatMessage(Main.codSignature, "§6Click here to download the server's Call of Duty §6resource pack").addLink(getLink(ThisPlugin.getPlugin().getConfig().getString("ResourceLink"))).addLoreLine("§bClick the text to continue");
			message.send(p);
		}
	}	
}
