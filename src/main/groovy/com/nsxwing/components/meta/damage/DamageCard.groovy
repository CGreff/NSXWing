package com.nsxwing.components.meta.damage

import com.nsxwing.components.pilots.Pilot

/**
 * Class from which the different kinds of damage cards inherit.
 */
abstract class DamageCard {
    int damageValue = 1
    boolean isCritical
    DamageType damageType

    void resolveCrit(Pilot pilot) {

    }
}
