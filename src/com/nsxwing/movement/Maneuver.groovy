package com.nsxwing.movement

import com.nsxwing.gamestate.Coordinate
import com.nsxwing.gamestate.Position

/**
 * Abstract class for describing a Maneuver object. Contains helpers that translate from local coordinates to global coordinates.
 */
abstract class Maneuver {

    public abstract Position move(Position position, Direction direction)

    Position rotatePosition(Position position, double radiansCW) {
        new Position(
                center: (new Coordinate(
                        x: position.center.x * Math.cos(radiansCW) + position.center.y * Math.sin(radiansCW),
                        y: -position.center.x * Math.sin(radiansCW) + position.center.y * Math.cos(radiansCW))),
                heading: position.heading)
    }
}
