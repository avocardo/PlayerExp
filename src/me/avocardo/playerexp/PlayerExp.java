package me.avocardo.playerexp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerExp extends JavaPlugin  {

	private PluginManager PM;

	public String v = "0.0.0";
	
	public Map <String, User> Users = new HashMap <String, User>();
	
	public List <String> Attributes = new ArrayList <String>();
	
	private FileConfiguration UsersConfig = null;
	private File UsersConfigFile = null;
	private FileConfiguration SettingsConfig = null;
	private File SettingsConfigFile = null;
	private FileConfiguration SkillsConfig = null;
	private File SkillsConfigFile = null;
		
	public int XP;
	
	public double Multiplier;
	
	public void log(String msg) {
		System.out.println("[PlayerExp]" + msg);
	}
	
	public void msg(Player p, String msg) {
		p.sendMessage("[PlayerExp] " + msg);
	}
	
	public void onEnable() {
		
		v = this.getDescription().getVersion();
		
		Users.clear();
		Attributes.clear();
		
		Attributes.add("melee");
		Attributes.add("archery");
		Attributes.add("defence");
		Attributes.add("mining");
		Attributes.add("experience");
		
		loadSettings();
		
		loadSkills();
		
		log("Loading users.yml");
		
		UsersConfigFile = new File(getDataFolder(), "users.yml");
		UsersConfig = YamlConfiguration.loadConfiguration(UsersConfigFile);
			
		InputStream defConfigStream = getResource("users.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			UsersConfig.setDefaults(defConfig);
		}
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			loadUser(p.getName());
		}
		
		PM = getServer().getPluginManager();
		PM.registerEvents(new BlockListener(this), this);
		PM.registerEvents(new PlayerListener(this), this);
		
		getCommand("pxp").setExecutor(new Commands(this));
				
	}
	
	public void onDisable() {
		
		User user = null;
		String str = "";
		
		if (!Users.isEmpty()) {
			for (Map.Entry<String, User> users : Users.entrySet()) {
				user = users.getValue();
				str = users.getKey();
				UsersConfig.set(str + ".melee", user.getMelee());
				UsersConfig.set(str + ".archery", user.getArchery());
				UsersConfig.set(str + ".defence", user.getDefence());
				UsersConfig.set(str + ".mining", user.getMining());
				UsersConfig.set(str + ".experience", user.getExperience());
			}
		}		
		
		try {
			UsersConfig.save(UsersConfigFile);
		} catch (IOException ex) {
			this.getLogger().log(Level.SEVERE, "Could not save users to " + UsersConfigFile, ex);
		}
		
	}
	
	public User getUser(Player p) {
		if (Users.containsKey(p.getName()))
			return Users.get(p.getName());
		return null;
	}
	
	public void loadSettings() {
		
		log("Loading settings.yml");
		
		SettingsConfigFile = new File(getDataFolder(), "settings.yml");
		SettingsConfig = YamlConfiguration.loadConfiguration(SettingsConfigFile);
			
		InputStream defConfigStream = getResource("settings.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			SettingsConfig.setDefaults(defConfig);
		}
		
		XP = SettingsConfig.getInt("XP", 25);
		Multiplier = SettingsConfig.getDouble("Multiplier", 2.0);
				
	}
	
	public void loadSkills() {
		
		log("Loading skills.yml");
		
		SkillsConfigFile = new File(getDataFolder(), "skills.yml");
		SkillsConfig = YamlConfiguration.loadConfiguration(SkillsConfigFile);
			
		InputStream defConfigStream = getResource("Skills.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			SkillsConfig.setDefaults(defConfig);
		}
		
		Set<String> skills = SkillsConfig.getConfigurationSection("").getKeys(false);

		if (skills.isEmpty()) {
			// No Skills
		} else {
			for (String str : skills) {
				for (SkillType s : SkillType.values()) {
					if (s.toString().equalsIgnoreCase(str)) {
						s.setUnlock(SkillsConfig.getInt(str + ".Unlock"));
						s.setCoolDown(SkillsConfig.getInt(str + ".CoolDown"));
					}
				}
			}
		}
		
	}
	
	public void loadUser(String str) {
		
		User user = new User(str);
		
		user.setMelee(SkillLimit(UsersConfig.getInt(str + ".melee", 100)));
		user.setArchery(SkillLimit(UsersConfig.getInt(str + ".archery", 100)));
		user.setDefence(SkillLimit(UsersConfig.getInt(str + ".defence", 100)));
		user.setMining(SkillLimit(UsersConfig.getInt(str + ".mining", 100)));
		user.setExperience(SkillLimit(UsersConfig.getInt(str + ".experience", 100)));
		
		if (!Users.containsKey(str)) Users.put(str, user);
		
	}
	
	public void unloadUser(String str) {
		if (Users.containsKey(str)) Users.remove(str);
	}
	
	public void saveUser(User user) {

		String str = user.getName();
		
		UsersConfig.set(str + ".melee", user.getMelee());
		UsersConfig.set(str + ".archery", user.getArchery());
		UsersConfig.set(str + ".defence", user.getDefence());
		UsersConfig.set(str + ".mining", user.getMining());
		UsersConfig.set(str + ".experience", user.getExperience());
		
		try {
			UsersConfig.save(UsersConfigFile);
		} catch (IOException ex) {
			this.getLogger().log(Level.SEVERE, "Could not save users to " + UsersConfigFile, ex);
		}
		
	}
	
	public void cycleSkill(Player p, User u) {
		
		Skill ActiveSkill = u.getActiveSkill();
		
		boolean next = false;
		
		for (Skill s : u.getSkills()) {
			if (!next) {
				if (s == null) {
					next = true;
					continue;
				} else if (s == ActiveSkill) {
					next = true;
					continue;
				}
			} else {
				if (s.getSkillType().getPermission(p.getItemInHand().getTypeId())) {
					u.setActiveSkill(s);
					break;
				}
			}
		}
		
		if (ActiveSkill == u.getActiveSkill()) {
			u.setActiveSkill(null);
		}
		
	}
	
	public boolean isBlade(int i) {
		
		if (i == 267) return true;
		if (i == 268) return true;
		if (i == 272) return true;
		if (i == 276) return true;
		if (i == 283) return true;

		return false;
	
	}
	
	public boolean isBow(int i) {
		
		if (i == 261) return true;

		return false;
	
	}
	
	public int SkillLimit(int i) {
		if (i > 10000) {
			return 10000;
		} else if (i < 100) {
			return 100;
		}
		return nearestTen(i);
	}
	
	public int nearestTen(int val) {
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
