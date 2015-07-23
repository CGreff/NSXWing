package com.nsxwing.phase

import com.nsxwing.agents.Player
import com.nsxwing.agents.PlayerAgent
import com.nsxwing.components.meta.PlayerIdentifier
import com.nsxwing.components.meta.damage.DamageCard
import com.nsxwing.components.meta.dice.AttackDie
import com.nsxwing.components.meta.dice.DiceResult
import com.nsxwing.components.meta.dice.EvadeDie
import com.nsxwing.components.pilots.Pilot
import com.nsxwing.gamestate.combat.Target
import com.nsxwing.gamestate.field.GameField
import groovy.util.logging.Slf4j

@Slf4j
class CombatPhase {

    Player champ
    Player scrub
    GameField gameField

    CombatPhase(Player champ, Player scrub, GameField gameField) {
        this.champ = champ
        this.scrub = scrub
        this.gameField = gameField
    }


    void doPhase(List<PlayerAgent> agents) {
        Set<PlayerAgent> hasAttacked = [] as Set
        int pilotSkill
        for(PlayerAgent agent : agents) {
            if (!hasAttacked.contains(agent)) {
                pilotSkill = agent.pilot.pilotSkill;
                List<Target> targets = gameField.getTargetsFor(champ, scrub, agent).sort { it.priority }
                Target target = targets ? targets.get(0) : null

                if (target) {
                    log.info("${agent} is attacking ${target}")
                    doCombat(agent, target)

                    if (isDestroyed(target.targetAgent.pilot)) {
                        if (target.targetAgent.pilot.pilotSkill != pilotSkill) {
                            hasAttacked.add(target.targetAgent)
                        }

                        Player affectedPlayer = target.targetAgent.owningPlayer == PlayerIdentifier.CHAMP ? champ : scrub
                        affectedPlayer.agents.remove(target.targetAgent)

                        log.info("${agent.pilot} destroyed ${target.targetAgent.pilot}")
                    }
                }

                hasAttacked.add(agent)
            }
        }
    }

    boolean isDestroyed(Pilot pilot) {
        int damage = 0

        for (DamageCard damageCard : pilot.damageCards) {
            damage += damageCard.damageValue
        }

        damage >= pilot.hullPoints
    }

    //TODO: Fix this. Autoblaster, Ten Numb, Kath, etc.
    private void doCombat(PlayerAgent agent, Target target) {
        agent.pilot.rollAttackDice(target)
    }
}
