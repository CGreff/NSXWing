package com.nsxwing.movement

import com.nsxwing.gamestate.Position

/**
 * Implementation of Maneuver that does a K-turn with distance 1-5 (due to pilot abilities).
 */
public class Koiogran implements Maneuver {

    private final int distance
    private final ManeuverDifficulty difficulty

    Koiogran(int distance, ManeuverDifficulty difficulty) {
        this.distance = distance
        this.difficulty = difficulty
    }

    @Override
    public void move(Position position, Direction direction) {

    }
}
