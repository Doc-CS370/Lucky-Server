package com.game.server;

import java.util.Arrays;

public class Player {

    Card[] playerHand = new Card[10];
    int handIndex = 0; //SHOULD ALWAYS BE LOCATED ON A NULL CARD VALUE
    int spiteTokens = 0;
    int playerLocation = 0;
    int turnsLeft = 0;
    String name;
    boolean isAlive = false;

    public Card[] getPlayerHand() {
        return playerHand;
    }

    public void setAlive() {
        isAlive = true;

    }

    public boolean getStatus() {

        return isAlive;
    }

    public void setName(String newName) {

        name = newName;
    }

    public String getName() {

        return name;
    }

    public void setLocation(int newVal) {

        playerLocation = newVal;
    }

    public int getLocation() {

        return playerLocation;
    }

    public void endTurn() {

        turnsLeft--;
    }

    public int getTurnsLeft() {

        return turnsLeft;
    }

    public void addTurns(int val) {

        turnsLeft = turnsLeft + val;

    }

    public void addHandIndex() {

        handIndex++;
    }

    public void decHandIndex() {

        handIndex--;
    }

    public void addCard(Card cardToAdd) {
        playerHand[handIndex] = cardToAdd;
        handIndex++;

    }

    public void removeCard(Card cardToRemove) {

        for (int i = 0; i < 10; i++) {
            if(playerHand[i].equals(cardToRemove)) {
                int mark = i;
                while(mark < 9) {
                    playerHand[mark] = playerHand[mark+1];
                    mark++;
                }
                playerHand[9] = new Card(); //Can be default card because cardStatus by default is 0
                handIndex--;
                i = 10;
            }


        }

    }

    public void testHand() {

        //THIS IS JUST TO FORCE CARDS INTO SOMEONES HAND
        //playerHand[0] = 78;
        //playerHand[1] = 79;
        //playerHand[2] = 1;
        handIndex = 3;
    }
    public void print() {
        System.out.println("/////////////////////////////////////////////////");
        System.out.println("CURRENT PLAYER IS: " + name + " IS " + isAlive);
        System.out.println("HOLDING CARDS: " + Arrays.toString(playerHand));
        System.out.println("WITH AN INDEX OF: " + handIndex);
        System.out.println("WITH " + spiteTokens + " SPITE TOKENS");
        System.out.println("CURRENTLY IN ROOM: " + playerLocation);
        System.out.println("WITH " + turnsLeft + " TURNS LEFT");
        System.out.println("/////////////////////////////////////////////////");

    }
}
