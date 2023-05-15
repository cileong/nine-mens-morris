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
        if (player.hasFormedMill())
            return false;

        Board board = game.getBoard();
        Position source = board.getPosition(move.getFrom());
        Position destination = board.getPosition(move.getTo());

        return player.canPerformMove(board, move) &&
                source.canPieceBeMoved(player) &&
                destination.canPieceBePlaced(player);
    }

    @Override
    public void executeOn(Game game) {
        move.executeOn(game.getBoard());
        game.storePlayedMove(move);

        // Test whether the player has formed a mill.
        Board board = game.getBoard();
        Position destination = board.getPosition(move.getTo());
        if (destination.isInVerticalMill() || destination.isInHorizontalMill())
            game.getPlayer().setHasFormedMill(true);

        game.getPlayer().attemptTransitionPhase();
        game.getOpponent().attemptTransitionPhase();

        // If the player has formed a mill as a result of this move,
        // do not switch the control to the other player.
        if (!game.getPlayer().hasFormedMill())
            game.switchActivePlayer();
    }

}
