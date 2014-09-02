package com.nsxwing.movement

import com.nsxwing.gamestate.Position

/**
 * Implementation of Movement that does a 45 degree Bank turn, distance 1-3.
 */
public class BankTurn implements Movement {

    private final int distance

    BankTurn(int distance) {
        this.distance = distance
    }

    @Override
    public void move(Position position) {

    }
}