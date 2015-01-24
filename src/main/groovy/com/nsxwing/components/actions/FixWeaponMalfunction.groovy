package com.nsxwing.components.actions

import com.nsxwing.agents.PlayerAgent
import com.nsxwing.components.meta.damage.WeaponMalfunction
import com.nsxwing.components.meta.dice.AttackDie
import com.nsxwing.components.meta.dice.DiceResult

/**
 *
 */
class FixWeaponMalfunction extends Action {

    @Override
    void execute(PlayerAgent target) {
        AttackDie attackDie = new AttackDie()
        attackDie.roll()
        if (attackDie.result == DiceResult.SUCCESS || attackDie.result == DiceResult.CRITICAL_HIT) {
            target.pilot.attack++
            target.pilot.damageCards.find { it instanceof WeaponMalfunction }.isCritical = false
            target.pilot.ship.actions.remove(this)
        }
    }
}
