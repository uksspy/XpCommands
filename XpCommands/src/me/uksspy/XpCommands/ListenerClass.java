package me.uksspy.XpCommands;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.clip.placeholderapi.PlaceholderAPI;

public class ListenerClass implements Listener{

	private Config conf;
	boolean placeholderApi;
	
	public ListenerClass(XpCommands plugin, Config conf) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		placeholderApi = plugin.placeholderApi;
		this.conf = conf;
	} 
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event){
		Player player = event.getPlayer();
		if(!player.hasPermission("xpc.ignore")){
			String cmd = event.getMessage().toLowerCase();
			String key = keyBeginning(conf.cmdCosts, cmd);
			if(key != null){
				XpCost xpCost = conf.cmdCosts.get(key);
				if(xpCost.canAfford(player)){
					if(xpCost.isLevels()){
						player.setLevel(player.getLevel() - xpCost.getAmount());
					}else{
						ExperienceManager em = new ExperienceManager(player);
						em.setTotalExperience(em.getTotalExperience() - xpCost.getAmount());
					}
					if(conf.successmsg.length() != 0){
						String msg;
						msg = placeholderApi ? PlaceholderAPI.setPlaceholders(player, conf.successmsg) : conf.successmsg;
						player.sendMessage(msg);
					}
					if(placeholderApi)event.setMessage(PlaceholderAPI.setPlaceholders(player, event.getMessage()));
				}else{
					if(conf.noxpmsg.length() != 0){
						String msg;
						msg = placeholderApi ? PlaceholderAPI.setPlaceholders(player, conf.noxpmsg) : conf.noxpmsg;
						player.sendMessage(msg);
					}
					event.setCancelled(true);
				}
			}
		}
	}
	
	private String keyBeginning(HashMap<String, XpCost> map, String key){
	    Iterator it = map.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        if(key.startsWith(pair.getKey().toString())) return pair.getKey().toString();
	    }
	    return null;
		
	}
}
