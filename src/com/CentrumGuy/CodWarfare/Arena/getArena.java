package com.CentrumGuy.CodWarfare.Arena;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.CentrumGuy.CodWarfare.Files.ArenasFile;
import com.CentrumGuy.CodWarfare.Files.EnabledArenasFile;

public class getArena {

	public static String getName(String Arena) {
		if (!(ArenasFile.getData().getString("Arenas." + Arena) == null)) {
			return ArenasFile.getData().getString("Arenas." + Arena);
		}else{
			return null;
		}
	}
	
	public static String getType(String Arena) {
		if (!(ArenasFile.getData().getString("Arenas." + Arena) == null)) {
			return ArenasFile.getData().getString("Arenas." + Arena + ".Type");
		}else{
			return null;
		}
	}
	
	public static boolean getEnabled(String Arena) {
		int nextNum = 1;
		
		while (EnabledArenasFile.getData().get("EnabledArenas." + nextNum) != null) {
			if (EnabledArenasFile.getData().getString("EnabledArenas." + nextNum).equals(Arena)) {
				return true;
			}
			
			nextNum++;
		}
		
		return false;
	}

	public static Location getBlueSpawn(String Arena) {
		
		if (!(ArenasFile.getData().getString("Arenas." + Arena) == null)) {
			if ((ArenasFile.getData().getString("Arenas." + Arena + ".Type").equals("TDM")) || (ArenasFile.getData().getString("Arenas." + Arena + ".Type").equals("INFECT")) || (ArenasFile.getData().getString("Arenas." + Arena + ".Type").equals("CTF")) || (ArenasFile.getData().getString("Arenas." + Arena + ".Type").equals("KC"))) {
				if (!(ArenasFile.getData().getString("Arenas." + Arena + ".Spawns.Blue") == null)) {
					

		
		final World world;
		final double X;
		final double Y;
		final double Z;
		
		world = Bukkit.getServer().getWorld(ArenasFile.getData().getString("Arenas." + Arena + ".Spawns.Blue.World"));
		X = ArenasFile.getData().getDouble("Arenas." + Arena + ".Spawns.Blue.X");
		Y = ArenasFile.getData().getDouble("Arenas." + Arena + ".Spawns.Blue.Y");
		Z = ArenasFile.getData().getDouble("Arenas." + Arena + ".Spawns.Blue.Z");
		
		Location blueSpawn = new Location(world, X, Y, Z);
		
		return blueSpawn;
		
				}else{
					return null;
				}
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	public static Location getRedSpawn(String Arena) {
		if (!(ArenasFile.getData().getString("Arenas." + Arena) == null)) {
			if ((ArenasFile.getData().getString("Arenas." + Arena + ".Type").equals("TDM")) || (ArenasFile.getData().getString("Arenas." + Arena + ".Type").equals("INFECT")) || (ArenasFile.getData().getString("Arenas." + Arena + ".Type").equals("CTF")) || (ArenasFile.getData().getString("Arenas." + Arena + ".Type").equals("KC"))) {
				if (!(ArenasFile.getData().getString("Arenas." + Arena + ".Spawns.Red") == null)) {
		
		final World world;
		final double X;
		final double Y;
		final double Z;
		
		world = Bukkit.getServer().getWorld(ArenasFile.getData().getString("Arenas." + Arena + ".Spawns.Red.World"));
		X = ArenasFile.getData().getDouble("Arenas." + Arena + ".Spawns.Red.X");
		Y = ArenasFile.getData().getDouble("Arenas." + Arena + ".Spawns.Red.Y");
		Z = ArenasFile.getData().getDouble("Arenas." + Arena + ".Spawns.Red.Z");
		
		Location redSpawn = new Location(world, X, Y, Z);
		
		return redSpawn;
		
				}else{
					return null;
				}
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	public static Location getFFASpawn(String Arena, int num) {
		if (!(ArenasFile.getData().getString("Arenas." + Arena) == null)) {
			if ((ArenasFile.getData().getString("Arenas." + Arena + ".Type").equals("FFA")) || (ArenasFile.getData().getString("Arenas." + Arena + ".Type").equals("GUN")) || (ArenasFile.getData().getString("Arenas." + Arena + ".Type").equals("ONEIN"))) {
				if ((!(ArenasFile.getData().getString("Arenas." + Arena + ".Spawns") == null)) && (!(ArenasFile.getData().getString("Arenas." + Arena + ".Spawns").isEmpty()))) {
					if (ArenasFile.getData().getString("Arenas." + Arena + ".Spawns." + num) != null) {
						
						final World world;
						final double X;
						final double Y;
						final double Z;
						
						world = Bukkit.getServer().getWorld(ArenasFile.getData().getString("Arenas." + Arena + ".Spawns." + num + ".World"));
						X = ArenasFile.getData().getDouble("Arenas." + Arena + ".Spawns." + num + ".X");
						Y = ArenasFile.getData().getDouble("Arenas." + Arena + ".Spawns." + num + ".Y");
						Z = ArenasFile.getData().getDouble("Arenas." + Arena + ".Spawns." + num + ".Z");
						
						Location redSpawn = new Location(world, X, Y, Z);
						
						return redSpawn;
						
					}else{
						return null;
					}
				}else{
					return null;
				}
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	public static Location getFlagLocation(String team, String Arena) {
		if (!(ArenasFile.getData().getString("Arenas." + Arena) == null)) {
			if ((ArenasFile.getData().getString("Arenas." + Arena + ".Type").equals("CTF"))) {
				if (!(ArenasFile.getData().getString("Arenas." + Arena + ".Flags.Red") == null)) {
		
					if (team.equalsIgnoreCase("Red")) {
						final World world;
						final double X;
						final double Y;
						final double Z;
		
						world = Bukkit.getServer().getWorld(ArenasFile.getData().getString("Arenas." + Arena + ".Flags.Red.World"));
						X = ArenasFile.getData().getDouble("Arenas." + Arena + ".Flags.Red.X");
						Y = ArenasFile.getData().getDouble("Arenas." + Arena + ".Flags.Red.Y");
						Z = ArenasFile.getData().getDouble("Arenas." + Arena + ".Flags.Red.Z");
						
						Location redSpawn = new Location(world, X, Y, Z);
		
						return redSpawn;
					}else if (team.equalsIgnoreCase("blue")) {
						if (ArenasFile.getData().getString("Arenas." + Arena + ".Flags.Blue") == null) return null;
							final World world;
							final double X;
							final double Y;
							final double Z;
			
							world = Bukkit.getServer().getWorld(ArenasFile.getData().getString("Arenas." + Arena + ".Flags.Blue.World"));
							X = ArenasFile.getData().getDouble("Arenas." + Arena + ".Flags.Blue.X");
							Y = ArenasFile.getData().getDouble("Arenas." + Arena + ".Flags.Blue.Y");
							Z = ArenasFile.getData().getDouble("Arenas." + Arena + ".Flags.Blue.Z");
			
							Location blueSpawn = new Location(world, X, Y, Z);
			
							return blueSpawn;
					}else {
						return null;
					}
				}else{
					return null;
				}
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	public static Location getSpectatorSpawn(String Arena) {
		if (!(ArenasFile.getData().getString("Arenas." + Arena) == null)) {
			if ((ArenasFile.getData().getString("Arenas." + Arena + ".Type").equals("ONEIN"))) {
				if (!(ArenasFile.getData().getString("Arenas." + Arena + ".Spectator.Spawn") == null)) {
		
		final World world;
		final double X;
		final double Y;
		final double Z;
		
		world = Bukkit.getServer().getWorld(ArenasFile.getData().getString("Arenas." + Arena + ".Spectator.Spawn.World"));
		X = ArenasFile.getData().getDouble("Arenas." + Arena + ".Spectator.Spawn.X");
		Y = ArenasFile.getData().getDouble("Arenas." + Arena + ".Spectator.Spawn.Y");
		Z = ArenasFile.getData().getDouble("Arenas." + Arena + ".Spectator.Spawn.Z");
		
		Location Spawn = new Location(world, X, Y, Z);
		
		return Spawn;
		
				}else{
					return null;
				}
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
}
