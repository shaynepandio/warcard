package com.shayne.warcard.objects;

public class Card {
	private Rank rank;
	private Suit suit;

	public Card() {
	}

	public Rank getRank() {
		return rank;
	}

	public Suit getSuit() {
		return suit;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	@Override
	public String toString() {
		return this.suit + "-" + this.rank + " " +
		 "Suit value = " + this.suit + " " +
		 "Rank value = " + this.rank;
}
}
