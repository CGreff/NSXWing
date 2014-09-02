package com.nsxwing.movement

import com.nsxwing.gamestate.Position

/**
 * Implementation of Movement that goes forward 1-5 distance.
 */
public class Forward implements Movement {

    private final int distance

    Forward(int distance) {
        this.distance = distance
    }

    @Override
    public void move(Position position) {

    }
}