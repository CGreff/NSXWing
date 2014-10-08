package com.nsxwing.movement

import com.nsxwing.gamestate.Coordinate
import com.nsxwing.gamestate.Position
import org.junit.Before
import org.junit.Test

/**
 * Test for Bank Turns
 */
class BankTurnTest {

    private Position startPosition
    private Maneuver maneuver
    private static final float EPSILON = 0.00001

    @Before
    void setUp() {
        startPosition = new Position(
                center: new Coordinate(x: 0, y: 0),
                heading: 0
        )

        maneuver = new BankTurn(1, ManeuverDifficulty.WHITE)
    }

    @Test
    void 'should end in correct spot when turning left from 0,0'() {
        Position position = maneuver.move(startPosition, Direction.LEFT)

        assert position.center.x + 34.85429303880083 < EPSILON
        assert position.center.y - 84.14570696119915 < EPSILON
        assert position.heading == -Math.PI / 4
    }

    @Test
    void 'should end in correct spot when turning right from 0,0'() {
        Position position = maneuver.move(startPosition, Direction.RIGHT)

        assert position.center.x - 34.85429303880083 < EPSILON
        assert position.center.y - 84.14570696119915 < EPSILON
        assert position.heading == Math.PI / 4
    }

    @Test
    void 'should end in correct spot when turning 2 right from 0,0'() {
        Position position = new BankTurn(2, ManeuverDifficulty.WHITE).move(startPosition, Direction.RIGHT)

        assert position.center.x - 50.963420073540746 < EPSILON
        assert position.center.y - 123.03657992645928 < EPSILON
        assert position.heading == Math.PI / 4
    }

    @Test
    void 'should end in correct spot when turning 3 right from 0,0'() {
        Position position = new BankTurn(3, ManeuverDifficulty.WHITE).move(startPosition, Direction.RIGHT)

        assert position.center.x - 67.07254710828065 < EPSILON
        assert position.center.y - 161.92745289171938 < EPSILON
        assert position.heading == Math.PI / 4
    }

    @Test
    void 'should end in correct spot when turning right after a 1 right'() {
        startPosition = new Position(
                center: new Coordinate(x: 34.85429303880083, y: 84.14570696119915),
                heading: Math.PI / 4
        )
        Position position = maneuver.move(startPosition, Direction.RIGHT)

        assert position.center.x - 119 < EPSILON
        assert position.center.y - 119 < EPSILON
        assert position.heading == Math.PI / 2
    }

    @Test
    void 'should end in correct spot when turning left after a 1 right'() {
        startPosition = new Position(
                center: new Coordinate(x: 34.85429303880083, y: 84.14570696119915),
                heading: Math.PI / 4
        )
        Position position = maneuver.move(startPosition, Direction.LEFT)

        assert position.center.x - 119 < EPSILON
        assert position.center.y - 168.2914139223983 < EPSILON
        assert position.heading == 0
    }
}
