package com.nsxwing.gamestate.field

import com.nsxwing.agents.Player
import com.nsxwing.agents.PlayerAgent
import com.nsxwing.gamestate.combat.FiringLine
import com.nsxwing.gamestate.combat.Target

/**
 * Class describing the playing field. (3' x 3')
 */
public class Board {
    static final int X_SIZE = 913
    static final int Y_SIZE = 913

    final Player CHAMPION
    final Player CHALLENGER

    Board(Player champ, Player scrub) {
        CHAMPION = champ
        CHALLENGER = scrub
    }

    Set<Target> getVisibleEnemiesFor(Player player, PlayerAgent agent) {
        Player opponent = (player == CHAMPION) ? CHALLENGER : CHAMPION
        Set<Target> visibleEnemies = []

        for (PlayerAgent enemyAgent : opponent.agents) {
            for (Coordinate shipCoordinates : enemyAgent.position.boxPoints) {
                if (isTargetable(agent.getFiringArc(), shipCoordinates)) {
                    visibleEnemies.add(new Target(targetAgent: enemyAgent))
                    break;
                }
            }
        }

        getRangeAndObstructions(visibleEnemies, agent)

        visibleEnemies
    }

    private void getRangeAndObstructions(Set<Target> enemies, PlayerAgent agent) {
        for (Target enemy: enemies) {
            Coordinate closestPoint = findClosestPoint(enemy.targetAgent.position.boxPoints, agent.position.center)
            //TODO: Implement obstructions & shit.
            enemy.obstructed = false
            enemy.range = Math.ceil(getDistanceBetween(closestPoint, agent.position.center) / 100.0).intValue()

            if (enemy.range > 3 || enemy.range == 0) {
                enemies.remove(enemy)
            }
        }
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
