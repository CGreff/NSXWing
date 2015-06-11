package com.nsxwing.phase

import com.nsxwing.agents.Player
import com.nsxwing.agents.PlayerAgent
import com.nsxwing.components.meta.PlayerIdentifier
import com.nsxwing.gamestate.field.GameField
import com.nsxwing.gamestate.field.Position
import com.nsxwing.movement.Maneuver
import groovy.util.logging.Slf4j

@Slf4j
class PlanningPhase {


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
                    ManeuverStrength strength = getManeuverStrength(agent, maneuver)
                    if (!bestManeuver) {
                        bestManeuver = new RankedManeuver(maneuver: maneuver, strength: strength)
                    } else if (isBetterManeuver(strength, bestManeuver)) {
                        bestManeuver = new RankedManeuver(maneuver: maneuver, strength: strength)
                    }
                }
            }
            chosenManeuvers.put(agent, bestManeuver?.maneuver)
        }
        chosenManeuvers
    }

    ManeuverStrength getManeuverStrength(PlayerAgent agent, Maneuver maneuver) {
        Position position = maneuver.move(agent.position)
        List<PlayerAgent> enemies = agent.owningPlayer == PlayerIdentifier.SCRUB ? champ.agents.sort { it.pointCost * -1 } : scrub.agents.sort { it.pointCost * -1 }
        List<Position> potentialEnemyPositions = []

        for (PlayerAgent enemy : enemies) {
            potentialEnemyPositions.addAll(getPositionsFor(enemy))
        }

        int numTargets = gameField.getTargetCoverageFor(agent, potentialEnemyPositions)
        double distanceToEnemies = gameField.getDistanceBetween(agent.position.center, enemies.get(0).position.center) / GameField.X_SIZE

        boolean hasNextMove = false
        for (Maneuver nextManeuver : agent.pilot.ship.maneuvers) {
            if (gameField.isLegalManeuver(position, nextManeuver)) {
                hasNextMove = true
                break
            }
        }

        hasNextMove ? new ManeuverStrength(numTargets: numTargets, distanceToEnemies: distanceToEnemies) : new ManeuverStrength(numTargets: -1)
    }

    private List<Position> getPositionsFor(PlayerAgent agent) {
        List<Position> positions = []
        for (Maneuver maneuver : agent.pilot.ship.maneuvers) {
            positions.add(maneuver.move(agent.position))
        }

        positions
    }

    private boolean isBetterManeuver(ManeuverStrength strength, RankedManeuver bestManeuver) {
        strength.numTargets > bestManeuver.strength.numTargets ||
                (strength.numTargets == bestManeuver.strength.numTargets && strength.distanceToEnemies < bestManeuver.strength.distanceToEnemies)
    }

    private class RankedManeuver {
        Maneuver maneuver
        ManeuverStrength strength
    }

    private class ManeuverStrength {
        int numTargets
        double distanceToEnemies
    }
}
