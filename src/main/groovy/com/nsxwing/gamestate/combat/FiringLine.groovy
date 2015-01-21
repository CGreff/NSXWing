package com.nsxwing.gamestate.combat

import com.nsxwing.gamestate.field.Coordinate
import groovy.transform.Immutable

/**
 *
 */
@Immutable
class FiringLine {
    double originX
    double originY
    double lineX
    double lineY


    /*
     * Gathered with: position = sign( (Bx-Ax)*(Y-Ay) - (By-Ay)*(X-Ax) )
     * Sign is positive = left of line; negative = right of line.
     */
    boolean isLeftOfLine(Coordinate coordinate) {
        getDeterminant(coordinate) >= 0
    }

    boolean isRightOfLine(Coordinate coordinate) {
         getDeterminant(coordinate) <= 0
    }

    private double getDeterminant(Coordinate coordinate) {
        ((lineX - originX) * (coordinate.y - originY) - (lineY - originY) * (coordinate.x - originX))
    }
}
