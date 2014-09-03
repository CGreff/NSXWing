package com.nsxwing.movement

import com.nsxwing.gamestate.Position

/**
 * Implementation of Maneuver that does a 45 degree Bank turn, distance 1-3.
 */
public class BankTurn implements Maneuver {

    private final int distance
    private final ManeuverDifficulty difficulty

    BankTurn(int distance, ManeuverDifficulty difficulty) {
        this.distance = distance
        this.difficulty = difficulty
    }

    @Override
    public void move(Position position) {

    }
}