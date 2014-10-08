package com.nsxwing.components.meta

/**
 * Implementation of the Dice interface for Evading.
 */
class EvadeDice implements Dice {

    @Override
    DiceResult roll(int numDice) {
        DiceResult result = new DiceResult()
        for (int i = 0; i < numDice; i++) {
            Random r = new Random()
            double val = r.nextDouble() * 0.8

            if (val < 0.3) {
                result.numSuccesses++
            } else if (val < 0.5) {
                result.numFocus++
            }
        }
        result
    }
}
