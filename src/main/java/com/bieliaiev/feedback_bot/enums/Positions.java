package com.bieliaiev.feedback_bot.enums;

public enum Positions {

	ELECTRICIAN ("Electrician"),
	MECHANIC ("Mechanic"),
	ENGINE_SPECIALIST ("Engine specialist"),
	PAINTER ("Painter"),
	WELDER ("Welder"),
	MANAGER ("Manager");
	
	private final String text;
	
	Positions(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
}
