package com.nsxwing.gamestate

import com.nsxwing.agents.Player
import com.nsxwing.agents.PlayerAgent
import com.nsxwing.components.actions.Action
import com.nsxwing.components.actions.Focus
import com.nsxwing.components.meta.PlayerIdentifier
import com.nsxwing.components.meta.damage.DamageCard
import com.nsxwing.components.meta.dice.AttackDie
import com.nsxwing.components.meta.dice.DiceResult
import com.nsxwing.components.meta.dice.EvadeDie
import com.nsxwing.components.pilots.Pilot
import com.nsxwing.gamestate.combat.Target
import com.nsxwing.gamestate.field.Coordinate
import com.nsxwing.gamestate.field.GameField
import com.nsxwing.gamestate.field.Position
import com.nsxwing.movement.Maneuver
import com.nsxwing.movement.ManeuverDifficulty
import groovy.util.logging.Slf4j

/**
 *
 */
@Slf4j
class GameController {

    static PlayerIdentifier PLAYER_WITH_INITIATIVE
    static final Closure ACTIVATION_COMPARATOR = {
        double initiativeModifier = it.owningPlayer == PLAYER_WITH_INITIATIVE ? 0.1 : 0
        it.pilot.pilotSkill - initiativeModifier
    }

    static final Closure COMBAT_COMPARATOR = {
        double initiativeModifier = it.owningPlayer == PLAYER_WITH_INITIATIVE ? 0.1 : 0
        (it.pilot.pilotSkill - initiativeModifier) * -1.0
    }

    final Player champ
    final Player scrub
    final GameField gameField

    GameController(Player champ, Player scrub) {
        this.champ = champ
        this.scrub = scrub
        PLAYER_WITH_INITIATIVE = determineInitiative()
        gameField = new GameField()
    }

    Player doTurn() {
        log.info("Planning turn")
        Map<PlayerAgent, Maneuver> chosenManeuvers = planTurn()
        log.info("Begin Activation Phase")
        doActivationPhase(chosenManeuvers)
        log.info("Begin Combat Phase")
        doCombatPhase()
        checkForWinner()
    }

    Map<PlayerAgent, Maneuver> planTurn() {
        Map<PlayerAgent, Maneuver> chosenManeuvers = [:]
        RankedManeuver bestManeuver = null
        for (PlayerAgent agent : getCombinedAgentList { it }) {
            for (Maneuver maneuver : agent.pilot.ship.maneuvers) {
                if (isLegalManeuver(agent, maneuver)) {
                    double strength = getManeuverStrength(agent, maneuver)
                    if (!bestManeuver) {
                        bestManeuver = new RankedManeuver(maneuver: maneuver, strength: strength)
                    } else if (strength > bestManeuver.strength) {
                        bestManeuver = new RankedManeuver(maneuver: maneuver, strength: strength)
                    }
                }
            }
            chosenManeuvers.put(agent, bestManeuver?.maneuver)
        }
        chosenManeuvers
    }

    double getManeuverStrength(PlayerAgent agent, Maneuver maneuver) {
        double strength = 1.0
        Position position = maneuver.move(agent.position)
        List<PlayerAgent> enemies = agent.owningPlayer == PlayerIdentifier.CHAMP ? champ.agents.sort { it.pointCost * -1 } : scrub.agents.sort { it.pointCost * -1 }
        PlayerAgent bestTarget
        for (PlayerAgent enemy : enemies) {
            for (Coordinate point : enemy.position.boxPoints) {
                if (gameField.isTargetable(agent.firingArc, point)) {
                    bestTarget = enemy
                    break
                }
            }

            if (bestTarget) {
                break
            }
        }

        if (bestTarget) {
            strength = bestTarget.pointCost
        } else {
            strength = 1000 - (gameField.getDistanceBetween(position.center, enemies.get(0).position.center))
        }

        boolean hasNextMove = false
        for (Maneuver nextManeuver : agent.pilot.ship.maneuvers) {
            if (isLegalManeuver(position, nextManeuver)) {
                hasNextMove = true
                break
            }
        }

        hasNextMove ? strength : 0
    }

