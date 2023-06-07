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

    public UndoAction(Game game) {
        // Pop the last move from the stack.
        Move lastMove = game.getPlayedMove().pop();
        //switch the active player back to the previous player.
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

        // Check whether a mill has formed before undoing.
        if (game.getOpponent().hasFormedMill()) {
            // If a mill has formed, switch the opponent back as the current player.
            game.switchActivePlayer();
            // Set the current's player mill status to false
            // after undoing the move that formed a mill.
            game.getPlayer().setHasFormedMill(false);
        }

        // Check whether the previous move is a remove action.
        if (move.from() ==  null) {
            // If the previous move is a remove action,
            // increment the opponent's pieces on board.
            game.getOpponent().incrementPiecesOnBoard();
            // Set the current player's mill status to true
            // In order to let the current player remove a piece again after undoing.
            game.getPlayer().setHasFormedMill(true);
            // Set the previous removed piece back to the board.
            game.getBoard().getPosition(move.to()).setPiece(game.getOpponent().getPieceColour());
        } else if (move.to() ==  null) {
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
