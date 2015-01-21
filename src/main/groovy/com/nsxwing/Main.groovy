package com.nsxwing

import com.nsxwing.agents.Player
import com.nsxwing.agents.PlayerAgent
import com.nsxwing.components.Faction
import com.nsxwing.gamestate.field.Coordinate
import com.nsxwing.gamestate.Phase
import com.nsxwing.gamestate.field.Position
import com.nsxwing.utilities.PilotConstructor

public class Main {

    public static void main(String[] args) {
        Player p1 = new Player(costOfList: 100, faction: Faction.EMPIRE, agents: [
                new PlayerAgent(pilot: PilotConstructor.getPilot('Academy Pilot'), position: new Position(center: new Coordinate(x: 20, y: 20), heading: 0), phase: Phase.PRE_MOVEMENT),
                new PlayerAgent(pilot: PilotConstructor.getPilot('Academy Pilot'), position: new Position(center: new Coordinate(x: 65, y: 20), heading: 0), phase: Phase.PRE_MOVEMENT),
                new PlayerAgent(pilot: PilotConstructor.getPilot('Academy Pilot'), position: new Position(center: new Coordinate(x: 110, y: 20), heading: 0), phase: Phase.PRE_MOVEMENT),
                new PlayerAgent(pilot: PilotConstructor.getPilot('Academy Pilot'), position: new Position(center: new Coordinate(x: 155, y: 20), heading: 0), phase: Phase.PRE_MOVEMENT),
                new PlayerAgent(pilot: PilotConstructor.getPilot('Obsidian Squadron Pilot'), position: new Position(center: new Coordinate(x: 200, y: 20), heading: 0), phase: Phase.PRE_MOVEMENT),
                new PlayerAgent(pilot: PilotConstructor.getPilot('Obsidian Squadron Pilot'), position: new Position(center: new Coordinate(x: 245, y: 20), heading: 0), phase: Phase.PRE_MOVEMENT),
                new PlayerAgent(pilot: PilotConstructor.getPilot('Obsidian Squadron Pilot'), position: new Position(center: new Coordinate(x: 290, y: 20), heading: 0), phase: Phase.PRE_MOVEMENT),
                new PlayerAgent(pilot: PilotConstructor.getPilot('Obsidian Squadron Pilot'), position: new Position(center: new Coordinate(x: 345, y: 20), heading: 0), phase: Phase.PRE_MOVEMENT)
        ].sort { it.pilot.pilotSkill })

        Player p2 = new Player(costOfList: 100, faction: Faction.EMPIRE, agents: [
                new PlayerAgent(pilot: PilotConstructor.getPilot('Academy Pilot'), position: new Position(center: new Coordinate(x: 20, y: 880), heading: Math.PI/2), phase: Phase.PRE_MOVEMENT),
                new PlayerAgent(pilot: PilotConstructor.getPilot('Academy Pilot'), position: new Position(center: new Coordinate(x: 65, y: 880), heading: Math.PI/2), phase: Phase.PRE_MOVEMENT),
                new PlayerAgent(pilot: PilotConstructor.getPilot('Academy Pilot'), position: new Position(center: new Coordinate(x: 110, y: 880), heading: Math.PI/2), phase: Phase.PRE_MOVEMENT),
                new PlayerAgent(pilot: PilotConstructor.getPilot('Academy Pilot'), position: new Position(center: new Coordinate(x: 155, y: 880), heading: Math.PI/2), phase: Phase.PRE_MOVEMENT),
                new PlayerAgent(pilot: PilotConstructor.getPilot('Obsidian Squadron Pilot'), position: new Position(center: new Coordinate(x: 200, y: 880), heading: Math.PI/2), phase: Phase.PRE_MOVEMENT),
                new PlayerAgent(pilot: PilotConstructor.getPilot('Obsidian Squadron Pilot'), position: new Position(center: new Coordinate(x: 245, y: 880), heading: Math.PI/2), phase: Phase.PRE_MOVEMENT),
                new PlayerAgent(pilot: PilotConstructor.getPilot('Obsidian Squadron Pilot'), position: new Position(center: new Coordinate(x: 290, y: 880), heading: Math.PI/2), phase: Phase.PRE_MOVEMENT),
                new PlayerAgent(pilot: PilotConstructor.getPilot('Obsidian Squadron Pilot'), position: new Position(center: new Coordinate(x: 345, y: 880), heading: Math.PI/2), phase: Phase.PRE_MOVEMENT)
        ].sort { it.pilot.pilotSkill })
    }
}
