package com.nsxwing.components.pilots.rebels.z95

import com.nsxwing.components.actions.Action
import com.nsxwing.components.actions.Focus
import com.nsxwing.components.actions.TargetLock
import com.nsxwing.components.pilots.Pilot
import com.nsxwing.components.pilots.Ship
import com.nsxwing.gamestate.combat.FiringArc
import com.nsxwing.movement.BankTurn
import com.nsxwing.movement.Forward
import com.nsxwing.movement.Koiogran
import com.nsxwing.movement.Maneuver
import com.nsxwing.movement.Turn

import static com.nsxwing.movement.Direction.*
import static com.nsxwing.movement.ManeuverDifficulty.*

class Z95 extends Pilot {
    static final List<Maneuver> MANEUVERS = [
            new Forward(1, GREEN),
            new Forward(2, GREEN),
            new Forward(3, WHITE),
            new Forward(4, WHITE),
            new Koiogran(3, RED),
            new BankTurn(1, WHITE, LEFT),
            new BankTurn(2, GREEN, LEFT),
            new BankTurn(3, WHITE, LEFT),
            new BankTurn(1, WHITE, RIGHT),
            new BankTurn(2, GREEN, RIGHT),
            new BankTurn(3, WHITE, RIGHT),
            new Turn(2, WHITE, LEFT),
            new Turn(3, WHITE, LEFT),
            new Turn(2, WHITE, RIGHT),
            new Turn(3, WHITE, RIGHT)
    ]

    static final Set<Action> ACTIONS = [
            new Focus(),
            new TargetLock(),
    ]

    public Z95() {
        ship = new Ship(maneuvers: MANEUVERS, actions: ACTIONS, isHuge: false, primaryArc: FiringArc.NORMAL)
        attack = 2
        agility = 2
        shieldPoints = 2
        hullPoints = 2
    }
}
