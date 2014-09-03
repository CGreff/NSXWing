package com.nsxwing.movement

import com.nsxwing.gamestate.Position

/**
 * Interface implemented by the different types of movements.
 */
public interface Maneuver {
    public void move(Position position)
}
