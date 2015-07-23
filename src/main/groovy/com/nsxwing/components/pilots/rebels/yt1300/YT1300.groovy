package com.nsxwing.components.pilots.rebels.yt1300

import com.nsxwing.components.actions.Action
import com.nsxwing.components.actions.Focus
import com.nsxwing.components.actions.TargetLock
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

class YT1300 extends Pilot {
    static final List<Maneuver> MANEUVERS = [
            new Forward(1, ManeuverDifficulty.GREEN),
            new Forward(2, ManeuverDifficulty.GREEN),
            new Forward(3, ManeuverDifficulty.WHITE),
            new Forward(4, ManeuverDifficulty.WHITE),
            new Koiogran(3, ManeuverDifficulty.RED),
            new Koiogran(4, ManeuverDifficulty.RED),
            new BankTurn(1, ManeuverDifficulty.GREEN, Direction.LEFT),
            new BankTurn(2, ManeuverDifficulty.WHITE, Direction.LEFT),
            new BankTurn(3, ManeuverDifficulty.WHITE, Direction.LEFT),
            new BankTurn(1, ManeuverDifficulty.GREEN, Direction.RIGHT),
            new BankTurn(2, ManeuverDifficulty.WHITE, Direction.RIGHT),
            new BankTurn(3, ManeuverDifficulty.WHITE, Direction.RIGHT),
            new Turn(1, ManeuverDifficulty.WHITE, Direction.LEFT),
            new Turn(2, ManeuverDifficulty.WHITE, Direction.LEFT),
            new Turn(1, ManeuverDifficulty.WHITE, Direction.RIGHT),
            new Turn(2, ManeuverDifficulty.WHITE, Direction.RIGHT)
    ]

    static final Set<Action> ACTIONS = [
            new Focus(),
            new TargetLock()
    ]

    public YT1300() {
        ship = new Ship(maneuvers: MANEUVERS, actions: ACTIONS, isHuge: true, primaryArc: FiringArc.TURRET)
        attack = 3
        agility = 1
        shieldPoints = 5
        hullPoints = 8
    }
}
