package com.CentrumGuy.CodWarfare.Utilities;

import org.bukkit.ChatColor;

import com.CentrumGuy.CodWarfare.Main;
import com.CentrumGuy.CodWarfare.Files.LangFile;

public class Language {

	public static String getString(String path, Boolean sign) {
		String message = LangFile.getData().getString(path);
		if (message == null) message = "";
		
		String coloredText = ChatColor.translateAlternateColorCodes('&', message);
		if (sign) coloredText = Main.codSignature + coloredText;
		
		
		return coloredText;
	}
}