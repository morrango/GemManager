package me.br456.Gem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class GemDisplay implements Listener{
	
	static SettingsManager settings = SettingsManager.getInstance();
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String name = player.getName();
		
		Scoreboard board= Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("Gems", "dummy");
		
		if(settings.getData().contains(name)) {
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			obj.setDisplayName(name);
			obj.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Gems")).setScore(settings.getData().getInt(name));
			
			player.setScoreboard(board);
		} else { 
			settings.getData().set(name, 0);
			settings.saveData();
			
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			obj.setDisplayName(name);
			obj.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Gems")).setScore(0);
			
			player.setScoreboard(board);
		}		
		
	}
	
	public static void updateScoreboard(Player player, double d) {
		int b = (int)Math.round(d);
		Scoreboard board = player.getScoreboard();
		Objective gemsObj = board.getObjective(DisplaySlot.SIDEBAR);
		gemsObj.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Gems")).setScore(b);
	}	
}
