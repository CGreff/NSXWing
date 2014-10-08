package com.nsxwing.components.abilities;

import com.nsxwing.components.Pilot
import com.nsxwing.components.Ship

/**
 * Interface to be followed by all Pilot Abilities. Should have a conditional function that determines if the
 * required conditions for activation have been met (for example, Wedge has declared a target but has not rolled dice),
 * and an activate function that modifies the game state as appropriate.
 */
public interface Ability {

    public boolean metCondition()
    public void activateAbility(Pilot pilot, Ship ship)
}
