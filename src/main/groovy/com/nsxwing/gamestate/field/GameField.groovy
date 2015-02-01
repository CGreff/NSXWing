package com.nsxwing.gamestate.field

import com.nsxwing.agents.Player
import com.nsxwing.agents.PlayerAgent
import com.nsxwing.components.meta.PlayerIdentifier
import com.nsxwing.gamestate.combat.FiringLine
import com.nsxwing.gamestate.combat.Target
import com.nsxwing.movement.Maneuver

/**
 * Class describing the playing field. (3' x 3')
 */
public class GameField {

    static final int X_SIZE = 913
    static final int Y_SIZE = 913

    Set<Target> getTargetsFor(Player champ, Player scrub, PlayerAgent agent) {
        Player opponent = (agent.owningPlayer == PlayerIdentifier.CHAMP) ? scrub : champ
        Set<Target> visibleEnemies = []
        int range

        for (PlayerAgent enemyAgent : opponent.agents) {
            for (Coordinate shipCoordinates : enemyAgent.position.boxPoints) {
                if (isTargetable(agent.getFiringArc(), shipCoordinates)) {
                    range = getRangeToTarget(enemyAgent, agent)
                    if (range <= 3 && range != 0) {
                        visibleEnemies.add(new Target(targetAgent: enemyAgent, range: range, obstructed: false))
                        break;
                    }
                }
            }
        }

        visibleEnemies
    }

    boolean isOutOfBounds(List<Coordinate> boxPoints) {
        for (Coordinate point : boxPoints) {
            if (point.x < 0 || point.x > X_SIZE || point.y < 0 || point.y > Y_SIZE) {
                return true
            }
        }

        false
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

    private double getDistanceBetween(Coordinate c1, Coordinate c2) {
        Math.sqrt(Math.pow(c1.y - c2.y, 2) + Math.pow(c1.x - c2.x, 2))
    }

    /*
     * Returns whether the coordinate is between the left and right firing lines or not.
     */
    private boolean isTargetable(List<FiringLine> firingLines, Coordinate coordinate) {
        firingLines.get(0).isRightOfLine(coordinate) && firingLines.get(1).isLeftOfLine(coordinate)
    }
}
