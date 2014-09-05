package com.nsxwing.movement

import com.nsxwing.gamestate.Coordinate
import com.nsxwing.gamestate.Position

/**
 * Implementation of Maneuver that does a 45 degree Bank turn, distance 1-3.
 */
public class BankTurn extends Maneuver {

    private final int distance
    private final ManeuverDifficulty difficulty

    BankTurn(int distance, ManeuverDifficulty difficulty) {
        this.distance = distance
        this.difficulty = difficulty
    }

    @Override
    public Position move(Position position, Direction direction) {
        Position localPosition
        double heading = Math.PI / 4
        double xModifier = distance * 15
        double yModifier = distance * 34
        if (direction == Direction.LEFT) {
            localPosition = new Position(
                    lowerRight: new Coordinate(x: -4 - xModifier, y: 50 + yModifier),
                    upperRight: new Coordinate(x: -14.284 - xModifier, y: 78.284 + yModifier),
                    lowerLeft: new Coordinate(x: -32.284 - xModifier, y: 38.284 + yModifier),
                    upperLeft: new Coordinate(x: -44 - xModifier, y: 66.569 + yModifier),
                    heading: heading
            )
        } else {
            localPosition = new Position(
                    upperLeft: new Coordinate(x: 14.284 + xModifier, y: 78.284 + yModifier),
                    lowerLeft: new Coordinate(x: 4 + xModifier, y: 50 + yModifier),
                    upperRight: new Coordinate(x: 44 + xModifier, y: 66.569 + yModifier),
                    lowerRight: new Coordinate(x: 32.284 + xModifier, y: 38.284 + yModifier),
                    heading: -(heading)
            )
        }

        translateCoordinates(position, localPosition)
    }
}