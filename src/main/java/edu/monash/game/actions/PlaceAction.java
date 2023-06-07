package edu.monash.game.actions;

import edu.monash.game.Board;
import edu.monash.game.Game;
import edu.monash.game.Move;
import edu.monash.game.Position;
import edu.monash.game.player.Player;

/**
 * An action that places a piece on the board.
 */
public class PlaceAction implements Action {
    /**
     * The move to be executed.
     */
    private final Move move;

    /**
     * Constructs a new place action.
     *
     * @param player The player who is performing the move.
     * @param to     The position to which the piece is being moved.
     */
    public PlaceAction(Player player, Integer to) {
        move = new Move(player.getPieceColour(), null, to);
    }

    /**
     * Checks whether the place action is valid.
     *
     * @param game   The game object.
     * @param player The player who is performing the move.
     * @return True if the action is valid, false otherwise.
     */
    @Override
    public boolean isValid(Game game, Player player) {
        if (player.hasFormedMill()) {
            return false;
        }

        Board board = game.getBoard();
        Position destination = board.getPosition(move.to());

        // Check whether the player can place a piece on the board.
        return destination.canPieceBePlaced(player);
    }

    /**
     * Executes the place action on the game.
     *
     * @param game The game object.
     */
    @Override
    public void executeOn(Game game) {
        move.executeOn(game.getBoard());
        game.storePlayedMove(move);

        // Test whether the player has formed a mill.
        Board board = game.getBoard();
        Position destination = board.getPosition(move.to());

        // If the player has formed a mill, set the flag to true.
        if (destination.isInVerticalMill() || destination.isInHorizontalMill()) {
            game.getPlayer().setHasFormedMill(true);
        }

        // Update the number of pieces on hand and on board.
        game.getPlayer().decrementPiecesOnHand();
        game.getPlayer().incrementPiecesOnBoard();

        // Attempt to transition the player to the next PlayerPhase.
        game.getPlayer().attemptTransitionPhase();
        game.getOpponent().attemptTransitionPhase();

        // If the player has formed a mill as a result of this move,
        // do not switch the control to the other player.
        if (!game.getPlayer().hasFormedMill())
            game.switchActivePlayer();
    }

}
