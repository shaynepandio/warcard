package com.shayne.warcard.objects;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class Game {

	public LinkedList<Card> getTheTopCard(LinkedList<Player> playerList) {
		LinkedList<Card> topCardList = new LinkedList<>();

		for (Player player : playerList) {
			topCardList.add(player.getPlayerCards().remove(0));
		}
		return topCardList;
	}

	public boolean isTheCardHigher(Card c1, Card c2) {
		if (c1.getSuit().getSuitValue() > c2.getSuit().getSuitValue()) {
			return true;
		} else if (c1.getSuit().getSuitValue() == c2.getSuit().getSuitValue()) {
			return c1.getRank().getRankValue() > c2.getRank().getRankValue();
		} else {
			return false;
		}
	}

	public int findTheHighestCard(LinkedList<Card> topCardList) {

		int tempIndex = 0;

		for (int i = 1; i < topCardList.size(); i++) {
			if (!isTheCardHigher(topCardList.get(tempIndex), topCardList.get(i))) {
				tempIndex = i;
				// System.out.println("The highest card is index [" + tempIndex + "] " +
				// topCardList.get(tempIndex) + ".");
			}
			System.out.println("The highest card is " + topCardList.get(tempIndex) + ".");
		}
		return tempIndex;
	}

	private LinkedList<Card> addCardToWinner(int winnerIndex, LinkedList<Card> topCardList) {

		int i = winnerIndex;
		LinkedList<Card> sortedList = new LinkedList<>();

		while (i < topCardList.size()) {

			sortedList.add(topCardList.get(i++));

		}

		i = 0;

		while (i < winnerIndex) {

			sortedList.add(topCardList.get(i++));
		}

		return sortedList;
	}

	public void playGame(LinkedList<Player> playerList) throws FileNotFoundException {

		int round = 1;
		LinkedList<Card> topCardList = new LinkedList<>();

		do {
			System.out.println("Round " + round);
			System.out.println("====================");
			for (Player player : playerList) {
				System.out.println(player.getPlayerName() + ": " + player.getPlayerCards());
			}
			topCardList = getTheTopCard(playerList);
			System.out.println("Top cards: " + topCardList);
			int winnerIndex = findTheHighestCard(topCardList);
			// playerList.get(winnerIndex).getPlayerCards().addAll(topCardList);
			System.out.println(
					"The winner for Round # " + round + " is " + playerList.get(winnerIndex).getPlayerName() + ".");

			System.out.println(winnerIndex);

			System.out.println("====================");
			// topCardList.clear();
			playerList.get(winnerIndex).getPlayerCards().addAll(addCardToWinner(winnerIndex, topCardList));

			for (int i = 0; i < playerList.size(); i++) {
				if (playerList.get(i).getPlayerCards().isEmpty()) {
					playerList.remove(i);
					i = 0;
				}
			}

			topCardList.clear();
			round++;
		} while (playerList.size() > 1);
		System.out.println("Game Over...  The winner is " + playerList.get(0).getPlayerName() + "."
				+ "\nWinning Deck : " + playerList.get(0).getPlayerCards());
	}

	public void start() throws FileNotFoundException {

		System.out.println("Welcome to WarCard \n");

		GameDetails game = new GameDetails();
		playGame(game.setTheGame());

	}
}