package com.nsxwing.gamestate.combat

import com.nsxwing.agents.PlayerAgent
import org.junit.Before
import org.junit.Test

/**
 *
 */
class TargetTest {
    private Target target

    @Before
    void setUp() {
        target = new Target(targetAgent: new PlayerAgent())
    }

    @Test
    void 'should remove the Target from a set even after modifying it (equals test)'() {
        Set<Target> targets = [target]
        assert targets.contains(target)

        target.range = 4
        target.obstructed = false
        assert targets.contains(target)

        targets.remove(target)
        assert !targets.contains(target)
    }
}
