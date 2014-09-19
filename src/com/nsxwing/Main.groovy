package com.nsxwing

import com.nsxwing.gamestate.Coordinate
import com.nsxwing.gamestate.Position
import com.nsxwing.movement.BankTurn
import com.nsxwing.movement.Direction
import com.nsxwing.movement.Forward
import com.nsxwing.movement.HardTurn
import com.nsxwing.movement.Maneuver
import com.nsxwing.movement.ManeuverDifficulty
import com.nsxwing.utilities.PilotConstructor

public class Main {

    public static void main(String[] args) {
        //Testing Grounds because I'm a bad person.
        System.out.println(PilotConstructor.getPilot('Academy Pilot'))
        Position startPos = new Position(
                lowerLeft: new Coordinate(x: 0, y: 40),
                lowerRight: new Coordinate(x: 40, y: 40),
                upperLeft: new Coordinate(x: 0, y: 80),
                upperRight: new Coordinate(x: 40, y: 80),
                heading: Math.PI/2
        )
        Maneuver maneuver = new HardTurn(3, ManeuverDifficulty.WHITE)
        Position position = maneuver.move(startPos, Direction.RIGHT)
        position = maneuver.getPositionFromCenterPoint(new Coordinate(x: 60, y:20), -Math.PI/2)
        System.out.println("p1: ${position.lowerLeft.x},${position.lowerLeft.y}; p2: ${position.lowerRight.x},${position.lowerRight.y}; p3: ${position.upperLeft.x},${position.upperLeft.y}; p4: ${position.upperRight.x},${position.upperRight.y}" +
                " heading: ${position.heading}")
    }
}
