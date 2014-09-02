package com.nsxwing.agents;

import com.nsxwing.components.Pilot
import com.nsxwing.gamestate.Phase
import com.nsxwing.gamestate.Position

/**
 * An agent in charge of a single ship, and maintains its pilot, ship, equipment, etc. as well as
 * its current position and its current phase (activation (movement/ability), combat whatever).
 */
public class PlayerAgent {
    Pilot pilot
    Position position
    Phase phase
}
