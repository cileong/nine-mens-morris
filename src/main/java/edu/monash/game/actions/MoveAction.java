package edu.monash.game.actions;

import edu.monash.game.Board;
import edu.monash.game.Game;
import edu.monash.game.Move;
import edu.monash.game.player.Player;

public class MoveAction implements Action {

    private Move move;

    public MoveAction(int from, int to) {
    }

    @Override
    public boolean isValid(Game game, Player player) {
        Board board = game.getBoard();
        return false;
    }

    @Override
    public void execute(Game game) {
        // TODO: Perform move.
        Board board = game.getBoard();

        game.storePlayedMove(move);
    }

}
