package me.avocardo.playerexp;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

	private PlayerExp PlayerExp;
	
	public Commands(PlayerExp PlayerExp) {
		
		this.PlayerExp = PlayerExp;
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (label.equalsIgnoreCase("pxp")) {
			
			if (args.length > 0) {
				// PXP attribute <player> <value>
				if (PlayerExp.Attributes.contains(args[0].toLowerCase())) {
					if (args.length > 2) {
						if (sender instanceof Player) {
							Player p = (Player) sender;
							if (p.hasPermission("pxp.admin." + args[0].toLowerCase())) {
								String str = args[1];
								String v = args[2];
								if (isNumber(v) == true) {
			  						if (v.length() < Integer.MAX_VALUE) {
			  							Player player = Bukkit.getPlayer(str);
			  							User u = null;
			  							if (player != null) {
			  								u = PlayerExp.getUser(player);
			  							}
				  						if (u != null) {
				  							int value = Integer.parseInt(v);
				  							if (args[0].equalsIgnoreCase("melee")) {
				  								u.setMelee(PlayerExp.SkillLimit(nearestTen(value)));
				  							} else if (args[0].equalsIgnoreCase("archery")) {
				  								u.setArchery(PlayerExp.SkillLimit(nearestTen(value)));
				  							} else if (args[0].equalsIgnoreCase("defence")) {
				  								u.setDefence(PlayerExp.SkillLimit(nearestTen(value)));
				  							} else if (args[0].equalsIgnoreCase("mining")) {
				  								u.setMining(PlayerExp.SkillLimit(nearestTen(value)));
				  							} else if (args[0].equalsIgnoreCase("experience")) {
				  								u.setExperience(PlayerExp.SkillLimit(nearestTen(value)));
				  							}
				  							PlayerExp.saveUser(u);
				  						} else {
				  							PlayerExp.msg(p, args[1] + " has not been recognised...");
				  						}
			  						} else {
			  							PlayerExp.msg(p, args[2] + " is too large a number...");
			  						}
								} else {
									PlayerExp.msg(p, args[2] + " is not a number...");
								}
							} else {
								PlayerExp.msg(p, "you do not have permission...");
							}
						} else {
							String str = args[1];
							String v = args[2];
							if (isNumber(v) == true) {
								if (v.length() < Integer.MAX_VALUE) {
		  							Player player = Bukkit.getPlayer(str);
		  							User u = null;
		  							if (player != null) {
		  								u = PlayerExp.getUser(player);
		  							}
			  						if (u != null) {
			  							int value = Integer.parseInt(v);
			  							if (args[0].equalsIgnoreCase("melee")) {
			  								u.setMelee(PlayerExp.SkillLimit(nearestTen(value)));
			  							} else if (args[0].equalsIgnoreCase("archery")) {
			  								u.setArchery(PlayerExp.SkillLimit(nearestTen(value)));
			  							} else if (args[0].equalsIgnoreCase("defence")) {
			  								u.setDefence(PlayerExp.SkillLimit(nearestTen(value)));
			  							} else if (args[0].equalsIgnoreCase("mining")) {
			  								u.setMining(PlayerExp.SkillLimit(nearestTen(value)));
			  							} else if (args[0].equalsIgnoreCase("experience")) {
			  								u.setExperience(PlayerExp.SkillLimit(nearestTen(value)));
			  							}
			  							PlayerExp.saveUser(u);
			  						} else {
			  							PlayerExp.log(args[1] + " has not been recognised...");
			  						}
		  						} else {
		  							PlayerExp.log(args[2] + " is too large a number...");
		  						}
							} else {
								PlayerExp.log(args[2] + " is not a number...");
							}
						}
					} else {
						if (sender instanceof Player) {
							Player p = (Player) sender;
							PlayerExp.msg(p, "/pxp " + args[0].toLowerCase() + " <player> <value>");
						} else {
							PlayerExp.log("/pxp " + args[0].toLowerCase() + " <player> <value>");
						}
					}
				}
				// PXP level attribute
				if (args[0].equalsIgnoreCase("level")) {
					if (args.length > 1) {
						if (sender instanceof Player) {
							Player p = (Player) sender;
							if (p.hasPermission("pxp.user." + args[0].toLowerCase())) {
			  					User u = PlayerExp.getUser(p);
				  				if (u != null) {
				  					if (args[1].equalsIgnoreCase("melee")) {
				  						int i = levelUp(u.getMelee());
				  						if (p.getTotalExperience() >= i) {
				  							p.setTotalExperience(p.getTotalExperience() - i);
				  							unlockSkill(p, u.getMelee(), u.getMelee() + 10);
				  							u.setMelee(PlayerExp.SkillLimit(u.getMelee() + 10));
				  							PlayerExp.msg(p, "you levelled up melee for " + i + "XP...");
				  						} else {
				  							PlayerExp.msg(p, "you require " + (i - p.getTotalExperience()) + "XP to level up melee again...");
				  						}
				  					} else if (args[1].equalsIgnoreCase("archery")) {
				  						int i = levelUp(u.getArchery());
				  						if (p.getTotalExperience() >= i) {
				  							p.setTotalExperience(p.getTotalExperience() - i);
				  							unlockSkill(p, u.getArchery(), u.getArchery() + 10);
				  							u.setArchery(PlayerExp.SkillLimit(u.getArchery() + 10));
				  							PlayerExp.msg(p, "you levelled up archery for " + i + "XP...");
				  						} else {
				  							PlayerExp.msg(p, "you require " + (i - p.getTotalExperience()) + "XP to level up archery again...");
				  						}
				  					} else if (args[1].equalsIgnoreCase("defence")) {
				  						int i = levelUp(u.getDefence());
				  						if (p.getTotalExperience() >= i) {
				  							p.setTotalExperience(p.getTotalExperience() - i);
				  							unlockSkill(p, u.getDefence(), u.getDefence() + 10);
				  							u.setDefence(PlayerExp.SkillLimit(u.getDefence() + 10));
				  							PlayerExp.msg(p, "you levelled up defence for " + i + "XP...");
				  						} else {
				  							PlayerExp.msg(p, "you require " + (i - p.getTotalExperience()) + "XP to level up defence again...");
				  						}
				  					} else if (args[1].equalsIgnoreCase("mining")) {
				  						int i = levelUp(u.getMining());
				  						if (p.getTotalExperience() >= i) {
				  							p.setTotalExperience(p.getTotalExperience() - i);
				  							unlockSkill(p, u.getMining(), u.getMining() + 10);
				  							u.setMining(PlayerExp.SkillLimit(u.getMining() + 10));
				  							PlayerExp.msg(p, "you levelled up mining for " + i + "XP...");
				  						} else {
				  							PlayerExp.msg(p, "you require " + (i - p.getTotalExperience()) + "XP to level up mining again...");
				  						}
				  					} else if (args[1].equalsIgnoreCase("experience")) {
				  						int i = levelUp(u.getExperience());
				  						if (p.getTotalExperience() >= i) {
				  							p.setTotalExperience(p.getTotalExperience() - i);
				  							unlockSkill(p, u.getExperience(), u.getExperience() + 10);
				  							u.setExperience(PlayerExp.SkillLimit(u.getExperience() + 10));
				  							PlayerExp.msg(p, "you levelled up experience for " + i + "XP...");
				  						} else {
				  							PlayerExp.msg(p, "you require " + (i - p.getTotalExperience()) + "XP to level up experience again...");
				  						}
				  					} else {
				  						PlayerExp.msg(p, "/pxp level <skill>");
				  					}
				  					PlayerExp.saveUser(u);
				  				} else {
				  					PlayerExp.msg(p, args[1] + " has not been recognised...");
				  				}
							} else {
								PlayerExp.msg(p, "you do not have permission...");
							}
						} else {
							PlayerExp.log("/pxp level " + args[0].toLowerCase());
							PlayerExp.log("cannot run this command from the console...");
						}
					} else {
						if (sender instanceof Player) {
							Player p = (Player) sender;
							PlayerExp.msg(p, "/pxp level " + args[0].toLowerCase());
						} else {
							PlayerExp.log("/pxp level " + args[0].toLowerCase());
						}
					}
				}
				// PXP User Stats
				if (args[0].equalsIgnoreCase("stats")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						if (args.length > 1) {
							if (p.hasPermission("pxp.admin.stats")) {
								User u = PlayerExp.getUser(Bukkit.getPlayer(args[1]));
				  				if (u != null) {
				  					stats(p, u);
				  				} else {
				  					PlayerExp.msg(p, args[0] + " has not been recognised...");
				  				}
							} else {
								PlayerExp.msg(p, "you do not have permission...");
							}
						} else {
							if (p.hasPermission("pxp.user.stats")) {
								User u = PlayerExp.getUser(p);
				  				if (u != null) {
				  					stats(p);
				  				}
							} else {
								PlayerExp.msg(p, "you do not have permission...");
							}
						}
					} else {
						if (args.length > 1) {
							User u = PlayerExp.getUser(Bukkit.getPlayer(args[1]));
			  				if (u != null) {
			  					stats(u);
			  				} else {
			  					PlayerExp.log(args[0] + " has not been recognised...");
			  				}
						} else {
							PlayerExp.log("/pxp stats <player>");
						}
					}
				}
			} else {
				//run help
			}
			
			return true;
			
		}
		
		return false;
		
	}
		
	private int levelUp(int attr) {
		if (attr > 100) {
			return (int) Math.ceil((((attr - 100) / 10) * PlayerExp.Multiplier) * PlayerExp.XP);
		}
		return PlayerExp.XP;
	}
	
	private void unlockSkill(Player p, int get, int set) {
		
		for (SkillType s : SkillType.values()) {
			if (s.getUnlock() > get && s.getUnlock() <= set) {
				PlayerExp.getUser(p).addSkill(new Skill(s));
				PlayerExp.msg(p, "you have unlocked the " + s.getName() + " skill...");
			}
		}
		
	}
	
	private void stats(User u) {
		
		Player p = Bukkit.getPlayer(u.getName());
		
		Skill skill = u.getActiveSkill();
		String str = "";
		
		if (skill != null) {
			str = skill.getSkillType().getName();
		} else {
			str = "none";
		}
		
		PlayerExp.log("---------------------------------------");
		PlayerExp.log("Player: " + u.getName());
		PlayerExp.log("Active Skill: " + str);
		PlayerExp.log("---------------------------------------");
		PlayerExp.log("Melee: " + u.getMelee() + "   (" + p.getTotalExperience() + "/" + levelUp(u.getMelee()) + ")");
		PlayerExp.log("Archery: " + u.getArchery() + "   (" + p.getTotalExperience() + "/" + levelUp(u.getArchery()) + ")");
		PlayerExp.log("Defence: " + u.getDefence() + "   (" + p.getTotalExperience() + "/" + levelUp(u.getDefence()) + ")");
		PlayerExp.log("Mining: " + u.getMining() + "   (" + p.getTotalExperience() + "/" + levelUp(u.getMining()) + ")");
		PlayerExp.log("Experience: " + u.getExperience() + "   (" + p.getTotalExperience() + "/" + levelUp(u.getExperience()) + ")");
		PlayerExp.log("---------------------------------------");
		
	}
	
	private void stats(Player p, User u) {
		
		Skill skill = u.getActiveSkill();
		String str = "";
		
		if (skill != null) {
			str = skill.getSkillType().getName();
		} else {
			str = "none";
		}
		
		PlayerExp.msg(p, "---------------------------------------");
		PlayerExp.msg(p, "Player: " + u.getName());
		PlayerExp.msg(p, "Active Skill: " + str);
		PlayerExp.msg(p, "---------------------------------------");
		PlayerExp.msg(p, "Melee: " + u.getMelee() + "   (" + p.getTotalExperience() + "/" + levelUp(u.getMelee()) + ")");
		PlayerExp.msg(p, "Archery: " + u.getArchery() + "   (" + p.getTotalExperience() + "/" + levelUp(u.getArchery()) + ")");
		PlayerExp.msg(p, "Defence: " + u.getDefence() + "   (" + p.getTotalExperience() + "/" + levelUp(u.getDefence()) + ")");
		PlayerExp.msg(p, "Mining: " + u.getMining() + "   (" + p.getTotalExperience() + "/" + levelUp(u.getMining()) + ")");
		PlayerExp.msg(p, "Experience: " + u.getExperience() + "   (" + p.getTotalExperience() + "/" + levelUp(u.getExperience()) + ")");
		PlayerExp.msg(p, "---------------------------------------");
		
	}
	
	private void stats(Player p) {
		
		User u = PlayerExp.getUser(p);
		int xp = p.getTotalExperience();
		
		Skill skill = u.getActiveSkill();
		String str = "";
		
		if (skill != null) {
			str = skill.getSkillType().getName();
		} else {
			str = "none";
		}
		
		PlayerExp.msg(p, "---------------------------------------");
		PlayerExp.msg(p, "Player: " + u.getName());
		PlayerExp.msg(p, "Active Skill: " + str);
		PlayerExp.msg(p, "---------------------------------------");
		PlayerExp.msg(p, "Melee: " + u.getMelee() + "   (" + xp + "/" + levelUp(u.getMelee()) + ")");
		PlayerExp.msg(p, "Archery: " + u.getArchery() + "   (" + xp + "/" + levelUp(u.getArchery()) + ")");
		PlayerExp.msg(p, "Defence: " + u.getDefence() + "   (" + xp + "/" + levelUp(u.getDefence()) + ")");
		PlayerExp.msg(p, "Mining: " + u.getMining() + "   (" + xp + "/" + levelUp(u.getMining()) + ")");
		PlayerExp.msg(p, "Experience: " + u.getExperience() + "   (" + xp + "/" + levelUp(u.getExperience()) + ")");
		PlayerExp.msg(p, "---------------------------------------");
		
	}
	
	private boolean isNumber(String string) {
		char[] c = string.toCharArray();
		for (int i = 0; i < string.length(); i++) {
			if (!Character.isDigit(c[i])) {
				return false;
			}
		}
		return true;
	}

	private int nearestTen(int val) {
		int x = val;
		for(int i = 0; i < 9; i++) {
			if(x % 10 == 0) {
				break;
			} else {
				x++;
			}
		}
		return x;
	}
	
}
