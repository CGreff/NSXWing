package com.nsxwing.phase

import com.nsxwing.agents.Player
import com.nsxwing.agents.PlayerAgent
import com.nsxwing.components.Faction
import com.nsxwing.components.meta.PlayerIdentifier
import com.nsxwing.components.pilots.imperials.tieadvanced.TempestSquadronPilot
import com.nsxwing.components.pilots.imperials.tiefighter.AcademyPilot
import com.nsxwing.gamestate.GameController
import com.nsxwing.gamestate.field.Coordinate
import com.nsxwing.gamestate.field.GameField
import com.nsxwing.gamestate.field.Position
import com.nsxwing.utilities.BoardState
import org.junit.Before
import org.junit.Test

class PlanningPhaseTest {

    Player champ
    Player scrub
    GameField field
    PlanningPhase planningPhase
    GameController controller

    @Before
    void setUp() {
        champ = new Player(identifier: PlayerIdentifier.CHAMP, costOfList: 100, faction: Faction.EMPIRE, agents: [
                new PlayerAgent(pilot: new AcademyPilot(), position: new Position(center: new Coordinate(x: 475.2274887854693, y: 332.69935136161223), heading: 2.356194490192345), owningPlayer: PlayerIdentifier.CHAMP)
        ])
        scrub = new Player(identifier: PlayerIdentifier.SCRUB, costOfList: 100, faction: Faction.EMPIRE, agents: [
                new PlayerAgent(pilot: new TempestSquadronPilot(), position: new Position(center: new Coordinate(x: 543.971175493115, y: 613.2625894155134), heading: 0.7853981633974483), owningPlayer: PlayerIdentifier.SCRUB),
        ])

        field = new GameField()
        controller = new GameController(champ, scrub)
        planningPhase = new PlanningPhase(champ, scrub, field)
    }

    @Test
    void 'should be a testing ground'() {
        List<BoardState> boardStates = planningPhase.buildFutureBoardStates(controller.getCombinedAgentList { it })
        boardStates
    }
}
