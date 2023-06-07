package edu.monash.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A move in the game.
 */
public record Move(PieceColour pieceColour, Integer from, Integer to) {

    /**
     * Creates a move.
     *
     * @param pieceColour The piece colour indicating the player that performs the move.
     * @param from        The position to move from.
     * @param to          The position to move to.
     */
    @JsonCreator
    public Move(@JsonProperty("pieceColour") PieceColour pieceColour,
                @JsonProperty("from") Integer from,
                @JsonProperty("to") Integer to) {
        this.pieceColour = pieceColour;
        this.from = from;
        this.to = to;
    }

    /**
     * Executes the move on a board.
     *
     * @param board The board to execute the move on.
     */
    public void executeOn(Board board) {
        // Get the positions.
        Position fromPosition = (from != null) ? board.getPosition(from) : null;
        Position toPosition = (to != null) ? board.getPosition(to) : null;

        // If the from position is not null, remove the piece from it.
        if (fromPosition != null)
            fromPosition.setPiece(null);

        // If the to position is not null, set the piece on it.
        if (toPosition != null)
            toPosition.setPiece(pieceColour);
    }

}
