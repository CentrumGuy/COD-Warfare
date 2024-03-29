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
 
public class AvailableGunsFile {
 
        private AvailableGunsFile() { }
       
        static AvailableGunsFile instance = new AvailableGunsFile();
       
        public static AvailableGunsFile getInstance() {
                return instance;
        }
       
        static Plugin p;
       
        static FileConfiguration availableGuns;
        static File aGfile;
       
        public static void setup(Plugin p) {
	          if (!(MySQL.mySQLenabled())) {
		            if (!p.getDataFolder().exists()) {
		                    p.getDataFolder().mkdir();
		            }
		           
		            aGfile = new File(p.getDataFolder(), "AvailableGuns.yml");
		           
		            if (!aGfile.exists()) {
		                    try {
		                            aGfile.createNewFile();
		                    }
		                    catch (IOException e) {
		                            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create AvailableGuns.yml!");
		                    }
		            }
		           
		            availableGuns = YamlConfiguration.loadConfiguration(aGfile);
	          }else{
	        	  try {
		        	MySQL.createAPGTable();
					MySQL.createASGTable();
				} catch (Exception e) {
					e.printStackTrace();
				}
	          }
        }
       
        public static FileConfiguration getData() {
                return availableGuns;
        }
       
        public static void saveData() {
        	if (!(MySQL.mySQLenabled())) {
                try {
                	availableGuns.save(aGfile);
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save AvailableGuns.yml!");
                }
        	}
        }
       
        public static void reloadData() {
        	if (!(MySQL.mySQLenabled())) {
        		availableGuns = YamlConfiguration.loadConfiguration(aGfile);
        	}
        }
       
        public static PluginDescriptionFile getDesc() {
                return p.getDescription();
        }
}