package com.game.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadRoom {

	public static ArrayList<Room> compileMansion() throws FileNotFoundException {
		//Room[] rooms = new Room[32];
		ArrayList<Room> rooms = new ArrayList<>();
		Scanner mapIn = new Scanner(new File("./assets/rooms.txt"));

		for (int i = 0; mapIn.hasNext(); i++) {
			rooms.add(i, new Room());
			rooms.get(i).setRoomNumber(mapIn.nextInt());
			mapIn.nextLine();
			String adjacentRooms = mapIn.nextLine();
			String[] adjacentStringArray = adjacentRooms.split(",");
			int[] adjacentIntArray = new int[adjacentStringArray.length];
			for (int i2 = 0; i2 < adjacentStringArray.length; i2++) {
				String numberAsString = adjacentStringArray[i2];
				adjacentIntArray[i2] = Integer.parseInt(numberAsString);
			}
			rooms.get(i).setAdjacentRooms(adjacentIntArray);

			String sightlinesString = mapIn.nextLine();
			String[] sightlinesStringArray = sightlinesString.split(",");
			int[] sightlinesIntArray = new int[sightlinesStringArray.length];
			for (int i2 = 0; i2 < sightlinesStringArray.length; i2++) {
				String numberAsString = sightlinesStringArray[i2];
				sightlinesIntArray[i2] = Integer.parseInt(numberAsString);
			}
			rooms.get(i).setRoomSightlines(sightlinesIntArray);

			rooms.get(i).setRoomFlavor(mapIn.nextLine());
			// System.out.println("LOADED:");
			rooms.get(i).print();
		}
		mapIn.close();

		return rooms;
	}
}
