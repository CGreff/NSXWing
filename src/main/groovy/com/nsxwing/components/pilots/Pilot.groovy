package com.nsxwing.components.pilots

import com.nsxwing.components.equipment.EquipmentSlot
import com.nsxwing.components.meta.damage.DamageCard
import com.nsxwing.components.meta.damage.DamageDeck
import com.nsxwing.components.meta.dice.AttackDie
import com.nsxwing.components.meta.dice.DiceResult
import com.nsxwing.components.meta.dice.EvadeDie
import com.nsxwing.gamestate.combat.Target
import groovy.util.logging.Slf4j

/**
 * Class describing the Pilot. Includes Shield and Hull values instead of the ship because the YT-1300
 * has variable shields, hull, and attack based on the chosen pilots.
 */
@Slf4j
public abstract class Pilot {
    Ship ship
    Closure pilotAbility = { it }
    Set<EquipmentSlot> equipment = []
    List<DamageCard> damageCards = []
    int pilotSkill
    int attack
    int agility
    int shieldPoints
    int hullPoints
    int pointCost
    boolean isUnique
    int numStressTokens = 0
    int numFocusTokens = 0
    int numEvadeTokens = 0

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

    boolean isStressed() {
        numStressTokens > 0
    }

    List<DiceResult> rollAttackDice(Target target) {
        boolean spentFocus = false
        List<AttackDie> attackDice = AttackDie.getDice(target.range == 1 ? attack + 1 : attack)
        attackDice.collect {
            if (it.result == DiceResult.FOCUS && numFocusTokens > 0) {
                it.result = DiceResult.SUCCESS
                spentFocus = true
            }
            it
        }
        spentFocus ? numFocusTokens-- : null
        spentFocus = false
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

    @Override
    String toString() {
        "${this.getClass().simpleName}:(${hullPoints}-${damageCards.size()}, ${shieldPoints})"
    }
}
