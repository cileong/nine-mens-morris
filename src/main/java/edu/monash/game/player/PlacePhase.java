package edu.monash.game.player;

import edu.monash.game.Board;
import edu.monash.game.Move;

public class PlacePhase implements PlayerPhase {

    private final Player player;

    public PlacePhase(Player player) {
        this.player = player;
    }

    @Override
    public boolean validate(Board board, Move move) {
        // Only accept moves with no source.
        return move.getFrom() == null && move.getTo() != null;
    }

    @Override
    public void transition() {
        if (player.getPiecesOnHand() <= 0)
            player.setPhase(SlidePhase::new);
    }

}
