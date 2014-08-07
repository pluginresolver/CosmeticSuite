package org.arkham.cs.effects;

import java.util.HashMap;
import java.util.UUID;

import org.arkham.cs.CosmeticSuite;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class EffectManager implements Listener{
	
	private HashMap<UUID, CustomEffect> effects = new HashMap<>();
	
	public EffectManager(){
		CosmeticSuite cs = CosmeticSuite.getInstance();
		cs.getServer().getPluginManager().registerEvents(this, cs);
	}
	
	public CustomEffect getEffect(Player player){
		return effects.get(player.getUniqueId());
	}
	
	public void setEffect(Player player, CustomEffect effect){
		effects.put(player.getUniqueId(), effect);
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event){
		if(event.getTo().getBlockX() == event.getFrom().getBlockX() && event.getTo().getBlockZ() == event.getFrom().getBlockZ()){
			return;
		}
		Player player = event.getPlayer();
		CustomEffect effect = getEffect(player);
		if(effect == null){
			return;
		}
		ParticleEffect playedEffect = effect.getEffect();
		Location loc = player.getLocation();
		playedEffect.display(loc, 0, 0, 0, 50F, effect.getAmount());
	}

}
