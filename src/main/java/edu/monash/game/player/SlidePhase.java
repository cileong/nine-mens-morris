package edu.monash.game.player;

import edu.monash.game.Board;
import edu.monash.game.Move;
import edu.monash.game.Position;

public class SlidePhase implements PlayerPhase {

    private final Player player;

    public SlidePhase(Player player) {
        this.player = player;
    }

    @Override
    public boolean validate(Board board, Move move) {
        Position source = board.getPosition(move.from()),
                destination = board.getPosition(move.to());

        return source != null && source.isNeighbourTo(destination);
    }

    @Override
    public void transition() {
        if (player.getPiecesOnBoard() == 3)
            player.setPhase(JumpPhase::new);
        else if (player.getPiecesOnHand() != 0)
            player.setPhase(PlacePhase::new);
    }

}
