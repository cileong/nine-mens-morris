package edu.monash.game.actions;

import edu.monash.game.*;
import edu.monash.game.player.Player;

public class SaveAction implements Action {

    @Override
    public boolean isValid(Game game, Player player) {
        return true;
    }

    @Override
    public void executeOn(Game game) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

}
