package com.shayne.warcard.objects;

import java.util.LinkedList;
import java.util.List;

public class Player {
	private String playerName;
	private LinkedList<Card> playerCards;
	
	public Player(String data) {
		this.playerName = data;
		this.playerCards = new LinkedList<Card>();
	}

	public String getPlayerName() {	
		return playerName;
	}

	public LinkedList<Card> getPlayerCards() {
		return playerCards;
	}

	@Override
	public String toString() {
		return playerName;
	}
	
}
