package edu.monash.game.actions;

import edu.monash.game.Game;
import edu.monash.game.player.Player;

public interface Action {

    /**
     * Checks whether the action is valid.
     *
     * @param game   The game object.
     * @param player The player who is performing the action.
     * @return True if the action is valid, false otherwise.
     */
    boolean isValid(Game game, Player player);

    /**
     * Executes the action on the game.
     *
     * @param game The game object.
     */
    void executeOn(Game game);

}
