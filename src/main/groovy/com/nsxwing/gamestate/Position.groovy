package com.nsxwing.gamestate

/**
 * Marks a given ship position using the coordinates of its center.
 */
public class Position {
    Coordinate center
    //in Radians, measured CW from X axis
    double heading

    List<Coordinate> getBoxPoints() {
        List<Coordinate> boxPoints = [
                new Coordinate(x: -20, y: 20),
                new Coordinate(x: 20, y: 20),
                new Coordinate(x: 20, y: -20),
                new Coordinate(x: -20, y: -20)
        ]

        for(Coordinate point : boxPoints) {
            rotatePoint(point)
            point.x += center.x
            point.y += center.y
        }

        boxPoints
    }

    private void rotatePoint(Coordinate point) {
        double x = point.x
        double y = point.y
        point.x = x * Math.cos(heading) + y * Math.sin(heading)
        point.y = -x * Math.sin(heading) + y * Math.cos(heading)
    }
}
