package com.nsxwing.movement

import com.nsxwing.gamestate.Coordinate
import com.nsxwing.gamestate.Position

/**
 * Abstract class for describing a Maneuver object. Contains helpers that translate from local coordinates to global coordinates.
 */
abstract class Maneuver {

    protected static final List<String> COORDINATE_IDENTIFIERS = ['lowerLeft', 'lowerRight', 'upperLeft', 'upperRight'].asImmutable()

    public abstract Position move(Position position, Direction direction)

    Position translateCoordinates(Position originalPosition, Position localPosition) {
        Position newPosition = new Position()
        for (String field : COORDINATE_IDENTIFIERS) {
            double x = originalPosition.lowerLeft.x + (localPosition."$field".x - originalPosition.lowerLeft.x) * Math.cos(originalPosition.heading) + (localPosition."$field".y - originalPosition.lowerLeft.y) * Math.sin(originalPosition.heading)
            double y = originalPosition.lowerLeft.y - (localPosition."$field".x - originalPosition.lowerLeft.x) * Math.sin(originalPosition.heading) - (localPosition."$field".y - originalPosition.lowerLeft.y) * Math.cos(originalPosition.heading)
            newPosition."$field" = new Coordinate(x: x, y: y)
        }

        newPosition.setHeading((originalPosition.heading + localPosition.heading) % (2 * Math.PI))
        newPosition
    }

    Position rotateBoard(double cwRadians) {

    }

//        newX = centerX + (point2x-centerX)*Math.cos(x) - (point2y-centerY)*Math.sin(x);
//        newY = centerY + (point2x-centerX)*Math.sin(x) + (point2y-centerY)*Math.cos(x);
    Position getPositionFromCenterPoint(Coordinate center, double cwRadians, int shipSize = 40) {
        Position centerPosition = new Position(lowerLeft: center, heading: cwRadians)
        Position positionAtThetaZero = new Position(lowerLeft: new Coordinate(x: center.x - shipSize/2, y: center.y - shipSize/2),
                                                    lowerRight: new Coordinate(x: center.x + shipSize/2, y: center.y - shipSize/2),
                                                    upperLeft: new Coordinate(x: center.x - shipSize/2, y: center.y + shipSize/2),
                                                    upperRight: new Coordinate(x: center.x + shipSize/2, y: center.y + shipSize/2),
                                                    heading: 0)
        translateCoordinates(centerPosition, positionAtThetaZero)
    }
}
