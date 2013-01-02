package me.avocardo.playerexp;

public enum SkillType {
	
	FIRE_BLADE(1, 10000, 600),
	POISON_BLADE(2, 10000, 600),
	FIRE_ARROW(3, 10000, 600),
	POISON_ARROW(4, 10000, 600),
	EXPLOSIVE_ARROW(5, 10000, 600),
	CONFUSION_ARROW(6, 10000, 600),
	BLINDNESS_ARROW(7, 10000, 600);
	
	int ID;
	int Unlock;
	int CoolDown;
	
	SkillType(int ID, int Unlock, int CoolDown) {
		this.ID = ID;
		this.Unlock = Unlock;
        this.CoolDown = CoolDown;
    }
	
    public int getCoolDown() {
        return CoolDown;
    }
    
    public void setCoolDown(int CoolDown) {
        this.CoolDown = CoolDown;
    }
        
    public int getUnlock() {
        return Unlock;
    }
    
    public void setUnlock(int Unlock) {
        this.Unlock = Unlock;
    }
    
    public int getId() {
        return ID;
    }
    
    public boolean getPermission(int ItemID) {
    	
    	switch (this) {
    		case FIRE_BLADE:
    			if (ItemID == 267) return true;
    			if (ItemID == 268) return true;
    			if (ItemID == 272) return true;
    			if (ItemID == 276) return true;
    			if (ItemID == 283) return true;
    		return false;
    		case POISON_BLADE:
    			if (ItemID == 267) return true;
    			if (ItemID == 268) return true;
    			if (ItemID == 272) return true;
    			if (ItemID == 276) return true;
    			if (ItemID == 283) return true;
    		return false;
    		case FIRE_ARROW:
    			if (ItemID == 261) return true;
    		return false;
    		case POISON_ARROW:
    			if (ItemID == 261) return true;
        	return false;
    		case EXPLOSIVE_ARROW:
    			if (ItemID == 261) return true;
        	return false;
    		case CONFUSION_ARROW:
    			if (ItemID == 261) return true;
        	return false;
    		case BLINDNESS_ARROW:
    			if (ItemID == 261) return true;
        	return false;
    		default:
            return true;
    	}
    	
    }
	
	@Override
	public String toString() {
		String s = super.toString();
		return s.toLowerCase();
	}
	
	public String getName() {
		String s = super.toString();
		return s.replaceAll("_", " ").toLowerCase();
	}
		
}
