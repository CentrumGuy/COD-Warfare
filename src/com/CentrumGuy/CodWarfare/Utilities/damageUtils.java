package com.CentrumGuy.CodWarfare.Utilities;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.CentrumGuy.CODWeapons.Utilities.ParticleEffect;
import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.SpecialWeapons.ElectroMagneticPulse;
import com.CentrumGuy.CodWarfare.SpecialWeapons.GetPlayersOnOtherTeam;

public class damageUtils {
	public static String getUncoloredName(String s) {
		s = StringUtils.remove(s, "§e");
		return s;
	}
	
	public static void nameAll(Material m, Player p, String name) {
		for (int i = 0 ; i < p.getInventory().getSize() ; i++) {
			if (p.getInventory().getItem(i) == null) continue;
			if (p.getInventory().getItem(i).getType() == m) {
				//if (p.getInventory().getItem(i).hasItemMeta()) {
				if (p.getInventory().getItem(i).equals(ElectroMagneticPulse.EMP)) continue;
					ItemMeta im = p.getInventory().getItem(i).getItemMeta();
					im.setDisplayName(name);
					p.getInventory().getItem(i).setItemMeta(im);
				//}
			}
		}
	}
	
	public static ItemStack getItem(Material m, String WeaponName) {
		String name = "§e" + WeaponName;
		return Items.createItem(m, 1, 0, name);
	}
	
	public static void RemoveArmor(Player player) {
	    player.getInventory().setHelmet(null);
	    player.getInventory().setChestplate(null);
	    player.getInventory().setLeggings(null);
	    player.getInventory().setBoots(null);
	}
	
	@SuppressWarnings("deprecation")
	public static void damage(LivingEntity damager, LivingEntity damagee, int damage) {
		if (damager instanceof Player) {
			Player p = (Player) damager;
			if (!(p.isOnline())) return;
		}
		
		if (damagee.getHealth() <= 0) return;
		
		EntityDamageByEntityEvent e = new EntityDamageByEntityEvent(damager, damagee, DamageCause.ENTITY_ATTACK, damage);
		Bukkit.getServer().getPluginManager().callEvent(e);
		if (e.isCancelled()) return;

		double damage1 = damagee.getHealth() - damage;
		double damage2 = damage1 + 1;
		double endHealth = damage1;
		
		if (endHealth <= 0) {
			ItemStack[] armor = null;
			if (damagee instanceof Player) {
				Player p = (Player) damagee;
				armor = p.getInventory().getArmorContents();
				RemoveArmor(p);
				p.updateInventory();
			}
			
			damagee.setHealth(1);
			damagee.damage(300, damager);
			if (damagee instanceof Player) {
				Player p = (Player) damagee;
				p.getInventory().setArmorContents(armor);
				p.updateInventory();
			}
		}else{
			ItemStack[] armor = null;
			if (damagee instanceof Player) {
				Player p = (Player) damagee;
				armor = p.getInventory().getArmorContents();
				RemoveArmor(p);
				p.updateInventory();
			}
			
			damagee.setHealth(damage2);
			damagee.damage(1, damager);
			
			if (damagee instanceof Player) {
				Player p = (Player) damagee;
				p.getInventory().setArmorContents(armor);
				p.updateInventory();
			}
		}	
	}
	
