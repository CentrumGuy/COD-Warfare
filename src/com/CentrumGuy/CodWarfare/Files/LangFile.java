package com.CentrumGuy.CodWarfare.Files;
 
import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

 
public class LangFile {
 
        private LangFile() { }
       
        static LangFile instance = new LangFile();
       
        public static LangFile getInstance() {
                return instance;
        }
       
        static Plugin p;
       
        static FileConfiguration language;
        static File lfile;
       
        public static void setup(Plugin p) {
               
                if (!p.getDataFolder().exists()) {
                        p.getDataFolder().mkdir();
                }
               
                lfile = new File(p.getDataFolder(), "language.yml");
               
                if (!lfile.exists()) {
                        try {
                                lfile.createNewFile();
                        }
                        catch (IOException e) {
                                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create language.yml!");
                        }
                }
               
                language = YamlConfiguration.loadConfiguration(lfile);
                language.addDefault("noArenas", "&aYou left COD-Warfare because there are no arenas");
                language.addDefault("nextArena1", "&b&m=========================");
                language.addDefault("nextArena2", "&6Next Arena:&e ");
                language.addDefault("nextArena3", "&6Game-Mode:&e ");
                language.addDefault("nextArena4", "&b&m=========================");
                language.addDefault("gameOver", "&6&lGAME OVER!");
                language.addDefault("startingNewGame", "&d&lStarting New Game...");
                language.addDefault("newHighestKillStreak", "&b&lNew highest kill streak:&6&l ");
                language.options().copyDefaults(true);
                saveData();
        }
       
        public static FileConfiguration getData() {
                return language;
        }
       
        public static void saveData() {
                try {
                        language.save(lfile);
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save language.yml!");
                }
        }
       
        public static void reloadData() {
                language = YamlConfiguration.loadConfiguration(lfile);
        }
       
        public static PluginDescriptionFile getDesc() {
                return p.getDescription();
        }
}