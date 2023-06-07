package edu.monash.game.actions;

import edu.monash.game.Game;
import edu.monash.game.player.Player;

/**
 * An interface representing an action that can be performed on the game.
 * This is the only correct way to interact with the game model.
 */
public interface Action {

    /**
     * Tests whether the action is valid.
     *
     * @param game   The game model.
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
