package edu.monash.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record Move(PieceColour pieceColour, Integer from, Integer to) {

    @JsonCreator
    public Move(@JsonProperty("pieceColour") PieceColour pieceColour,
                @JsonProperty("from") Integer from,
                @JsonProperty("to") Integer to) {
        this.pieceColour = pieceColour;
        this.from = from;
        this.to = to;
    }

    public void executeOn(Board board) {
        Position fromPosition = (from != null) ? board.getPosition(from) : null;
        Position toPosition = (to != null) ? board.getPosition(to) : null;

        if (fromPosition != null)
            fromPosition.setPiece(null);

        if (toPosition != null)
            toPosition.setPiece(pieceColour);
    }

}
