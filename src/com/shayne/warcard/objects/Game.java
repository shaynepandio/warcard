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
		int playerNum = 0;

		do {
			if (playerNum != playerList.size()) {
				for (playerNum = 0; playerNum < playerList.size(); playerNum++) {
					playerList.get(playerNum).getPlayerCards().add(shuffledDeck.getFirst());
					if (shuffledDeck.isEmpty()) {
						break;
					}
				}
			}
		} while (!shuffledDeck.isEmpty());

		System.out.println("INITIAL DECK:");
		for (Player player : playerList) {
			System.out.println("Player" + player.getPlayerName() + ": " + player.getPlayerCards());
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
		if (c1.getRank().getRankValue() == c2.getRank().getRankValue()) { 
			return c1.getSuit().getSuitValue() > c2.getSuit().getSuitValue(); 
		}
		return false;
	}

	public static int findTheHighestCard(LinkedList<Card> topCardList) {

		int tempIndex = 0;

		for (int i = 0; i < topCardList.size(); i++) {
			if (isTheCardHigher(topCardList.get(tempIndex), topCardList.get(i)))
				;
			tempIndex = i;
		}
		System.out.println("The highest card is " + tempIndex);
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

		System.out.println("WarCard");

		GameDetails.setTheGame();

		while (playerList.size() > 1) {
			System.out.println("Round " + round);
			System.out.println("====================");
			topCardList = getTheTopCard(playerList);
			System.out.println("Top cards: " + topCardList);
			int winnerIndex = findTheHighestCard(topCardList);
			playerList.get(winnerIndex).getPlayerCards().addAll(topCardList);
			System.out.println("The winner for Round # " + round + "is " + playerList.get(winnerIndex).getPlayerName());
			System.out.println("====================");
			topCardList.clear();

			for (int i = 0; i < playerList.size(); i++) {
				if (playerList.get(i).getPlayerCards().isEmpty()) {
					playerList.remove(i);
				}

			round++;
			}

			System.out.println("Game Over...  \n Winning Deck : \n");
			System.out.println("Player " + playerList.get(0).getPlayerName() + playerList.get(0).getPlayerCards());
		}
	}

	public static void start() throws FileNotFoundException {
		
		System.out.println("Welcome to WarCard \n");
				
		GameDetails.setTheGame();
}
}