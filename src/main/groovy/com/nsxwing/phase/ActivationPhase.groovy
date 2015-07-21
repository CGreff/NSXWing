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

    void doPhase(List<PlayerAgent> agents) {
        agents.each {
            it.doManeuver(it.owningPlayer == PlayerIdentifier.CHAMP ? champ : scrub)
            if (!it.pilot.isStressed()) {
                performAction(it)
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
