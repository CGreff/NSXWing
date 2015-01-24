package com.nsxwing.components.meta.damage

import com.nsxwing.components.equipment.EquipmentType
import com.nsxwing.components.pilots.Pilot

/**
 *
 */
class InjuredPilot extends DamageCard {

    DamageType damageType = DamageType.PILOT

    @Override
    void resolveCrit(Pilot pilot) {
        pilot.pilotAbility = { it }
        pilot.equipments.removeAll { it.type == EquipmentType.ELITE_TALENT }
    }
}
