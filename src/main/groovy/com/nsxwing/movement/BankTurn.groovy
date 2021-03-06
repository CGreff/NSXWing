package com.nsxwing.movement

import com.nsxwing.gamestate.field.Coordinate
import com.nsxwing.gamestate.field.Position

/**
 * Implementation of Maneuver that does a 45 degree Bank turn, distance 1-3.
 */
public class BankTurn extends Maneuver {

    double radius

    BankTurn(int distance, ManeuverDifficulty difficulty, Direction direction) {
        this.radius = 64 + (distance * 55)
        this.difficulty = difficulty
        this.direction = direction
    }

    @Override
    public Position move(Position position) {
        Position localPosition = rotatePosition(position, -position.heading)
        double heading = (direction == Direction.LEFT) ? -Math.PI / 4 : Math.PI / 4

        double theta = (direction == Direction.LEFT) ? Math.PI / 4 : 3 * Math.PI / 4
        double xModifier = (direction == Direction.LEFT) ? localPosition.center.x - radius : localPosition.center.x + radius
        double yModifier = localPosition.center.y

        Position newLocalPosition = new Position(
                center: new Coordinate(
                        x: (radius * Math.cos(theta) + xModifier),
                        y: (radius * Math.sin(theta) + yModifier),
                ),
                heading: (localPosition.heading + heading) % (2 * Math.PI))
        rotatePosition(newLocalPosition, position.heading)
    }
}