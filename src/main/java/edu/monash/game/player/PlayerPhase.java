package edu.monash.game.player;

import edu.monash.game.Board;
import edu.monash.game.Move;

public interface PlayerPhase {

    boolean validate(Board board, Move move);

    void transition();

}
