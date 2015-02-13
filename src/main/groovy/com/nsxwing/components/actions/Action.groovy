package com.nsxwing.components.actions

import com.nsxwing.agents.PlayerAgent

/**
 * Abstract class extended by the specific Actions ships can take.
 */
abstract class Action {

    boolean hasExecuted
    int actionPriority = 0

    void execute(PlayerAgent target) {
        hasExecuted = true
    }
}
