package com.nsxwing.components.pilots.imperials.tieadvanced

import com.nsxwing.components.actions.Action
import com.nsxwing.components.actions.BarrelRoll
import com.nsxwing.components.actions.Evade
import com.nsxwing.components.actions.Focus
import com.nsxwing.components.actions.TargetLock
import com.nsxwing.components.pilots.Pilot
import com.nsxwing.components.pilots.Ship
import com.nsxwing.movement.BankTurn
import com.nsxwing.movement.Forward
import com.nsxwing.movement.Koiogran
import com.nsxwing.movement.Maneuver
import com.nsxwing.movement.ManeuverDifficulty
import com.nsxwing.movement.Turn

/**
 *
 */
class TieAdvanced extends Pilot {
    static final Set<Maneuver> MANEUVERS = [
            new Forward(2, ManeuverDifficulty.GREEN),
            new Forward(3, ManeuverDifficulty.GREEN),
            new Forward(4, ManeuverDifficulty.WHITE),
            new Forward(5, ManeuverDifficulty.WHITE),
            new Koiogran(4, ManeuverDifficulty.RED),
            new BankTurn(1, ManeuverDifficulty.GREEN),
            new BankTurn(2, ManeuverDifficulty.WHITE),
            new BankTurn(3, ManeuverDifficulty.WHITE),
            new Turn(2, ManeuverDifficulty.WHITE),
            new Turn(3, ManeuverDifficulty.WHITE)
    ]

    static final Set<Action> ACTIONS = [
            new Focus(),
            new Evade(),
            new BarrelRoll(),
            new TargetLock()
    ]

    final Ship ship = new Ship(maneuvers: MANEUVERS, actions: ACTIONS, isHuge: false)
    final int attack = 2
    final int agility = 3
    final int shieldPoints = 2
    final int hullPoints = 3
}