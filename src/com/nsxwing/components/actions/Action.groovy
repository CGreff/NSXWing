package com.nsxwing.components.actions

import com.nsxwing.agents.PlayerAgent

/**
 *
 */
abstract class Action {

    boolean hasExecuted

    void execute(PlayerAgent target) {
        hasExecuted = true
    }
}
