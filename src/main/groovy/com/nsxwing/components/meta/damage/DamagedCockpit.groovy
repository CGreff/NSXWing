package com.nsxwing.components.meta.damage

import com.nsxwing.components.pilots.Pilot

/**
 *
 */
class DamagedCockpit extends DamageCard {
    DamageType damageType = DamageType.PILOT

    @Override
    void resolveCrit(Pilot pilot) {
        pilot.pilotSkill = 0
    }
}
