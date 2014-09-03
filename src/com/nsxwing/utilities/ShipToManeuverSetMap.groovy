package com.nsxwing.utilities

import com.nsxwing.movement.BankTurn
import com.nsxwing.movement.Forward
import com.nsxwing.movement.HardTurn
import com.nsxwing.movement.Koiogran
import com.nsxwing.movement.Maneuver
import com.nsxwing.movement.ManeuverDifficulty
import static com.nsxwing.movement.ManeuverDifficulty.GREEN
import static com.nsxwing.movement.ManeuverDifficulty.WHITE
import static com.nsxwing.movement.ManeuverDifficulty.RED

/**
 * Utility class to take a ship name and provide the Set<Maneuver> for it.
 */
class ShipToManeuverSetMap {

    private static final Map<String, Set<Maneuver>> SHIP_TO_MANEUVER_MAP
    private static final Map<String, Class> MANEUVER_KEY_MAP = [
            'F' : Forward,
            'K' : Koiogran,
            'B' : BankTurn,
            'T' : HardTurn
    ].asImmutable()
    private static final Map<String, ManeuverDifficulty> MANEUVER_DIFFICULTY_MAP = [
            'W' : WHITE,
            'G' : GREEN,
            'R' : RED,
    ].asImmutable()

    static {
        Map<String, Set<Maneuver>> map = [:]
        BufferedReader reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream('ShipManeuvers.csv')))
        String shipManeuverLine = reader.readLine()

        while(shipManeuverLine) {
            String[] maneuverInfo = shipManeuverLine.split(',')
            String mapKey = maneuverInfo[0]
            Set<Maneuver> maneuvers = []
            for (int i = 1; i < maneuverInfo.length; i++) {
                maneuvers.add((Maneuver) MANEUVER_KEY_MAP.get(maneuverInfo[i].charAt(1) as String)
                        .newInstance(Integer.parseInt(maneuverInfo[i].charAt(0) as String),
                        MANEUVER_DIFFICULTY_MAP.get(maneuverInfo[i].charAt(2) as String)))
            }
            map.put(mapKey, maneuvers)
            shipManeuverLine = reader.readLine()
        }

        SHIP_TO_MANEUVER_MAP = map.asImmutable()
    }


    static Set<Maneuver> getManeuvers(String shipName) {
        SHIP_TO_MANEUVER_MAP.get(shipName)
    }
}
