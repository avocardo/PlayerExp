package me.avocardo.playerexp;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	private String Name;
	
	private Skill ActiveSkill;
	
	private List<Skill> Skills = new ArrayList<Skill>();
	
	private int Melee;
	private int Archery;
	private int Defence;
	private int Mining;
	private int Experience;

	public User(String Name) {
		this.Name = Name;
		this.ActiveSkill = null;
		this.Skills.add(null);
	}
	
	public String getName() {
		return Name;
	}
	
	public List<Skill> getSkills() {
		return Skills;
	}
	
	public void addSkill(Skill Skill) {
		this.Skills.add(Skill);
	}
	
	public Skill getActiveSkill() {
		return ActiveSkill;
	}
	
	public void setActiveSkill(Skill ActiveSkill) {
		this.ActiveSkill = ActiveSkill;
	}
	
	public int getMelee() {
		return Melee;
	}

	public void setMelee(int Melee) {
		this.Melee = Melee;
	}

	public int getArchery() {
		return Archery;
	}

	public void setArchery(int Archery) {
		this.Archery = Archery;
	}

	public int getDefence() {
		return Defence;
	}

	public void setDefence(int Defence) {
		this.Defence = Defence;
	}

	public int getMining() {
		return Mining;
	}

	public void setMining(int Mining) {
		this.Mining = Mining;
	}
	
	public int getExperience() {
		return Experience;
	}

	public void setExperience(int Experience) {
		this.Experience = Experience;
	}

}
