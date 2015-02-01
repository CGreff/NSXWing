package com.nsxwing.gamestate

import com.nsxwing.agents.Player
import com.nsxwing.agents.PlayerAgent
import com.nsxwing.components.meta.PlayerIdentifier
import com.nsxwing.gamestate.field.GameField
import com.nsxwing.gamestate.field.Position
import com.nsxwing.movement.Maneuver
import com.nsxwing.movement.ManeuverDifficulty

/**
 *
 */
class GameController {

    static PlayerIdentifier PLAYER_WITH_INITIATIVE
    static final Closure ACTIVATION_COMPARATOR = {
        double initiativeModifier = it.owningPlayer == PLAYER_WITH_INITIATIVE ? 0.1 : 0
        it.pilot.pilotSkill - initiativeModifier
    }

    static final Closure COMBAT_COMPARATOR = {
        double initiativeModifier = it.owningPlayer == PLAYER_WITH_INITIATIVE ? 0.1 : 0
        (it.pilot.pilotSkill - initiativeModifier) * -1.0
    }

    final Player champ
    final Player scrub
    final GameField gameField

    GameController(Player champ, Player scrub) {
        this.champ = champ
        this.scrub = scrub
        PLAYER_WITH_INITIATIVE = determineInitiative()
        gameField = new GameField()
    }

    void doTurn() {
        planTurn()
        doActivationPhase()
        doCombatPhase()
    }

    Map<PlayerAgent, Maneuver> planTurn() {
        Map<PlayerAgent, Maneuver> chosenManeuvers = [:]
        RankedManeuver bestManeuver = null
        for (PlayerAgent agent : champ.agents) {
            for (Maneuver maneuver : agent.pilot.ship.maneuvers) {
                if (isLegalManeuver(agent, maneuver)) {
                    double strength = getManeuverStrength(maneuver)
                    if (!bestManeuver) {
                        bestManeuver = new RankedManeuver(maneuver: maneuver, strength: strength)
                    } else if (strength > bestManeuver.strength) {
                        bestManeuver = new RankedManeuver(maneuver: maneuver, strength: strength)
                    }
                }
            }
            chosenManeuvers.put(agent, bestManeuver.maneuver)
        }
        chosenManeuvers
    }

    double getManeuverStrength(Maneuver maneuver) {
        0
    }

    void doActivationPhase() {

    }

    void doCombatPhase() {

    }



    List<PlayerAgent> getCombinedAgentList(Closure comparator) {
        List<PlayerAgent> combinedList = new ArrayList(champ.agents)
        combinedList.addAll(scrub.agents)
        combinedList.sort(comparator)
    }

    boolean isLegalManeuver(PlayerAgent agent, Maneuver maneuver) {
        Position newPosition = maneuver.move(agent.position)
        !gameField.isOutOfBounds(newPosition.boxPoints) || !(agent.pilot.numStressTokens > 0 && maneuver.difficulty == ManeuverDifficulty.RED)
    }

    private PlayerIdentifier determineInitiative() {
        (champ.costOfList <= scrub.costOfList) ? PlayerIdentifier.CHAMP : PlayerIdentifier.SCRUB
    }

    private class RankedManeuver {
        Maneuver maneuver
        double strength
    }
}
