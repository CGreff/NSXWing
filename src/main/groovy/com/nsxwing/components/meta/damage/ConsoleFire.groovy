package com.nsxwing.components.meta.damage

import com.nsxwing.components.actions.FixConsoleFire
import com.nsxwing.components.pilots.Pilot

/**
 *
 */
class ConsoleFire extends DamageCard {

    DamageType damageType = DamageType.SHIP

    @Override
    void resolveCrit(Pilot pilot) {
        pilot.ship.actions.add(new FixConsoleFire())
        //TODO: Rest will be resolved in a pre-combat phase.
    }
}
