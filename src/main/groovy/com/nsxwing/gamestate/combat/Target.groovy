package com.nsxwing.gamestate.combat

import com.nsxwing.agents.PlayerAgent

/**
 *
 */
class Target {
    PlayerAgent targetAgent
    int range
    boolean obstructed

    @Override
    boolean equals(Object o) {
        targetAgent == ((Target)o).targetAgent
    }
}
