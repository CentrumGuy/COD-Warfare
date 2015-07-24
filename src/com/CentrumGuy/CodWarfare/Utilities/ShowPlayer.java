package com.CentrumGuy.CodWarfare.Utilities;

import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Main;

public class ShowPlayer {

	public static void showPlayer(Player p) {
		
		for (int i = 0 ; i < Main.PlayingPlayers.size() ; i++) {
			Player pp = Main.PlayingPlayers.get(i);
			
			if ((!(p.equals(pp)))) p.showPlayer(pp);
			if ((!(pp.equals(p)))) pp.showPlayer(p);
			
			/*CraftPlayer ppp = (CraftPlayer) p;
			CraftPlayer pppp = (CraftPlayer) pp;
			
			ppp.showPlayer(pppp);
			pppp.showPlayer(ppp);
			ppp.showPlayer(pp);
			pppp.showPlayer(p);*/
		}
		
		/*for (int i = 0 ; i < Main.PlayingPlayers.size() ; i++) {
			Player pp = Main.PlayingPlayers.get(i);
			
			if (pp != p && !(pp.equals(p))) {
				PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn(((CraftPlayer) pp).getHandle());
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
			}
		}*/
		
		/*for (int i = 0 ; i < Main.PlayingPlayers.size() ; i++) {
			Player pp = Main.PlayingPlayers.get(i);
			p.showPlayer(pp);
			pp.showPlayer(p);
			
			/*CraftPlayer ppp = (CraftPlayer) p;
			CraftPlayer pppp = (CraftPlayer) pp;
			
			ppp.showPlayer(pppp);
			pppp.showPlayer(ppp);
			ppp.showPlayer(pp);
			pppp.showPlayer(p);
		}*/
	}
	
}
