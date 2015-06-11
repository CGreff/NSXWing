package com.nsxwing.phase

import com.nsxwing.agents.Player
import com.nsxwing.agents.PlayerAgent
import com.nsxwing.components.meta.PlayerIdentifier
import com.nsxwing.gamestate.field.GameField
import com.nsxwing.gamestate.field.Position
import com.nsxwing.movement.Maneuver

class PlanningPhase {

    static final double STRENGTH_NO_TARGETS = 1000
    static final double STRENGTH_MINIFIER = 0.1
    static final double STRENGTH_MAXIMIZER = 1.0

    final Player champ
    final Player scrub
    final GameField gameField

    PlanningPhase(Player champ, Player scrub, GameField gameField) {
        this.champ = champ
        this.scrub = scrub
        this.gameField = gameField
    }


    Map<PlayerAgent, Maneuver> doPhase(List<PlayerAgent> playerAgents) {
        Map<PlayerAgent, Maneuver> chosenManeuvers = [:]
        RankedManeuver bestManeuver
        for (PlayerAgent agent : playerAgents) {
            bestManeuver = null
            for (Maneuver maneuver : agent.pilot.ship.maneuvers) {
                if (gameField.isLegalManeuver(agent, maneuver)) {
                    double strength = getManeuverStrength(agent, maneuver)
                    if (!bestManeuver) {
                        bestManeuver = new RankedManeuver(maneuver: maneuver, strength: strength)
                    } else if (strength > bestManeuver.strength) {
                        bestManeuver = new RankedManeuver(maneuver: maneuver, strength: strength)
                    }
                }
            }
            chosenManeuvers.put(agent, bestManeuver?.maneuver)
        }
        chosenManeuvers
    }

    double getManeuverStrength(PlayerAgent agent, Maneuver maneuver) {
        Position position = maneuver.move(agent.position)
        List<PlayerAgent> enemies = agent.owningPlayer == PlayerIdentifier.CHAMP ? champ.agents.sort { it.pointCost * -1 } : scrub.agents.sort { it.pointCost * -1 }
        List<Position> potentialEnemyPositions = []

        for (PlayerAgent enemy : enemies) {
            potentialEnemyPositions.addAll(getPositionsFor(enemy))
        }

        int numTargets = gameField.getTargetCoverageFor(agent, potentialEnemyPositions)

        boolean hasNextMove = false
        for (Maneuver nextManeuver : agent.pilot.ship.maneuvers) {
            if (gameField.isLegalManeuver(position, nextManeuver)) {
                hasNextMove = true
                break
            }
        }

        hasNextMove ? numTargets : 0
    }

    private List<Position> getPositionsFor(PlayerAgent agent) {
        List<Position> positions = []
        for (Maneuver maneuver : agent.pilot.ship.maneuvers) {
            positions.add(maneuver.move(agent.position))
        }

        positions
    }

    private class RankedManeuver {
        Maneuver maneuver
        double strength
    }
}
