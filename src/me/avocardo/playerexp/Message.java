package me.avocardo.playerexp;

public enum Message {
	
	SKILL_DEACTIVATED(""),
	SKILL_ACTIVATED(""),
	NOT_RECOGNISED(""),
	NUMBER_TOO_LARGE(""),
	NOT_NUMBER(""),
	NO_PERMISSION(""),
	LEVEL_UP(""),
	UNLOCK_SKILL("");
	
	String Message;
	
	Message(String Message) {
		this.Message = Message;
    }
	
	//Create SendMessage with Object object and Player player
	
	@Override
	public String toString() {
		return super.toString();
	}
		
}
