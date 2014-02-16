package me.br456.Gem;

import me.br456.Commands.GemCommand;
import me.br456.Gem.Updater.UpdateResult;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Gem extends JavaPlugin{
	
	SettingsManager settings = SettingsManager.getInstance();
	public static boolean updateAvailable;
	
	public void onEnable() {
		settings.setup(this);
		settings.saveConfig();
		settings.saveData();
		
		registerEvents();
		registerEconomy();
		registerCommands();
		
		if(settings.getConfig().getBoolean("Auto-Update") == false) {
			getServer().getLogger().info("Auto-Updates Disabled");
			return;
		} else {
			Updater updater = new Updater(this, 67890, this.getFile(), Updater.UpdateType.NO_DOWNLOAD, false);
			if(updater.getResult() == UpdateResult.UPDATE_AVAILABLE) {
				updateAvailable = true;
			}
			return;
		}
	}
	
	public static Plugin getPlugin() {
		return Bukkit.getServer().getPluginManager().getPlugin("GemManager");
	}
	
	private void registerEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            final ServicesManager sm = getServer().getServicesManager();
            sm.register(Economy.class, new VaultConnector(), this, ServicePriority.Highest);
            getServer().getLogger().info("Registered Vault interface");
        } else {
            getServer().getLogger().warning("Vault not found. Vault support disabled");
        }
    }
	
	private void registerEvents() {
		PluginManager manager = getServer().getPluginManager();
		
		manager.registerEvents(new GemDisplay(), this);
		manager.registerEvents(new EmeraldListeners(), this);
		manager.registerEvents(new MobDrops(), this);
	}
	
	private void registerCommands() {
		GemCommand gemCommand= new GemCommand();
		getCommand("gem").setExecutor(gemCommand);
	}

}

//Thanks to Sleaker for helping with Vault integration