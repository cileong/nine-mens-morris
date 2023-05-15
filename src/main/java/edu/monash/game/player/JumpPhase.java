package edu.monash.game.player;

import edu.monash.game.Board;
import edu.monash.game.Move;
import edu.monash.game.Position;

class JumpPhase implements PlayerPhase {

    private final Player player;

    public JumpPhase(Player player) {
        this.player = player;
    }

    @Override
    public boolean validate(Board board, Move move) {
        return true;
    }

    @Override
    public void transition() {
        // To terminal state. Player has lost.
        if (player.getPiecesOnBoard() < 3)
            player.setPhase(unused -> null);
    }

}
