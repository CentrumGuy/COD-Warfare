package com.CentrumGuy.CodWarfare.Utilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class Damage {
	@SuppressWarnings("deprecation")
	public static void damage(LivingEntity damager, LivingEntity damagee, double damage) {
		EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(damager, damagee, DamageCause.ENTITY_ATTACK, damage);
		Bukkit.getServer().getPluginManager().callEvent(event);
		if (event.isCancelled()) return;
		if (event.getDamage() != damage) damage = event.getDamage();
		if (damage <= 1) {
			damagee.damage(1, damager);
		}else if (damage >= damagee.getHealth()) {
			damagee.setHealth(1);
			damagee.damage(200, damager);
		}else{
			double damageChanged = damage - 1;
			damagee.damage(damageChanged);
			damagee.damage(3, damager);
		}
	}
}
