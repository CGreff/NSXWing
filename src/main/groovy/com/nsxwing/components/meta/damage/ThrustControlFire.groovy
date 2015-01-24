package com.nsxwing.components.meta.damage

import com.nsxwing.components.pilots.Pilot

/**
 *
 */
class ThrustControlFire extends DamageCard {

    DamageType damageType = DamageType.SHIP

    @Override
    void resolveCrit(Pilot pilot) {
        pilot.numStressTokens++
        this.isCritical = false
    }
}
