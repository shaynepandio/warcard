package com.shayne.warcard.objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

import com.shayne.warcard.constants.Rank;
import com.shayne.warcard.constants.Suit;

public class GameDetails {
	private Scanner sc = new Scanner(System.in);
	private int numOfShuffle = 0;
	private int numOfPlayers = 0;

	public LinkedList<Card> createDeck() {

		LinkedList<Card> deck = new LinkedList<>();
		boolean hasTokenized = false;
		Scanner scanner = null;

		File file = new File("src/input.txt");
		if (file.exists()) {
			try {
				scanner = new Scanner(file);
				while (scanner.hasNextLine()) {
					String cardFile = scanner.nextLine();

					String[] cardFiles = cardFile.split(",");
					for (String cardData : cardFiles) {
						String pair[] = cardData.split("-");
						Suit suit = findSuit(pair[0]);
						Rank rank = findRank(pair[1]);

						Card card = new Card();
						card.setSuit(suit);
						card.setRank(rank);
						deck.add(card);
						hasTokenized = true;
					}
				}
			} catch (FileNotFoundException e) {

			}
		} else {
			do {
				System.out.println("Please enter input file path: ");
				String file1 = sc.nextLine();
				File inputFile = new File(file1); // File inputFile = new File("C:\\Users\\Susan\\Desktop\\input.txt");\
				try {
					scanner = new Scanner(inputFile);
					if (inputFile.exists()) {
						while (scanner.hasNextLine()) {
							String cardFile = scanner.nextLine();
							String[] cardFiles = cardFile.split(",");
							for (String cardData : cardFiles) {
								String pair[] = cardData.split("-");
								Suit suit = findSuit(pair[0]);
								Rank rank = findRank(pair[1]);

								Card card = new Card();
								card.setSuit(suit);
								card.setRank(rank);
								deck.add(card);
								hasTokenized = true;
							}
						}
					}
				} catch (FileNotFoundException e) {
					System.out.println("File not found.");
				}
			} while (!hasTokenized);
		}
		
		scanner.close();
		System.out.println("Initial deck: \n" + deck);
		return deck;
	}


	public LinkedList<Player> setTheGame() throws FileNotFoundException {

		LinkedList<Card> deck = createDeck();
		LinkedList<Card> newShuffledDeck = new LinkedList<>();
		boolean flag = false;

		do {
			System.out.println("Select type of shuffle: \n [1] Random Shuffle \n [2] Faroshuffle");

			if (sc.hasNextInt()) {
				int num = sc.nextInt();
				if (num == 1) {
					newShuffledDeck = deck;
					Collections.shuffle(newShuffledDeck);
					flag = true;
				} else if (num == 2) {
					numOfShuffle = inputGameData("shuffle", 0, 7);
					if (numOfShuffle == 0) {
						newShuffledDeck = deck;
						flag = true;
					} else {
						newShuffledDeck = shuffleDeck(numOfShuffle, deck);
						flag = true;
					}
				} else {
					System.out.println("Input must be 1 or 2 only.");
				}
			} else {
				sc.nextLine();
				System.out.println("Wrong input.");
			}
		} while (!flag);

		System.out.println("SHUFFLED DECK:");
		System.out.println(newShuffledDeck);

		numOfPlayers = inputGameData("players", 2, 52);
		LinkedList<Player> playerList = addPlayer(numOfPlayers);
		giveCardToPlayer(playerList, newShuffledDeck);

		sc.close();
		return playerList;
	}

	public LinkedList<Card> shuffleDeck(int numOfShuffle, LinkedList<Card> deck) {
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

	public LinkedList<Player> addPlayer(int numOfPlayers) {
		LinkedList<Player> playerList = new LinkedList<>();
		for (int i = 0; i < numOfPlayers; i++) {
			playerList.add(new Player("Player " + (i + 1) + ""));
			System.out.println("Added Player [" + (i + 1) + "]");
		}
		return playerList;
	}

	public void giveCardToPlayer(LinkedList<Player> playerList, LinkedList<Card> shuffledDeck) {

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

	public int inputGameData(String input, int min, int max) {


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
					System.out.println("Invalid input. Please enter between " + "[" + min + "-" + max + "].");
				}
			} else {
				System.out.println("Invalid input. Input must be an integer.");
				sc.nextLine();
			}
		} while (!isCorrect);

		return value;

	}

	public Suit findSuit(String suitName) {
		for (Suit suit : Suit.values()) {
			if (suit.getSuitName().equals(suitName)) {
				return suit;
			}
		}
		return null;
	}

	public Rank findRank(String rankName) {
		for (Rank rank : Rank.values()) {
			if (rank.getRankName().equals(rankName)) {
				return rank;
			}
		}
		return null;
	}

}