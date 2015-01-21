package com.nsxwing.gamestate

import com.nsxwing.agents.Player
import com.nsxwing.agents.PlayerAgent

/**
 * Class describing the playing field. (3' x 3')
 */
public class Board {
    static final int X_SIZE = 913
    static final int Y_SIZE = 913

    static final Player CHAMPION
    static final Player CHALLENGER

    Set<Target> getVisibleEnemiesFor(Player player, PlayerAgent agent) {
        Player opponent = (player == CHAMPION) ? CHALLENGER : CHAMPION

        for (PlayerAgent enemyAgent : opponent.agents) {
            for (Coordinate shipCoordinates : enemyAgent.position.boxPoints) {
                //TODO: Figure out how to intersect a firing arc with a set of coordinates.
            }
        }
    }
}
