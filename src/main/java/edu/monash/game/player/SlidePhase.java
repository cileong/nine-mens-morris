package edu.monash.game.player;

import edu.monash.game.Board;
import edu.monash.game.Move;
import edu.monash.game.Position;
import edu.monash.game.player.Player;
import edu.monash.game.player.PlayerPhase;

public class SlidePhase implements PlayerPhase {

    private final Player player;

    public SlidePhase(Player player) {
        this.player = player;
    }

    @Override
    public boolean validate(Board board, Move move) {
        Position source = board.getPosition(move.getFrom()),
                destination = board.getPosition(move.getTo());

        return source != null && source.isNeighbourTo(destination);
    }

    @Override
    public void transition() {
        if (player.getPiecesOnBoard() == 3)
            player.setPhase(JumpPhase::new);
    }

}
