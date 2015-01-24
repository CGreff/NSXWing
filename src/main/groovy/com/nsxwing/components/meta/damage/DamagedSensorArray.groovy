package com.nsxwing.components.meta.damage

import com.nsxwing.components.actions.Action
import com.nsxwing.components.actions.FixSensorArray
import com.nsxwing.components.pilots.Pilot

/**
 *
 */
class DamagedSensorArray extends DamageCard {

    DamageType damageType = DamageType.SHIP

    @Override
    void resolveCrit(Pilot pilot) {
        Set<Action> oldActions = pilot.ship.actions
        FixSensorArray fixSensorArrayAction = new FixSensorArray(formerActions: oldActions)
        pilot.ship.actions = [fixSensorArrayAction] as Set
    }
}
