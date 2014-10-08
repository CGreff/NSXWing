package com.nsxwing.components.actions

import com.nsxwing.agents.PlayerAgent

/**
 * Implementation of Action that grants a Cloak token.
 */
class Cloak extends Action {

    boolean isCloaked

    @Override
    void execute(PlayerAgent target) {
        super.execute(target)
        isCloaked = true
    }
}
