package edu.monash.game.player;

import edu.monash.game.Board;
import edu.monash.game.Move;
import edu.monash.game.Position;

public class PlacePhase implements PlayerPhase {

    private final Player player;

    public PlacePhase(Player player) {
        this.player = player;
    }

    @Override
    public boolean validate(Board board, Move move) {
        Position source = board.getPosition(move.getFrom()),
                destination = board.getPosition(move.getTo());

        return source == null && destination != null;
    }

    @Override
    public void transition() {
        if (player.getPiecesOnHand() <= 0)
            player.setPhase(SlidePhase::new);
    }

}
