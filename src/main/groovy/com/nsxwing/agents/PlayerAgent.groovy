package com.nsxwing.agents

import com.nsxwing.components.meta.PlayerIdentifier
import com.nsxwing.components.meta.dice.AttackDie
import com.nsxwing.components.meta.dice.DiceResult
import com.nsxwing.components.meta.dice.EvadeDie
import com.nsxwing.components.pilots.Pilot
import com.nsxwing.gamestate.combat.FiringLine
import com.nsxwing.gamestate.combat.Target
import com.nsxwing.gamestate.field.Coordinate
import com.nsxwing.gamestate.field.GameField
import com.nsxwing.gamestate.field.Position
import com.nsxwing.movement.Maneuver
import com.nsxwing.movement.ManeuverDifficulty
import com.nsxwing.movement.heuristic.ManeuverStrength
import com.nsxwing.movement.heuristic.RankedManeuver
import groovy.util.logging.Slf4j

/**
 * An agent in charge of a single ship, and maintains its pilot, ship, equipment, etc. as well as
 * its current position and its current xphase (activation (movement/ability), combat whatever).
 */
@Slf4j
public class PlayerAgent {
    PlayerIdentifier owningPlayer
    Pilot pilot
    Position position
    int pointCost
    private Maneuver plannedManeuver

    /*
     * Always returns the left line in position 0 and the right line in position 1.
     * Rear firing arcs get positions 2 and 3, maintaining the left first rule.
     * Turrets return an empty list.
     */
    //TODO: Implement those other arcs.
    List<FiringLine> getFiringArc() {
        List<Coordinate> boxPoints = position.getBoxPoints(pilot.ship.isHuge)

        [new FiringLine([
                originX: position.center.x,
                originY: position.center.y,
                lineX: boxPoints.get(0).x,
                lineY: boxPoints.get(0).y]),
         new FiringLine([
                 originX: position.center.x,
                 originY: position.center.y,
                 lineX: boxPoints.get(1).x,
                 lineY: boxPoints.get(1).y
         ])]
    }

    @Override
    String toString() {
        "${owningPlayer} - ${pilot}:(${position.center.x},${position.center.y}:${position.heading})"
    }

    void planManeuver(GameField gameField, Player champ, Player scrub) {
        RankedManeuver bestManeuver = null
        for (Maneuver maneuver : pilot.ship.maneuvers) {
            if (gameField.isLegalManeuver(this.position, maneuver) && canPerformManeuver(maneuver)) {
                ManeuverStrength strength = gameField.getManeuverStrength(this, maneuver, champ, scrub)
                if (!bestManeuver) {
                    bestManeuver = new RankedManeuver(maneuver: maneuver, strength: strength)
                } else if (isBetterManeuver(strength, bestManeuver)) {
                    bestManeuver = new RankedManeuver(maneuver: maneuver, strength: strength)
                }
            }
        }

        plannedManeuver = bestManeuver?.maneuver
    }

    boolean canPerformManeuver(Maneuver maneuver) {
        !(maneuver.difficulty == ManeuverDifficulty.RED && this.pilot.isStressed());
    }

    ManeuverStrength getPositionStrength(GameField gameField, Player champ, Player scrub) {
        gameField.getPositionStrength(this, champ, scrub)
    }

    private boolean isBetterManeuver(ManeuverStrength strength, RankedManeuver bestManeuver) {
        strength.numTargets > bestManeuver.strength.numTargets ||
                (strength.numTargets == bestManeuver.strength.numTargets && strength.distanceToEnemies < bestManeuver.strength.distanceToEnemies)
    }

    void doManeuver(Player owningPlayer) {
        if (!plannedManeuver) {
            log.info("${this} flew off the board.")
            owningPlayer.agents.remove(this)
        } else {
            log.info("${this} is performing ${plannedManeuver}")
            this.position = plannedManeuver.move(this.position)

            if (plannedManeuver.difficulty == ManeuverDifficulty.RED) {
                this.pilot.numStressTokens++
            } else if (plannedManeuver.difficulty == ManeuverDifficulty.GREEN && this.pilot.isStressed()) {
                this.pilot.numStressTokens--
            }
        }
    }
}
