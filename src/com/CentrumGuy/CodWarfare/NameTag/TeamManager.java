package com.CentrumGuy.CodWarfare.NameTag;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TeamManager
{
  private ArrayList<CODTeam> teams = new ArrayList<CODTeam>();
  private ArrayList<UUID> playersInGlobalTeamBoard = new ArrayList<UUID>();
  
  
  public CODTeam registerTeam(String name)
  {
    if (getTeam(name) == null)
    {
      CODTeam team = new CODTeam(name);
      this.teams.add(team);
      for (UUID id : this.playersInGlobalTeamBoard) {
        team.addPlayer(Bukkit.getPlayer(id));
      }
      return team;
    }
    Bukkit.getLogger().warning("DevNote: Attempt to register team that already exists.");
    return getTeam(name);
  }
  
  public void addPlayerToGlobalBoard(Player p, CODTeam teamToJoin)
  {
    if (this.teams.contains(teamToJoin))
    {
      if (!this.playersInGlobalTeamBoard.contains(p.getUniqueId()))
      {
        teamToJoin.addPlayer(p);
        for (CODTeam others : this.teams) {
          if (!teamToJoin.equals(others)) {
            others.addViewer(p);
          }
        }
      }
      else
      {
        Bukkit.getLogger().warning("DevNote: Player is already in global team board. Player: " + p.getName());
      }
    }
    else {
      Bukkit.getLogger().warning("DevNote: Attempt to join a non-tracked team. Attempt ignored.");
    }
  }
  
  public void addPlayerToGlobalBoard(Player p, String teamToJoin)
  {
    if (getTeam(teamToJoin) != null)
    {
      CODTeam team = getTeam(teamToJoin);
      if (this.teams.contains(team))
      {
        if (!this.playersInGlobalTeamBoard.contains(p.getUniqueId()))
        {
          team.addPlayer(p);
          for (CODTeam others : this.teams) {
            if (!team.equals(others)) {
              others.addViewer(p);
            }
          }
        }
        else
        {
          Bukkit.getLogger().warning("DevNote: Player is already in global team board. Player: " + p.getName());
        }
      }
      else {
        Bukkit.getLogger().warning("DevNote: Attempt to join a non-tracked team. Attempt ignored.");
      }
    }
    else
    {
      Bukkit.getLogger().warning("DevNote: Attempt to add player to non-existent team.");
    }
  }
  
  public void removePlayerFromGlobalBoard(Player p)
  {
    this.playersInGlobalTeamBoard.remove(p.getUniqueId());
    for (CODTeam team : this.teams) {
      team.removePlayer(p);
    }
  }
  
  public void clearTeams()
  {
    for (CODTeam team : this.teams) {
      team.unregister();
    }
    this.teams.clear();
    this.playersInGlobalTeamBoard.clear();
  }
  
  public CODTeam getPlayerTeam(Player p)
  {
    for (CODTeam team : this.teams) {
      if (team.isMember(p)) {
        return team;
      }
    }
    return null;
  }
  
  public CODTeam getTeam(String name)
  {
    for (CODTeam team : this.teams) {
      if (team.getName().equalsIgnoreCase(name)) {
        return team;
      }
    }
    return null;
  }
}