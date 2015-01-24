package com.nsxwing.movement

import com.nsxwing.gamestate.field.Coordinate
import com.nsxwing.gamestate.field.Position

/**
 * Implementation of Maneuver that goes forward 1-5 distance.
 */
public class Forward extends Maneuver {

    double moveDistance
    ManeuverDifficulty difficulty

    Forward(int distance, ManeuverDifficulty difficulty) {
        this.moveDistance = distance * 40 + 40
        this.difficulty = difficulty
    }

    @Override
    public Position move(Position position, Direction direction) {
        Position localPosition = rotatePosition(position, -position.heading)
        Position newLocalPosition = new Position(
                center: new Coordinate(x: localPosition.center.x, y: localPosition.center.y + moveDistance),
                heading: localPosition.heading
        )
        rotatePosition(newLocalPosition, position.heading)
    }
}