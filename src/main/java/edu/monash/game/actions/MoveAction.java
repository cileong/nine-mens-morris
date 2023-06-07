package edu.monash.game.actions;

import edu.monash.game.Board;
import edu.monash.game.Game;
import edu.monash.game.Move;
import edu.monash.game.Position;
import edu.monash.game.player.Player;

/**
 * An action that moves a piece on the board.
 */
public class MoveAction implements Action {

    /**
     * The move to be performed.
     */
    private final Move move;

    /**
     * Constructs a new move action.
     *
     * @param player The player who is performing the move.
     * @param from   The position from which the piece is being moved.
     * @param to     The position to which the piece is being moved.
     */
    public MoveAction(Player player, Integer from, Integer to) {
        move = new Move(player.getPieceColour(), from, to);
    }

    /**
     * Checks whether the move action is valid.
     *
     * @param game   The game object.
     * @param player The player who is performing the move.
     * @return True if the action is valid, false otherwise.
     */
    @Override
    public boolean isValid(Game game, Player player) {
        if (player.hasFormedMill())
            return false;

        Board board = game.getBoard();
        Position source = board.getPosition(move.from());
        Position destination = board.getPosition(move.to());

        // Check whether the player can perform the move.
        return player.canPerformMove(board, move) &&
                source.canPieceBeMoved(player) &&
                destination.canPieceBePlaced(player);
    }

    /**
     * Executes the move action on the game.
     *
     * @param game The game object.
     */
    @Override
    public void executeOn(Game game) {
        // Execute the move on the board.
        move.executeOn(game.getBoard());
        game.storePlayedMove(move);

        // Test whether the player has formed a mill.
        Board board = game.getBoard();
        Position destination = board.getPosition(move.to());

        // If the player has formed a mill, set the flag to true.
        game.getPlayer().setHasFormedMill(destination.isInMill());

        // Attempt to transition the player to the next PlayerPhase.
        game.getPlayer().attemptTransitionPhase();
        game.getOpponent().attemptTransitionPhase();

        // If the player has formed a mill as a result of this move,
        // do not switch the control to the other player.
        if (!game.getPlayer().hasFormedMill())
            game.switchActivePlayer();
    }

}
