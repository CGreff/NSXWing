package com.nsxwing.components.actions

import com.nsxwing.agents.PlayerAgent

/**
 * Implementation of Action that locks onto an enemy target.
 */
class TargetLock extends Action {

    Set<PlayerAgent> targets = [] as Set
    int actionPriority = 7

    @Override
    void execute(PlayerAgent target) {
        super.execute(target)
        targets.add(target)
    }
}
