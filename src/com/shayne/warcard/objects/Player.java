package com.shayne.warcard.objects;

import java.util.LinkedList;
import java.util.List;

public class Player {
	private String playerName;
	private List<Card> playerCards;
	
	public Player(String data) {
		this.playerName = data;
		this.playerCards = new LinkedList<Card>();
	}

	public String getPlayerName() {	
		return playerName;
	}

	public List<Card> getPlayerCards() {
		return playerCards;
	}

	@Override
	public String toString() {
		return playerName;
	}
	
}
