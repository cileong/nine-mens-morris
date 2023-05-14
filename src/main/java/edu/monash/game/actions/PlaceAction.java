package edu.monash.game.actions;

import edu.monash.game.Board;
import edu.monash.game.Game;
import edu.monash.game.Move;
import edu.monash.game.Position;
import edu.monash.game.player.Player;

public class PlaceAction implements Action {

    private final Move move;

    public PlaceAction(Player player, Integer to) {
        move = new Move(player.getPieceColour(), null, to);
    }

    @Override
    public boolean isValid(Game game, Player player) {
        Board board = game.getBoard();
        Position destination = board.getPosition(move.getTo());
        return destination.canPieceBePlaced(player);
    }

    @Override
    public void executeOn(Game game) {
        move.executeOn(game.getBoard());
        game.storePlayedMove(move);
    }

}
