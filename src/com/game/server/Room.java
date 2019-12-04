package com.game.server;

import java.util.Arrays;

public class Room {

	int roomNumber = 0;
	int numberOfOccupants = 0;
	int[] adjacentRooms;
	int[] roomSightlines;
	String roomFlavor = "ROOM FLAVOR TEXT";

	// GET FUNCTIONS

	public int getRoomNumber() {
		return roomNumber;
	}

	public int getNumberOfOccupants() {
		return numberOfOccupants;
	}

	public String getRoomFlavor() {
		return roomFlavor;
	}

	public int[] getAdjacentRooms() {
		return adjacentRooms;
	}
	
	public int[] getRoomSightlines() {
		return roomSightlines;
	}
	
	public int getNumberOfSightlines() {
		
		return roomSightlines.length;
	}

	// SET FUNCTIONS

	public void setRoomNumber(int newVal) {
		roomNumber = newVal;
	}

	public void setNumberOfOccupants(int newVal) {
		numberOfOccupants = newVal;
	}

	public void setRoomFlavor(String newVal) {
		roomFlavor = newVal;
	}

	public void setAdjacentRooms(int[] adjacencies) {
		adjacentRooms = adjacencies;
	}
	
	public void setRoomSightlines(int[] sightlines) {
		roomSightlines = sightlines;
	}

	// MODIFIERS

	public void addOccupant() {
		numberOfOccupants++;
	}

	public void removeOccupant() {
		numberOfOccupants--;
	}

	public void addOccupant(int numberOfPeople) {
		numberOfOccupants = numberOfOccupants + numberOfPeople;
	}

	// DIAGNOSTIC PRINT

	public void print() {

		System.out.println("ROOM NUMBER: " + roomNumber);
		System.out.println("ROOM FLAVOR: " + roomFlavor);
		System.out.println("NUMBER OF OCCUPANTS: " + numberOfOccupants);
		System.out.println("ADJACENT ROOMS: " + Arrays.toString(adjacentRooms));
		System.out.println("ROOM SIGHTLINES: " + Arrays.toString(roomSightlines));
		System.out.println();
	}
}
