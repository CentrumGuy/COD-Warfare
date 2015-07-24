package com.CentrumGuy.CodWarfare.NameTag;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class CODTeam {
  private ArrayList<UUID> viewers = new ArrayList<UUID>();
  private ArrayList<UUID> players = new ArrayList<UUID>();
  private String name = "";
  private String displayName = "";
  private String prefix = "";
  private String suffix = "";
  private boolean canAttackTeam = true;
  private boolean canSeeInvisPlayer = false;
  
  protected CODTeam(String name)
  {
    this.name = name;
  }
  
  public void addPlayer(Player p)
  {
    if (!this.players.contains(p.getUniqueId())) {
      this.players.add(p.getUniqueId());
    }
    if (!this.viewers.contains(p.getUniqueId())) {
      this.viewers.add(p.getUniqueId());
    }
    update();
  }

public void removePlayer(Player p)
  {
    this.players.remove(p.getUniqueId());
    this.viewers.remove(p.getUniqueId());
    if (p.getScoreboard().getTeam(this.name) != null) {
      p.getScoreboard().getTeam(this.name).removePlayer(p);
    }
    update();
  }
  
  public boolean isMember(Player p)
  {
    return this.players.contains(p.getUniqueId());
  }
  
  public boolean isViewer(Player p)
  {
    return this.viewers.contains(p.getUniqueId());
  }
  
  public void addViewer(Player p)
  {
    if (this.viewers.contains(p.getUniqueId())) {
      this.viewers.add(p.getUniqueId());
    }
    update();
  }
  
  public void unregister()
  {
    for (UUID pid : this.viewers) {
      if (Bukkit.getPlayer(pid).getScoreboard().getTeam(this.name) != null) {
        Bukkit.getPlayer(pid).getScoreboard().getTeam(this.name).unregister();
      }
    }
    this.viewers.clear();
    this.players.clear();
  }
  
  public void setPrefix(String prefix)
  {
    this.prefix = prefix;
    update();
  }
  
  public String getPrefix()
  {
    return this.prefix;
  }
  
  public void setSuffix(String suffix)
  {
    this.suffix = suffix;
    update();
  }
  
  public String getSuffix()
  {
    return this.suffix;
  }
  
  public String getDisplayName()
  {
    return this.displayName;
  }
  
  public void setDisplayName(String name)
  {
    this.displayName = name;
    update();
  }
  
  public boolean getFriendlyFire()
  {
    return this.canAttackTeam;
  }
  
  public void setFriendlyFire(boolean attackTeam)
  {
    this.canAttackTeam = attackTeam;
    update();
  }
  
  public boolean getCanSeeInvisPlayer()
  {
    return this.canSeeInvisPlayer;
  }
  
  public void setCanSeeInvisPlayer(boolean invis)
  {
    this.canSeeInvisPlayer = invis;
    update();
  }
  
private void update(){
    for (UUID pid : this.viewers) {
      if (Bukkit.getPlayer(pid).isOnline())
      {
        Player p = Bukkit.getPlayer(pid);
        if (p.getScoreboard() != null)
        {
          Scoreboard s = p.getScoreboard();
          Team t = null;
          if (s.getTeam(this.name) != null)
          {
            t = s.getTeam(this.name);
            for (OfflinePlayer tmp : t.getPlayers()) {
              if (!this.players.contains(tmp.getUniqueId())) {
                t.removePlayer(tmp);
              }
            }
            for (UUID tmp : this.players) {
              if (!t.getPlayers().contains(Bukkit.getOfflinePlayer(tmp))) {
                t.addPlayer(Bukkit.getOfflinePlayer(tmp));
              }
            }
          }
          else
          {
            t = s.registerNewTeam(this.name);
            for (UUID tmp : this.players) {
              t.addPlayer(Bukkit.getOfflinePlayer(tmp));
            }
          }
          t.setPrefix(this.prefix);
          t.setSuffix(this.suffix);
          t.setDisplayName(this.displayName);
          t.setAllowFriendlyFire(this.canAttackTeam);
          t.setCanSeeFriendlyInvisibles(this.canSeeInvisPlayer);
          for (UUID ids : this.players) {
            t.addPlayer(Bukkit.getOfflinePlayer(ids));
          }
        }
      }
    }
  }
  
  public String getName()
  {
    return this.name;
  }
}

