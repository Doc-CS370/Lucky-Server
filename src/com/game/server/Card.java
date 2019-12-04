	/*
	 *PUBLIC SERVICE ANNOUNCEMENT
	 *The cards.txt file is formated as such:
	 *Card Number
	 *Card Type
	 *Card Value
	 *Card Special Value
	 *Card Special Location
	 *Card Flavor Text
	 */

package com.game.server;

public class Card {

	int cardType = 0;
	/*
	 * 1 = Failure card 
	 * 2 = Weapon card 
	 * 3 = Move card 
	 * 4 = Room card
	 */
	int cardStatus = 0;
	/*
	 * 0 = Not Drawn 
	 * 1 = In a players hand 
	 * 2 = Used and in discard pile 
	 * 3 = Used and removed from play
	 */

	int cardNumber = 0;
	int cardValue = 0;
	int cardSpecialValue = 0;
	int cardLocation = 0;
	private String cardFlavor = "CARD FLAVOR TEXT";

	// GET FUNCTIONS

	public int getCardNumber() {
		return cardNumber;
	}

	public int getCardType() {
		return cardType;
	}

	public int getCardValue() {
		return cardValue;
	}

	public int getCardSpecialValue() {
		return cardSpecialValue;
	}

	public int getCardLocation() {
		return cardLocation;
	}
	
	public int getCardStatus() {
		return cardStatus;
	}

	public String getCardFlavor() {
		return cardFlavor;
	}

	// SET FUNCTIONS

	public void setCardNumber(int newVal) {
		cardNumber = newVal;
	}

	public void setCardType(int newVal) {
		cardType = newVal;
	}

	public void setCardValue(int newVal) {
		cardValue = newVal;
	}

	public void setCardSpecialValue(int newVal) {
		cardSpecialValue = newVal;
	}

	public void setCardLocation(int newVal) {
		cardLocation = newVal;
	}

	public void setCardFlavor(String newVal) {
		cardFlavor = newVal;
	}
	public void setCardStatus(int newVal) {
		cardStatus = newVal;
	}
	
	//DIAGNOSTIC PRINT

	public void print() {

		System.out.println("CARD TYPE: " + cardType);
		System.out.println("CARD STATUS: " + cardStatus);
		System.out.println("CARD NUMBER: " + cardNumber);
		System.out.println("CARD VALUE: " + cardValue);
		System.out.println("CARD SPECIAL VALUE: " + cardSpecialValue);
		System.out.println("CARD LOCATION: " + cardLocation);
		System.out.println("CARD FLAVOR: " + cardFlavor);
		System.out.println();
		
	}

}
