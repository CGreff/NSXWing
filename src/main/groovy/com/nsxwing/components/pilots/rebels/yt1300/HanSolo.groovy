package com.nsxwing.components.pilots.rebels.yt1300

import com.nsxwing.components.meta.dice.AttackDie
import com.nsxwing.components.meta.dice.DiceResult
import com.nsxwing.components.meta.dice.EvadeDie
import com.nsxwing.gamestate.combat.Target
import groovy.util.logging.Slf4j

@Slf4j
class HanSolo extends YT1300 {

    public HanSolo() {
        super()
        pilotSkill = 9
        pointCost = 46
        isUnique = true
    }

    @Override
    List<DiceResult> rollAttackDice(Target target) {
        boolean spentFocus = false
        List<AttackDie> attackDice = tryAttackDice(target)
        if (attackDice.size() < 2) {
            attackDice.collect { it.roll() }
        }
        attackDice.collect {
            if (it.result == DiceResult.FOCUS && numFocusTokens > 0) {
                it.result = DiceResult.SUCCESS
                spentFocus = true
            }
            it
        }
        spentFocus ? numFocusTokens-- : null
        attackDice.removeAll { it.result == DiceResult.NOTHING || it.result == DiceResult.FOCUS }

        List<EvadeDie> evadeDice  = EvadeDie.getDice(target.range > 2 ? target.targetAgent.pilot.agility + 1 : target.targetAgent.pilot.agility)
        evadeDice.collect {
            if (it.result == DiceResult.FOCUS && target.targetAgent.pilot.numFocusTokens > 0) {
                it.result = DiceResult.SUCCESS
                spentFocus = true
            }
            it
        }
        spentFocus ? target.targetAgent.pilot.numFocusTokens-- : null

        evadeDice.removeAll { it.result == DiceResult.NOTHING || it.result == DiceResult.FOCUS }

        for (int i = 0; i < attackDice.size() - evadeDice.size(); i++) {
            //TODO: Crits
            target.targetAgent.pilot.sufferDamage(false)
            log.info("${target.targetAgent} suffered a damage!")
        }
    }

    private List<AttackDie> tryAttackDice(Target target) {
        List<AttackDie> attackDice = AttackDie.getDice(target.range == 1 ? attack + 1 : attack)

        attackDice.removeAll { it.result == DiceResult.NOTHING }
        attackDice
    }
}
