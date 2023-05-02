package edu.monash.game.actions;

import edu.monash.game.Game;
import edu.monash.game.player.Player;

public class LoadAction implements Action {

    @Override
    public boolean isValid(Game game, Player player) {
        return true;
    }

    @Override
    public void executeOn(Game game) {

    }

}
