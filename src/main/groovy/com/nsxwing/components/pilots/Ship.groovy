package com.nsxwing.components.pilots

import com.nsxwing.components.actions.Action
import com.nsxwing.movement.Maneuver
import groovy.transform.Immutable

/**
 * Class describing a Ship and its contained field(s).
 */
@Immutable(copyWith = true)
public class Ship {
    Set<Maneuver> maneuvers
    Set<Action> actions
    boolean isHuge
}
