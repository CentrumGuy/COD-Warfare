package com.CentrumGuy.CodWarfare.Files;
 
import java.io.File;
import java.io.IOException;
 
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
 
public class EnabledArenasFile {
 
        private EnabledArenasFile() { }
       
        static EnabledArenasFile instance = new EnabledArenasFile();
       
        public static EnabledArenasFile getInstance() {
                return instance;
        }
       
        static Plugin p;
       
        static FileConfiguration EnabledArenas;
        static File EAfile;
       
        public static void setup(Plugin p) {
               
                if (!p.getDataFolder().exists()) {
                        p.getDataFolder().mkdir();
                }
               
                EAfile = new File(p.getDataFolder(), "EnabledArenas.yml");
               
                if (!EAfile.exists()) {
                        try {
                                EAfile.createNewFile();
                        }
                        catch (IOException e) {
                                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create arenas.yml!");
                        }
                }
               
                EnabledArenas = YamlConfiguration.loadConfiguration(EAfile);
        }
       
        public static FileConfiguration getData() {
                return EnabledArenas;
        }
       
        public static void saveData() {
                try {
                        EnabledArenas.save(EAfile);
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save arenas.yml!");
                }
        }
       
        public static void reloadData() {
                EnabledArenas = YamlConfiguration.loadConfiguration(EAfile);
        }
       
        public static PluginDescriptionFile getDesc() {
                return p.getDescription();
        }
}