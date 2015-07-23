package com.nsxwing

import com.nsxwing.agents.Player
import com.nsxwing.agents.PlayerAgent
import com.nsxwing.components.Faction
import com.nsxwing.components.meta.PlayerIdentifier
import com.nsxwing.components.pilots.imperials.tieadvanced.TempestSquadronPilot
import com.nsxwing.components.pilots.imperials.tiefighter.AcademyPilot
import com.nsxwing.components.pilots.imperials.tiefighter.ObsidianSquadronPilot
import com.nsxwing.components.pilots.rebels.yt1300.HanSolo
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
        champ.agents = [
                new PlayerAgent(pilot: new HanSolo(), owningPlayer: PlayerIdentifier.CHAMP, pointCost: 46)
        ]

        scrub.agents = [
                new PlayerAgent(pilot: new AcademyPilot(), owningPlayer: PlayerIdentifier.SCRUB, pointCost: 12),
                new PlayerAgent(pilot: new AcademyPilot(), owningPlayer: PlayerIdentifier.SCRUB, pointCost: 12),
                new PlayerAgent(pilot: new AcademyPilot(), owningPlayer: PlayerIdentifier.SCRUB, pointCost: 12)
        ]

        champ.placeUnits()
        scrub.placeUnits()
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

            if (turnNumber == 100) {
                winner = champ.pointsRemaining >= scrub.pointsRemaining ? champ : scrub
            }
        }


        Long gameTime = startTime.until(LocalDateTime.now(), ChronoUnit.MILLIS)
        log.info("${winner} won on turn ${turnNumber} in ${gameTime}ms!")
        new GameRecord(winner: winner?.identifier, numberOfTurns: turnNumber, pointsRemaining: winner?.getPointsRemaining())
    }
}
