package com.nsxwing.agents

import com.nsxwing.components.Faction
import com.nsxwing.components.meta.PlayerIdentifier
import com.nsxwing.gamestate.field.Coordinate
import com.nsxwing.gamestate.field.Position
import com.nsxwing.movement.Maneuver
import com.nsxwing.utilities.PilotUtility

/**
 * Object in charge of the individual Player Agents. Has a Faction and a point total.
 */
public class Player {
    PlayerIdentifier identifier
    List<PlayerAgent> agents
    Faction faction
    int costOfList

    void buildList() {
        double y = identifier == PlayerIdentifier.CHAMP ? 20 : 880
        double heading = identifier == PlayerIdentifier.CHAMP ? 0 : Math.PI
        agents = [
                new PlayerAgent(pilot: PilotUtility.getPilot('Academy Pilot'), position: new Position(center: new Coordinate(x: 20, y: y), heading: heading), owningPlayer: identifier),
                new PlayerAgent(pilot: PilotUtility.getPilot('Academy Pilot'), position: new Position(center: new Coordinate(x: 65, y: y), heading: heading), owningPlayer: identifier),
                new PlayerAgent(pilot: PilotUtility.getPilot('Academy Pilot'), position: new Position(center: new Coordinate(x: 110, y: y), heading: heading), owningPlayer: identifier),
                new PlayerAgent(pilot: PilotUtility.getPilot('Academy Pilot'), position: new Position(center: new Coordinate(x: 155, y: y), heading: heading), owningPlayer: identifier),
                new PlayerAgent(pilot: PilotUtility.getPilot('Obsidian Squadron Pilot'), position: new Position(center: new Coordinate(x: 200, y: y), heading: heading), owningPlayer: identifier),
                new PlayerAgent(pilot: PilotUtility.getPilot('Obsidian Squadron Pilot'), position: new Position(center: new Coordinate(x: 245, y: y), heading: heading), owningPlayer: identifier),
                new PlayerAgent(pilot: PilotUtility.getPilot('Obsidian Squadron Pilot'), position: new Position(center: new Coordinate(x: 290, y: y), heading: heading), owningPlayer: identifier),
                new PlayerAgent(pilot: PilotUtility.getPilot('Obsidian Squadron Pilot'), position: new Position(center: new Coordinate(x: 345, y: y), heading: heading), owningPlayer: identifier)
        ]
    }

    void chooseManeuver(PlayerAgent agent) {
        Set<Maneuver> possibleMoves = agent.pilot.ship.maneuvers
    }
}
