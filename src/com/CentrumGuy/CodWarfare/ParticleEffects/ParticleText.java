package com.CentrumGuy.CodWarfare.ParticleEffects;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

public class ParticleText
{
  Plugin plugin;

  public ParticleText(Plugin plgn)
  {
    this.plugin = plgn;
  }

  public double SpawnLetter(Location loc, String letter, ParticleEffect effect, int dir, int heightmod, double init_size, float speed)
  {
    String pattern = "";
    int width = 5;

    width = 5;

    if (letter.equalsIgnoreCase(".")) {
      pattern = pattern + "00000";
      pattern = pattern + "00000";
      pattern = pattern + "00000";
      pattern = pattern + "00000";
      pattern = pattern + "00100";
    }
    else {
      for (int row = 1; row < 10; row++)
      {
        String l = letter.toUpperCase();
        String rowPattern = this.plugin.getConfig().getString("LetterStyle." + l + ".Row" + row, "-1");
        if (rowPattern.equalsIgnoreCase("-1")) break;
        width = rowPattern.length();
        pattern = pattern + rowPattern;
      }

      if (pattern.equalsIgnoreCase(""))
      {
        width = 5;
        pattern = pattern + "11111";
        pattern = pattern + "10101";
        pattern = pattern + "11111";
        pattern = pattern + "10101";
        pattern = pattern + "11111";
      }

    }

    String tmp = "";
    double xx = loc.getX();
    double yy = loc.getY();
    double zz = loc.getZ();

    double x = xx;

    double z = zz;
    Location loc2 = loc.clone();

    for (int i = 0; i < pattern.length(); i++) {
      tmp = pattern.substring(i, i + 1);
      int type = Integer.parseInt(tmp);

      loc2.setX(xx);
      loc2.setY(yy);
      loc2.setZ(zz);

      if (type == 1) {
        createEffect(effect, loc2, speed, 4, heightmod);
      }
      double size = init_size;

      if (dir == 1)
      {
        xx += size;
        if ((i + 1) % width == 0)
        {
          xx = x;
          yy -= size;
        }
      }
      else if (dir == 2)
      {
        zz += size;
        if ((i + 1) % width == 0)
        {
          zz = z;
          yy -= size;
        }
      }
      else if (dir == 3)
      {
        xx -= size;
        if ((i + 1) % width == 0)
        {
          xx = x;
          yy -= size;
        }
      }
      else
      {
        zz -= size;
        if ((i + 1) % width == 0)
        {
          zz = z;
          yy -= size;
        }
      }
    }

    return width * init_size;
  }

  public void createEffect(ParticleEffect effect, Location location, float effectSpeed, int amountOfParticles, int heightmod) {
	  effect.display(0, 0, 0, effectSpeed, amountOfParticles, new Location(location.getWorld(), location.getX(), ((location.getY()) + (heightmod)), location.getZ()));
  }

  public void SpawnPhrase(Location origin, Location loc, String phrase, ParticleEffect effect, Direction direction, int heightmod, double init_size, float speed)
  {
    String letter = "";
    Location loc2 = loc.clone();
    double x = loc.getX();
    double y = loc.getY();
    double z = loc.getZ();
    double size = init_size;
    
    int dir = 1;
    
    if (direction == Direction.NORTH) {
    	dir = 3;
    }else if (direction == Direction.SOUTH) {
    	dir = 1;
    }else if (direction == Direction.EAST) {
    	dir = 4;
    }else if (direction == Direction.WEST) {
    	dir = 2;
    }

    if (dir == 1)
      x -= (size * phrase.length() - 1.0D) / 2.0D + size * 2.5D / 6.0D;
    else if (dir == 2)
      z -= (size * phrase.length() - 1.0D) / 2.0D + size * 2.5D / 6.0D;
    else if (dir == 3)
      x += (size * phrase.length() - 1.0D) / 2.0D + size * 2.5D / 6.0D;
    else if (dir == 4) {
      z += (size * phrase.length() - 1.0D) / 2.0D + size * 2.5D / 6.0D;
    }

    for (int i = 0; i <= phrase.length() - 1; i++)
    {
      if (dir == 1)
      {
        double newxz = x + i * size;
        loc2.setX(newxz);
        loc2.setY(y);
        loc2.setZ(z);
      }
      else if (dir == 2)
      {
        double newxz = z + i * size;
        loc2.setX(x);
        loc2.setY(y);
        loc2.setZ(newxz);
      }
      else if (dir == 3)
      {
        double newxz = x - i * size;
        loc2.setX(newxz);
        loc2.setY(y);
        loc2.setZ(z);
      }
      else
      {
        double newxz = z - i * size;
        loc2.setX(x);
        loc2.setY(y);
        loc2.setZ(newxz);
      }

      letter = phrase.substring(i, i + 1);
      SpawnLetter(loc2, letter, effect, dir, heightmod, init_size / 6.0D, speed);
    }
  }
}