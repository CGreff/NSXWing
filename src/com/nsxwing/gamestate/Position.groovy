package com.nsxwing.gamestate

/**
 * Marks a given ship position using the coordinates of its 4 corners.
 */
public class Position {
    Coordinate lowerLeft
    Coordinate lowerRight
    Coordinate upperLeft
    Coordinate upperRight
    //in Radians, measured CCW from X axis
    double heading

    public Coordinate getCenter() {
        new Coordinate(x: (lowerLeft.x + lowerRight.x + upperLeft.x + upperRight.x)/4,
                       y: (lowerLeft.y + lowerRight.y + upperLeft.y + upperRight.y)/4)
    }
}
