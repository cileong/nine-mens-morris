package edu.monash.game.actions;

import edu.monash.game.Board;
import edu.monash.game.Game;
import edu.monash.game.Move;
import edu.monash.game.Position;
import edu.monash.game.player.Player;

/**
 * An action that removes a piece from the board.
 */
public class RemoveAction implements Action {

    /**
     * The move to be performed.
     */
    private final Move move;

    /**
     * Constructs a new remove action.
     *
     * @param player The player who is performing the move.
     * @param from   The position from which the piece is being moved.
     */
    public RemoveAction(Player player, Integer from) {
        move = new Move(player.getPieceColour(), from, null);
    }

    /**
     * Checks whether the remove action is valid.
     *
     * @param game   The game object.
     * @param player The player who is performing the move.
     * @return True if the action is valid, false otherwise.
     */
    @Override
    public boolean isValid(Game game, Player player) {
        // If the player has not formed a mill,
        // they cannot remove their opponent's piece.
        if (!player.hasFormedMill())
            return false;

        Board board = game.getBoard();
        Position source = board.getPosition(move.from());
        return source.canPieceBeRemoved(player);
    }

    /**
     * Executes the remove action on the game.
     *
     * @param game The game object.
     */
    @Override
    public void executeOn(Game game) {
        // Execute the move on the board and track it.
        move.executeOn(game.getBoard());
        game.storePlayedMove(move);

        // Decrement the number of pieces of the opponent on the board.
        game.getOpponent().decrementPiecesOnBoard();

        // Attempt to transition the player to the next PlayerPhase.
        game.getPlayer().attemptTransitionPhase();
        game.getOpponent().attemptTransitionPhase();

        // Reset the player's mill status as they cannot form a
        // mill while removing their opponent's pieces.
        game.getPlayer().setHasFormedMill(false);

        // Pass the control to the opponent.
        game.switchActivePlayer();
    }

}
