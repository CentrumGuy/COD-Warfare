package com.CentrumGuy.CodWarfare.Arena;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Files.ArenasFile;

public class FFASpawningSystem {
	
	private static ArrayList<Location> Spawns = new ArrayList<Location>();
	private static HashMap<Player, Location> spawn = new HashMap<Player, Location>();
	private static ArrayList<Location> startSpawns = new ArrayList<Location>();

	public static void RegisterSpawns(ArrayList<Player> pp, String Arena) {
		
		startSpawns.clear();
		Spawns.clear();
		spawn.clear();
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			spawn.put(p, null);
		}
		
		int Number = 1;
		while (ArenasFile.getData().get("Arenas." + Arena + ".Spawns." + Number) != null) {
				
				double X = ArenasFile.getData().getDouble("Arenas." + Arena + ".Spawns." + Number + ".X");
				double Y = ArenasFile.getData().getDouble("Arenas." + Arena + ".Spawns." + Number + ".Y");
				double Z = ArenasFile.getData().getDouble("Arenas." + Arena + ".Spawns." + Number + ".Z");
			
				String WorldName = ArenasFile.getData().getString("Arenas." + Arena + ".Spawns." + Number + ".World");
			
				World world = Bukkit.getServer().getWorld(WorldName);
			
				Location loc = new Location(world, X, Y, Z);
			
				Spawns.add(loc);
				
				Number++;
		}
		
		startSpawns.addAll(Spawns);
	}
	
	public static Location SpawnPlayer(Player p, boolean Teleport) {
		
		int size = Spawns.size();
		
		int i = new Random().nextInt(size);
		
		Location LocSpawn = Spawns.get(i);
		
		if (spawn.get(p) != null) {
			if (LocSpawn.equals(spawn.get(p))) {
				if (i != ((Spawns.size()) - (1))) {
					LocSpawn = Spawns.get(i + 1);
					
					if (Teleport) p.teleport(LocSpawn);
					spawn.put(p, LocSpawn);

					return LocSpawn;
				}else if (i != (0)) {
					LocSpawn = Spawns.get(i - 1);
					
					if (Teleport) p.teleport(LocSpawn);
					spawn.put(p, LocSpawn);
					
					return LocSpawn;
				}else{
					if (Teleport) p.teleport(LocSpawn);
					spawn.put(p, LocSpawn);
					
					return LocSpawn;
				}
			} else {
				if (Teleport) p.teleport(LocSpawn);
				spawn.put(p, LocSpawn);
				
				return LocSpawn;
			}
		}else{
			if (!(startSpawns.isEmpty())) {
				Random r = new Random();
				int spawnNum = r.nextInt(startSpawns.size());
				LocSpawn = startSpawns.get(spawnNum);
				
				if (Teleport) p.teleport(LocSpawn);
				spawn.put(p, LocSpawn);
				startSpawns.remove(spawnNum);
				return LocSpawn;
			}else{
				if (Teleport) p.teleport(LocSpawn);
				spawn.put(p, LocSpawn);
				return LocSpawn;
			}
		}
	}
	
	public static int getPlayerNumber(Player p) {
		for (int i = 0 ; i < Main.PlayingPlayers.size() ; i++) {
			Player pp = Main.PlayingPlayers.get(i);
			if (pp.equals(p)) return i;
		}
		
		return -1;
	}
}