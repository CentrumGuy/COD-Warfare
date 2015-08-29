package com.CentrumGuy.CodWarfare.Utilities;

import org.bukkit.Material;

import com.CentrumGuy.CODWeapons.Weapons.C4;
import com.CentrumGuy.CODWeapons.Weapons.Flashbang;
import com.CentrumGuy.CODWeapons.Weapons.Grenade;
import com.CentrumGuy.CODWeapons.Weapons.Molotov;
import com.CentrumGuy.CODWeapons.Weapons.Tomahawk;
import com.CentrumGuy.CodWarfare.Main;

public class weaponItems {
	public static boolean sameID(Material itemID) {
		if (!(Main.weapons)) return false;
		if (Grenade.getGrenadeItem().getType().equals(itemID)) return true;
		if (Tomahawk.getTomahawkItem().getType().equals(itemID)) return true;
		if (C4.getC4Block().getType().equals(itemID)) return true;
		if (Flashbang.getFlashbangItem().getType().equals(itemID)) return true;
		if (Molotov.getMolotovItem().getType().equals(itemID)) return true;
		return false;
	}
}
