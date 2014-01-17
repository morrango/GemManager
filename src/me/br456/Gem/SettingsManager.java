package me.br456.Gem;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class SettingsManager {
	 
    private SettingsManager() { }
   
    static SettingsManager instance = new SettingsManager();
   
    public static SettingsManager getInstance() {
            return instance;
    }
   
    Plugin p;
    public Plugin getPlugin() {
    	return p;
    }
   
    FileConfiguration config;
    File cfile;
   
    FileConfiguration data;
    File dfile;
   
    public void setup(Plugin p) {
            cfile = new File(p.getDataFolder(), "config.yml");
            config = p.getConfig();
           
            if (!p.getDataFolder().exists()) {
            		p.saveDefaultConfig();
                    p.getDataFolder().mkdir();
            }
           
            dfile = new File(p.getDataFolder(), "data.yml");
           
            if (!dfile.exists()) {
                    try {
                            dfile.createNewFile();
                    }
                    catch (IOException e) {
                            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create data.yml!");
                    }
            }
            
            if (!cfile.exists()) {
                try {
                        cfile.createNewFile();
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create config.yml!");
                }
        }
           
            data = YamlConfiguration.loadConfiguration(dfile);
            config = YamlConfiguration.loadConfiguration(cfile);
    }
   
    public FileConfiguration getData() {
            return data;
    }
   
    public void saveData() {
            try {
                    data.save(dfile);
            }
            catch (IOException e) {
                    Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save data.yml!");
            }
    }
   
    public void reloadData() {
            data = YamlConfiguration.loadConfiguration(dfile);
    }
   
    public FileConfiguration getConfig() {
            return config;
    }
   
    public void saveConfig() {
            try {
                    config.save(cfile);
            }
            catch (IOException e) {
                    Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save config.yml!");
            }
    }
   
    public void reloadConfig() {
            config = YamlConfiguration.loadConfiguration(cfile);
    }
   
    public PluginDescriptionFile getDesc() {
            return p.getDescription();
    }
    
    public int getBalance(String name) {
    	return data.getInt(name);
    }
    
    public void addBalance(String name, double amnt) {
    	if(amnt < 0) return;
    	if(amnt + getBalance(name) > 1000000) return;
    	setBalance(name, getBalance(name) + amnt);
    }
    
    public boolean removeBalance(String name, double amnt) {
    	if(getBalance(name) - amnt < 0) return false;
    	if(amnt < 0) return false;
    	setBalance(name, getBalance(name) - amnt);
    	return true;
    }
    
    public void setBalance(String name, double d) {
    	data.set(name, d);
    	saveData();
    	
    	Player player = Bukkit.getServer().getPlayer(name);
    	GemDisplay.updateScoreboard(player, d);
    }
    
}