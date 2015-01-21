package com.nsxwing.gamestate.field

import com.nsxwing.gamestate.field.Coordinate
import com.nsxwing.gamestate.field.Position
import org.junit.Before
import org.junit.Test

/**
 * Tests for the Position almost-POGO.
 */
class PositionTest {

    private Position startPosition
    private static final float EPSILON = 0.00001

    @Before
    void setUp() {
        startPosition = new Position(center: new Coordinate(x: 0, y: 0), heading: 0)
    }

    @Test
    void 'should get the correct box points for a center point at 0 radians clockwise'() {
        List<Coordinate> boxPoints = startPosition.getBoxPoints()

        assert Math.abs(boxPoints.get(0).x + 20) < EPSILON
        assert Math.abs(boxPoints.get(0).y - 20) < EPSILON
        assert Math.abs(boxPoints.get(1).x - 20) < EPSILON
        assert Math.abs(boxPoints.get(1).y - 20) < EPSILON
        assert Math.abs(boxPoints.get(2).x - 20) < EPSILON
        assert Math.abs(boxPoints.get(2).y + 20) < EPSILON
        assert Math.abs(boxPoints.get(3).x + 20) < EPSILON
        assert Math.abs(boxPoints.get(3).y + 20) < EPSILON
    }

    @Test
    void 'should get the correct box points for a center point at pi/4 radians clockwise'() {
        startPosition.heading = Math.PI/4
        List<Coordinate> boxPoints = startPosition.getBoxPoints()

        assert Math.abs(boxPoints.get(0).x) < EPSILON
        assert Math.abs(boxPoints.get(0).y - 28.284271247461902) < EPSILON
        assert Math.abs(boxPoints.get(1).x - 28.284271247461902) < EPSILON
        assert Math.abs(boxPoints.get(1).y) < EPSILON
        assert Math.abs(boxPoints.get(2).x) < EPSILON
        assert Math.abs(boxPoints.get(2).y + 28.284271247461902) < EPSILON
        assert Math.abs(boxPoints.get(3).x + 28.284271247461902) < EPSILON
        assert Math.abs(boxPoints.get(3).y) < EPSILON
    }

    @Test
    void 'should get the correct box points for a non-zero center point at pi/4 radians clockwise'() {
        startPosition.center = new Coordinate(x: 40, y: 80)
        startPosition.heading = Math.PI/4
        List<Coordinate> boxPoints = startPosition.getBoxPoints()

        assert Math.abs(boxPoints.get(0).x - 40) < EPSILON
        assert Math.abs(boxPoints.get(0).y - 108.284271247461902) < EPSILON
        assert Math.abs(boxPoints.get(1).x - 68.284271247461902) < EPSILON
        assert Math.abs(boxPoints.get(1).y - 80) < EPSILON
        assert Math.abs(boxPoints.get(2).x - 40) < EPSILON
        assert Math.abs(boxPoints.get(2).y - 51.7157287525381) < EPSILON
        assert Math.abs(boxPoints.get(3).x - 11.7157287525381) < EPSILON
        assert Math.abs(boxPoints.get(3).y - 80) < EPSILON
    }
}
