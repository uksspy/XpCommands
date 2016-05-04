package me.uksspy.XpCommands;

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
			if(conf.cmdCosts.containsKey(cmd)){
				XpCost xpCost = conf.cmdCosts.get(cmd);
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
}
