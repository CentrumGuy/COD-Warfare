package com.CentrumGuy.CodWarfare.Files;
 
import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
 
public class WeaponsFile {
 
        private WeaponsFile() { }
       
        static WeaponsFile instance = new WeaponsFile();
       
        public static WeaponsFile getInstance() {
                return instance;
        }
       
        static Plugin p;
       
        static FileConfiguration Weapons;
        static File wfile;
       
        public static void setup(Plugin p) {
               
                if (!p.getDataFolder().exists()) {
                        p.getDataFolder().mkdir();
                }
               
                wfile = new File(p.getDataFolder(), "Weapons.yml");
               
                if (!wfile.exists()) {
                        try {
                                wfile.createNewFile();
                        }
                        catch (IOException e) {
                                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create Weapons.yml!");
                        }
                }
               
                Weapons = YamlConfiguration.loadConfiguration(wfile);
        }
       
        public static FileConfiguration getData() {
                return Weapons;
        }
       
        public static void saveData() {
                try {
                        Weapons.save(wfile);
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save Weapons.yml!");
                }
        }
       
        public static void reloadData() {
                Weapons = YamlConfiguration.loadConfiguration(wfile);
        }
       
        public static PluginDescriptionFile getDesc() {
                return p.getDescription();
        }
}