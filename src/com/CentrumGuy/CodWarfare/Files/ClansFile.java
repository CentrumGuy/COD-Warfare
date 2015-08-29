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
 
public class ClansFile {
 
        private ClansFile() { }
       
        static ClansFile instance = new ClansFile();
       
        public static ClansFile getInstance() {
                return instance;
        }
       
        static Plugin p;
       
        static FileConfiguration clans;
        static File cfile;
       
        public static void setup(Plugin p) {
            if (!(MySQL.mySQLenabled())) {
                if (!p.getDataFolder().exists()) {
                        p.getDataFolder().mkdir();
                }
               
                cfile = new File(p.getDataFolder(), "clans.yml");
               
                if (!cfile.exists()) {
                        try {
                                cfile.createNewFile();
                        }
                        catch (IOException e) {
                                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create clans.yml!");
                        }
                }
               
                clans = YamlConfiguration.loadConfiguration(cfile);
            }else{
            	try {
					MySQL.createClanTable();
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        }
       
        public static FileConfiguration getData() {
                return clans;
        }
       
        public static void saveData() {
        	if (!(MySQL.mySQLenabled())) {
                try {
                        clans.save(cfile);
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save clans.yml!");
                }
        	}
        }
       
        public static void reloadData() {
        	if (!(MySQL.mySQLenabled())) {
                clans = YamlConfiguration.loadConfiguration(cfile);
        	}
        }
       
        public static PluginDescriptionFile getDesc() {
                return p.getDescription();
        }
}