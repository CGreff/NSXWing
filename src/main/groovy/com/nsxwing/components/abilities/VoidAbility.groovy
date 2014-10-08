package com.nsxwing.components.abilities

import com.nsxwing.components.Pilot
import com.nsxwing.components.Ship

/**
 * The ability implemented by nameless mooks; never meets its condition to activate and no-ops on activation.
 */
class VoidAbility implements Ability {

    @Override
    boolean metCondition() {
        return false
    }

    @Override
    void activateAbility(Pilot pilot, Ship ship) {

    }
}
