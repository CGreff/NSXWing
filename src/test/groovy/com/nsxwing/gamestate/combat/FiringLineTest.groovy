package com.nsxwing.gamestate.combat

import com.nsxwing.gamestate.field.Coordinate
import org.junit.Before
import org.junit.Test

/**
 *
 */
class FiringLineTest {

    FiringLine firingLine

    @Before
    void setUp() {
        firingLine = new FiringLine(originX: 0, originY: 0, lineX: 20, lineY: 20)
    }

    @Test
    void 'should return true when calling isLeftOfLine for a point left of the firing line.'() {
        Coordinate coord = new Coordinate(x: 20, y: 100)
        assert firingLine.isLeftOfLine(coord)
        assert !firingLine.isRightOfLine(coord)
    }

    @Test
    void 'should return true when calling isRightOfLine for a point right of the firing line.'() {
        Coordinate coord = new Coordinate(x: 40, y: 30)
        assert !firingLine.isLeftOfLine(coord)
        assert firingLine.isRightOfLine(coord)
    }

    @Test
    void 'should return true for both directions for a point on the firing line.'() {
        Coordinate coord = new Coordinate(x: 20, y: 20)
        assert firingLine.isLeftOfLine(coord)
        assert firingLine.isRightOfLine(coord)
    }

    @Test
    void 'should return correctly when the line has a negative direction.'() {
        firingLine = new FiringLine(originX: 0, originY: 0, lineX: 0, lineY: -28.2842712474619)
        Coordinate coord = new Coordinate(x: 10, y: -20)
        assert firingLine.isLeftOfLine(coord)
        assert !firingLine.isRightOfLine(coord)

        coord = new Coordinate(x: -10, y: -20)
        assert !firingLine.isLeftOfLine(coord)
        assert firingLine.isRightOfLine(coord)
    }

    @Test
    void 'should behave properly when the firing line is not 0 centered.'() {
        firingLine = new FiringLine(originX: 50, originY: 50, lineX: 50, lineY: 78.2842712474619)
        Coordinate coord = new Coordinate(x: 60, y: 20)
        assert !firingLine.isLeftOfLine(coord)
        assert firingLine.isRightOfLine(coord)
    }
}
