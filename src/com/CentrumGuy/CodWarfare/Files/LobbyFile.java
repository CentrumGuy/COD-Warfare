package com.CentrumGuy.CodWarfare.Files;
 
import java.io.File;
import java.io.IOException;
 
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
 
public class LobbyFile {
 
        private LobbyFile() { }
       
        static LobbyFile instance = new LobbyFile();
       
        public static LobbyFile getInstance() {
                return instance;
        }
       
        static Plugin p;
       
        static FileConfiguration lobby;
        static File lfile;
       
        public static void setup(Plugin p) {
               
                if (!p.getDataFolder().exists()) {
                        p.getDataFolder().mkdir();
                }
               
                lfile = new File(p.getDataFolder(), "lobby.yml");
               
                if (!lfile.exists()) {
                        try {
                                lfile.createNewFile();
                        }
                        catch (IOException e) {
                                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create lobby.yml!");
                        }
                }
               
                lobby = YamlConfiguration.loadConfiguration(lfile);
        }
       
        public static FileConfiguration getData() {
                return lobby;
        }
       
        public static void saveData() {
                try {
                        lobby.save(lfile);
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save lobby.yml!");
                }
        }
       
        public static void reloadData() {
                lobby = YamlConfiguration.loadConfiguration(lfile);
        }
       
        public static PluginDescriptionFile getDesc() {
                return p.getDescription();
        }
}