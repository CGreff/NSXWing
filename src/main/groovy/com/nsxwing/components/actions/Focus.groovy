package com.nsxwing.components.actions

import com.nsxwing.agents.PlayerAgent

/**
 * Implementation of Action that grants a Focus Token.
 */
class Focus extends Action {

    int availableFocusTokens
    int actionPriority = 8

    @Override
    void execute(PlayerAgent target) {
        super.execute(target)
        target.pilot.numFocusTokens++
    }
}
