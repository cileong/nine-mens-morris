package edu.monash.game.player;

import edu.monash.game.Board;
import edu.monash.game.Move;
import edu.monash.game.PieceColour;

import java.util.function.Function;

/**
 * A player in the game.
 */
public class Player {

    /**
     * The colour of the player's pieces.
     */
    private final PieceColour pieceColour;

    /**
     * The number of pieces the player has on hand and on board.
     */
    private int piecesOnHand, piecesOnBoard;

    /**
     * Whether the player has formed a mill.
     */
    private boolean hasFormedMill;

    /**
     * The current phase of the player.
     */
    private PlayerPhase phase;

    public Player(PieceColour pieceColour, Function<Player, PlayerPhase> initialPhaseConstructor) {
        this.pieceColour = pieceColour;
        this.piecesOnHand = 9;
        this.piecesOnBoard = 0;
        this.hasFormedMill = false;
        this.phase = initialPhaseConstructor.apply(this);
    }

    /**
     * Gets the colour of the player's pieces.
     *
     * @return The colour of the player's pieces.
     */
    public PieceColour getPieceColour() {
        return pieceColour;
    }

    /**
     * Gets the number of pieces the player has on hand.
     *
     * @return The number of pieces the player has on hand.
     */
    public int getPiecesOnHand() {
        return piecesOnHand;
    }

    /**
     * Gets the number of pieces the player has on the board.
     *
     * @return The number of pieces the player has on the board.
     */
    public int getPiecesOnBoard() {
        return piecesOnBoard;
    }

    /**
     * Increment the total number of pieces the player has.
     */
    public void incrementPiecesOnHand() {
        piecesOnHand++;
    }

    /**
     * Decrement the total number of pieces the player has.
     */
    public void decrementPiecesOnHand() {
        piecesOnHand--;
    }

    /**
     * Increment the number of pieces the player has on the board.
     */
    public void incrementPiecesOnBoard() {
        piecesOnBoard++;
    }

    /**
     * Decrement the number of pieces the player has on the board.
     */
    public void decrementPiecesOnBoard() {
        piecesOnBoard--;
    }

    /**
     * Checks whether the player has formed a mill.
     *
     * @return True if the player has formed a mill, false otherwise.
     */
    public boolean hasFormedMill() {
        return hasFormedMill;
    }

    /**
     * Sets whether the player has formed a mill.
     *
     * @param hasFormedMill Whether the player has formed a mill.
     */
    public void setHasFormedMill(boolean hasFormedMill) {
        this.hasFormedMill = hasFormedMill;
    }

    /**
     * Checks whether the player has lost.
     *
     * @return True if the player has lost, false otherwise.
     */
    public boolean hasLost() {
        return phase == null;
    }

    /**
     * Gets the current phase of the player.
     *
     * @return The current phase of the player.
     */
    public PlayerPhase getPhase() {
        return phase;
    }

    /**
     * Attempts to transition the player to the next phase.
     */
    public void attemptTransitionPhase() {
        phase.transition();
    }

    /**
     * Sets the phase of the player.
     *
     * @param phaseConstructor The constructor of the phase.
     */
    public void setPhase(Function<Player, PlayerPhase> phaseConstructor) {
        phase = phaseConstructor.apply(this);
    }

    /**
     * Checks whether the player can perform a move.
     *
     * @param board The board.
     * @param move  The move.
     * @return True if the player can perform the move, false otherwise.
     */
    public boolean canPerformMove(Board board, Move move) {
        return phase.validate(board, move);
    }

}
