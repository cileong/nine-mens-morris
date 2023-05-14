package edu.monash.game.player;

import edu.monash.game.Piece;

import java.util.function.Function;

public class Player {

    private final Piece pieceColour;

    private int piecesOnHand, piecesOnBoard;

    private PlayerPhase phase;

    public Player(Piece pieceColour, Function<Player, PlayerPhase> initialPhaseConstructor) {
        this.pieceColour = pieceColour;
        this.piecesOnHand = 9;
        this.piecesOnBoard = 0;
        this.phase = initialPhaseConstructor.apply(this);
    }

    public Piece getPieceColour() {
        return pieceColour;
    }

    public int getPiecesOnHand() {
        return piecesOnHand;
    }

    public int getPiecesOnBoard() {
        return piecesOnBoard;
    }

    public void incrementPiecesOnHand() {
        piecesOnHand++;
    }

    public void decrementPiecesOnHand() {
        piecesOnHand--;
    }

    public void incrementPiecesOnBoard() {
        piecesOnBoard++;
    }

    public void decrementPiecesOnBoard() {
        piecesOnBoard--;
    }

    public boolean hasLost() {
        return phase == null;
    }

    public PlayerPhase getPhase() {
        return phase;
    }

    public void setPhase(Function<Player, PlayerPhase> phaseConstructor) {
        phase = phaseConstructor.apply(this);
    }

}
