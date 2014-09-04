package com.nsxwing.movement

import com.nsxwing.gamestate.Coordinate
import com.nsxwing.gamestate.Position

/**
 * Implementation of Maneuver that goes forward 1-5 distance.
 */
public class Forward extends Maneuver {

    private final int distance
    private final ManeuverDifficulty difficulty

    Forward(int distance, ManeuverDifficulty difficulty) {
        this.distance = distance
        this.difficulty = difficulty
    }

    @Override
    public Position move(Position position, Direction direction) {
        final double moveDistance = distance * 40 + 40
        Position localPosition = new Position(
                lowerLeft: new Coordinate(x: 0, y: moveDistance),
                lowerRight: new Coordinate(x: 40, y: moveDistance),
                upperLeft: new Coordinate(x: 0, y: moveDistance + 40),
                upperRight: new Coordinate(x: 40, y: moveDistance + 40),
                heading: 0
        )

        translateCoordinates(position, localPosition)
    }
}