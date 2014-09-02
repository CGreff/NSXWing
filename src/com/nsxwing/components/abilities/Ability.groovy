package com.nsxwing.components.abilities;

import com.nsxwing.components.Pilot;
import com.nsxwing.components.Ship;

/**
 * Created by Christopher on 9/1/14.
 */
public interface Ability {

    public boolean metCondition()
    public void activateAbility(Pilot pilot, Ship ship)
}
