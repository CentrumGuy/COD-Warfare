package com.CentrumGuy.CodWarfare.Achievements;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.CentrumGuy.CODWeapons.API.Items;

public class Achievement {
	String name = "";
	String description = "";
	AchievementType type = null;
	Integer reward;
	Boolean nameOnSecondLine = false;
	public static HashMap<AchievementType, Achievement> achievementsForTypes = new HashMap<AchievementType, Achievement>();
	public static ArrayList<Achievement> achievements = new ArrayList<Achievement>();
	
	public Achievement(String Name, String Description, AchievementType Type, Integer Reward, Boolean NameOnSecondLine) {
		name = Name;
		description = Description;
		type = Type;
		reward = Reward;
		nameOnSecondLine = NameOnSecondLine;
		achievementsForTypes.put(type, this);
		achievements.add(this);
	}
	
	public ItemStack getUnlockedAchievementItem() {
		ArrayList<String> desc = new ArrayList<String>();
		if (nameOnSecondLine) desc.add("§a§l§n" + name);
		desc.add("");
		desc.add("§2§oUnlocked");
		desc.add("§f" + description);
		desc.add("§aRewarded§2 " + reward + " §acredits");
		
		if (nameOnSecondLine == false) {
			return Items.createItem(Material.INK_SACK, 1, (byte) 10, "§f§lAchievement: §a§l§n" + name, desc);
		}else{
			return Items.createItem(Material.INK_SACK, 1, (byte) 10, "§f§lAchievement:", desc);
		}
	}
	
	public ItemStack getLockedAchievementItem() {
		ArrayList<String> desc = new ArrayList<String>();
		if (nameOnSecondLine) desc.add("§7§l§n" + name);
		desc.add("");
		desc.add("§8§oLocked");
		desc.add("§7" + description);
		desc.add("§7Reward:§f " + reward + " §7credits");
		
		if (nameOnSecondLine == false) {
			return Items.createItem(Material.INK_SACK, 1, (byte) 8, "§f§lAchievement: §7§l§n" + name, desc);
		}else{
			return Items.createItem(Material.INK_SACK, 1, (byte) 8, "§f§lAchievement:", desc);
		}
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getName() {
		return name;
	}
	
	public AchievementType getType() {
		return type;
	}
	
	public Integer getReward() {
		return reward;
	}
	
	public Boolean getNameOnSecondLine() {
		return nameOnSecondLine;
	}
	
	public Achievement setDescription(String Description) {
		description = Description;
		return this;
	}
	
	public Achievement setName(String Name) {
		name = Name;
		return this;
	}
	
	public Achievement setType(AchievementType Type) {
		type = Type;
		return this;
	}
	
	public Achievement setReward(Integer Reward) {
		reward = Reward;
		return this;
	}
	
	public Achievement setNameOnSecondLine(Boolean nosl) {
		nameOnSecondLine = nosl;
		return this;
	}
	
	public static ArrayList<Achievement> getAchievements() {
		return achievements;
	}
	
	public static Achievement getAchievementForType(AchievementType Atype) {
		return achievementsForTypes.get(Atype);
	}
}
