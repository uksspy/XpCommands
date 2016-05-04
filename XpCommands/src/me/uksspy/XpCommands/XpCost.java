package me.uksspy.XpCommands;

import org.bukkit.entity.Player;

public class XpCost {
	
	private boolean levels;
	private int amount;
	
	public XpCost(boolean levels, int amount){
		this.levels = levels;
		this.amount = amount;
	}

	public boolean isLevels() {
		return levels;
	}

	public void setLevels(boolean levels) {
		this.levels = levels;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public boolean canAfford(Player player){
		ExperienceManager em = new ExperienceManager(player);
		if(levels){
			return player.getLevel() >= amount;
		}else{
			return em.getTotalExperience() >= amount;
		}
	}
}
