package com.nsxwing.components.meta.damage

import com.nsxwing.components.pilots.Pilot

/**
 *
 */
class DirectHit extends DamageCard {

    @Override
    void resolveCrit(Pilot pilot) {
        this.damageValue = 2
    }
}
