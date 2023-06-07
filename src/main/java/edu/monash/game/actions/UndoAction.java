package edu.monash.game.actions;

import edu.monash.game.Game;
import edu.monash.game.Move;
import edu.monash.game.player.Player;

/**
 * An action that undoes the last move.
 */
public class UndoAction implements Action {

    /**
     * The move that is being undone.
     */
    private final Move move;

    /**
     * The position from which the piece is being moved.
     */
    private Integer from;

    /**
     * The position to which the piece is being moved.
     */
    private Integer to;

    /**
     * Constructs a new undo action.
     * @param game The game where the undo is being performed.
     */
    public UndoAction(Game game) {
        // Pop the previous move from the stack.
        Move lastMove = game.getPlayedMove().pop();

        // Switch the active player back to the previous player.
        game.switchActivePlayer();

        // Set the from and to positions oppositely.
        this.from = lastMove.to();
        this.to = lastMove.from();
        this.move = new Move(game.getPlayer().getPieceColour(), this.from, this.to);
    }

    /**
     * Checks whether the undo action is valid.
     *
     * @param game   The game object.
     * @param player The player who is performing the undo action.
     * @return True
     */
    @Override
    public boolean isValid(Game game, Player player) {
        return true;
    }

    /**
     * Executes the undo action on the game.
     *
     * @param game The game object.
     */
    @Override
    public void executeOn(Game game) {
        move.executeOn(game.getBoard());

        // If a mill has formed, then the current player should be the opponent.
        // If they have set a mill, it is now gone.
        if (game.getOpponent().hasFormedMill()) {
            game.switchActivePlayer();
            game.getPlayer().setHasFormedMill(false);
        }

        // If a piece is being moved back to the board...
        if (move.from() == null) {
            game.getOpponent().incrementPiecesOnBoard();

            // Allow the player to remove a piece from the board again after undoing.
            game.getPlayer().setHasFormedMill(true);

            game.getBoard().getPosition(move.to()).setPiece(game.getOpponent().getPieceColour());
        }

        // If a piece is being moved back to the hand...
        if (move.to() == null) {
            game.getPlayer().decrementPiecesOnBoard();
            game.getPlayer().incrementPiecesOnHand();
        }

        // Attempt to transition the phase of the players.
        game.getPlayer().attemptTransitionPhase();
        game.getOpponent().attemptTransitionPhase();
    }

    /**
     * Gets the position from which the piece is being moved.
     *
     * @return The position from of the move that is being undone.
     */
    public Integer getFrom() {
        return from;
    }

    /**
     * Gets the position to which the piece is being moved.
     *
     * @return The position to of the move that is being undone.
     */
    public Integer getTo() {
        return to;
    }

}
