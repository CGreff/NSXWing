package com.nsxwing

import com.nsxwing.agents.Player
import com.nsxwing.components.Faction
import com.nsxwing.components.meta.PlayerIdentifier
import com.nsxwing.gamestate.GameController
import com.nsxwing.utilities.GameRecord
import groovy.util.logging.Slf4j

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

/**
 * The class to play the actual game.
 */
@Slf4j
class Game {

    Player champ
    Player scrub
    GameController controller

    Game() {
        champ = new Player(identifier: PlayerIdentifier.CHAMP, costOfList: 100, faction: Faction.EMPIRE)
        scrub = new Player(identifier: PlayerIdentifier.SCRUB, costOfList: 100, faction: Faction.EMPIRE)

        //TODO: Implement list building.
        champ.buildList()
        scrub.buildList()
        controller = new GameController(champ, scrub)
    }

    GameRecord playGame() {
        int turnNumber = 1
        log.info("Starting turn 1")
        LocalDateTime startTime = LocalDateTime.now()
        Player winner = controller.doTurn()

        while (!winner) {
            turnNumber++
            log.info("Starting turn ${turnNumber}")
            winner = controller.doTurn()
        }

        Long gameTime = startTime.until(LocalDateTime.now(), ChronoUnit.MILLIS)
        log.info("${winner} won on turn ${turnNumber} in ${gameTime}ms!")
        new GameRecord(winner: winner.identifier, numberOfTurns: turnNumber, pointsRemaining: winner.getPointsRemaining())
    }
}
