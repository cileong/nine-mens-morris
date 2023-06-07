package edu.monash.game.player;

import edu.monash.game.Board;
import edu.monash.game.Move;
import edu.monash.game.Position;

/**
 * A phase in which a player places a piece on the board.
 */
public class PlacePhase implements PlayerPhase {

    /**
     * The player of which this phase belongs to.
     */
    private final Player player;

    /**
     * Constructs a new place phase.
     *
     * @param player The player.
     */
    public PlacePhase(Player player) {
        this.player = player;
    }

    /**
     * Checks whether the move is valid in this phase.
     *
     * @param board The board object.
     * @param move  The move object.
     * @return True if the action is valid, false otherwise.
     */
    @Override
    public boolean validate(Board board, Move move) {
        Position source = board.getPosition(move.from()),
                destination = board.getPosition(move.to());

        return source == null && destination != null;
    }

    /**
     * Transitions to the slide phase.
     */
    @Override
    public void transition() {
        if (player.getPiecesOnHand() <= 0)
            player.setPhase(SlidePhase::new);
    }

}
