package edu.monash.game.player;

import edu.monash.game.Piece;

public class Player {

    private final Piece pieceColour;

    private PlayerPhase phase;

    public Player(Piece pieceColour, PlayerPhase initialPhase) {
        this.pieceColour = pieceColour;
        this.phase = initialPhase;
    }

    public Piece getPieceColour() {
        return pieceColour;
    }

    public PlayerPhase getPhase() {
        return phase;
    }

    public void setPhase(PlayerPhase phase) {
        this.phase = phase;
    }

}
