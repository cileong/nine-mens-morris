package edu.monash.game.actions;

import edu.monash.game.Game;
import edu.monash.game.player.Player;

public class UndoAction implements Action {

    @Override
    public boolean isValid(Game game, Player player) {
        return false;
    }

    @Override
    public void executeOn(Game game) {

    }

}
