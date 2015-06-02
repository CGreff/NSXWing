package com.nsxwing.agents

import com.nsxwing.components.Faction
import com.nsxwing.components.meta.PlayerIdentifier
import com.nsxwing.gamestate.field.Coordinate
import com.nsxwing.gamestate.field.Position

/**
 * Object in charge of the individual Player Agents. Has a Faction and a point total.
 */
public class Player {
    PlayerIdentifier identifier
    List<PlayerAgent> agents
    Faction faction
    int costOfList

    void placeUnits() {
        double y = identifier == PlayerIdentifier.CHAMP ? 20 : 880
        double heading = identifier == PlayerIdentifier.CHAMP ? 0 : Math.PI

        double x = 0
        for (PlayerAgent agent : agents) {
            agent.position = new Position(center: new Coordinate(x: x, y: y), heading: heading)
            x += 60
        }
    }

    int getPointsRemaining() {
        int points = 0

        for (PlayerAgent agent : agents) {
            points += agent.pointCost
        }

        points
    }

    @Override
    String toString() {
        identifier as String
    }
}
