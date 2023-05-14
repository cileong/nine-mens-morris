package edu.monash.game;

public class Move {

    private final Piece piece;
    private final Integer from, to;

    public Move(Piece piece, Integer from, Integer to) {
        this.piece = piece;
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
            toPosition.setPiece(piece);
    }

}
