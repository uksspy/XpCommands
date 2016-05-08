package me.uksspy.XpCommands;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.external.EZPlaceholderHook;

public class Placeholders extends EZPlaceholderHook{

	private XpCommands plugin;
	
	public Placeholders(XpCommands plugin){
		super(plugin, "xpcommands");
		this.plugin = plugin;
	}

	@Override
	public String onPlaceholderRequest(Player p, String identifer) {
		//No placeholders yet
		if(identifer.equals("test")) return "TTTT";
		return null;
	}
}
