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
 
public class ShopFile {
 
        private ShopFile() { }
       
        static ShopFile instance = new ShopFile();
       
        public static ShopFile getInstance() {
                return instance;
        }
       
        static Plugin p;
       
        static FileConfiguration Shops;
        static File sfile;
       
        public static void setup(Plugin p) {
        	if (!(MySQL.mySQLenabled())) {
                if (!p.getDataFolder().exists()) {
                        p.getDataFolder().mkdir();
                }
               
                sfile = new File(p.getDataFolder(), "Shops.yml");
               
                if (!sfile.exists()) {
                        try {
                                sfile.createNewFile();
                        }
                        catch (IOException e) {
                                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create Shops.yml!");
                        }
                }
               
                Shops = YamlConfiguration.loadConfiguration(sfile);
                
        	}else{
            	try {
					MySQL.createSecondaryShopTable();
					MySQL.createPrimaryShopTable();
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        }
       
        public static FileConfiguration getData() {
                return Shops;
        }
       
        public static void saveData() {
        	if (!(MySQL.mySQLenabled())) {
                try {
                	Shops.save(sfile);
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save Shops.yml!");
                }
        	}
        }
       
        public static void reloadData() {
        	if (!(MySQL.mySQLenabled())) {
        		Shops = YamlConfiguration.loadConfiguration(sfile);
        	}
        }
       
        public static PluginDescriptionFile getDesc() {
                return p.getDescription();
        }
}