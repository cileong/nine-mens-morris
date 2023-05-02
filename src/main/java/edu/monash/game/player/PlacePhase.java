package edu.monash.game.player;

import edu.monash.game.Board;
import edu.monash.game.Move;

public class PlacePhase implements PlayerPhase {

    private final Player player;

    public PlacePhase(Player player) {
        this.player = player;
    }

    @Override
    public boolean validate(Board board, Move move) {
        return false;
    }

}
