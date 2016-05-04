package me.uksspy.XpCommands;

import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

	
	FileConfiguration config;
	
	HashMap<String, XpCost> cmdCosts = new HashMap<>();
	String noxpmsg;
	String successmsg;
	String nopermmsg;
	
	public Config(XpCommands plugin){
		config = plugin.getConfig();
		loadConfig();
	}
	
	private void loadConfig(){
		List<String> costsStr = config.getStringList("commands");
		for(String s : costsStr){
			String cmd = s.split(",")[0].toLowerCase();
			String xpStr = s.split(",")[1];
			
			cmdCosts.put(cmd, new XpCost(xpStr.contains("L"), Integer.parseInt(xpStr.replace("L", ""))));	
		}
		
		noxpmsg = ChatColor.translateAlternateColorCodes('&',config.getString("messages.noxp"));
		successmsg = ChatColor.translateAlternateColorCodes('&',config.getString("messages.success"));
		nopermmsg = ChatColor.translateAlternateColorCodes('&',config.getString("messages.noperm"));
		
	}
}
