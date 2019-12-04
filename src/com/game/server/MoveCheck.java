package com.game.server;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

public class MoveCheck {

    public static boolean isMoveValid(ArrayList<Room> rooms, ArrayList<Player> player, int desiredRoom, int activeTurn) {
        boolean isMoveValid = false;

        int[] adjacentRooms = rooms.get(player.get(activeTurn).getLocation()).getAdjacentRooms();

        for (int i = 0; i < adjacentRooms.length; i++) {
            if (adjacentRooms[i] == desiredRoom) {
                isMoveValid = true;
                System.out.println("MOVE IS VALID, PROCEED");
            }else System.out.println("ERROR! INVALID MOVE!");

        }

        return isMoveValid;

    }

}