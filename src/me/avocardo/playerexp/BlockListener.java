package me.avocardo.playerexp;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

public class BlockListener implements Listener {

	private PlayerExp PlayerExp;

	public BlockListener(PlayerExp PlayerExp) {
		
		this.PlayerExp = PlayerExp;
        
    }

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockDamage(final BlockDamageEvent event) {
		
		if (event.isCancelled()) {
			return;
		}
		
		Player p = event.getPlayer();
		User u = PlayerExp.getUser(p);
	
		if (u != null) {
			
			if (u.getMining() > 100) {
				short before = event.getItemInHand().getDurability();
				Random random = new Random();
				int i = random.nextInt(u.getMining() - 1);
	            if (i > 99 && before > 0) {
	            	short value = (short) (before - 1);
	            	event.getItemInHand().setDurability(value);
	            }
			}
			
		}
		
	}
	
}
