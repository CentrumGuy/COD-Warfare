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
 
public class KitFile {
 
        private KitFile() { }
       
        static KitFile instance = new KitFile();
       
        public static KitFile getInstance() {
                return instance;
        }
       
        static Plugin p;
       
        static FileConfiguration Kits;
        static File kfile;
       
        public static void setup(Plugin p) {
             if (!(MySQL.mySQLenabled())) {
                if (!p.getDataFolder().exists()) {
                        p.getDataFolder().mkdir();
                }
               
                kfile = new File(p.getDataFolder(), "Kits.yml");
               
                if (!kfile.exists()) {
                        try {
                                kfile.createNewFile();
                        }
                        catch (IOException e) {
                                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create arenas.yml!");
                        }
                }
               
                Kits = YamlConfiguration.loadConfiguration(kfile);
             }else{
            	 try {
            		 MySQL.createKitsTable();
            	 }catch (Exception e) {
            		 e.printStackTrace();
            	 }
             }
        }
       
        public static FileConfiguration getData() {
                return Kits;
        }
       
        public static void saveData() {
        	if (!(MySQL.mySQLenabled())) {
                try {
                	Kits.save(kfile);
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save arenas.yml!");
                }
        	}
        }
       
        public static void reloadData() {
        	if (!(MySQL.mySQLenabled())) {
        		Kits = YamlConfiguration.loadConfiguration(kfile);
        	}
        }
       
        public static PluginDescriptionFile getDesc() {
                return p.getDescription();
        }
}