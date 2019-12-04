package com.game.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CardLoad {

	public static ArrayList<Card> compiledeck() throws FileNotFoundException {
		//Card[] deck = new Card[98];
		ArrayList<Card> deck = new ArrayList<>();
		Scanner deckIn = new Scanner(new File("./assets/cards.txt"));

		deck.add(0, new Card());

		deck.get(0).setCardNumber(0);
		deck.get(0).setCardType(999);
		deck.get(0).setCardValue(999);
		deck.get(0).setCardSpecialValue(999);
		deck.get(0).setCardLocation(999);
		deck.get(0).setCardFlavor("TEST Card");
		deck.get(0).print();
		for (int i = 1; deckIn.hasNext(); i++) {
			deck.add(i, new Card());
			deck.get(i).setCardNumber(deckIn.nextInt());
			deckIn.nextLine();
			deck.get(i).setCardType(deckIn.nextInt());
			deckIn.nextLine();
			deck.get(i).setCardValue(deckIn.nextInt());
			deckIn.nextLine();
			deck.get(i).setCardSpecialValue(deckIn.nextInt());
			deckIn.nextLine();
			deck.get(i).setCardLocation(deckIn.nextInt());
			deckIn.nextLine();
			deck.get(i).setCardFlavor(deckIn.nextLine());

			//System.out.println("LOADED: ");
			deck.get(i).print();

		}
		deckIn.close();

		return deck;
	}

}
