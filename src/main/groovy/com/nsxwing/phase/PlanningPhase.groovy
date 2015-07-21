package com.nsxwing.phase

import com.nsxwing.agents.Player
import com.nsxwing.agents.PlayerAgent
import com.nsxwing.gamestate.field.GameField
import com.nsxwing.gamestate.field.Position
import groovy.util.logging.Slf4j

@Slf4j
class PlanningPhase {

    private static final int LOOKAHEAD_TURNS = 3

    final Player champ
    final Player scrub
    final GameField gameField

    PlanningPhase(Player champ, Player scrub, GameField gameField) {
        this.champ = champ
        this.scrub = scrub
        this.gameField = gameField
    }


    void doPhase(List<PlayerAgent> playerAgents) {
        Map<PlayerAgent, Position> initialPositions = playerAgents.collectEntries { agent -> [agent, agent.position] }

        playerAgents.each { agent ->
            agent.planManeuver(gameField, champ, scrub)
        }

        initialPositions.each { agent, position ->  agent.setPosition(position) }
    }
}
