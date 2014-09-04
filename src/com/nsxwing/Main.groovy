package com.nsxwing

import com.nsxwing.gamestate.Coordinate
import com.nsxwing.gamestate.Position
import com.nsxwing.movement.BankTurn
import com.nsxwing.movement.Direction
import com.nsxwing.movement.Maneuver
import com.nsxwing.movement.ManeuverDifficulty
import com.nsxwing.utilities.PilotConstructor

public class Main {

    public static void main(String[] args) {
        //Testing Grounds because I'm a bad person.
        System.out.println(PilotConstructor.getPilot('Academy Pilot'))
        Position poo = new Position(
                lowerLeft: new Coordinate(x: 59.284, y: 180.284),
                lowerRight: new Coordinate(x: 49, y: 152),
                upperLeft: new Coordinate(x: 89, y: 168.568),
                upperRight: new Coordinate(x: 77.284, y: 140.284),
                heading: 0
        )
        Maneuver leavesButt = new BankTurn(3, ManeuverDifficulty.WHITE)
        Position toilet = leavesButt.move(poo, Direction.LEFT)
        System.out.println("p1: ${toilet.lowerLeft.x},${toilet.lowerLeft.y}; p2: ${toilet.lowerRight.x},${toilet.lowerRight.y}; p2: ${toilet.upperLeft.x},${toilet.upperLeft.y}; p4: ${toilet.upperRight.x},${toilet.upperRight.y}" +
                " heading: ${toilet.heading}")
    }
}
