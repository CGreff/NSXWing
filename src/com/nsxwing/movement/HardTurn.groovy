package com.nsxwing.movement

import com.nsxwing.gamestate.Coordinate
import com.nsxwing.gamestate.Position

/**
 * Implementation of the Hard Turn that goes 1-3 distance.
 */
public class HardTurn extends Maneuver {

    private final double radius
    private final ManeuverDifficulty difficulty

    HardTurn(int distance, ManeuverDifficulty difficulty) {
        this.radius = 28.28427124746191 + (distance * 28.28427124746191)
        this.difficulty = difficulty
    }

    @Override
    public Position move(Position position, Direction direction) {
        Position localPosition = rotatePosition(position, -position.heading)
        double heading = (direction == Direction.LEFT) ? -(3 * Math.PI / 2) : Math.PI / 2
        double xModifier = (direction == Direction.LEFT) ? localPosition.center.x - radius : localPosition.center.x + radius
        double yModifier = localPosition.center.y

        Position newLocalPosition = new Position(
                center: new Coordinate(
                        x: (radius * Math.cos(heading) + xModifier),
                        y: (radius * Math.sin(heading) + yModifier),
                ),
                heading: (localPosition.heading + heading) % (2 * Math.PI))
        rotatePosition(newLocalPosition, position.heading)
    }
}
