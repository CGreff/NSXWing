package com.nsxwing

import com.nsxwing.components.meta.PlayerIdentifier
import com.nsxwing.utilities.GameRecord
import groovy.util.logging.Slf4j

@Slf4j
public class Main {

    public static void main(String[] args) {
        Game game
        List<GameRecord> gameStats = []
        int numGames = 1

        for (int i = 0; i < numGames; i++) {
            game = new Game()
            gameStats.add(game.playGame())
        }

        logStats(gameStats.findAll { it.winner == PlayerIdentifier.CHAMP }, numGames)
        logStats(gameStats.findAll { it.winner == PlayerIdentifier.SCRUB }, numGames)
    }

    private static void logStats(List<GameRecord> records, int numGames) {
        double avgPoints = 0
        double avgTurns = 0
        for (GameRecord record : records) {
            avgPoints += record.pointsRemaining
            avgTurns += record.numberOfTurns
        }
        avgPoints = avgPoints / records.size()
        avgTurns = avgTurns / records.size()
        double winRate = records.size() / numGames
        if (records.size() > 0) {
            log.info("${records.get(0).winner} had a win rate of ${winRate} and won with an average of ${avgPoints} points left in ${avgTurns} turns.")
        }
    }
}
