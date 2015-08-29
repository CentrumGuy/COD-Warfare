package com.CentrumGuy.CodWarfare.Files;
 
import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import com.CentrumGuy.CodWarfare.MySQL.MySQL;
 
public class PerksFile {
 
        private PerksFile() { }
       
        static PerksFile instance = new PerksFile();
       
        public static PerksFile getInstance() {
                return instance;
        }
       
        static Plugin p;
       
        static FileConfiguration perks;
        static File pfile;
       
        public static void setup(Plugin p) {
        	if (!(MySQL.mySQLenabled())) {
                if (!p.getDataFolder().exists()) {
                        p.getDataFolder().mkdir();
                }
               
                pfile = new File(p.getDataFolder(), "perks.yml");
               
                if (!pfile.exists()) {
                        try {
                                pfile.createNewFile();
                        }
                        catch (IOException e) {
                                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create perks.yml!");
                        }
                }
               
                perks = YamlConfiguration.loadConfiguration(pfile);
                
                perks.addDefault("Perks.Cost.SPEED", 500);
                perks.addDefault("Perks.Cost.MARATHON", 450);
                perks.addDefault("Perks.Cost.SCAVENGER", 700);
                perks.addDefault("Perks.Cost.HARDLINE", 300);
                perks.options().copyDefaults(true);
                saveData();
        	}else{
        		try {
        			MySQL.createPerksTable();
        		}catch (Exception e) {
        			e.printStackTrace();
        		}
        	}
        }
       
        public static FileConfiguration getData() {
                return perks;
        }
       
        public static void saveData() {
        	if (!(MySQL.mySQLenabled())) {
                try {
                        perks.save(pfile);
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save perks.yml!");
                }
        	}
        }
       
        public static void reloadData() {
        	if (!(MySQL.mySQLenabled())) {
                perks = YamlConfiguration.loadConfiguration(pfile);
        	}
        }
       
        public static PluginDescriptionFile getDesc() {
                return p.getDescription();
        }
}