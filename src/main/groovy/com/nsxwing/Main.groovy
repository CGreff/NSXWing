package com.nsxwing

import com.nsxwing.agents.Player
import com.nsxwing.components.Faction
import com.nsxwing.components.meta.PlayerIdentifier
import com.nsxwing.gamestate.GameController
import groovy.util.logging.Slf4j

@Slf4j
public class Main {

    public static void main(String[] args) {
        Player champ = new Player(identifier: PlayerIdentifier.CHAMP, costOfList: 100, faction: Faction.EMPIRE)
        Player scrub = new Player(identifier: PlayerIdentifier.SCRUB, costOfList: 100, faction: Faction.EMPIRE)

        //TODO: Implement list building.
        champ.buildList()
        scrub.buildList()

        GameController controller = new GameController(champ, scrub)

        int turnNumber = 1
        log.info("Starting turn 1")
        Player winner = controller.doTurn()

        while (!winner) {
            turnNumber++
            log.info("Starting turn ${turnNumber}")
            winner = controller.doTurn()
        }

        log.info("${winner} won on turn ${turnNumber}!")
    }
}
