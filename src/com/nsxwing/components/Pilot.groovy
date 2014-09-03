package com.nsxwing.components

import com.nsxwing.components.abilities.Ability
import com.nsxwing.components.equipment.EquipmentSlot

/**
 * Class describing the Pilot. Includes Shield and Hull values instead of the ship because the YT-1300
 * has variable shields, hull, and attack based on the chosen pilots.
 */
public class Pilot {
    Ability ability
    Ship ship
    Set<EquipmentSlot> equipments
    int pilotSkill
    int attack
    int agility
    int shieldPoints
    int hullPoints
    int pointCost
}
