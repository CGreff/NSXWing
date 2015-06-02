package com.nsxwing.components.pilots

import com.nsxwing.components.actions.Action
import com.nsxwing.gamestate.combat.FiringArc
import com.nsxwing.movement.Maneuver

/**
 * Class describing a Ship and its contained field(s).
 */
public class Ship {
    List<Maneuver> maneuvers
    Set<Action> actions
    FiringArc primaryArc
    boolean isHuge
}
