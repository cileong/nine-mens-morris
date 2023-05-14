package edu.monash.game.player;

import edu.monash.game.Board;
import edu.monash.game.Move;

public class JumpPhase implements PlayerPhase {

    private final Player player;

    public JumpPhase(Player player) {
        this.player = player;
    }

    public boolean validate(Board board, Move move) {
        return false;
    }

    @Override
    public void transition() {
        if (player.getPiecesOnBoard() < 3)
            player.setPhase(unused -> null);
    }

}
