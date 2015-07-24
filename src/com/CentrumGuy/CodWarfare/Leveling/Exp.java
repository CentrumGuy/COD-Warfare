package com.CentrumGuy.CodWarfare.Leveling;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class Exp {

	public static HashMap<Player, Integer> NeededExpFromBefore = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> NeededExpNow = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> ExpNow = new HashMap<Player, Integer>();
	
	public static Integer getExp(Player p) {
		return ExpNow.get(p);
	}
	
	public static void setNeededExp(Player p, Integer Amount) {
		NeededExpNow.put(p, 0);
		NeededExpNow.put(p, Amount);
		float precent = (((float) ExpNow.get(p)) / ((float) NeededExpNow.get(p)));
		p.setExp(precent);
	}
	
	public static void addExp(Player p, Integer amount) {
		
		ExpNow.put(p, ExpNow.get(p) + amount);
		
		float precent = (((float) ExpNow.get(p)) / ((float) NeededExpNow.get(p)));
		p.setExp(precent);
		
		if (ExpNow.get(p) >= NeededExpNow.get(p)) {
			NeededExpFromBefore.put(p, 0);
			NeededExpFromBefore.put(p, NeededExpNow.get(p));
			NeededExpNow.put(p, 0);
			ExpNow.put(p, 0);
			NeededExpNow.put(p, NeededExpFromBefore.get(p) + (NeededExpFromBefore.get(p) / 7)).intValue();
			
			Level.add(p, 1);
			
			precent = (((float) ExpNow.get(p)) / ((float) NeededExpNow.get(p)));
			p.setExp(precent);
			return;
		}else if (ExpNow.get(p) < NeededExpNow.get(p)) {
			return;
		}else{
			return;
		}
	}
	
	public static Integer getNeededExp(Player p) {
		return NeededExpNow.get(p);
	}
	
	public static Integer getNeededExpFromBefore(Player p) {
		return NeededExpFromBefore.get(p);
	}
	
	public static void setExp(Player p, Integer Amount) {
		ExpNow.put(p, 0);
		ExpNow.put(p, Amount);
		if (ExpNow.get(p) == 0) {
			p.setExp(0);
			return;
		}
		
		float precent = (((float) ExpNow.get(p)) / ((float) NeededExpNow.get(p)));
		p.setExp(precent);
	}
}
