package com.nsxwing.movement

import com.nsxwing.gamestate.Position

/**
 * Implementation of the Hard Turn that goes 1-3 distance.
 */
public class HardTurn implements Maneuver {

    private final int distance
    private final ManeuverDifficulty difficulty

    HardTurn(int distance, ManeuverDifficulty difficulty) {
        this.distance = distance
        this.difficulty = difficulty
    }

    @Override
    public void move(Position position, Direction direction) {

    }
}
