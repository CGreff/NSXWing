package com.nsxwing.gamestate

import com.nsxwing.agents.Player
import com.nsxwing.agents.PlayerAgent
import com.nsxwing.components.Faction
import com.nsxwing.components.meta.PlayerIdentifier
import com.nsxwing.gamestate.combat.Target
import com.nsxwing.gamestate.field.Coordinate
import com.nsxwing.gamestate.field.GameField
import com.nsxwing.gamestate.field.Position
import com.nsxwing.utilities.PilotUtility
import org.junit.Before
import org.junit.Test

class GameControllerTest {
    Player champ
    Player scrub
    GameField field
    GameController controller

    @Before
    void setUp() {
        champ = new Player(identifier: PlayerIdentifier.CHAMP, costOfList: 85, faction: Faction.EMPIRE, agents: [
                new PlayerAgent(pilot: PilotUtility.getPilot('Academy Pilot'), position: new Position(center: new Coordinate(x: 65, y: 20), heading: 0), owningPlayer: PlayerIdentifier.CHAMP),
                new PlayerAgent(pilot: PilotUtility.getPilot('Obsidian Squadron Pilot'), position: new Position(center: new Coordinate(x: 200, y: 20), heading: 0), owningPlayer: PlayerIdentifier.CHAMP),
                new PlayerAgent(pilot: PilotUtility.getPilot('Backstabber'), position: new Position(center: new Coordinate(x: 245, y: 20), heading: 0), owningPlayer: PlayerIdentifier.CHAMP),
                new PlayerAgent(pilot: PilotUtility.getPilot('Dark Curse'), position: new Position(center: new Coordinate(x: 290, y: 20), heading: 0), owningPlayer: PlayerIdentifier.CHAMP),
                new PlayerAgent(pilot: PilotUtility.getPilot('Maarek Stele'), position: new Position(center: new Coordinate(x: 345, y: 20), heading: 0), owningPlayer: PlayerIdentifier.CHAMP)
        ])
        scrub = new Player(identifier: PlayerIdentifier.SCRUB, costOfList: 79, faction: Faction.EMPIRE, agents: [
                new PlayerAgent(pilot: PilotUtility.getPilot('Academy Pilot'), position: new Position(center: new Coordinate(x: 20, y: 280), heading: Math.PI), owningPlayer: PlayerIdentifier.SCRUB),
                new PlayerAgent(pilot: PilotUtility.getPilot('Academy Pilot'), position: new Position(center: new Coordinate(x: 155, y: 280), heading: Math.PI), owningPlayer: PlayerIdentifier.SCRUB),
                new PlayerAgent(pilot: PilotUtility.getPilot('Obsidian Squadron Pilot'), position: new Position(center: new Coordinate(x: 200, y: 300), heading: Math.PI), owningPlayer: PlayerIdentifier.SCRUB),
                new PlayerAgent(pilot: PilotUtility.getPilot('Howlrunner'), position: new Position(center: new Coordinate(x: 245, y: 280), heading: Math.PI), owningPlayer: PlayerIdentifier.SCRUB),
                new PlayerAgent(pilot: PilotUtility.getPilot('Darth Vader'), position: new Position(center: new Coordinate(x: 345, y: 70), heading: Math.PI), owningPlayer: PlayerIdentifier.SCRUB)
        ])

        field = new GameField()
        controller = new GameController(champ, scrub)
    }

    @Test
    void 'should correctly sort from lowest pilot skill first for activation'() {
        List<PlayerAgent> combinedAgents = controller.getCombinedAgentList(controller.ACTIVATION_COMPARATOR)
        assert combinedAgents.size() == 10
        assert combinedAgents.get(0).pilot.pilotSkill < combinedAgents.get(9).pilot.pilotSkill
        assert combinedAgents.get(0).owningPlayer == PlayerIdentifier.SCRUB
    }

    @Test
    void 'should correctly sort from highest pilot skill first for combat'() {
        List<PlayerAgent> combinedAgents = controller.getCombinedAgentList(controller.COMBAT_COMPARATOR)
        assert combinedAgents.size() == 10
        assert combinedAgents.get(0).pilot.pilotSkill > combinedAgents.get(9).pilot.pilotSkill
        assert combinedAgents.get(0).owningPlayer == PlayerIdentifier.SCRUB
    }

    @Test
    void 'should get the appropriate targets for Backstabber'() {
        Set<Target> targets = field.getTargetsFor(champ, scrub, champ.agents.get(2))
        assert targets.size() == 3
        assert !targets.contains(scrub.agents.get(4))
    }

    @Test
    void 'should get all targets for Howlrunner'() {
        Set<Target> targets = field.getTargetsFor(champ, scrub, scrub.agents.get(3))
        assert targets.size() == 5
    }
}
