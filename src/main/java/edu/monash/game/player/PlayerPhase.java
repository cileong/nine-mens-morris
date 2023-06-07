package edu.monash.game.player;

import edu.monash.game.Board;
import edu.monash.game.Move;

/**
 * An interface for player phases.
 */
public interface PlayerPhase {

    /**
     * Validates a phase.
     *
     * @param board The board.
     * @param move  The move.
     * @return True if the move is valid, false otherwise.
     */
    boolean validate(Board board, Move move);

    /**
     * Transitions to the next phase.
     */
    void transition();

}
