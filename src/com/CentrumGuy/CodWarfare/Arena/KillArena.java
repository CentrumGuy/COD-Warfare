package com.CentrumGuy.CodWarfare.Arena;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.CentrumGuy.CodWarfare.Utilities.GetNormalName;
import com.CentrumGuy.CodWarfare.Utilities.Prefix;
import com.CentrumGuy.CodWarfare.Utilities.SendCoolMessages;
import com.CentrumGuy.CodWarfare.Utilities.ShowPlayer;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Arena.BaseArena.ArenaType;
import com.CentrumGuy.CodWarfare.Inventories.KitInventory;
import com.CentrumGuy.CodWarfare.Plugin.ThisPlugin;
import com.CentrumGuy.CodWarfare.Utilities.GameCountdown;

public class KillArena {

    public static ArrayList<Player> RedTeam = new ArrayList<Player>();
    public static ArrayList<Player> BlueTeam = new ArrayList<Player>();
    private static HashMap<Player, String> Team = new HashMap<Player, String>();
    private static ArrayList<Player> pls = Main.PlayingPlayers;

    public static int RedTeamScore;
    public static int BlueTeamScore;

    public static void assignTeam(String Arena) {

        RedTeam.clear();
        BlueTeam.clear();
        Main.PlayingPlayers.clear();
        pls.clear();
        Team.clear();

        if (getArena.getType(Arena).equals("KC")) {
            if (BaseArena.state == BaseArena.ArenaState.STARTED) {

                Main.PlayingPlayers.addAll(Main.WaitingPlayers);
                Main.WaitingPlayers.clear();

                BaseArena.type = ArenaType.KC;

                for (int assign = 0; assign < pls.size(); assign++) {

                    Player p = pls.get(assign);

                    if (RedTeam.size() < BlueTeam.size()) {
                        RedTeam.add(p);
                    } else if (BlueTeam.size() < RedTeam.size()) {
                        BlueTeam.add(p);
                    } else {

                        Random RandomTeam = new Random();
                        int TeamID = 0;

                        TeamID = RandomTeam.nextInt(2);

                        if (TeamID == 0) {
                            RedTeam.add(p);
                        } else {
                            BlueTeam.add(p);
                        }
                    }


                    if (RedTeam.contains(p)) {
                        Team.put(p, "Red");
                    } else {
                        Team.put(p, "Blue");
                    }

                    continue;

                }
            }
        }
    }

