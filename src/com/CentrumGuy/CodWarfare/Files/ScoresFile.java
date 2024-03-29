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
 
public class ScoresFile {
 
        private ScoresFile() { }
       
        static ScoresFile instance = new ScoresFile();
       
        public static ScoresFile getInstance() {
                return instance;
        }
       
        static Plugin p;
       
        static FileConfiguration Scores;
        static File sfile;
       
        public static void setup(Plugin p) {
            if (!(MySQL.mySQLenabled())) {
                if (!p.getDataFolder().exists()) {
                        p.getDataFolder().mkdir();
                }
               
                sfile = new File(p.getDataFolder(), "Scores.yml");
               
                if (!sfile.exists()) {
                        try {
                                sfile.createNewFile();
                        }
                        catch (IOException e) {
                                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create Scores.yml!");
                        }
                }
               
                Scores = YamlConfiguration.loadConfiguration(sfile);
            }else{
            	try {
            		MySQL.createScoresTable();
            	}catch (Exception e) {
            		e.printStackTrace();
            	}
            }
        }
       
        public static FileConfiguration getData() {
                return Scores;
        }
       
        public static void saveData() {
        	if (!(MySQL.mySQLenabled())) {
                try {
                        Scores.save(sfile);
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save Scores.yml!");
                }
        	}
        }
       
        public static void reloadData() {
        	if (!(MySQL.mySQLenabled())) {
                Scores = YamlConfiguration.loadConfiguration(sfile);
        	}
        }
       
        public static PluginDescriptionFile getDesc() {
                return p.getDescription();
        }
}