package com.nsxwing.phase

import com.nsxwing.agents.Player
import com.nsxwing.agents.PlayerAgent
import com.nsxwing.components.actions.Action
import com.nsxwing.components.actions.Focus
import com.nsxwing.components.meta.PlayerIdentifier
import com.nsxwing.gamestate.field.GameField
import com.nsxwing.movement.Maneuver
import com.nsxwing.movement.ManeuverDifficulty
import groovy.util.logging.Slf4j

/**
 *
 */
@Slf4j
class ActivationPhase {

    Player champ
    Player scrub
    GameField gameField

    ActivationPhase(Player champ, Player scrub, GameField gameField) {
        this.champ = champ
        this.scrub = scrub
        this.gameField = gameField
    }

    void doPhase(List<PlayerAgent> agents, Map<PlayerAgent, Maneuver> chosenManeuvers) {
        for(PlayerAgent agent : agents) {
            //TODO: Advanced Sensors
            maneuver(agent, chosenManeuvers.get(agent))
            if (!agent.pilot.isStressed()) {
                performAction(agent)
            }
        }
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
            } else if (maneuver.difficulty == ManeuverDifficulty.GREEN && agent.pilot.isStressed()) {
                agent.pilot.numStressTokens--
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
}