    public static void startKC(final String Arena) {
        if (getArena.getType(Arena).equals("KC")) {
            if (BaseArena.state == BaseArena.ArenaState.STARTED) {

                RedTeamScore = 0;
                BlueTeamScore = 0;

                for (int ID = 0; ID < pls.size(); ID++) {
                    final Player p = pls.get(ID);

                    if (RedTeam.contains(p)) {
                        if (Main.extraCountdown) {
                            p.sendMessage(Main.codSignature + "§cYou have joined the §4§lRED §cteam");
                            Bukkit.getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
                                public void run() {
                                    SendCoolMessages.clearTitleAndSubtitle(p);
                                    SendCoolMessages.resetTitleAndSubtitle(p);
                                    SendCoolMessages.sendTitle(p, "§6", 10, 30, 10);
                                    SendCoolMessages.sendSubTitle(p, "§c§lYOU JOINED THE §4§lRED TEAM", 10, 30, 10);
                                }
                            }, 40);
                        }

                        GameCountdown.startCountdown(p, getArena.getRedSpawn(Arena), "§c§lGO GO GO!!!", Main.codSignature + "§c§lYou joined the §4§lred §c§lteam!");

                        p.closeInventory();
                        p.teleport(getArena.getRedSpawn(Arena));
                        p.getInventory().clear();
                        p.setHealth(20);
                        p.setFoodLevel(20);
							
							/*for (Player pp : BlueTeam) {
								Main.blue.addPlayer(pp);
							}
							
							for (Player pp : RedTeam) {
								Main.red.addPlayer(pp);
							}*/

                        Prefix.setDispName(p, "§c" + p.getName());
                        if (Main.dispName.get(p) != null) p.setPlayerListName(Main.dispName.get(p));
                        SendCoolMessages.TabHeaderAndFooter("§4§lRed §c§lTeam", "§6§lCOD-Warfare\n" + getBetterTeam(), p);

                        Color c = Color.fromRGB(255, 0, 0);
                        p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
                        p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
                        p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
                        p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));

                        Main.setGameBoard(p);

                        if (Main.GameDeathsScore.get(p.getName()) != null)
                            Main.GameDeathsScore.get(p.getName()).setScore(0);
                        if (Main.GameKillsScore.get(p.getName()) != null)
                            Main.GameKillsScore.get(p.getName()).setScore(0);
                        if (Main.GameKillStreakScore.get(p.getName()) != null)
                            Main.GameKillStreakScore.get(p.getName()).setScore(0);

                    } else if (BlueTeam.contains(p)) {
                        if (Main.extraCountdown) {
                            p.sendMessage(Main.codSignature + "§9You have joined the §1§lBLUE §9team");
                            Bukkit.getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
                                public void run() {
                                    SendCoolMessages.clearTitleAndSubtitle(p);
                                    SendCoolMessages.resetTitleAndSubtitle(p);
                                    SendCoolMessages.sendTitle(p, "§6", 10, 50, 10);
                                    SendCoolMessages.sendSubTitle(p, "§9§lYOU JOINED THE §1§lBLUE TEAM", 10, 50, 10);
                                }
                            }, 40);
                        }
                        GameCountdown.startCountdown(p, getArena.getBlueSpawn(Arena), "§9§lGO GO GO!!!", Main.codSignature + "§9§lYou joined the §1§lblue §1§lteam!");

                        p.closeInventory();
                        p.teleport(getArena.getBlueSpawn(Arena));
                        p.getInventory().clear();
                        p.setHealth(20);
                        p.setFoodLevel(20);
							
							/*for (Player pp : BlueTeam) {
								Main.blue.addPlayer(pp);
							}
							
							for (Player pp : RedTeam) {
								Main.red.addPlayer(pp);
							}*/

                        Prefix.setDispName(p, "§9" + p.getName());
                        if (Main.dispName.get(p) != null) p.setPlayerListName(Main.dispName.get(p));
                        SendCoolMessages.TabHeaderAndFooter("§1§lBlue §9§lTeam", "§6§lCOD-Warfare\n" + getBetterTeam(), p);

                        Color c = Color.fromRGB(0, 0, 255);
                        p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
                        p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
                        p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
                        p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));

                        Main.setGameBoard(p);

                        if (Main.GameDeathsScore.get(p.getName()) != null)
                            Main.GameDeathsScore.get(p.getName()).setScore(0);
                        if (Main.GameKillsScore.get(p.getName()) != null)
                            Main.GameKillsScore.get(p.getName()).setScore(0);
                        if (Main.GameKillStreakScore.get(p.getName()) != null)
                            Main.GameKillStreakScore.get(p.getName()).setScore(0);
                    }
                }

                if (Main.extraCountdown) {
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ThisPlugin.getPlugin(), new Runnable() {
                        @Override
                        public void run() {
                            if (BaseArena.type != ArenaType.KC) {
                                BaseArena.type = ArenaType.KC;
                            }

                            RedTeamScore = 0;
                            BlueTeamScore = 0;

                            for (int ID = 0; ID < pls.size(); ID++) {
                                Player p = pls.get(ID);
                                if (!(Main.PlayingPlayers.contains(p))) continue;

                                if (RedTeam.contains(p)) {

                                    p.closeInventory();
                                    p.teleport(getArena.getRedSpawn(Arena));
                                    p.getInventory().clear();
                                    p.setHealth(20);
                                    p.setFoodLevel(20);
                                    p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 5));
									
	
									/*for (Player pp : BlueTeam) {
										Main.blue.addPlayer(pp);
									}
									
									for (Player pp : RedTeam) {
										Main.red.addPlayer(pp);
									}*/

                                    p.sendMessage("");
                                    p.sendMessage("§e§m=====================================================");
                                    p.sendMessage("§b§lYou are playing a game of §4§lKill Confirmed!");
                                    p.sendMessage(" §7§l- §6§lKill the §9§lBlue Team!");
                                    p.sendMessage(" §7§l- §6§lArena:§e§l " + Arena);
                                    p.sendMessage(" §7§l- §6§lGood luck!");
                                    p.sendMessage("§e§m=====================================================");
                                    p.sendMessage("");

                                    ShowPlayer.showPlayer(p);

                                    p.getInventory().clear();

                                    p.getInventory().setItem(0, KitInventory.getKit(p).getItem(0));
                                    if (!(Main.CrackShot)) {
                                        p.getInventory().setItem(1, KitInventory.getKit(p).getItem(1));
                                        p.getInventory().setItem(2, KitInventory.getKit(p).getItem(3));
                                    } else {
                                        p.getInventory().setItem(1, Main.CrackShotAPI.generateWeapon(GetNormalName.get(KitInventory.getKit(p).getItem(1).getItemMeta().getDisplayName())));
                                        p.getInventory().setItem(2, Main.CrackShotAPI.generateWeapon(GetNormalName.get(KitInventory.getKit(p).getItem(3).getItemMeta().getDisplayName())));
                                    }
                                    p.getInventory().setItem(19, KitInventory.getKit(p).getItem(2));
                                    p.getInventory().setItem(25, KitInventory.getKit(p).getItem(4));
                                    p.getInventory().setItem(3, KitInventory.getKit(p).getItem(5));
                                    p.getInventory().setItem(4, KitInventory.getKit(p).getItem(6));
                                    p.getInventory().setItem(5, KitInventory.getKit(p).getItem(7));


                                    Color c = Color.fromRGB(255, 0, 0);
                                    p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
                                    p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
                                    p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
                                    p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));

                                } else if (BlueTeam.contains(p)) {

                                    p.closeInventory();
                                    p.teleport(getArena.getBlueSpawn(Arena));
                                    p.getInventory().clear();
                                    p.setHealth(20);
                                    p.setFoodLevel(20);
                                    p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 5));
									
									/*for (Player pp : BlueTeam) {
										Main.blue.addPlayer(pp);
									}
									
									for (Player pp : RedTeam) {
										Main.red.addPlayer(pp);
									}*/

                                    p.sendMessage("");
                                    p.sendMessage("§e§m=====================================================");
                                    p.sendMessage("§b§lYou are playing a game of §4§lKill Confirmed!");
                                    p.sendMessage(" §7§l- §6§lKill the §c§lRed Team!");
                                    p.sendMessage(" §7§l- §6§lArena:§e§l " + Arena);
                                    p.sendMessage(" §7§l- §6§lGood luck!");
                                    p.sendMessage("§e§m=====================================================");
                                    p.sendMessage("");

                                    ShowPlayer.showPlayer(p);

                                    p.getInventory().clear();

                                    p.getInventory().setItem(0, KitInventory.getKit(p).getItem(0));
                                    if (!(Main.CrackShot)) {
                                        p.getInventory().setItem(1, KitInventory.getKit(p).getItem(1));
                                        p.getInventory().setItem(2, KitInventory.getKit(p).getItem(3));
                                    } else {
                                        p.getInventory().setItem(1, Main.CrackShotAPI.generateWeapon(GetNormalName.get(KitInventory.getKit(p).getItem(1).getItemMeta().getDisplayName())));
                                        p.getInventory().setItem(2, Main.CrackShotAPI.generateWeapon(GetNormalName.get(KitInventory.getKit(p).getItem(3).getItemMeta().getDisplayName())));
                                    }
                                    p.getInventory().setItem(19, KitInventory.getKit(p).getItem(2));
                                    p.getInventory().setItem(25, KitInventory.getKit(p).getItem(4));
                                    p.getInventory().setItem(3, KitInventory.getKit(p).getItem(5));
                                    p.getInventory().setItem(4, KitInventory.getKit(p).getItem(6));
                                    p.getInventory().setItem(5, KitInventory.getKit(p).getItem(7));

                                    Color c = Color.fromRGB(0, 0, 255);
                                    p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
                                    p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
                                    p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
                                    p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));


                                } else {
                                    return;
                                }
                            }
                        }
                    }, 225);
                } else {
                    if (BaseArena.type != ArenaType.KC) {
                        BaseArena.type = ArenaType.KC;
                    }

                    RedTeamScore = 0;
                    BlueTeamScore = 0;

                    for (int ID = 0; ID < pls.size(); ID++) {
                        Player p = pls.get(ID);

                        if (RedTeam.contains(p)) {

                            p.closeInventory();
                            p.teleport(getArena.getRedSpawn(Arena));
                            p.getInventory().clear();
                            p.setHealth(20);
                            p.setFoodLevel(20);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 5));

                            p.sendMessage("");
                            p.sendMessage("§e§m=====================================================");
                            p.sendMessage("§b§lYou are playing a game of §4§lKill Confirmed!");
                            p.sendMessage(" §7§l- §6§lKill the §9§lBlue Team!");
                            p.sendMessage(" §7§l- §6§lArena:§e§l " + Arena);
                            p.sendMessage(" §7§l- §6§lGood luck!");
                            p.sendMessage("§e§m=====================================================");
                            p.sendMessage("");

                            ShowPlayer.showPlayer(p);

                            p.getInventory().clear();

                            p.getInventory().setItem(0, KitInventory.getKit(p).getItem(0));
                            if (!(Main.CrackShot)) {
                                p.getInventory().setItem(1, KitInventory.getKit(p).getItem(1));
                                p.getInventory().setItem(2, KitInventory.getKit(p).getItem(3));
                            } else {
                                p.getInventory().setItem(1, Main.CrackShotAPI.generateWeapon(GetNormalName.get(KitInventory.getKit(p).getItem(1).getItemMeta().getDisplayName())));
                                p.getInventory().setItem(2, Main.CrackShotAPI.generateWeapon(GetNormalName.get(KitInventory.getKit(p).getItem(3).getItemMeta().getDisplayName())));
                            }
                            p.getInventory().setItem(19, KitInventory.getKit(p).getItem(2));
                            p.getInventory().setItem(25, KitInventory.getKit(p).getItem(4));
                            p.getInventory().setItem(3, KitInventory.getKit(p).getItem(5));
                            p.getInventory().setItem(4, KitInventory.getKit(p).getItem(6));
                            p.getInventory().setItem(5, KitInventory.getKit(p).getItem(7));


                            Color c = Color.fromRGB(255, 0, 0);
                            p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
                            p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
                            p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
                            p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));

                        } else if (BlueTeam.contains(p)) {

                            p.closeInventory();
                            p.teleport(getArena.getBlueSpawn(Arena));
                            p.getInventory().clear();
                            p.setHealth(20);
                            p.setFoodLevel(20);
                            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 5));

                            p.sendMessage("");
                            p.sendMessage("§e§m=====================================================");
                            p.sendMessage("§b§lYou are playing a game of §4§lKill Confirmed!");
                            p.sendMessage(" §7§l- §6§lKill the §c§lRed Team!");
                            p.sendMessage(" §7§l- §6§lArena:§e§l " + Arena);
                            p.sendMessage(" §7§l- §6§lGood luck!");
                            p.sendMessage("§e§m=====================================================");
                            p.sendMessage("");

                            ShowPlayer.showPlayer(p);

                            p.getInventory().clear();

                            p.getInventory().setItem(0, KitInventory.getKit(p).getItem(0));
                            if (!(Main.CrackShot)) {
                                p.getInventory().setItem(1, KitInventory.getKit(p).getItem(1));
                                p.getInventory().setItem(2, KitInventory.getKit(p).getItem(3));
                            } else {
                                p.getInventory().setItem(1, Main.CrackShotAPI.generateWeapon(GetNormalName.get(KitInventory.getKit(p).getItem(1).getItemMeta().getDisplayName())));
                                p.getInventory().setItem(2, Main.CrackShotAPI.generateWeapon(GetNormalName.get(KitInventory.getKit(p).getItem(3).getItemMeta().getDisplayName())));
                            }
                            p.getInventory().setItem(19, KitInventory.getKit(p).getItem(2));
                            p.getInventory().setItem(25, KitInventory.getKit(p).getItem(4));
                            p.getInventory().setItem(3, KitInventory.getKit(p).getItem(5));
                            p.getInventory().setItem(4, KitInventory.getKit(p).getItem(6));
                            p.getInventory().setItem(5, KitInventory.getKit(p).getItem(7));

                            Color c = Color.fromRGB(0, 0, 255);
                            p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
                            p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
                            p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
                            p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));


                        } else {
                            return;
                        }
                    }
                }
            }
        }
    }

    private static ItemStack getColorArmor(Material m, Color c) {
        ItemStack i = new ItemStack(m, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta) i.getItemMeta();
        meta.setColor(c);
        i.setItemMeta(meta);
        return i;
    }

    public static Location getSpawn(Player p) {
        if (RedTeam.contains(p)) {
            return getArena.getRedSpawn(PickRandomArena.CurrentArena);
        } else if (BlueTeam.contains(p)) {
            return getArena.getBlueSpawn(PickRandomArena.CurrentArena);
        } else {
            return null;
        }
    }

    private static String getBetterTeam() {
		if (RedTeamScore > BlueTeamScore) {
			String team = "§cRed§4(§c" + (RedTeamScore - BlueTeamScore) + "§4)";
			return team;
		}else if (BlueTeamScore > RedTeamScore) {
			String team = "§9Blue§1(§9" + (BlueTeamScore - RedTeamScore) + "§1)";
			return team;
		}else{
			String team = "§eTie(0)";
			return team;
		}
    }

	public static void dropItem(PlayerDeathEvent e) {
    	Player p = e.getEntity();
    	ItemStack blueBlock = new ItemStack(Material.WOOL, 1, (byte) 11);
    	ItemStack redBlock = new ItemStack(Material.WOOL, 1, (byte) 14);
    	
        if (BlueTeam.contains(p)) {
            Location loc = p.getLocation();
            Item blueWool = p.getWorld().dropItem(loc, blueBlock);
            blueWool.setMetadata("codBlueTag", new FixedMetadataValue(ThisPlugin.getPlugin(), blueWool));
        }else if (RedTeam.contains(p)) {
        	Location loc = p.getLocation();
        	Item redWool = p.getWorld().dropItem(loc, redBlock);
        	redWool.setMetadata("codRedTag", new FixedMetadataValue(ThisPlugin.getPlugin(), redWool));
        }
    }
    
    public static void ItemPickupEvent(PlayerPickupItemEvent e) {
    	Player p = e.getPlayer();
    	Item item = e.getItem();
    	if (Main.PlayingPlayers.contains(p)) {
    		if (BaseArena.type == BaseArena.ArenaType.KC) {
    			if (BlueTeam.contains(p)) {
    				if (item.hasMetadata("codBlueTag")) {
    					e.setCancelled(true);
    					item.remove();
    					SendCoolMessages.sendTitle(p, "", 20, 40, 20);
    					SendCoolMessages.sendSubTitle(p, "§9§lKill Denied", 20, 40, 20);
    				}else if (item.hasMetadata("codRedTag")) {
    					e.setCancelled(true);
    					item.remove();
    					SendCoolMessages.sendTitle(p, "", 20, 40, 20);
    					SendCoolMessages.sendSubTitle(p, "§c§lKill Confirmed", 20, 40, 20);
    					BlueTeamScore = BlueTeamScore + 1;
    				}
    			}else if (RedTeam.contains(p)) {
    				if (item.hasMetadata("codBlueTag")) {
    					e.setCancelled(true);
    					item.remove();
    					SendCoolMessages.sendTitle(p, "", 20, 40, 20);
    					SendCoolMessages.sendSubTitle(p, "§9§lKill Confirmed", 20, 40, 20);
    					RedTeamScore = RedTeamScore + 1;
    				}else if (item.hasMetadata("codRedTag")) {
    					e.setCancelled(true);
    					item.remove();
    					SendCoolMessages.sendTitle(p, "", 20, 40, 20);
    					SendCoolMessages.sendSubTitle(p, "§c§lKill Denied", 20, 40, 20);
    				}
    			}
    		}
    	}
    }


    @SuppressWarnings("deprecation")
    public static void SpawnPlayer(Player p) {
        if (BaseArena.state == BaseArena.ArenaState.ENDING) {
            p.getInventory().clear();
            p.updateInventory();
            Main.invincible.add(p);

            if (Team.get(p).equalsIgnoreCase("Red")) {
                Color c = Color.fromRGB(255, 0, 0);
                p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
                p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
                p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
                p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
            } else if (Team.get(p).equalsIgnoreCase("Blue")) {
                Color c = Color.fromRGB(0, 0, 255);
                p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
                p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
                p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
                p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));
            }

            return;
        }

        if (Team.get(p).equalsIgnoreCase("Red")) {

            p.teleport(getArena.getRedSpawn(PickRandomArena.CurrentArena));

            p.closeInventory();
            p.getInventory().clear();
            p.setHealth(20);
            p.setFoodLevel(20);
            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 5));

            Color c = Color.fromRGB(255, 0, 0);
            p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
            p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
            p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
            p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));

            Main.GameKillStreakScore.get(p.getName()).setScore(0);

            p.getInventory().clear();

            p.getInventory().setItem(0, KitInventory.getKit(p).getItem(0));
            if (!(Main.CrackShot)) {
                p.getInventory().setItem(1, KitInventory.getKit(p).getItem(1));
                p.getInventory().setItem(2, KitInventory.getKit(p).getItem(3));
            } else {
                p.getInventory().setItem(1, Main.CrackShotAPI.generateWeapon(GetNormalName.get(KitInventory.getKit(p).getItem(1).getItemMeta().getDisplayName())));
                p.getInventory().setItem(2, Main.CrackShotAPI.generateWeapon(GetNormalName.get(KitInventory.getKit(p).getItem(3).getItemMeta().getDisplayName())));
            }
            p.getInventory().setItem(19, KitInventory.getKit(p).getItem(2));
            p.getInventory().setItem(25, KitInventory.getKit(p).getItem(4));
            p.getInventory().setItem(3, KitInventory.getKit(p).getItem(5));
            p.getInventory().setItem(4, KitInventory.getKit(p).getItem(6));
            p.getInventory().setItem(5, KitInventory.getKit(p).getItem(7));

        } else if (Team.get(p).equalsIgnoreCase("Blue")) {

            p.teleport(getArena.getBlueSpawn(PickRandomArena.CurrentArena));

            p.closeInventory();
            p.getInventory().clear();
            p.setHealth(20);
            p.setFoodLevel(20);
            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 5));

            Color c = Color.fromRGB(0, 0, 255);
            p.getInventory().setHelmet(getColorArmor(Material.LEATHER_HELMET, c));
            p.getInventory().setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, c));
            p.getInventory().setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, c));
            p.getInventory().setBoots(getColorArmor(Material.LEATHER_BOOTS, c));

            Main.GameKillStreakScore.get(p.getName()).setScore(0);

            p.getInventory().clear();

            p.getInventory().setItem(0, KitInventory.getKit(p).getItem(0));
            if (!(Main.CrackShot)) {
                p.getInventory().setItem(1, KitInventory.getKit(p).getItem(1));
                p.getInventory().setItem(2, KitInventory.getKit(p).getItem(3));
            } else {
                p.getInventory().setItem(1, Main.CrackShotAPI.generateWeapon(GetNormalName.get(KitInventory.getKit(p).getItem(1).getItemMeta().getDisplayName())));
                p.getInventory().setItem(2, Main.CrackShotAPI.generateWeapon(GetNormalName.get(KitInventory.getKit(p).getItem(3).getItemMeta().getDisplayName())));
            }
            p.getInventory().setItem(19, KitInventory.getKit(p).getItem(2));
            p.getInventory().setItem(25, KitInventory.getKit(p).getItem(4));
            p.getInventory().setItem(3, KitInventory.getKit(p).getItem(5));
            p.getInventory().setItem(4, KitInventory.getKit(p).getItem(6));
            p.getInventory().setItem(5, KitInventory.getKit(p).getItem(7));

        } else {
            return;
        }
    }

    public static void endKC() {
    	for (World w : Bukkit.getServer().getWorlds()) {
    		for (Entity e : w.getEntities()) {
    			if (e.hasMetadata("codRedTag")) e.remove();
    			if (e.hasMetadata("codBlueTag")) e.remove();
    		}
    	}
    	
        if (BlueTeamScore > RedTeamScore) {
            for (Player pp : Main.PlayingPlayers) {
                pp.sendMessage("");
                pp.sendMessage("");
                pp.sendMessage("");
                pp.sendMessage("§7║ §b§lStatistics:§6§l " + PickRandomArena.CurrentArena);
                pp.sendMessage("§7║");
                pp.sendMessage("§7║ §7§lWinner: §9§lBlue Team(" + (BlueTeamScore - RedTeamScore) + ")             §b§lTotal Kills:§a§l " + Main.GameKillsScore.get(pp.getName()).getScore());

                DecimalFormat df = new DecimalFormat("#.##");

                float kdr = ((float) Main.GameKillsScore.get(pp.getName()).getScore()) / ((float) Main.GameDeathsScore.get(pp.getName()).getScore());

                if (Main.GameDeathsScore.get(pp.getName()).getScore() == 0) {
                    pp.sendMessage("§7║" + " §b§lTotal Deaths:§a§l " + Main.GameDeathsScore.get(pp.getName()).getScore() + "       §b§lKDR:§a§l " + Main.GameKillsScore.get(pp.getName()).getScore());
                } else if (Main.GameKillsScore.get(pp.getName()).getScore() == 0) {
                    pp.sendMessage("§7║" + " §b§lTotal Deaths:§a§l " + Main.GameDeathsScore.get(pp.getName()).getScore() + "       §b§lKDR:§a§l " + Main.GameKillsScore.get(pp.getName()).getScore());
                } else {
                    pp.sendMessage("§7║" + " §b§lTotal Deaths:§a§l " + Main.GameDeathsScore.get(pp.getName()).getScore() + "       §b§lKDR:§a§l " + df.format(kdr));
                }

                pp.sendMessage("§7║");
                pp.sendMessage("§7╚§7§l════════════════════════════");
            }

            for (Player pp : Main.WaitingPlayers) {
                pp.sendMessage(Main.codSignature + "§bBlue §9team won!");
            }
        } else if (RedTeamScore > BlueTeamScore) {
            for (Player pp : Main.PlayingPlayers) {
                pp.sendMessage("");
                pp.sendMessage("");
                pp.sendMessage("");
                pp.sendMessage("§7║ §b§lStatistics:§6§l " + PickRandomArena.CurrentArena);
                pp.sendMessage("§7║");
                pp.sendMessage("§7║ §7§lWinner: §c§lRed Team(" + (RedTeamScore - BlueTeamScore) + ")             §b§lTotal Kills:§a§l " + Main.GameKillsScore.get(pp.getName()).getScore());

                DecimalFormat df = new DecimalFormat("#.##");

                float kdr = ((float) Main.GameKillsScore.get(pp.getName()).getScore()) / ((float) Main.GameDeathsScore.get(pp.getName()).getScore());

                if (Main.GameDeathsScore.get(pp.getName()).getScore() == 0) {
                    pp.sendMessage("§7║" + " §b§lTotal Deaths:§a§l " + Main.GameDeathsScore.get(pp.getName()).getScore() + "       §b§lKDR:§a§l " + Main.GameKillsScore.get(pp.getName()).getScore());
                } else if (Main.GameKillsScore.get(pp.getName()).getScore() == 0) {
                    pp.sendMessage("§7║" + " §b§lTotal Deaths:§a§l " + Main.GameDeathsScore.get(pp.getName()).getScore() + "       §b§lKDR:§a§l " + Main.GameKillsScore.get(pp.getName()).getScore());
                } else {
                    pp.sendMessage("§7║" + " §b§lTotal Deaths:§a§l " + Main.GameDeathsScore.get(pp.getName()).getScore() + "       §b§lKDR:§a§l " + df.format(kdr));
                }

                pp.sendMessage("§7║");
                pp.sendMessage("§7╚§7§l════════════════════════════");
            }

            for (Player pp : Main.WaitingPlayers) {
                pp.sendMessage(Main.codSignature + "§4Red §cteam won!");
            }
        } else {
            for (Player pp : Main.PlayingPlayers) {
                pp.sendMessage("");
                pp.sendMessage("");
                pp.sendMessage("");
                pp.sendMessage("§7║ §b§lStatistics:§6§l " + PickRandomArena.CurrentArena);
                pp.sendMessage("§7║");
                pp.sendMessage("§7║ §7§lWinner: §6§lTIE(0)             §b§lTotal Kills:§a§l " + Main.GameKillsScore.get(pp.getName()).getScore());

                DecimalFormat df = new DecimalFormat("#.##");

                float kdr = ((float) Main.GameKillsScore.get(pp.getName()).getScore()) / ((float) Main.GameDeathsScore.get(pp.getName()).getScore());

                if (Main.GameDeathsScore.get(pp.getName()).getScore() == 0) {
                    pp.sendMessage("§7║" + " §b§lTotal Deaths:§a§l " + Main.GameDeathsScore.get(pp.getName()).getScore() + "       §b§lKDR:§a§l " + Main.GameKillsScore.get(pp.getName()).getScore());
                } else if (Main.GameKillsScore.get(pp.getName()).getScore() == 0) {
                    pp.sendMessage("§7║" + " §b§lTotal Deaths:§a§l " + Main.GameDeathsScore.get(pp.getName()).getScore() + "       §b§lKDR:§a§l " + Main.GameKillsScore.get(pp.getName()).getScore());
                } else {
                    pp.sendMessage("§7║" + " §b§lTotal Deaths:§a§l " + Main.GameDeathsScore.get(pp.getName()).getScore() + "       §b§lKDR:§a§l " + df.format(kdr));
                }

                pp.sendMessage("§7║");
                pp.sendMessage("§7╚§7§l════════════════════════════");
            }

            for (Player pp : Main.WaitingPlayers) {
                pp.sendMessage(Main.codSignature + "§eTie! §6Nobody won");
            }
        }
    }
}

