package edu.monash.game.player;

import edu.monash.game.Board;
import edu.monash.game.Move;
import edu.monash.game.player.Player;
import edu.monash.game.player.PlayerPhase;

public class SlidePhase implements PlayerPhase {

    private final Player player;

    public SlidePhase(Player player) {
        this.player = player;
    }

    public boolean validate(Board board, Move move) {
        return false;
    }

}
