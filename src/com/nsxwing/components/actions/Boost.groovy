package com.nsxwing.components.actions

import com.nsxwing.agents.PlayerAgent
import com.nsxwing.movement.BankTurn
import com.nsxwing.movement.Forward
import com.nsxwing.movement.Maneuver
import com.nsxwing.movement.ManeuverDifficulty

/**
 * Implementation of Action that causes a boost.
 */
class Boost extends Action {

    private static final Set<Maneuver> BOOST_MANEUVERS = [
            new Forward(1, ManeuverDifficulty.WHITE),
            new BankTurn(1, ManeuverDifficulty.WHITE)
    ]

    @Override
    void execute(PlayerAgent target) {
        super.execute(target)
        chooseOptimalBoost(target)
    }

    private void chooseOptimalBoost(PlayerAgent target) {

    }
}
