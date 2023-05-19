package edu.monash.game.actions;

import edu.monash.game.Board;
import edu.monash.game.Game;
import edu.monash.game.Move;
import edu.monash.game.Position;
import edu.monash.game.player.Player;

public class RemoveAction implements Action {

    private final Move move;

    public RemoveAction(Player player, Integer from) {
        move = new Move(player.getPieceColour(), from, null);
    }

    @Override
    public boolean isValid(Game game, Player player) {
        if (!player.hasFormedMill())
            return false;

        Board board = game.getBoard();
        Position source = board.getPosition(move.getFrom());
        return source.canPieceBeRemoved(player);
    }

    @Override
    public void executeOn(Game game) {
        move.executeOn(game.getBoard());
        game.storePlayedMove(move);

        game.getPlayer().decrementPiecesOnBoard();
        game.getPlayer().attemptTransitionPhase();
        game.getOpponent().attemptTransitionPhase();

        game.getPlayer().setHasFormedMill(false);

        game.switchActivePlayer();
    }

}
