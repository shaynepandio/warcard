package com.shayne.warcard.objects;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Game {
	private static Scanner sc = new Scanner(System.in);

	public static Suit findSuit(String suitName) {
		for (Suit suit : Suit.values()) {
			if (suit.getSuitName().equals(suitName)) {
				return suit;
			}
		}
		return null;
	}

	public static Rank findRank(String rankName) {
		for (Rank rank : Rank.values()) {
			if (rank.getRankName().equals(rankName)) {
				return rank;
			}
		}
		return null;
	}

	public static LinkedList<Card> shuffleDeck(int numOfShuffle, LinkedList<Card> deck) {
		LinkedList<Card> temporaryDeck = new LinkedList<>();
		LinkedList<Card> shuffledDeck = new LinkedList<>();

		temporaryDeck.addAll(deck);

		int shuffleMade = 0;
		while (shuffleMade < numOfShuffle) {

			shuffledDeck = new LinkedList<Card>();

			int half = deck.size() / 2;
			for (int i = 0; i < half; i++) {
				shuffledDeck.add(temporaryDeck.get(i));
				shuffledDeck.add(temporaryDeck.get(i + half));
			}
			temporaryDeck.clear();
			temporaryDeck.addAll(shuffledDeck);
			shuffleMade++;
		}
		return shuffledDeck;

	}

	public static LinkedList<Player> addPlayer(int numOfPlayers) {
		LinkedList<Player> playerList = new LinkedList<>();
		for (int i = 0; i < numOfPlayers; i++) {
			playerList.add(new Player("Player " + (i + 1) + ""));
			System.out.println("Added Player [" + (i + 1) + "]");
		}
		return playerList;
	}

	public static void giveCardToPlayer(LinkedList<Player> playerList, LinkedList<Card> shuffledDeck) {

		do {
			for (int i = 0; i < playerList.size(); i++) {
				if (shuffledDeck.isEmpty()) {
					break;
				} else {
					playerList.get(i).getPlayerCards().add(shuffledDeck.removeFirst());
				}
			}
		} while (!shuffledDeck.isEmpty());

		System.out.println("INITIAL DECK:");
		for (Player player : playerList) {
			System.out.println(player.getPlayerName() + ": " + player.getPlayerCards());
		}
	}

	public static LinkedList<Card> getTheTopCard(LinkedList<Player> playerList) {
		LinkedList<Card> topCardList = new LinkedList<>();

		for (Player player : playerList) {
			topCardList.add(player.getPlayerCards().remove(0));
		}
		return topCardList;
	}

	public static boolean isTheCardHigher(Card c1, Card c2) {
		if (c1.getSuit().getSuitValue() > c2.getSuit().getSuitValue()) {
			return true;
		} else if (c1.getSuit().getSuitValue() == c2.getSuit().getSuitValue()) {
			return c1.getRank().getRankValue() > c2.getRank().getRankValue();
		} else {
			return false;
		}

	}

	public static int findTheHighestCard(LinkedList<Card> topCardList) {

		int tempIndex = 0;

		for (int i = 1; i < topCardList.size(); i++) {
			if (!isTheCardHigher(topCardList.get(tempIndex), topCardList.get(i))) {
				tempIndex = i;
				// System.out.println("The highest card is index [" + tempIndex + "] " +
				// topCardList.get(tempIndex) + ".");
			}
			System.out.println("The highest card is index [" + tempIndex + "] " + topCardList.get(tempIndex) + ".");
		}
		System.out.println("2nd The highest card is " + topCardList.get(tempIndex) + ".");
		return tempIndex;
	}

	public static int inputGameData(String input, int min, int max) {

		boolean isCorrect = false;
		int value = 0;

		do {
			System.out.println("Please enter number of " + input + " between [" + min + "-" + max + "] :");
			if (sc.hasNextInt()) {
				value = sc.nextInt();
				sc.nextLine();
				if (value >= min && value <= max) {
					isCorrect = true;
				} else {
					System.out.println("Invalid input. Please enter between" + "[" + min + "-" + max + "] :");
				}
			} else {
				sc.nextLine();
				System.out.println("Invalid input. Input must be an integer.");
			}
		} while (!isCorrect);
		return value;
	}

	public static void playGame(int numOfPlayers, LinkedList<Player> playerList) throws FileNotFoundException {

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
			System.out.println("====================");
			// topCardList.clear();

			for (int i = 0; i < playerList.size(); i++) {
				if (playerList.get(i).getPlayerCards().isEmpty()) {
					playerList.remove(i);
					i = 0;
				}
			}

			playerList.get(winnerIndex).getPlayerCards().addAll(addCardToWinner(winnerIndex, topCardList));
			topCardList.clear();
			round++;
		} while (playerList.size() > 1);
		System.out.println("Game Over...  The winner is " + playerList.get(0).getPlayerName() + "."
				+ "\nWinning Deck : " + playerList.get(0).getPlayerCards());
	}

	private static LinkedList<Card> addCardToWinner(int winnerIndex, LinkedList<Card> topCardList) {

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

	public static void start() throws FileNotFoundException {

		System.out.println("Welcome to WarCard \n");

		GameDetails.setTheGame();
	}
}