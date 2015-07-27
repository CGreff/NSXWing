package com.nsxwing.components.pilots.rebels.bwing

import com.nsxwing.components.actions.Action
import com.nsxwing.components.actions.BarrelRoll
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

import static com.nsxwing.movement.Direction.LEFT
import static com.nsxwing.movement.Direction.RIGHT
import static com.nsxwing.movement.ManeuverDifficulty.*

class BWing extends Pilot {
    static final List<Maneuver> MANEUVERS = [
            new Forward(1, GREEN),
            new Forward(2, GREEN),
            new Forward(3, WHITE),
            new Forward(4, RED),
            new Koiogran(2, RED),
            new BankTurn(1, GREEN, LEFT),
            new BankTurn(2, WHITE, LEFT),
            new BankTurn(3, RED, LEFT),
            new BankTurn(1, GREEN, RIGHT),
            new BankTurn(2, WHITE, RIGHT),
            new BankTurn(3, RED, RIGHT),
            new Turn(1, RED, LEFT),
            new Turn(2, WHITE, LEFT),
            new Turn(1, RED, RIGHT),
            new Turn(2, WHITE, RIGHT)
    ]

    static final Set<Action> ACTIONS = [
            new Focus(),
            new TargetLock(),
            new BarrelRoll()
    ]

    public BWing() {
        ship = new Ship(maneuvers: MANEUVERS, actions: ACTIONS, isHuge: false, primaryArc: FiringArc.NORMAL)
        attack = 3
        agility = 1
        shieldPoints = 5
        hullPoints = 3
    }
}
