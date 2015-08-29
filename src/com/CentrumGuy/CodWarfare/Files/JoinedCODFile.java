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
 
public class JoinedCODFile {
 
        private JoinedCODFile() { }
       
        static JoinedCODFile instance = new JoinedCODFile();
       
        public static JoinedCODFile getInstance() {
                return instance;
        }
       
        static Plugin p;
       
        static FileConfiguration joined;
        static File jfile;
       
        public static void setup(Plugin p) {
            if (!(MySQL.mySQLenabled())) {
                if (!p.getDataFolder().exists()) {
                        p.getDataFolder().mkdir();
                }
               
                jfile = new File(p.getDataFolder(), "joined.yml");
               
                if (!jfile.exists()) {
                        try {
                                jfile.createNewFile();
                        }
                        catch (IOException e) {
                                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create joined.yml!");
                        }
                }
               
                joined = YamlConfiguration.loadConfiguration(jfile);
            }else{
            	try {
					MySQL.createJoinedTable();
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        }
       
        public static FileConfiguration getData() {
                return joined;
        }
       
        public static void saveData() {
            if (!(MySQL.mySQLenabled())) {
                try {
                        joined.save(jfile);
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save joined.yml!");
                }
            }
        }
       
        public static void reloadData() {
            if (!(MySQL.mySQLenabled())) {
                joined = YamlConfiguration.loadConfiguration(jfile);
            }
        }
       
        public static PluginDescriptionFile getDesc() {
                return p.getDescription();
        }
}