package edu.monash.game.actions;

import edu.monash.game.Game;
import edu.monash.game.player.Player;

public interface Action {

    boolean isValid(Game game, Player player);

    void execute(Game game);

}
