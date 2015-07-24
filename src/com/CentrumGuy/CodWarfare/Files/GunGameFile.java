package com.CentrumGuy.CodWarfare.Files;
 
import java.io.File;
import java.io.IOException;
 
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
 
public class GunGameFile {
 
        private GunGameFile() { }
       
        static GunGameFile instance = new GunGameFile();
       
        public static GunGameFile getInstance() {
                return instance;
        }
       
        static Plugin p;
       
        static FileConfiguration GunGame;
        static File ggFile;
       
        public static void setup(Plugin p) {
               
                if (!p.getDataFolder().exists()) {
                        p.getDataFolder().mkdir();
                }
               
                ggFile = new File(p.getDataFolder(), "GunGame.yml");
               
                if (!ggFile.exists()) {
                        try {
                                ggFile.createNewFile();
                        }
                        catch (IOException e) {
                                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create GunGame.yml!");
                        }
                }
               
                GunGame = YamlConfiguration.loadConfiguration(ggFile);
        }
       
        public static FileConfiguration getData() {
                return GunGame;
        }
       
        public static void saveData() {
                try {
                        GunGame.save(ggFile);
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save GunGame.yml!");
                }
        }
       
        public static void reloadData() {
                GunGame = YamlConfiguration.loadConfiguration(ggFile);
        }
       
        public static PluginDescriptionFile getDesc() {
                return p.getDescription();
        }
}