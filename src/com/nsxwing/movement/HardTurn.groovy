package com.nsxwing.movement

import com.nsxwing.gamestate.Position

/**
 * Implementation of the Hard Turn that goes 1-3 distance.
 */
public class HardTurn implements Movement {

    private final int distance

    HardTurn(int distance) {
        this.distance = distance
    }

    @Override
    public void move(Position position) {

    }
}
