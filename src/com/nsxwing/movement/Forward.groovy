package com.nsxwing.movement

import com.nsxwing.gamestate.Position

/**
 * Implementation of Maneuver that goes forward 1-5 distance.
 */
public class Forward implements Maneuver {

    private final int distance
    private final ManeuverDifficulty difficulty

    Forward(int distance, ManeuverDifficulty difficulty) {
        this.distance = distance
        this.difficulty = difficulty
    }

    @Override
    public void move(Position position) {

    }
}