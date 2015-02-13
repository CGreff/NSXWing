package com.nsxwing.components.actions

import com.nsxwing.agents.PlayerAgent
import com.nsxwing.movement.BankTurn
import com.nsxwing.movement.Direction
import com.nsxwing.movement.Forward
import com.nsxwing.movement.Maneuver
import com.nsxwing.movement.ManeuverDifficulty

/**
 * Implementation of Action that causes a boost.
 */
class Boost extends Action {

    int actionPriority = 9

    static final Set<Maneuver> BOOST_MANEUVERS = [
            new Forward(1, ManeuverDifficulty.WHITE),
            new BankTurn(1, ManeuverDifficulty.WHITE, Direction.LEFT),
            new BankTurn(1, ManeuverDifficulty.WHITE, Direction.RIGHT)
    ]

    @Override
    void execute(PlayerAgent target) {
        super.execute(target)
    }
}
