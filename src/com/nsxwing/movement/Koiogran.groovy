package com.nsxwing.movement

import com.nsxwing.gamestate.Position

/**
 * Implementation of Movement that does a K-turn with distance 1-5 (due to pilot abilities).
 */
public class Koiogran implements Movement {

    private final int distance

    Koiogran(int distance) {
        this.distance = distance
    }

    @Override
    public void move(Position position) {

    }
}
