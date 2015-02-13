package com.nsxwing.gamestate

import com.nsxwing.agents.Player
import com.nsxwing.agents.PlayerAgent
import com.nsxwing.components.actions.Action
import com.nsxwing.components.actions.Focus
import com.nsxwing.components.meta.PlayerIdentifier
import com.nsxwing.components.meta.dice.AttackDie
import com.nsxwing.components.meta.dice.DiceResult
import com.nsxwing.components.meta.dice.EvadeDie
import com.nsxwing.gamestate.combat.Target
import com.nsxwing.gamestate.field.Coordinate
import com.nsxwing.gamestate.field.GameField
import com.nsxwing.gamestate.field.Position
import com.nsxwing.movement.Maneuver
import com.nsxwing.movement.ManeuverDifficulty

/**
 *
 */
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
        Map<PlayerAgent, Maneuver> chosenManeuvers = planTurn()
        doActivationPhase(chosenManeuvers)
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
            chosenManeuvers.put(agent, bestManeuver.maneuver)
        }
        chosenManeuvers
    }

    double getManeuverStrength(PlayerAgent agent, Maneuver maneuver) {
        double strength = 1.0
        double enemyStrengthModifier = 1.0
        Position position = maneuver.move(agent.position)
        for (PlayerAgent enemy : agent.owningPlayer == PlayerIdentifier.CHAMP ? champ.agents : scrub.agents) {
            boolean canTarget = false

            for (Coordinate point : position.boxPoints) {
                if (gameField.isTargetable(enemy.firingArc, point)) {
                    canTarget = true
                    break
                }
            }

            if (canTarget) {
                enemyStrengthModifier /= enemy.pilot.attack
            }
        }
        strength * enemyStrengthModifier
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
                doCombat(agent, target)

                if (target.targetAgent.pilot.hullPoints == 0) {
                    Player affectedPlayer = target.targetAgent.owningPlayer == PlayerIdentifier.CHAMP ? champ : scrub
                    affectedPlayer.agents.remove(target.targetAgent)
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
        !gameField.isOutOfBounds(newPosition.boxPoints) || !(agent.pilot.numStressTokens > 0 && maneuver.difficulty == ManeuverDifficulty.RED)
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
        agent.position = maneuver.move(agent.position)
        if (maneuver.difficulty == ManeuverDifficulty.RED) {
            agent.pilot.numStressTokens++
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
        List<EvadeDie> evadeDice  = EvadeDie.getDice(target.range > 2 ? target.targetAgent.pilot.agility + 1 : target.targetAgent.pilot.agility)
        attackDice.removeAll { it.result == DiceResult.NOTHING || it.result == DiceResult.FOCUS }
        evadeDice.removeAll { it.result == DiceResult.NOTHING || it.result == DiceResult.FOCUS }

        for (int i = 0; i < attackDice.size() - evadeDice.size(); i++) {
            //TODO: Crits
            target.targetAgent.pilot.sufferDamage(false)
        }
    }

    private class RankedManeuver {
        Maneuver maneuver
        double strength
    }
}
