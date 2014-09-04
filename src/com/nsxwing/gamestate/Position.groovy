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
}
