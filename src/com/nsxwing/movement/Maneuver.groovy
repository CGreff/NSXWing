package com.nsxwing.movement

import com.nsxwing.gamestate.Coordinate
import com.nsxwing.gamestate.Position

/**
 * Abstract class for describing a Maneuver object. Contains helpers that translate from local coordinates to global coordinates.
 */
abstract class Maneuver {

    private static final List<String> COORDINATE_IDENTIFIERS = ['lowerLeft', 'lowerRight', 'upperLeft', 'upperRight'].asImmutable()

    public abstract Position move(Position position, Direction direction)

    Position translateCoordinates(Position originalPosition, Position localPosition) {
        Position newPosition = new Position()
        for (String field : COORDINATE_IDENTIFIERS) {
            double x = localPosition."$field".x * Math.cos(originalPosition.heading) + localPosition."$field".y * Math.sin(originalPosition.heading)
            double y = -(localPosition."$field".x) * Math.sin(originalPosition.heading) + localPosition."$field".y * Math.cos(originalPosition.heading)
            newPosition."$field" = new Coordinate(x: x, y: y)
        }

        newPosition.setHeading((originalPosition.heading + localPosition.heading) % (2 * Math.PI))
        newPosition
    }
}
