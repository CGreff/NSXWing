package com.nsxwing.components.meta.damage

import com.nsxwing.components.equipment.Equipment
import com.nsxwing.components.equipment.EquipmentType
import com.nsxwing.components.pilots.Pilot

/**
 *
 */
class MunitionsFailure extends DamageCard {

    DamageType damageType = DamageType.SHIP
    final static List<EquipmentType> SECONDARY_WEAPON_TYPES = [EquipmentType.BOMB, EquipmentType.CANNON, EquipmentType.MISSILE, EquipmentType.TORPEDO, EquipmentType.TURRET]

    @Override
    void resolveCrit(Pilot pilot) {
        //Removes the lowest point cost secondary weapon.
        List<Equipment> secondaryWeapons = pilot.equipment.findAll { SECONDARY_WEAPON_TYPES.contains(it.type) }.sort { it.equipment.pointCost }
        secondaryWeapons.size() > 0 ? secondaryWeapons.remove(0) : null;
    }
}
