package com.nsxwing.components.meta.damage

import com.nsxwing.components.pilots.Pilot
import com.nsxwing.movement.ManeuverDifficulty
import com.nsxwing.movement.Turn

/**
 *
 */
class DamagedEngine extends DamageCard {

    DamageType damageType = DamageType.SHIP

    @Override
    void resolveCrit(Pilot pilot) {
        for (Turn turn : pilot.ship.maneuvers.findAll { it instanceof Turn }) {
            turn.difficulty = ManeuverDifficulty.RED
        }
    }
}
