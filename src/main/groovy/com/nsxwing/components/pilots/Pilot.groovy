package com.nsxwing.components.pilots

import com.nsxwing.components.equipment.EquipmentSlot
import com.nsxwing.components.meta.damage.DamageCard
import com.nsxwing.components.meta.damage.DamageDeck

/**
 * Class describing the Pilot. Includes Shield and Hull values instead of the ship because the YT-1300
 * has variable shields, hull, and attack based on the chosen pilots.
 */
public abstract class Pilot {
    Ship ship
    Closure pilotAbility
    Set<EquipmentSlot> equipments
    List<DamageCard> damageCards
    int pilotSkill
    int attack
    int agility
    int shieldPoints
    int hullPoints
    int pointCost
    boolean isUnique
    int numStressTokens

    void sufferDamage(boolean isCritical) {
        if (shieldPoints) {
            shieldPoints--
        } else {
            DamageCard damageCard = DamageDeck.draw()
            if (isCritical) {
                damageCard.isCritical = true
                damageCard.resolveCrit(this)
            }

            damageCards.add(damageCard)
        }
    }
}
