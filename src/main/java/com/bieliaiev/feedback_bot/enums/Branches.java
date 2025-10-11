package com.bieliaiev.feedback_bot.enums;

public enum Branches {

	KYIV_AUTO_CENTER ("Kyiv Auto Center"),
	LVIV_AUTO_CENTER ("Lviv Auto Center"),
	KHARKIV_AUTO_CENTER ("Kharkiv Auto Center"),
	POLTAVA_AUTO_CENTER ("Poltava Auto Center"),
	DNIPRO_AUTO_CENTER ("Dnipro Auto Center"),
	ODESA_AUTO_CENTER ("Odesa Auto Center"),
	RIVNE_AUTO_CENTER ("Rivne Auto Center"),
	CHERNIHIV_AUTO_CENTER ("Chernihiv Auto Center"),
	VINNYTSIA_AUTO_CENTER ("Vinnytsia Auto Center"),
	ZAPORIZHZHIA_AUTO_CENTER ("Zaporizhzhia Auto Center");
	
	private final String text;
	
	Branches (String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
}
