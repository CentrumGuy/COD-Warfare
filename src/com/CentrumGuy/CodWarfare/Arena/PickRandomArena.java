package com.CentrumGuy.CodWarfare.Arena;

import java.util.Random;

import com.CentrumGuy.CodWarfare.Files.EnabledArenasFile;

public class PickRandomArena {
	
	public static String CurrentArena;
	public static String UpcomingArena;

	public static void PickArena() {
		
	if (EnabledArenasFile.getData().contains("EnabledArenas")) {
	if (!(EnabledArenasFile.getData().getString("EnabledArenas").isEmpty())) {
		int NextArena = 0;
		
				Random Arena = new Random();
		
				int highestNumber = 1;

				while (EnabledArenasFile.getData().get("EnabledArenas." + highestNumber) != null) {
					highestNumber++;
				}

				highestNumber = highestNumber - 1;
				
			NextArena = Arena.nextInt(highestNumber);
			
			NextArena++;
			
			if (UpcomingArena != null) {
				CurrentArena = UpcomingArena;
			}
			
			UpcomingArena = EnabledArenasFile.getData().getString("EnabledArenas." + NextArena);
			if (UpcomingArena.equals(CurrentArena)) {
				if (EnabledArenasFile.getData().getString("EnabledArenas." + ((NextArena) + (1))) != null) {
					UpcomingArena = EnabledArenasFile.getData().getString("EnabledArenas." + ((NextArena) + (1)));
				}else if (EnabledArenasFile.getData().getString("EnabledArenas." + ((NextArena) - (1))) != null) {
					UpcomingArena = EnabledArenasFile.getData().getString("EnabledArenas." + ((NextArena) - (1)));
				}
			}
			
			if (CurrentArena == null) CurrentArena = UpcomingArena;
			
		}else{
			UpcomingArena = null;
		}
	
		}else{
			UpcomingArena = null;
		}
	}
}
