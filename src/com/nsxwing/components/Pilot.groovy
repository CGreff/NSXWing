package com.nsxwing.components

import com.nsxwing.components.abilities.Ability

/**
 * Class describing the Pilot. Includes Shield and Hull values instead of the ship because the YT-1200
 * has variable shields and hull based on the chosen pilots.
 */
public class Pilot {
    Ability ability
    int shieldPoints
    int hullPoints
    int pointCost
}
