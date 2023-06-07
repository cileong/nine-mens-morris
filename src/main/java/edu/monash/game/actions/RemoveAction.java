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
        // Check whether the player has formed a mill.
        if (!player.hasFormedMill())
            // The player has not formed a mill, so they cannot remove a piece.
            return false;

        // Check whether the player can perform the move.
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
        // Execute the move on the board.
        move.executeOn(game.getBoard());
        // Store the move in the game.
        game.storePlayedMove(move);

        // Decrement the number of pieces of the opponent on the board.
        game.getOpponent().decrementPiecesOnBoard();

        // Attempt to transition the player to the next PlayerPhase.
        game.getPlayer().attemptTransitionPhase();
        game.getOpponent().attemptTransitionPhase();

        // Reset the player's mill status.
        game.getPlayer().setHasFormedMill(false);

        // Switch the active player.
        game.switchActivePlayer();

    }

}
