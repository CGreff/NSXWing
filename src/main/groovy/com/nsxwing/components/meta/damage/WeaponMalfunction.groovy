package com.nsxwing.components.meta.damage

import com.nsxwing.components.actions.FixWeaponMalfunction
import com.nsxwing.components.pilots.Pilot

/**
 *
 */
class WeaponMalfunction extends DamageCard {

    DamageType damageType = DamageType.SHIP

    @Override
    void resolveCrit(Pilot pilot) {
        pilot.attack--
        pilot.ship.actions.add(new FixWeaponMalfunction())
    }
}
