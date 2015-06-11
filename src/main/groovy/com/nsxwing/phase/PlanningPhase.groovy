package com.nsxwing.phase

import com.nsxwing.agents.Player
import com.nsxwing.agents.PlayerAgent
import com.nsxwing.components.meta.PlayerIdentifier
import com.nsxwing.gamestate.combat.Target
import com.nsxwing.gamestate.field.GameField
import com.nsxwing.gamestate.field.Position
import com.nsxwing.movement.Maneuver

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
        double strength
        Position position = maneuver.move(agent.position)
        List<PlayerAgent> enemies = agent.owningPlayer == PlayerIdentifier.CHAMP ? champ.agents.sort { it.pointCost * -1 } : scrub.agents.sort { it.pointCost * -1 }
        List<Target> targets = gameField.getTargetsFor(champ, scrub, agent)

        if (targets) {
            strength = targets.sort { (it.targetAgent.pointCost * -1) - (0.1 * it.targetAgent.pilot.damageCards.size()) }.get(0).targetAgent.pointCost
        } else {
            strength = (1000 - (gameField.getDistanceBetween(position.center, enemies.get(0).position.center))) * (facingEnemies(position, enemies) ? 0.1 : 1.0)
        }

        boolean hasNextMove = false
        for (Maneuver nextManeuver : agent.pilot.ship.maneuvers) {
            if (gameField.isLegalManeuver(position, nextManeuver)) {
                hasNextMove = true
                break
            }
        }

        hasNextMove ? strength : 0
    }

    private boolean facingEnemies(Position position, List<PlayerAgent> enemies) {
        PlayerAgent temporaryAgent = new PlayerAgent(position: position)
        for (PlayerAgent enemy : enemies) {
            if (gameField.isTargetable(temporaryAgent.firingArc, enemy.position.center)) {
                return true
            }
        }

        false
    }

    private class RankedManeuver {
        Maneuver maneuver
        double strength
    }
}
