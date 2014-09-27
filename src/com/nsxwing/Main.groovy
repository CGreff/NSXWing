package com.nsxwing

import com.nsxwing.gamestate.Coordinate
import com.nsxwing.gamestate.Position
import com.nsxwing.movement.Direction
import com.nsxwing.movement.HardTurn
import com.nsxwing.movement.Maneuver
import com.nsxwing.movement.ManeuverDifficulty
import com.nsxwing.utilities.PilotConstructor

public class Main {

    public static void main(String[] args) {
        //Testing Grounds because I'm a bad person.
        System.out.println(PilotConstructor.getPilot('Academy Pilot'))
        Position startPos = new Position(
                center: new Coordinate(x: 56, y: 0),
                heading: 0
        )
        Maneuver maneuver = new HardTurn(1, ManeuverDifficulty.WHITE)
        Position position = maneuver.move(startPos, Direction.LEFT)
        System.out.println("center: ${position.center.x}, ${position.center.y}; heading: ${position.heading}")
    }
}