    void doActivationPhase(Map<PlayerAgent, Maneuver> chosenManeuvers) {
        for(PlayerAgent agent : getCombinedAgentList(ACTIVATION_COMPARATOR)) {
            //TODO: Advanced Sensors
            maneuver(agent, chosenManeuvers.get(agent))
            if (agent.pilot.numStressTokens == 0) {
                performAction(agent)
            }
        }
    }

    void doCombatPhase() {
        for(PlayerAgent agent : getCombinedAgentList(COMBAT_COMPARATOR)) {
            List<Target> targets = gameField.getTargetsFor(champ, scrub, agent).sort { it.priority }
            Target target = targets ? targets.get(0) : null
            if (target) {
                log.info("${agent} is attacking ${target}")
                doCombat(agent, target)

                if (isDestroyed(target.targetAgent.pilot)) {
                    Player affectedPlayer = target.targetAgent.owningPlayer == PlayerIdentifier.CHAMP ? champ : scrub
                    affectedPlayer.agents.remove(target.targetAgent)
                    log.info("${agent.pilot} destroyed ${target.targetAgent.pilot}")
                }
            }
        }
    }

    List<PlayerAgent> getCombinedAgentList(Closure comparator) {
        List<PlayerAgent> combinedList = new ArrayList(champ.agents)
        combinedList.addAll(scrub.agents)
        combinedList.sort(comparator)
    }

    boolean isLegalManeuver(PlayerAgent agent, Maneuver maneuver) {
        Position newPosition = maneuver.move(agent.position)
        !gameField.isOutOfBounds(newPosition.boxPoints) && !(agent.pilot.numStressTokens > 0 && maneuver.difficulty == ManeuverDifficulty.RED)
    }

    boolean isLegalManeuver(Position position, Maneuver maneuver) {
        Position newPosition = maneuver.move(position)
        !gameField.isOutOfBounds(newPosition.boxPoints)
    }

    private PlayerIdentifier determineInitiative() {
        (champ.costOfList <= scrub.costOfList) ? PlayerIdentifier.CHAMP : PlayerIdentifier.SCRUB
    }

    private Player checkForWinner() {
        if (!champ.agents) {
            return scrub
        }

        if (!scrub.agents) {
            return champ
        }

        null
    }

    private void maneuver(PlayerAgent agent, Maneuver maneuver) {
        if (!maneuver) {
            log.info("${agent} flew off the board.")
            Player owningPlayer = agent.owningPlayer == PlayerIdentifier.CHAMP ? champ : scrub
            owningPlayer.agents.remove(agent)
        } else {
            log.info("${agent} is performing ${maneuver}")
            agent.position = maneuver.move(agent.position)

            if (maneuver.difficulty == ManeuverDifficulty.RED) {
                agent.pilot.numStressTokens++
            }
        }
    }

    //TODO: Implement picking and performing actions.
    private void performAction(PlayerAgent agent) {
        for (Action action : agent.pilot.ship.actions.sort { it.actionPriority }) {
            if (action instanceof Focus) {
                action.execute(agent)
            }
        }
    }

    //TODO: Fix this.
    private void doCombat(PlayerAgent agent, Target target) {
        List<AttackDie> attackDice = AttackDie.getDice(target.range == 1 ? agent.pilot.attack + 1 : agent.pilot.attack)
        attackDice.removeAll { it.result == DiceResult.NOTHING || it.result == DiceResult.FOCUS }
        List<EvadeDie> evadeDice  = EvadeDie.getDice(target.range > 2 ? target.targetAgent.pilot.agility + 1 : target.targetAgent.pilot.agility)
        evadeDice.removeAll { it.result == DiceResult.NOTHING || it.result == DiceResult.FOCUS }

        for (int i = 0; i < attackDice.size() - evadeDice.size(); i++) {
            //TODO: Crits
            target.targetAgent.pilot.sufferDamage(false)
            log.info("${target.targetAgent} suffered a damage!")
        }
    }

    private boolean isDestroyed(Pilot pilot) {
        int damage = 0

        for (DamageCard damageCard : pilot.damageCards) {
            damage += damageCard.damageValue
        }

        damage >= pilot.hullPoints
    }

    private class RankedManeuver {
        Maneuver maneuver
        double strength
    }
}
