package com.nsxwing.components.actions

import com.nsxwing.agents.PlayerAgent
import com.nsxwing.components.meta.damage.DamagedSensorArray
import com.nsxwing.components.meta.dice.AttackDie
import com.nsxwing.components.meta.dice.DiceResult

/**
 *
 */
class FixSensorArray extends Action {

    private Set<Action> formerActions

    @Override
    void execute(PlayerAgent target) {
        AttackDie attackDie = new AttackDie()
        attackDie.roll()
        if (attackDie.result == DiceResult.SUCCESS) {
            target.pilot.ship.actions = formerActions
            target.pilot.damageCards.find { it instanceof DamagedSensorArray }.isCritical = false
            target.pilot.ship.actions.remove(this)
        }
    }
}
