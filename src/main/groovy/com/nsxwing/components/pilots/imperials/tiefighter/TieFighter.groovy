package com.nsxwing.components.pilots.imperials.tiefighter

import com.nsxwing.components.actions.Action
import com.nsxwing.components.actions.BarrelRoll
import com.nsxwing.components.actions.Evade
import com.nsxwing.components.actions.Focus
import com.nsxwing.components.pilots.Pilot
import com.nsxwing.components.pilots.Ship
import com.nsxwing.gamestate.combat.FiringArc
import com.nsxwing.movement.BankTurn
import com.nsxwing.movement.Direction
import com.nsxwing.movement.Forward
import com.nsxwing.movement.Koiogran
import com.nsxwing.movement.Maneuver
import com.nsxwing.movement.ManeuverDifficulty
import com.nsxwing.movement.Turn

class TieFighter extends Pilot {
    static final List<Maneuver> MANEUVERS = [
            new Forward(2, ManeuverDifficulty.GREEN),
            new Forward(3, ManeuverDifficulty.GREEN),
            new Forward(4, ManeuverDifficulty.WHITE),
            new Forward(5, ManeuverDifficulty.WHITE),
            new Koiogran(3, ManeuverDifficulty.RED),
            new Koiogran(4, ManeuverDifficulty.RED),
            new BankTurn(2, ManeuverDifficulty.GREEN, Direction.LEFT),
            new BankTurn(3, ManeuverDifficulty.WHITE, Direction.LEFT),
            new BankTurn(2, ManeuverDifficulty.GREEN, Direction.RIGHT),
            new BankTurn(3, ManeuverDifficulty.WHITE, Direction.RIGHT),
            new Turn(1, ManeuverDifficulty.WHITE, Direction.LEFT),
            new Turn(2, ManeuverDifficulty.WHITE, Direction.LEFT),
            new Turn(3, ManeuverDifficulty.WHITE, Direction.LEFT),
            new Turn(1, ManeuverDifficulty.WHITE, Direction.RIGHT),
            new Turn(2, ManeuverDifficulty.WHITE, Direction.RIGHT),
            new Turn(3, ManeuverDifficulty.WHITE, Direction.RIGHT)
    ]

    static final Set<Action> ACTIONS = [
            new Focus(),
            new Evade(),
            new BarrelRoll()
    ]

    Ship ship = new Ship(maneuvers: MANEUVERS, actions: ACTIONS, isHuge: false, primaryArc: FiringArc.NORMAL)
    int attack = 2
    int agility = 3
    int shieldPoints = 0
    int hullPoints = 3
}
