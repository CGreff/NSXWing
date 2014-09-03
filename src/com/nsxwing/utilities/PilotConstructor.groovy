package com.nsxwing.utilities

import com.nsxwing.components.Pilot
import com.nsxwing.components.Ship
import com.nsxwing.components.abilities.VoidAbility
import com.nsxwing.components.equipment.EquipmentSlot
import com.nsxwing.components.equipment.EquipmentType
import static com.nsxwing.components.equipment.EquipmentType.*

/**
 * Utility class that creates Pilots from a CSV file.
 */
class PilotConstructor {
    private static final Map<String, Pilot> NAME_TO_PILOT_MAP
    private static final Map<String, EquipmentType> EQUIPMENT_TYPE_MAP = [
            'A' : ASTROMECH,
            'B' : BOMB,
            'CA' : CANNON,
            'CR' : CREW,
            'E' : ELITE_TALENT,
            'I' : ILLICIT,
            'MI' : MISSILE,
            'MO' : MODIFICATION,
            'S' : SYSTEM_UPGRADE,
            'TI' : TITLE,
            'TO' : TORPEDO,
            'TU' : TURRET
    ]

    static {
        Map<String, Pilot> map = [:]

        //The CSV should have each line as a new ship with the format going: Pilot Name,Ship Name,Pilot Skill,Attack,Agility,Hull,Shield,Cost,Modification Slots (see map)
        BufferedReader reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream('Pilots.csv')))
        String pilotInfoLine = reader.readLine()

        while(pilotInfoLine) {
            String[] pilotInfo = pilotInfoLine.split(',')
            String mapKey = pilotInfo[0]
            Ship ship = ShipConstructor.getShip(pilotInfo[1])
            Set<EquipmentSlot> equipmentSlots = []

            for (int i = 9; i < pilotInfo.length; i++) {
                equipmentSlots.add(new EquipmentSlot(type: EQUIPMENT_TYPE_MAP.get(pilotInfo[i])))
            }

            map.put(mapKey, new Pilot(
                    //TODO: Make abilities.
                    ability: new VoidAbility(),
                    ship: ship,
                    pilotSkill: Integer.parseInt(pilotInfo[2]),
                    attack: Integer.parseInt(pilotInfo[3]),
                    agility: Integer.parseInt(pilotInfo[4]),
                    hullPoints: Integer.parseInt(pilotInfo[5]),
                    shieldPoints: Integer.parseInt(pilotInfo[6]),
                    pointCost: Integer.parseInt(pilotInfo[7]),
                    isUnique: Boolean.parseBoolean(pilotInfo[8]),
                    equipments: equipmentSlots
            ))
            pilotInfoLine = reader.readLine()
        }

        NAME_TO_PILOT_MAP = map.asImmutable()
    }

    static Pilot getPilot(String pilotName) {
        NAME_TO_PILOT_MAP.get(pilotName)
    }
}
