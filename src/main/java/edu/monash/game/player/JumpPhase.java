package edu.monash.game.player;

import edu.monash.game.Board;
import edu.monash.game.Move;
import edu.monash.game.Position;
/**
 A phase in which a player can jump a piece to any position on the board.
 */
class JumpPhase implements PlayerPhase {

    private final Player player;

    public JumpPhase(Player player) {
        this.player = player;
    }

    /**
     * Checks whether the move is valid in this phase.
     *
     * @param board The board.
     * @param move  The move.
     * @return True.
     */
    @Override
    public boolean validate(Board board, Move move) {
        return true;
    }

    /**
     * Transitions to the next phase or return to the previous phase.
     */
    @Override
    public void transition() {
        // To terminal state. Player has lost.
        if (player.getPiecesOnBoard() < 3)
            player.setPhase(unused -> null);
        // To slide phase after undoing a move and meet the condition.
        else if (player.getPiecesOnBoard() > 3)
            player.setPhase(SlidePhase::new);
    }

}