	public static void createExplosion(Location center, Integer radius, Player exploder) {
		float particleRadius = radius / 3;
		if (!(Bukkit.getServer().getOnlinePlayers().isEmpty())) ParticleEffect.EXPLOSION_LARGE.display(particleRadius, particleRadius, particleRadius, 0, radius, center);
		
		double circles = radius / 10.000000000000;
		for (LivingEntity e : center.getWorld().getLivingEntities()) {
			
			if (center.distance(e.getLocation()) <= circles) {
				if (!(e.equals(exploder))) damage(exploder, e, 20);
				if (e instanceof Player) {
					Player p = (Player) e;
					p.playSound(p.getLocation(), Sound.EXPLODE, 1F, 1F);
				}
			}else if (center.distance(e.getLocation()) <= 2 * circles) {
				if (!(e.equals(exploder))) damage(exploder, e, 18);
				if (e instanceof Player) {
					Player p = (Player) e;
					p.playSound(p.getLocation(), Sound.EXPLODE, 0.9F, 1F);
				}
			}else if (center.distance(e.getLocation()) <= 3 * circles) {
				if (!(e.equals(exploder))) damage(exploder, e, 16);
				if (e instanceof Player) {
					Player p = (Player) e;
					p.playSound(p.getLocation(), Sound.EXPLODE, 0.8F, 1F);
				}
			}else if (center.distance(e.getLocation()) <= 4 * circles) {
				if (!(e.equals(exploder))) damage(exploder, e, 14);
				if (e instanceof Player) {
					Player p = (Player) e;
					p.playSound(p.getLocation(), Sound.EXPLODE, 0.7F, 1F);
				}
			}else if (center.distance(e.getLocation()) <= 5 * circles) {
				if (!(e.equals(exploder))) damage(exploder, e, 12);
				if (e instanceof Player) {
					Player p = (Player) e;
					p.playSound(p.getLocation(), Sound.EXPLODE, 0.6F, 1F);
				}
			}else if (center.distance(e.getLocation()) <= 6 * circles) {
				if (!(e.equals(exploder))) damage(exploder, e, 10);
				if (e instanceof Player) {
					Player p = (Player) e;
					p.playSound(p.getLocation(), Sound.EXPLODE, 0.5F, 1F);
				}
			}else if (center.distance(e.getLocation()) <= 7 * circles) {
				if (!(e.equals(exploder))) damage(exploder, e, 8);
				if (e instanceof Player) {
					Player p = (Player) e;
					p.playSound(p.getLocation(), Sound.EXPLODE, 0.4F, 1F);
				}
			}else if (center.distance(e.getLocation()) <= 8 * circles) {
				if (!(e.equals(exploder))) damage(exploder, e, 6);
				if (e instanceof Player) {
					Player p = (Player) e;
					p.playSound(p.getLocation(), Sound.EXPLODE, 0.3F, 1F);
				}
			}else if (center.distance(e.getLocation()) <= 9 * circles) {
				if (!(e.equals(exploder))) damage(exploder, e, 4);
				if (e instanceof Player) {
					Player p = (Player) e;
					p.playSound(p.getLocation(), Sound.EXPLODE, 0.2F, 1F);
				}
			}else if (center.distance(e.getLocation()) <= 10 * circles) {
				if (!(e.equals(exploder))) damage(exploder, e, 2);
				if (e instanceof Player) {
					Player p = (Player) e;
					p.playSound(p.getLocation(), Sound.EXPLODE, 0.1F, 1F);
				}
			}
		}
	}
	
	public static void createCODPlayerExplosion(Location center, Integer radius, Player exploder) {
		float particleRadius = radius / 3;
		if (!(Bukkit.getServer().getOnlinePlayers().isEmpty())) ParticleEffect.EXPLOSION_LARGE.display(particleRadius, particleRadius, particleRadius, 0, radius, center);
		
		double circles = radius / 10.00000000000000;
		for (LivingEntity e : center.getWorld().getLivingEntities()) {
			if (e instanceof Player) {
				Player p = (Player) e;
				
				if (Main.PlayingPlayers.contains(p)) {
					if (GetPlayersOnOtherTeam.get(exploder).contains(p)) {
						if (center.distance(p.getLocation()) <= circles) {
							if (!(e.equals(exploder))) damage(exploder, p, 20);
							p.playSound(p.getLocation(), Sound.EXPLODE, 1F, 1F);
						}else if (center.distance(p.getLocation()) <= 2 * circles) {
							if (!(e.equals(exploder))) damage(exploder, p, 18);
							p.playSound(p.getLocation(), Sound.EXPLODE, 0.9F, 1F);
						}else if (center.distance(p.getLocation()) <= 3 * circles) {
							if (!(e.equals(exploder))) damage(exploder, p, 16);
							p.playSound(p.getLocation(), Sound.EXPLODE, 0.8F, 1F);
						}else if (center.distance(p.getLocation()) <= 4 * circles) {
							if (!(e.equals(exploder))) damage(exploder, p, 14);
							p.playSound(p.getLocation(), Sound.EXPLODE, 0.7F, 1F);
						}else if (center.distance(p.getLocation()) <= 5 * circles) {
							if (!(e.equals(exploder))) damage(exploder, p, 12);
							p.playSound(p.getLocation(), Sound.EXPLODE, 0.6F, 1F);
						}else if (center.distance(p.getLocation()) <= 6 * circles) {
							if (!(e.equals(exploder))) damage(exploder, p, 10);
							p.playSound(p.getLocation(), Sound.EXPLODE, 0.5F, 1F);
						}else if (center.distance(p.getLocation()) <= 7 * circles) {
							if (!(e.equals(exploder))) damage(exploder, p, 8);
							p.playSound(p.getLocation(), Sound.EXPLODE, 0.4F, 1F);
						}else if (center.distance(p.getLocation()) <= 8 * circles) {
							if (!(e.equals(exploder))) damage(exploder, p, 6);
							p.playSound(p.getLocation(), Sound.EXPLODE, 0.3F, 1F);
						}else if (center.distance(p.getLocation()) <= 9 * circles) {
							if (!(e.equals(exploder))) damage(exploder, p, 4);
							p.playSound(p.getLocation(), Sound.EXPLODE, 0.2F, 1F);
						}else if (center.distance(p.getLocation()) <= 10 * circles) {
							if (!(e.equals(exploder))) damage(exploder, p, 2);
							p.playSound(p.getLocation(), Sound.EXPLODE, 0.1F, 1F);
						}
					}
				}
			}
		}
	}
	
