package com.nsxwing

import com.nsxwing.agents.Player
import com.nsxwing.agents.PlayerAgent
import com.nsxwing.components.Faction
import com.nsxwing.components.meta.PlayerIdentifier
import com.nsxwing.gamestate.field.Coordinate
import com.nsxwing.gamestate.Phase
import com.nsxwing.gamestate.field.Position
import com.nsxwing.utilities.PilotConstructor

public class Main {

    public static void main(String[] args) {
        Player p1 = new Player(identifier: PlayerIdentifier.CHAMP, costOfList: 100, faction: Faction.EMPIRE)
        p1.buildList()
        Player p2 = new Player(identifier: PlayerIdentifier.SCRUB, costOfList: 100, faction: Faction.EMPIRE)
        p2.buildList()
    }
}
