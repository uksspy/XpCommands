package me.uksspy.XpCommands;

import static org.bukkit.ChatColor.*;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;


public class XpCommands extends JavaPlugin{
	
	Config conf;
	
	@Override
	public void onEnable() {
		conf = new Config(this);
		new ListenerClass(this, conf);
		this.saveDefaultConfig();
	}
	
	@Override
	public void onDisable() {
		this.saveDefaultConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("xpc")){
			if(sender.hasPermission("xpc.admin")){
				if(args.length == 3 && args[0].equalsIgnoreCase("add")){
					String command = args[1].toLowerCase();
					boolean levels = args[2].contains("L");
					int amount;
					try{
						amount = Integer.parseInt(args[2].replace("L", ""));
					}catch(NumberFormatException ex){
						sender.sendMessage(RED + "Invalid format!");
						ex.printStackTrace();
						return false;
					}
					List<String> commands = getConfig().getStringList("commands");
					commands.add(command + "," + args[2]);
					getConfig().set("commands", commands);
					saveConfig();
					conf.cmdCosts.put(command, new XpCost(levels, amount));
					sender.sendMessage(GREEN + "Xp command added sucessfully!");
				}else{
					sender.sendMessage(DARK_RED + "Usage: " + RED + "/xpc add <command> <cost>");
				}
			}else{
				if(conf.nopermmsg.length() != 0)sender.sendMessage(conf.nopermmsg);
			}
		}
		return false;
	}

}
