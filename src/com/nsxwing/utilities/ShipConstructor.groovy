package com.nsxwing.utilities

import com.nsxwing.components.Ship
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
class ShipConstructor {

    private static final Map<String, Ship> NAME_TO_SHIP_MAP
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
        Map<String, Ship> map = [:]
        BufferedReader reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream('ShipManeuvers.csv')))
        String shipInfoLine = reader.readLine()

        while(shipInfoLine) {
            String[] shipInfo = shipInfoLine.split(',')
            String mapKey = shipInfo[0]
            int attack = Integer.parseInt(shipInfo[1])
            int agility = Integer.parseInt(shipInfo[2])
            Set<Maneuver> maneuvers = []
            for (int i = 3; i < shipInfo.length; i++) {
                maneuvers.add((Maneuver) MANEUVER_KEY_MAP.get(shipInfo[i].charAt(1) as String)
                        .newInstance(Integer.parseInt(shipInfo[i].charAt(0) as String),
                        MANEUVER_DIFFICULTY_MAP.get(shipInfo[i].charAt(2) as String)))
            }
            map.put(mapKey, new Ship(attack: attack, agility: agility, maneuvers: maneuvers))
            shipInfoLine = reader.readLine()
        }

        NAME_TO_SHIP_MAP = map.asImmutable()
    }

    static Ship getShip(String shipName) {
        NAME_TO_SHIP_MAP.get(shipName)
    }
}
