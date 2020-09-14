package com.shayne.warcard.objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class GameDetails {
	private static Scanner sc = new Scanner(System.in);
	private static int numOfShuffle = 0;
	private static int numOfPlayers = 0;

	public static LinkedList<Card> createDeck() {

		LinkedList<Card> deck = new LinkedList<>();
		boolean hasTokenized = false;

		do {
			System.out.println("Please enter input file path: ");

			String file = sc.nextLine();
			File inputFile = new File(file); // File inputFile = new File("C:\\Users\\Susan\\Desktop\\input.txt");\
			try {
				sc = new Scanner(inputFile);
			} catch (FileNotFoundException e) {
				System.out.println("File not found.");
			}
			if (inputFile.exists()) {
				while (sc.hasNextLine()) {
					String cardFile = sc.nextLine();

					String[] cardFiles = cardFile.split(",");
					for (String cardData : cardFiles) {
						String pair[] = cardData.split("-");
						Suit suit = Game.findSuit(pair[0]);
						Rank rank = Game.findRank(pair[1]);

						Card card = new Card();
						card.setSuit(suit);
						card.setRank(rank);
						deck.add(card);
						hasTokenized = true;
					}
				}
			} else {
				System.out.println("Please enter input file path again: ");
				sc.nextLine();
			}
		} while (!hasTokenized);
		sc.close();
		return deck;
	}

	public static void setTheGame() throws FileNotFoundException {

		LinkedList<Card> deck = GameDetails.createDeck();
		LinkedList<Card> newShuffledDeck = new LinkedList<>();

		numOfShuffle = Game.inputGameData("shuffle", 0, 7);
		if(numOfShuffle == 0) {
			newShuffledDeck = deck;
		}else {
		newShuffledDeck = Game.shuffleDeck(numOfShuffle, deck);
		}
		
		System.out.println("SHUFFLED DECK:");
		System.out.println(newShuffledDeck);

		numOfPlayers = Game.inputGameData("players", 2, 52);
		LinkedList<Player> playerList = Game.addPlayer(numOfPlayers);
		Game.giveCardToPlayer(playerList, newShuffledDeck);
		Game.playGame(numOfPlayers, playerList);
	}

}
