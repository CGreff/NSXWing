package com.nsxwing.components.meta.dice

/**
 * Implementation of the Dice interface for Attacking.
 */
class AttackDie implements Die {

    boolean hasBeenRolled = false
    boolean hasBeenRerolled = false
    DiceResult result = DiceResult.NOTHING

    @Override
    void roll() {
        if (hasBeenRerolled) {
            return
        } else if (hasBeenRolled) {
            hasBeenRerolled = true
            rollIt()
        } else {
            hasBeenRolled = true
            rollIt()
        }
    }

    private void rollIt() {
        Random r = new Random()
        double val = r.nextDouble() * 0.8

        if (val < 0.3) {
            result = DiceResult.SUCCESS
        } else if (val < 0.4) {
            result = DiceResult.CRITICAL_HIT
        } else if (val < 0.6) {
            result = DiceResult.FOCUS
        } else {
            result = DiceResult.NOTHING
        }
    }
}
