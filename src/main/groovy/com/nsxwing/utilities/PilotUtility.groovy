package com.nsxwing.utilities

import com.nsxwing.components.pilots.Pilot

/**
 * Utility class that creates Pilots from a CSV file.
 */
class PilotUtility {
    private static final Map<String, Pilot> NAME_TO_PILOT_MAP

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
