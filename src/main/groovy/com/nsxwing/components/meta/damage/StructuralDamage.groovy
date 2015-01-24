package com.nsxwing.components.meta.damage

import com.nsxwing.components.actions.FixStructuralDamage
import com.nsxwing.components.pilots.Pilot

/**
 *
 */
class StructuralDamage extends DamageCard {

    DamageType damageType = DamageType.SHIP

    @Override
    void resolveCrit(Pilot pilot) {
        pilot.agility--
        pilot.ship.actions.add(new FixStructuralDamage())
    }
}
