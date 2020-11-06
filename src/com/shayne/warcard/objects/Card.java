package com.shayne.warcard.objects;

import com.shayne.warcard.constants.Rank;
import com.shayne.warcard.constants.Suit;

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
		return "[" + this.suit + "-" + this.rank + "]";
 }
}
