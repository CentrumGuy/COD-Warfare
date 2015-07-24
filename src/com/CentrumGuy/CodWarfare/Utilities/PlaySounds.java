package com.CentrumGuy.CodWarfare.Utilities;

import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PlaySounds {

	public static void playStartGameSound(Player p) {
		p.playSound(p.getLocation(), Sound.AMBIENCE_CAVE, 1F, 1F);
	}
	
	public static void playCountdownSound(Player p, Note n) {
		p.playNote(p.getLocation(), Instrument.PIANO, n);
	}
	
	public static void playLevelUp(Player p) {
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 1F, 1F);
	}
}
