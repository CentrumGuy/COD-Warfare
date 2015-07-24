package com.CentrumGuy.CodWarfare.Utilities;

import org.apache.commons.lang.StringUtils;

public class GetNormalName {

	public static String get(String gunName) {
		gunName = StringUtils.remove(gunName, "§0");
		gunName = StringUtils.remove(gunName, "&0");
		gunName = StringUtils.remove(gunName, "§1");
		gunName = StringUtils.remove(gunName, "&1");
		gunName = StringUtils.remove(gunName, "§2");
		gunName = StringUtils.remove(gunName, "&2");
		gunName = StringUtils.remove(gunName, "§3");
		gunName = StringUtils.remove(gunName, "&3");
		gunName = StringUtils.remove(gunName, "§4");
		gunName = StringUtils.remove(gunName, "&4");
		gunName = StringUtils.remove(gunName, "§5");
		gunName = StringUtils.remove(gunName, "&5");
		gunName = StringUtils.remove(gunName, "§6");
		gunName = StringUtils.remove(gunName, "&6");
		gunName = StringUtils.remove(gunName, "§7");
		gunName = StringUtils.remove(gunName, "&7");
		gunName = StringUtils.remove(gunName, "§8");
		gunName = StringUtils.remove(gunName, "&8");
		gunName = StringUtils.remove(gunName, "§9");
		gunName = StringUtils.remove(gunName, "&9");
		gunName = StringUtils.remove(gunName, "§a");
		gunName = StringUtils.remove(gunName, "&a");
		gunName = StringUtils.remove(gunName, "§b");
		gunName = StringUtils.remove(gunName, "&b");
		gunName = StringUtils.remove(gunName, "§c");
		gunName = StringUtils.remove(gunName, "&c");
		gunName = StringUtils.remove(gunName, "§d");
		gunName = StringUtils.remove(gunName, "&d");
		gunName = StringUtils.remove(gunName, "§e");
		gunName = StringUtils.remove(gunName, "&e");
		gunName = StringUtils.remove(gunName, "§f");
		gunName = StringUtils.remove(gunName, "&f");
		gunName = StringUtils.remove(gunName, "§l");
		gunName = StringUtils.remove(gunName, "&l");
		gunName = StringUtils.remove(gunName, "§k");
		gunName = StringUtils.remove(gunName, "&k");
		gunName = StringUtils.remove(gunName, "§m");
		gunName = StringUtils.remove(gunName, "&m");
		gunName = StringUtils.remove(gunName, "§n");
		gunName = StringUtils.remove(gunName, "&n");
		gunName = StringUtils.remove(gunName, "§o");
		gunName = StringUtils.remove(gunName, "&o");
		gunName = StringUtils.remove(gunName, "§r");
		gunName = StringUtils.remove(gunName, "&r");
		
		return gunName;
	}
}
