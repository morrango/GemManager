package me.br456.Gem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EmeraldListeners implements Listener{
	
	SettingsManager settings = SettingsManager.getInstance();

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		String name = player.getName();
		int aogpe = settings.getConfig().getInt("Amount of gems per emerald");
		if(!player.hasPermission("gem.emerald.deposit")) return;
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(player.getItemInHand().getType() == Material.EMERALD) {
				player.sendMessage(ChatColor.GREEN + "Successfully added " + aogpe + " gems to your account!");
				player.getInventory().remove(new ItemStack(Material.EMERALD, 1));
				
				int current = settings.getData().getInt(name);
				int newbal = current + aogpe;
				
				GemDisplay.updateScoreboard(player, newbal);
				settings.getData().set(name, newbal);
				settings.saveData();
			}
		}
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		if (event.getLine(0).equalsIgnoreCase("[Emerald]")) {
			 if(event.getPlayer().hasPermission("gem.create.emerald")) {
				 event.setLine(0, "§a[Emerald]");
			 }
		 }
	}
	
	@EventHandler
	public void onSignInteract(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block b = event.getClickedBlock();
			if(b.getType() == Material.WALL_SIGN || b.getType() == Material.SIGN_POST) {
				Player player = event.getPlayer();
				String name = player.getName();
				Sign sign = (Sign) b.getState();
				String[] lines = sign.getLines();
				if(lines[0] == "§a[Emerald]") {
					if(!player.hasPermission("gem.emerald.withdraw")) return;
					int current = settings.getData().getInt(name);
					int config = settings.getConfig().getInt("Amount of gems per emerald");
					
					if(config > current) {
						player.sendMessage(ChatColor.RED + "You must have at least" + config + " gems in your account!");
						return;
					} else { 
						int newbal = current - config;
						GemDisplay.updateScoreboard(player, newbal);
						player.getInventory().addItem(new ItemStack(Material.EMERALD, 1));
						settings.getData().set(name, newbal);
						settings.saveData();
					}
				}
			}
		}
	}
}