	public static void createBlindExplosion(Location center, Integer radius, Player exploder) {
		float particleRadius = radius / 3;
		if (!(Bukkit.getServer().getOnlinePlayers().isEmpty())) ParticleEffect.EXPLOSION_LARGE.display(particleRadius, particleRadius, particleRadius, 0, radius, center);
		
		for (LivingEntity e : center.getWorld().getLivingEntities()) {
			if (center.distance(e.getLocation()) <= radius) {
				if (!(e.equals(exploder))) e.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 1));
				if (e instanceof Player) {
					Player p = (Player) e;
					p.playSound(p.getLocation(), Sound.EXPLODE, 1F, 1F);
				}
			}else if (center.distance(e.getLocation()) <= radius * 2) {
				if (e instanceof Player) {
					Player p = (Player) e;
					p.playSound(p.getLocation(), Sound.EXPLODE, 0.5F, 1F);
				}
			}
		}
	}
	
	public static void createCODPlayerBlindExplosion(Location center, Integer radius, Player exploder) {
		float particleRadius = radius / 3;
		if (!(Bukkit.getServer().getOnlinePlayers().isEmpty())) ParticleEffect.EXPLOSION_LARGE.display(particleRadius, particleRadius, particleRadius, 0, radius, center);
		
		for (LivingEntity e : center.getWorld().getLivingEntities()) {
			if (e instanceof Player) {
				Player p = (Player) e;
				
				if (Main.PlayingPlayers.contains(p)) {
					if (GetPlayersOnOtherTeam.get(exploder).contains(p)) {
						if (center.distance(p.getLocation()) <= radius) {
							if (!(e.equals(exploder))) p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 1));
							p.playSound(p.getLocation(), Sound.EXPLODE, 1F, 1F);
						}else if (center.distance(p.getLocation()) <= radius * 2) {
							p.playSound(p.getLocation(), Sound.EXPLODE, 0.5F, 1F);
						}
					}
				}
			}
		}
	}
	
	public static void createFireExplosion(Location center, Integer radius, Player exploder) {
		float particleRadius = radius / 3;
		if (!(Bukkit.getServer().getOnlinePlayers().isEmpty())) ParticleEffect.EXPLOSION_LARGE.display(particleRadius, particleRadius, particleRadius, 0, radius, center);
		float radiusFlame = radius / 3;
		if (!(Bukkit.getServer().getOnlinePlayers().isEmpty())) ParticleEffect.FLAME.display(radiusFlame, radiusFlame, radiusFlame, 0, radius * 100, center);
		
		for (LivingEntity e : center.getWorld().getLivingEntities()) {
			if (center.distance(e.getLocation()) <= radius) {
				if (!(e.equals(exploder))) {
					Damage.damage(exploder, e, 2);
					e.setFireTicks(140);
				}
				if (e instanceof Player) {
					Player p = (Player) e;
					p.playSound(p.getLocation(), Sound.EXPLODE, 1F, 1F);
				}
			}else if (center.distance(e.getLocation()) <= radius * 2) {
				if (e instanceof Player) {
					Player p = (Player) e;
					p.playSound(p.getLocation(), Sound.EXPLODE, 0.5F, 1F);
				}
			}
		}
	}
	
	public static void createCODPlayerFireExplosion(Location center, Integer radius, Player exploder) {
		float particleRadius = radius / 3;
		if (!(Bukkit.getServer().getOnlinePlayers().isEmpty())) ParticleEffect.EXPLOSION_LARGE.display(particleRadius, particleRadius, particleRadius, 0, radius, center);
		float radiusFlame = radius / 3;
		if (!(Bukkit.getServer().getOnlinePlayers().isEmpty())) ParticleEffect.FLAME.display(radiusFlame, radiusFlame, radiusFlame, 0, radius * 100, center);
		
		for (LivingEntity e : center.getWorld().getLivingEntities()) {
			if (e instanceof Player) {
				Player p = (Player) e;
				
				if (Main.PlayingPlayers.contains(p)) {
					if (GetPlayersOnOtherTeam.get(exploder).contains(p)) {
						if (center.distance(p.getLocation()) <= radius) {
							if (!(e.equals(exploder))) {
								Damage.damage(exploder, p, 2);
								p.setFireTicks(140);
							}
							
							p.playSound(p.getLocation(), Sound.EXPLODE, 1F, 1F);
						}else if (center.distance(p.getLocation()) <= radius * 2) {
							p.playSound(p.getLocation(), Sound.EXPLODE, 0.5F, 1F);
						}
					}
				}
			}
		}
	}
}
