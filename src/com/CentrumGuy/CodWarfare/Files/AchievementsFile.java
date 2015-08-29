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
 
public class AchievementsFile {
 
        private AchievementsFile() { }
       
        static AchievementsFile instance = new AchievementsFile();
       
        public static AchievementsFile getInstance() {
                return instance;
        }
       
        static Plugin p;
       
        static FileConfiguration achivements;
        static File afile;
       
        public static void setup(Plugin p) {
            if (!(MySQL.mySQLenabled())) {
                if (!p.getDataFolder().exists()) {
                        p.getDataFolder().mkdir();
                }
               
                afile = new File(p.getDataFolder(), "Achivements.yml");
               
                if (!afile.exists()) {
                        try {
                                afile.createNewFile();
                        }
                        catch (IOException e) {
                                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create Achivements.yml!");
                        }
                }
               
                achivements = YamlConfiguration.loadConfiguration(afile);
            }else{
            	try {
            		MySQL.createAchievementsTable();
            	}catch (Exception e) {
            		e.printStackTrace();
            	}
            }
        }
       
        public static FileConfiguration getData() {
                return achivements;
        }
       
        public static void saveData() {
        	if (!(MySQL.mySQLenabled())) {
                try {
                        achivements.save(afile);
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save Achivements.yml!");
                }
        	}
        }
       
        public static void reloadData() {
        	if (!(MySQL.mySQLenabled())) {
                achivements = YamlConfiguration.loadConfiguration(afile);
        	}
        }
       
        public static PluginDescriptionFile getDesc() {
                return p.getDescription();
        }
}