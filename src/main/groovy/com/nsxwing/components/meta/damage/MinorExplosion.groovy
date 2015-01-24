package com.nsxwing.components.meta.damage

import com.nsxwing.components.meta.dice.AttackDie
import com.nsxwing.components.meta.dice.DiceResult
import com.nsxwing.components.pilots.Pilot

/**
 *
 */
class MinorExplosion extends DamageCard {

    DamageType damageType = DamageType.SHIP

    @Override
    void resolveCrit(Pilot pilot) {
        AttackDie attackDie = new AttackDie()
        attackDie.roll()
        if (attackDie.result == DiceResult.SUCCESS) {
            pilot.sufferDamage(false)
            this.isCritical = false
        }
    }
}
