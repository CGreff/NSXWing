package com.nsxwing.gamestate.field

import com.nsxwing.agents.Player
import com.nsxwing.agents.PlayerAgent
import com.nsxwing.components.meta.PlayerIdentifier
import com.nsxwing.gamestate.combat.FiringArc
import com.nsxwing.gamestate.combat.FiringLine
import com.nsxwing.gamestate.combat.Target
import com.nsxwing.movement.Maneuver
import com.nsxwing.movement.heuristic.ManeuverStrength

/**
 * Class describing the playing field. (3' x 3')
 */
public class GameField {

    static final int X_SIZE = 913
    static final int Y_SIZE = 913

    List<Target> getTargetsFor(Player champ, Player scrub, PlayerAgent agent) {
        Player opponent = (agent.owningPlayer == PlayerIdentifier.CHAMP) ? scrub : champ
        List<Target> visibleEnemies = []
        int range

        for (PlayerAgent enemyAgent : opponent.agents) {
            for (Coordinate shipCoordinates : enemyAgent.position.boxPoints) {
                if (isTargetable(agent.getFiringArc(), shipCoordinates) || agent.pilot.ship.primaryArc == FiringArc.TURRET) {
                    range = getRangeToTarget(enemyAgent, agent)
                    if (range <= 3 && range != 0) {
                        visibleEnemies.add(new Target(targetAgent: enemyAgent, range: range, obstructed: false, priority: getTargetPriority(enemyAgent)))
                        break
                    }
                }
            }
        }

        visibleEnemies
    }

    int getTargetCoverageFor(PlayerAgent agent, List<Position> potentialEnemyPositions) {
        int numTargets = 0

        for (Position position : potentialEnemyPositions) {
            for (Coordinate coordinate : position.boxPoints) {
                if (isTargetable(agent.getFiringArc(), coordinate) || agent.pilot.ship.primaryArc == FiringArc.TURRET) {
                    numTargets++
                    break
                }
            }
        }

        numTargets
    }

    double getTargetPriority(PlayerAgent agent) {
        agent.pointCost / ((agent.pilot.hullPoints + agent.pilot.shieldPoints) * (agent.pilot.agility + 1))
    }

    ManeuverStrength getManeuverStrength(PlayerAgent agent, Maneuver maneuver, Player champ, Player scrub) {
        Position oldPosition = agent.position
        agent.setPosition(maneuver.move(agent.position))

        ManeuverStrength strength = getPositionStrength(agent, champ, scrub)

        agent.setPosition(oldPosition)
        strength
    }

    ManeuverStrength getPositionStrength(PlayerAgent agent, Player champ, Player scrub) {
        Position position = agent.position
        List<PlayerAgent> enemies = agent.owningPlayer == PlayerIdentifier.SCRUB ? champ.agents.sort {
            it.pointCost * -1
        } : scrub.agents.sort { it.pointCost * -1 }
        List<Position> potentialEnemyPositions = []

        for (PlayerAgent enemy : enemies) {
            potentialEnemyPositions.addAll(getPositionsFor(enemy))
        }

        int numTargets = getTargetCoverageFor(agent, potentialEnemyPositions)
        double distanceToEnemies = getDistanceBetween(agent.position.center, enemies.get(0).position.center) / X_SIZE

        boolean hasNextMove = false
        for (Maneuver nextManeuver : agent.pilot.ship.maneuvers) {
            if (isLegalManeuver(position, nextManeuver)) {
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

    boolean isLegalManeuver(Position position, Maneuver maneuver) {
        Position newPosition = maneuver.move(position)
        !isOutOfBounds(newPosition.boxPoints)
    }

    boolean isOutOfBounds(List<Coordinate> boxPoints) {
        for (Coordinate point : boxPoints) {
            if (point.x < 0 || point.x > X_SIZE || point.y < 0 || point.y > Y_SIZE) {
                return true
            }
        }

        false
    }

    /*
     * Returns whether the coordinate is between the left and right firing lines or not.
     */
    boolean isTargetable(List<FiringLine> firingLines, Coordinate coordinate) {
        firingLines.get(0).isRightOfLine(coordinate) && firingLines.get(1).isLeftOfLine(coordinate)
    }

    private int getRangeToTarget(PlayerAgent target, PlayerAgent agent) {
        Coordinate closestPoint = findClosestPoint(target.position.boxPoints, agent.position.center)
        Math.ceil(getDistanceBetween(closestPoint, agent.position.center) / 100.0).intValue()
    }

    private Coordinate findClosestPoint(List<Coordinate> boxPoints, Coordinate centerPoint) {
        boxPoints.sort {
            getDistanceBetween(it, centerPoint)
        }.get(0)
    }

    double getDistanceBetween(Coordinate c1, Coordinate c2) {
        Math.sqrt(Math.pow(c1.y - c2.y, 2) + Math.pow(c1.x - c2.x, 2))
    }
}
