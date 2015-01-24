package com.nsxwing.components.actions

import com.nsxwing.agents.PlayerAgent
import com.nsxwing.components.meta.damage.ConsoleFire

/**
 *
 */
class FixConsoleFire extends Action {

    @Override
    void execute(PlayerAgent target) {
        hasExecuted = true
        target.pilot.damageCards.find { it instanceof ConsoleFire && it.isCritical }.isCritical = false
        target.pilot.ship.actions.remove(this)
    }
}
