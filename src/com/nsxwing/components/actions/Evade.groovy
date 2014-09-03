package com.nsxwing.components.actions

import com.nsxwing.agents.PlayerAgent

/**
 * Implementation of Action that grants an evade action.
 */
class Evade extends Action {

    int availableEvadeTokens

    @Override
    void execute(PlayerAgent target) {
        super.execute(target)
        availableEvadeTokens++
    }
}
