package com.nsxwing.movement

import com.nsxwing.gamestate.Coordinate
import com.nsxwing.gamestate.Position

/**
 * Implementation of the Hard Turn that goes 1-3 distance.
 */
public class HardTurn extends Maneuver {

    private final int distance
    private final ManeuverDifficulty difficulty

    HardTurn(int distance, ManeuverDifficulty difficulty) {
        this.distance = distance
        this.difficulty = difficulty
    }

    @Override
    public Position move(Position position, Direction direction) {
        Position localPosition
        double heading = Math.PI / 2
        double xModifier = distance * 28
        double yModifier = distance * 26
        if (direction == Direction.LEFT) {
            localPosition = new Position(
                    lowerLeft: new Coordinate(x: 11 - xModifier, y: 30 + yModifier),
                    lowerRight: new Coordinate(x: -29 - xModifier, y: 70 + yModifier),
                    upperLeft: new Coordinate(x: -29 - xModifier, y: -10 + yModifier),
                    upperRight: new Coordinate(x: -69 - xModifier, y: 30 + yModifier),
                    heading: heading
            )
        } else {
            localPosition = new Position(
                    lowerLeft: new Coordinate(x: 29 + xModifier, y: 70 + yModifier),
                    lowerRight: new Coordinate(x: -11 + xModifier, y: 30 + yModifier),
                    upperLeft: new Coordinate(x: 69 + xModifier, y: 30 + yModifier),
                    upperRight: new Coordinate(x: 29 + xModifier, y: -10 + yModifier),
                    heading: heading
            )
        }

        translateCoordinates(position, localPosition)
    }
}
