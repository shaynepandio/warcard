package com.shayne.warcard.objects;

public enum Suit {
	DIAMOND(4, "D"),
	HEART(3, "H"),
	SPADES(2, "S"),
	CLUBS(1, "C");

	private int suitValue;
	private String suitName;
	
	private Suit(int suitValue, String suitName) {
		this.suitValue = suitValue;
		this.suitName = suitName;
	}

	public int getSuitValue() {
		return suitValue;
	}

	public String getSuitName() {
		return suitName;
	}
	
	public String toString() {
		return suitName;
	}
}
