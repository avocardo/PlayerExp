package me.avocardo.playerexp;

public class Skill {

	private SkillType SkillType;
	private long LastUse;

	public Skill(SkillType SkillType) {

		this.SkillType = SkillType;
		this.LastUse = 0;

	}

	public SkillType getSkillType() {

		return this.SkillType;

	}

	public void setLastUse(long LastUse) {

		this.LastUse = LastUse;

	}

	public long getCooldown() {

		return this.SkillType.getCoolDown();

	}

	public long getCooldownLeft() {

		if (this.LastUse == 0) return 0;

		if (System.currentTimeMillis() - this.LastUse > this.SkillType.getCoolDown()) {
			return 0;
		}

		return (System.currentTimeMillis() - this.LastUse - this.SkillType.getCoolDown());

	}

}