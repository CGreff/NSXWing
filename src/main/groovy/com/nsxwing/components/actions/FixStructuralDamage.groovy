package com.nsxwing.components.actions

import com.nsxwing.agents.PlayerAgent
import com.nsxwing.components.meta.damage.StructuralDamage
import com.nsxwing.components.meta.dice.AttackDie
import com.nsxwing.components.meta.dice.DiceResult

/**
 *
 */
class FixStructuralDamage extends Action {

    @Override
    void execute(PlayerAgent target) {
        AttackDie attackDie = new AttackDie()
        attackDie.roll()
        if (attackDie.result == DiceResult.SUCCESS) {
            target.pilot.agility++
            target.pilot.damageCards.find { it instanceof StructuralDamage }.isCritical = false
            target.pilot.ship.actions.remove(this)
        }
    }
}
