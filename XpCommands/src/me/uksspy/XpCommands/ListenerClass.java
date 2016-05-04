package me.uksspy.XpCommands;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.print.attribute.standard.RequestingUserName;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ListenerClass implements Listener{

	private Config conf;
	
	
	public ListenerClass(XpCommands plugin, Config conf) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
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
					if(conf.successmsg != null)player.sendMessage(conf.successmsg);
				}else{
					player.sendMessage(conf.noxpmsg);
					event.setCancelled(true);
				}
			}
		}
	}
	
	private String keyBeginning(HashMap<String, XpCost> map, String key){
	    Iterator it = map.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println("cmd: " + key + " key: " + pair.getKey());
	        System.out.println(key.startsWith(pair.getKey().toString()));
	        if(key.startsWith(pair.getKey().toString())) return pair.getKey().toString();
	    }
	    return null;
		
	}
}
