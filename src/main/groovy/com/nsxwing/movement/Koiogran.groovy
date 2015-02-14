package com.nsxwing.movement

import com.nsxwing.gamestate.field.Coordinate
import com.nsxwing.gamestate.field.Position

/**
 * Implementation of Maneuver that does a K-turn with distance 1-5 (due to pilot abilities).
 */
public class Koiogran extends Maneuver {

    double moveDistance

    Koiogran(int distance, ManeuverDifficulty difficulty) {
        this.moveDistance = distance * 40 + 40
        this.difficulty = difficulty
    }

    @Override
    public Position move(Position position) {
        Position localPosition = rotatePosition(position, -position.heading)
        Position newLocalPosition = new Position(
                center: new Coordinate(x: localPosition.center.x, y: localPosition.center.y + moveDistance),
                heading: (localPosition.heading + Math.PI) % (2 * Math.PI)
        )
        rotatePosition(newLocalPosition, position.heading)
    }
}
