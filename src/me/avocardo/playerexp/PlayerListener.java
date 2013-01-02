package me.avocardo.playerexp;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerListener implements Listener {

	private PlayerExp PlayerExp;

	public PlayerListener(PlayerExp PlayerExp) {
		
		this.PlayerExp = PlayerExp;
        
    }

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerItemHeldEvent(PlayerItemHeldEvent event) {
		
		Player p = event.getPlayer();
		User u = PlayerExp.getUser(p);
		
		if (u != null) {
			u.setActiveSkill(null);
		}
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteract(final PlayerInteractEvent event) {

		Player p = event.getPlayer();
		User u = PlayerExp.getUser(p);
		
		switch (event.getAction()) {
			case RIGHT_CLICK_AIR:
				if (PlayerExp.isBlade(p.getItemInHand().getTypeId())) {
					PlayerExp.cycleSkill(p, u);
					if (u.getActiveSkill() == null) {
						PlayerExp.msg(p, "skills deactivated...");
					} else {
						PlayerExp.msg(p, u.getActiveSkill().getSkillType().getName() + " activated...");
					}
				}
			break;
			case LEFT_CLICK_AIR:
				if (PlayerExp.isBow(p.getItemInHand().getTypeId())) {
					PlayerExp.cycleSkill(p, u);
					if (u.getActiveSkill() == null) {
						PlayerExp.msg(p, "skills deactivated...");
					} else {
						PlayerExp.msg(p, u.getActiveSkill().getSkillType().getName() + " activated...");
					}
				}
			break;
			default:
			break;
		}
		
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		PlayerExp.loadUser(event.getPlayer().getName());
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerKick(PlayerKickEvent event) {
			
		PlayerExp.unloadUser(event.getPlayer().getName());
			
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent event) {
				
		PlayerExp.unloadUser(event.getPlayer().getName());
				
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onExpChange(PlayerExpChangeEvent event) {

		Player p = event.getPlayer();
		User u = PlayerExp.getUser(p);
		
		if (u != null) {
			double getXP = event.getAmount();
			double setXP = ((double) u.getExperience() / 100);
			event.setAmount((int) Math.ceil(getXP * setXP));
		}
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDamage(EntityDamageEvent event) {
		
		if (event.isCancelled()) {
            return;
        }
		
		if (event instanceof EntityDamageByEntityEvent) {
			return;
		}
		
		if (event.getEntity() instanceof Player) {
			
			Player p = (Player) event.getEntity();
			User u = PlayerExp.getUser(p);
			
			if (u != null) {
				int damage = event.getDamage();
				event.setDamage((int) Math.ceil((damage / u.getDefence()) * 100));
			}
			
		}
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onProjectileHit(ProjectileHitEvent event) {

		Entity entity = event.getEntity();
		
		if (entity instanceof Arrow) {
			Arrow arrow = (Arrow) entity;
			Entity shooter = arrow.getShooter();
			if (shooter instanceof Player) {
		        Player p = (Player) shooter;
		        User u = PlayerExp.getUser(p);
				if (u != null) {
					if (u.getActiveSkill().getCooldownLeft() == 0) {
						switch (u.getActiveSkill().getSkillType()) {
	    					case EXPLOSIVE_ARROW:
	    						entity.getWorld().createExplosion(entity.getLocation(), (int) Math.ceil(u.getDefence() / 100));
	    					break;
	    					default:
	    					break;
						}
					} else {
						// cooldown
			        }
				}
			}
		}
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
				
		if (event.isCancelled()) {
            return;
        }
		
		double damage = (double) event.getDamage();
		
		if (event.getEntity() instanceof Player) {
			Player p = (Player) event.getEntity();
			User u = PlayerExp.getUser(p);
			if (u != null) {
				damage = (damage / u.getDefence()) * 100;
			}
		}
		
		if (event.getDamager() instanceof Arrow) {
			Arrow arrow = (Arrow) event.getDamager();
			if (arrow.getShooter() instanceof Player) {
				Player ply = (Player) arrow.getShooter();
				User user = PlayerExp.getUser(ply);
				if (user != null) {
					damage = (damage * user.getArchery()) / 100;
					if (event.getEntity() instanceof LivingEntity) {
    					LivingEntity LivingEntity = (LivingEntity) event.getEntity();
    					if (user.getActiveSkill().getCooldownLeft() == 0) {
	    					switch (user.getActiveSkill().getSkillType()) {
	    	    				case FIRE_ARROW:
	    	    					LivingEntity.setFireTicks(user.getArchery());
	    	    				break;
	    	    				case POISON_ARROW:
	    	    					LivingEntity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, user.getMelee(), 1));
	    	    				break;
	    	    				case CONFUSION_ARROW:
	    	    					LivingEntity.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, user.getMelee(), 1));
	    	    				break;
	    	    				case BLINDNESS_ARROW:
	    	    					LivingEntity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, user.getMelee(), 1));
	    	    				break;
	    	    				default:
	    	    				break;
	    					}
    					} else {
    						//cooldown
    					}
					}
				}
			}
		}
		
		if (event.getDamager() instanceof Player) {
			Player ply = (Player) event.getDamager();
			User user = PlayerExp.getUser(ply);
			if (user != null) {
				damage = (damage * user.getMelee()) / 100;
				if (user.getActiveSkill().getCooldownLeft() == 0) {
					switch (user.getActiveSkill().getSkillType()) {
		    			case FIRE_BLADE:
		    				event.getEntity().setFireTicks(user.getMelee());
		    			break;
		    			case POISON_BLADE:
		    				if (event.getEntity() instanceof LivingEntity) {
		    					LivingEntity LivingEntity = (LivingEntity) event.getEntity();
		    					LivingEntity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, user.getMelee(), 1));
		    				}
		    			break;
		    			default:
		    			break;
					}
				} else {
					// coolodnw
				}
			}
		}
		
		damage = Math.ceil(damage);
		event.setDamage((int) damage);
		
	}
	
}