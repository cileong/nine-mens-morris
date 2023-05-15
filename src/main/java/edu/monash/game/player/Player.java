package edu.monash.game.player;

import edu.monash.game.Board;
import edu.monash.game.Move;
import edu.monash.game.PieceColour;

import java.util.function.Function;

public class Player {

    private final PieceColour pieceColour;

    private int piecesOnHand, piecesOnBoard;

    private boolean hasFormedMill;

    private PlayerPhase phase;

    public Player(PieceColour pieceColour, Function<Player, PlayerPhase> initialPhaseConstructor) {
        this.pieceColour = pieceColour;
        this.piecesOnHand = 9;
        this.piecesOnBoard = 0;
        this.hasFormedMill = false;
        this.phase = initialPhaseConstructor.apply(this);
    }

    public PieceColour getPieceColour() {
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

    public boolean hasFormedMill() {
        return hasFormedMill;
    }

    public void setHasFormedMill(boolean hasFormedMill) {
        this.hasFormedMill = hasFormedMill;
    }

    public boolean hasLost() {
        return phase == null;
    }

    public PlayerPhase getPhase() {
        return phase;
    }

    public void attemptTransitionPhase() {
        phase.transition();
    }

    public void setPhase(Function<Player, PlayerPhase> phaseConstructor) {
        phase = phaseConstructor.apply(this);
    }

    public boolean canPerformMove(Board board, Move move) {
        return phase.validate(board, move);
    }

}
