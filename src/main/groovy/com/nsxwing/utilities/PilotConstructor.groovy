package com.nsxwing.utilities

import com.nsxwing.components.pilots.Pilot
import com.nsxwing.components.pilots.Ship
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
            String packageId = pilotInfo[0]
            String pilotName = pilotInfo[1]

            map.put(pilotName, Class.forName("com.nsxwing.components.pilots.${packageId}.${pilotName.replaceAll(' ', '')}").newInstance() as Pilot)
            pilotInfoLine = reader.readLine()
        }

        NAME_TO_PILOT_MAP = map.asImmutable()
    }

    static Pilot getPilot(String pilotName) {
        NAME_TO_PILOT_MAP.get(pilotName)
    }

    static List<Pilot> getPilots() {
        NAME_TO_PILOT_MAP.values() as List
    }
}
