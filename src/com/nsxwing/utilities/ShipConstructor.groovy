package com.nsxwing.utilities

import com.nsxwing.components.Ship
import com.nsxwing.components.actions.Action
import com.nsxwing.components.actions.BarrelRoll
import com.nsxwing.components.actions.Boost
import com.nsxwing.components.actions.Cloak
import com.nsxwing.components.actions.Evade
import com.nsxwing.components.actions.Focus
import com.nsxwing.components.actions.TargetLock
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
 * Utility class to build a list of ships from a CSV file and provide a ship name to Ship factory method.
 */
class ShipConstructor {

    private static final Map<String, Ship> NAME_TO_SHIP_MAP
    private static final Map<String, Class> MANEUVER_KEY_MAP = [
            'F' : Forward,
            'K' : Koiogran,
            'B' : BankTurn,
            'T' : HardTurn
    ].asImmutable()
    private static final Map<String, Class> ACTION_KEY_MAP = [
            'barrelroll' : BarrelRoll,
            'boost' : Boost,
            'cloak' : Cloak,
            'evade' : Evade,
            'focus' : Focus,
            'targetlock' : TargetLock
    ].asImmutable()
    private static final Map<String, ManeuverDifficulty> MANEUVER_DIFFICULTY_MAP = [
            'W' : WHITE,
            'G' : GREEN,
            'R' : RED,
    ].asImmutable()

    static {
        Map<String, Ship> map = [:]

        //The CSV should have each line as a new ship with the format going: ShipName,IsHuge,Action,Action,Action..,Maneuver,Maneuver,Maneuver...
        //Maneuvers should match '[1-5][FKBT][WGR]'; Where 1 through 5 is the distance of the maneuver - see the maps above.
        BufferedReader reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream('Ships.csv')))
        String shipInfoLine = reader.readLine()

        while(shipInfoLine) {
            String[] shipInfo = shipInfoLine.split(',')
            String mapKey = shipInfo[0]
            boolean isHuge = Boolean.parseBoolean(shipInfo[1])
            //Keep track of the array pointer.
            int i = 2
            //Build the Action Set.
            Set<Action> actions = []
            while (!shipInfo[i].charAt(0).digit) {
                actions.add((Action) ACTION_KEY_MAP.get(shipInfo[i]).newInstance())
                i++
            }
            //Build the Maneuver Set.
            Set<Maneuver> maneuvers = []
            for (i; i < shipInfo.length; i++) {
                maneuvers.add((Maneuver) MANEUVER_KEY_MAP.get(shipInfo[i].charAt(1) as String)
                        .newInstance(Integer.parseInt(shipInfo[i].charAt(0) as String),
                        MANEUVER_DIFFICULTY_MAP.get(shipInfo[i].charAt(2) as String)))
            }
            //Put the ship in there.
            map.put(mapKey, new Ship(maneuvers: maneuvers, isHuge: isHuge, actions: actions))
            shipInfoLine = reader.readLine()
        }

        NAME_TO_SHIP_MAP = map.asImmutable()
    }

    static Ship getShip(String shipName) {
        NAME_TO_SHIP_MAP.get(shipName)
    }
}
