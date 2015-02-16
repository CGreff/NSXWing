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
import com.nsxwing.phase.ActivationPhase
import com.nsxwing.phase.CombatPhase
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
    private CombatPhase combatPhase
    private ActivationPhase activationPhase

    GameController(Player champ, Player scrub) {
        this.champ = champ
        this.scrub = scrub
        PLAYER_WITH_INITIATIVE = determineInitiative()
        gameField = new GameField()
        combatPhase = new CombatPhase(champ, scrub, gameField)
        activationPhase = new ActivationPhase(champ, scrub, gameField)
    }

    Player doTurn() {
        log.info("Planning turn")
        Map<PlayerAgent, Maneuver> chosenManeuvers = planTurn()
        log.info("Begin Activation Phase")
        activationPhase.doPhase(getCombinedAgentList(ACTIVATION_COMPARATOR), chosenManeuvers)
        log.info("Begin Combat Phase")
        combatPhase.doPhase(getCombinedAgentList(COMBAT_COMPARATOR))
        checkForWinner()
    }

    Map<PlayerAgent, Maneuver> planTurn() {
        Map<PlayerAgent, Maneuver> chosenManeuvers = [:]
        RankedManeuver bestManeuver
        for (PlayerAgent agent : getCombinedAgentList { it }) {
            bestManeuver = null
            for (Maneuver maneuver : agent.pilot.ship.maneuvers) {
                if (gameField.isLegalManeuver(agent, maneuver)) {
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
        double strength
        Position position = maneuver.move(agent.position)
        List<PlayerAgent> enemies = agent.owningPlayer == PlayerIdentifier.CHAMP ? champ.agents.sort { it.pointCost * -1 } : scrub.agents.sort { it.pointCost * -1 }
        List<Target> targets = gameField.getTargetsFor(champ, scrub, agent)

        if (targets) {
            strength = targets.sort { (it.targetAgent.pointCost * -1) - (0.1 * it.targetAgent.pilot.damageCards.size()) }.get(0).targetAgent.pointCost
        } else {
            strength = 1000 - ((gameField.getDistanceBetween(position.center, enemies.get(0).position.center)) * (facingEnemies(agent, enemies) ? 0.1 : 1.0))
        }

        boolean hasNextMove = false
        for (Maneuver nextManeuver : agent.pilot.ship.maneuvers) {
            if (gameField.isLegalManeuver(position, nextManeuver)) {
                hasNextMove = true
                break
            }
        }

        hasNextMove ? strength : 0
    }

    List<PlayerAgent> getCombinedAgentList(Closure comparator) {
        List<PlayerAgent> combinedList = new ArrayList(champ.agents)
        combinedList.addAll(scrub.agents)
        combinedList.sort(comparator)
    }

    private PlayerIdentifier determineInitiative() {
        (champ.costOfList <= scrub.costOfList) ? PlayerIdentifier.CHAMP : PlayerIdentifier.SCRUB
    }

    private Player checkForWinner() {
        if (!scrub.agents) {
            return champ
        }

        if (!champ.agents) {
            return scrub
        }

        null
    }

    private boolean facingEnemies(PlayerAgent agent, List<PlayerAgent> enemies) {
        for (PlayerAgent enemy : enemies) {
            if (gameField.isTargetable(agent.firingArc, enemy.position.center)) {
                return true
            }
        }

        false
    }

    private class RankedManeuver {
        Maneuver maneuver
        double strength
    }
}
