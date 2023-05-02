package edu.monash.game.player;

import edu.monash.game.Piece;

import java.util.function.Function;

public class Player {

    private final Piece pieceColour;

    private PlayerPhase phase;

    public Player(Piece pieceColour, Function<Player, PlayerPhase> initialPhaseConstructor) {
        this.pieceColour = pieceColour;
        this.phase = initialPhaseConstructor.apply(this);
    }

    public Piece getPieceColour() {
        return pieceColour;
    }

    public PlayerPhase getPhase() {
        return phase;
    }

    public void setPhase(Function<Player, PlayerPhase> phaseConstructor) {
        phase = phaseConstructor.apply(this);
    }

}
