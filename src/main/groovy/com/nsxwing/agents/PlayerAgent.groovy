package com.nsxwing.agents;

import com.nsxwing.components.pilots.Pilot
import com.nsxwing.gamestate.Phase
import com.nsxwing.gamestate.combat.FiringLine
import com.nsxwing.gamestate.field.Coordinate
import com.nsxwing.gamestate.field.Position

/**
 * An agent in charge of a single ship, and maintains its pilot, ship, equipment, etc. as well as
 * its current position and its current phase (activation (movement/ability), combat whatever).
 */
public class PlayerAgent {
    Pilot pilot
    Position position
    Phase phase

    /*
     * Always returns the left line in position 0 and the right line in position 1.
     * Rear firing arcs get positions 2 and 3, maintaining the left first rule.
     * Turrets return an empty list.
     */
    List<FiringLine> getFiringArc() {
        List<Coordinate> boxPoints = position.getBoxPoints()

        [
                new FiringLine([
                        originX: position.center.x,
                        originY: position.center.y,
                        lineX: boxPoints.get(0).x,
                        lineY: boxPoints.get(0).y]),
                new FiringLine([
                        originX: position.center.x,
                        originY: position.center.y,
                        lineX: boxPoints.get(1).x,
                        lineY: boxPoints.get(1).y
                ])
        ]
    }
}
