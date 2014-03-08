package me.br456.Commands;

import me.br456.Gem.GemDisplay;
import me.br456.Gem.SettingsManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GemCommand implements CommandExecutor{
	
	SettingsManager settings = SettingsManager.getInstance();
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandlabel, String[] args) {
		
		if(sender instanceof Player) {			
			Player player = (Player) sender;
			
			if(cmd.getLabel().equalsIgnoreCase("gem")) {
				if(!player.hasPermission("gem.admin.command")) return true;
				if(args.length == 0) {
					player.sendMessage(ChatColor.GREEN + "Commands:");
					player.sendMessage(ChatColor.GREEN + "	gem add <amnt> <player>");
					player.sendMessage(ChatColor.GREEN + "	gem sub <amnt> <player>");
					player.sendMessage(ChatColor.GREEN + "	gem set <amnt> <player>");
					player.sendMessage(ChatColor.GREEN + "	gem reset <player>");
				}
				
				if(args.length > 3) {
					player.sendMessage(ChatColor.RED + "Too many arguments!");
					return true;
				}
				
				if(args.length == 1) {
					player.sendMessage(ChatColor.RED + "Must specify an amount and a player!");
					return true;
				}
				
				if(args.length == 2) {
					if(args[0].equalsIgnoreCase("reset")) {
						String name = args[1];
						Player target = Bukkit.getPlayerExact(name);
						
						GemDisplay.updateScoreboard(target, 0);
						
						settings.getData().set(name, 0);
						settings.saveData();
						return true;						
					}
					player.sendMessage(ChatColor.RED + "Must specify a player!");
					return true;
				}
				
				if(args.length == 3) {
					if(args[0].equalsIgnoreCase("add")) {
						String name = args[2];
						int current = settings.getData().getInt(name);
						Player target = Bukkit.getPlayerExact(name);
						int amnt;
						
						try {
							amnt = Integer.parseInt(args[1]);
						} catch (NumberFormatException e) {
							player.sendMessage(ChatColor.RED + args[1] + " is not a valid number!");
							return true;
						}
						
						if(amnt < 0) {
							player.sendMessage(ChatColor.RED + "The minimum number is 0!");
							return true;
						}
						
						if(amnt + current > 1000000) {
							player.sendMessage(ChatColor.RED + "The maximum number is 1,000,000!");
							return true;
						}
						
						if(target == null) {
							player.sendMessage(ChatColor.RED + "Can not find " + name + "!");
							return true;
						} else {
							
							GemDisplay.updateScoreboard(target, amnt + current);
							
							settings.getData().set(name, amnt + current);
							settings.saveData();
							return true;
						}
					}	
					if(args[0].equalsIgnoreCase("sub")) {
						String name = args[2];
						int current = settings.getData().getInt(name);
						Player target = Bukkit.getPlayerExact(name);
						int amnt;
						
						try {
							amnt = Integer.parseInt(args[1]);
						} catch (NumberFormatException e) {
							player.sendMessage(ChatColor.RED + args[1] + " is not a valid number!");
							return true;
						}
						
						if(amnt < 0) {
							player.sendMessage(ChatColor.RED + "Can not subtract negative numbers!");
							return true;
						}
						
						if(target == null) {
							player.sendMessage(ChatColor.RED + "Can not find " + name + "!");
							return true;
						} else {
							if(amnt > current) {
								player.sendMessage(ChatColor.RED + name + " has insufficient funds!");
								return true;
							} else {							
								GemDisplay.updateScoreboard(target, current - amnt);
								
								settings.getData().set(name, current - amnt);
								settings.saveData();
								return true;
							}
						}						
					}	
					if(args[0].equalsIgnoreCase("set")) {
						String name = args[2];
						Player target = Bukkit.getPlayerExact(name);
						int amnt;
						
						try {
							amnt = Integer.parseInt(args[1]);
						} catch (NumberFormatException e) {
							player.sendMessage(ChatColor.RED + args[1] + " is not a valid number!");
							return true;
						}
						
						if(amnt < 0) {
							player.sendMessage(ChatColor.RED + "The minimum number is 0!");
							return true;
						}
						
						if(amnt > 1000000) {
							player.sendMessage(ChatColor.RED + "The maximum number is 1,000,000!");
							return true;
						}
						
						if(target == null) {
							player.sendMessage(ChatColor.RED + "Can not find " + name + "!");
							return true;
						} else {							
							GemDisplay.updateScoreboard(target, amnt);
								
							settings.getData().set(name, amnt);
							settings.saveData();
							return true;
						}
					}
				}
			}		
		} else {
			if(cmd.getLabel().equalsIgnoreCase("gem")) {
				if(args.length == 0) {
					sender.sendMessage(ChatColor.GREEN + "Commands:");
					sender.sendMessage(ChatColor.GREEN + "	gem add <amnt> <player>");
					sender.sendMessage(ChatColor.GREEN + "	gem sub <amnt> <player>");
					sender.sendMessage(ChatColor.GREEN + "	gem set <amnt> <player>");
					sender.sendMessage(ChatColor.GREEN + "	gem reset <player>");
				}
				
				if(args.length > 3) {
					sender.sendMessage(ChatColor.RED + "Too many arguments!");
					return true;
				}
				
				if(args.length == 1) {
					sender.sendMessage(ChatColor.RED + "Must specify an amount and a player!");
					return true;
				}
				
				if(args.length == 2) {
					if(args[0].equalsIgnoreCase("reset")) {
						String name = args[1];
						Player target = Bukkit.getPlayerExact(name);
						
						GemDisplay.updateScoreboard(target, 0);
						
						settings.getData().set(name, 0);
						settings.saveData();
						return true;						
					}
					sender.sendMessage(ChatColor.RED + "Must specify a player!");
					return true;
				}
				
				if(args.length == 3) {
					if(args[0].equalsIgnoreCase("add")) {
						String name = args[2];
						int current = settings.getData().getInt(name);
						Player target = Bukkit.getPlayerExact(name);
						int amnt;
						
						try {
							amnt = Integer.parseInt(args[1]);
						} catch (NumberFormatException e) {
							sender.sendMessage(ChatColor.RED + args[1] + " is not a valid number!");
							return true;
						}
						
						if(amnt < 0) {
							sender.sendMessage(ChatColor.RED + "The minimum number is 0!");
							return true;
						}
						
						if(amnt + current > 1000000) {
							sender.sendMessage(ChatColor.RED + "The maximum number is 1,000,000!");
							return true;
						}
						
						if(target == null) {
							sender.sendMessage(ChatColor.RED + "Can not find " + name + "!");
							return true;
						} else {
							
							GemDisplay.updateScoreboard(target, amnt + current);
							
							settings.getData().set(name, amnt + current);
							settings.saveData();
							return true;
						}
					}	
					if(args[0].equalsIgnoreCase("sub")) {
						String name = args[2];
						int current = settings.getData().getInt(name);
						Player target = Bukkit.getPlayerExact(name);
						int amnt;
						
						try {
							amnt = Integer.parseInt(args[1]);
						} catch (NumberFormatException e) {
							sender.sendMessage(ChatColor.RED + args[1] + " is not a valid number!");
							return true;
						}
						
						if(amnt < 0) {
							sender.sendMessage(ChatColor.RED + "Can not subtract negative numbers!");
							return true;
						}
						
						if(target == null) {
							sender.sendMessage(ChatColor.RED + "Can not find " + name + "!");
							return true;
						} else {
							if(amnt > current) {
								sender.sendMessage(ChatColor.RED + name + " has insufficient funds!");
								return true;
							} else {							
								GemDisplay.updateScoreboard(target, current - amnt);
								
								settings.getData().set(name, current - amnt);
								settings.saveData();
								return true;
							}
						}						
					}	
					if(args[0].equalsIgnoreCase("set")) {
						String name = args[2];
						Player target = Bukkit.getPlayerExact(name);
						int amnt;
						
						try {
							amnt = Integer.parseInt(args[1]);
						} catch (NumberFormatException e) {
							sender.sendMessage(ChatColor.RED + args[1] + " is not a valid number!");
							return true;
						}
						
						if(amnt < 0) {
							sender.sendMessage(ChatColor.RED + "The minimum number is 0!");
							return true;
						}
						
						if(amnt > 1000000) {
							sender.sendMessage(ChatColor.RED + "The maximum number is 1,000,000!");
							return true;
						}
						
						if(target == null) {
							sender.sendMessage(ChatColor.RED + "Can not find " + name + "!");
							return true;
						} else {							
							GemDisplay.updateScoreboard(target, amnt);
								
							settings.getData().set(name, amnt);
							settings.saveData();
							return true;
						}
					}
				}
			}
		}
        return true;
    }
}
