package edu.monash.game.player;

import edu.monash.game.player.Player;
import edu.monash.game.player.PlayerPhase;

public class SlidePhase implements PlayerPhase {

    private final Player player;

    public SlidePhase(Player player) {
        this.player = player;
    }

}
