package com.nsxwing.gamestate

import com.nsxwing.agents.Player
import com.nsxwing.agents.PlayerAgent
import com.nsxwing.components.meta.PlayerIdentifier
import com.nsxwing.gamestate.combat.Target
import com.nsxwing.gamestate.field.GameField
import com.nsxwing.gamestate.field.Position
import com.nsxwing.movement.Maneuver
import com.nsxwing.phase.ActivationPhase
import com.nsxwing.phase.CombatPhase
import com.nsxwing.phase.PlanningPhase
import groovy.util.logging.Slf4j

@Slf4j
class GameController {

    static PlayerIdentifier PLAYER_WITH_INITIATIVE
    static final Closure ACTIVATION_COMPARATOR = {
        double initiativeModifier = it.owningPlayer == PLAYER_WITH_INITIATIVE ? 0.1 : 0
        it.pilot.pilotSkill - initiativeModifier
    }

    static final Closure COMBAT_COMPARATOR = {
        double initiativeModifier = it.owningPlayer == PLAYER_WITH_INITIATIVE ? 0.1 : 0
        (it.pilot.pilotSkill - initiativeModifier) * -1.0
    }

    final Player champ
    final Player scrub
    final GameField gameField
    private PlanningPhase planningPhase
    private ActivationPhase activationPhase
    private CombatPhase combatPhase

    GameController(Player champ, Player scrub) {
        this.champ = champ
        this.scrub = scrub
        PLAYER_WITH_INITIATIVE = determineInitiative()
        gameField = new GameField()
        planningPhase = new PlanningPhase(champ, scrub, gameField)
        activationPhase = new ActivationPhase(champ, scrub, gameField)
        combatPhase = new CombatPhase(champ, scrub, gameField)
    }

    Player doTurn() {
        log.info("Planning turn")
        Map<PlayerAgent, Maneuver> chosenManeuvers = planningPhase.doPhase(getCombinedAgentList { it })
        log.info("Begin Activation Phase")
        activationPhase.doPhase(getCombinedAgentList(ACTIVATION_COMPARATOR), chosenManeuvers)
        log.info("Begin Combat Phase")
        combatPhase.doPhase(getCombinedAgentList(COMBAT_COMPARATOR))
        checkForWinner()
    }

    List<PlayerAgent> getCombinedAgentList(Closure comparator) {
        List<PlayerAgent> combinedList = new ArrayList(champ.agents)
        combinedList.addAll(scrub.agents)
        combinedList.sort(comparator)
    }

    private PlayerIdentifier determineInitiative() {
        (champ.costOfList <= scrub.costOfList) ? PlayerIdentifier.CHAMP : PlayerIdentifier.SCRUB
    }

    private Player checkForWinner() {
        if (!scrub.agents) {
            return champ
        }

        if (!champ.agents) {
            return scrub
        }

        null
    }
}
