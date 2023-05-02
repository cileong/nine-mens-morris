package edu.monash.game.actions;

import edu.monash.game.Board;
import edu.monash.game.Game;
import edu.monash.game.Move;
import edu.monash.game.Position;
import edu.monash.game.player.Player;

public class MoveAction implements Action {

    private final Move move;

    public MoveAction(Player player, Integer from, Integer to) {
        move = new Move(player.getPieceColour(), from, to);
    }

    @Override
    public boolean isValid(Game game, Player player) {
        Board board = game.getBoard();
        Position fromPosition = board.getPosition(move.getFrom());
        Position toPosition = board.getPosition(move.getTo());
        return fromPosition.canPieceBeMoved(player) && toPosition.canPieceBePlaced();
    }

    @Override
    public void executeOn(Game game) {
        move.executeOn(game.getBoard());
        game.storePlayedMove(move);
    }

}
