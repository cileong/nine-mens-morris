package edu.monash.game.player;

import edu.monash.game.Board;
import edu.monash.game.Move;
import edu.monash.game.Position;

/**
 * A phase in which a player slides a piece.
 */
public class SlidePhase implements PlayerPhase {

    /**
     * The player of which this phase belongs to.
     */
    private final Player player;

    /**
     * Constructs a new slide phase.
     * @param player The player.
     */
    public SlidePhase(Player player) {
        this.player = player;
    }

    /**
     * Checks whether the move is valid in this phase.
     *
     * @param board The board.
     * @param move  The move.
     * @return True if the move is valid, false otherwise.
     */
    @Override
    public boolean validate(Board board, Move move) {
        Position source = board.getPosition(move.from()),
                destination = board.getPosition(move.to());
        return source != null && source.isNeighbourTo(destination);
    }

    /**
     * Transitions to the next phase or return to the previous phase.
     */
    @Override
    public void transition() {
        if (player.getPiecesOnBoard() == 3)
            player.setPhase(JumpPhase::new);
        else if (player.getPiecesOnHand() != 0)
            player.setPhase(PlacePhase::new);
    }

}
