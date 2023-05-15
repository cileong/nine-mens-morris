package edu.monash.game;

public class Move {

    private final PieceColour pieceColour;
    private final Integer from, to;

    public Move(PieceColour pieceColour, Integer from, Integer to) {
        this.pieceColour = pieceColour;
        this.from = from;
        this.to = to;
    }

    public Integer getFrom() {
        return from;
    }

    public Integer getTo() {
        return to;
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
