package com.nsxwing

import com.nsxwing.agents.Player
import com.nsxwing.components.Faction
import com.nsxwing.components.meta.PlayerIdentifier
import com.nsxwing.gamestate.GameController

public class Main {

    public static void main(String[] args) {
        Player champ = new Player(identifier: PlayerIdentifier.CHAMP, costOfList: 100, faction: Faction.EMPIRE)
        Player scrub = new Player(identifier: PlayerIdentifier.SCRUB, costOfList: 100, faction: Faction.EMPIRE)

        //TODO: Implement list building.
        champ.buildList()
        scrub.buildList()

        GameController controller = new GameController(champ, scrub)

        Player winner = controller.doTurn()
        while (!winner) {
            winner = controller.doTurn()
        }

        System.out.println("${winner} won!")
    }
}
