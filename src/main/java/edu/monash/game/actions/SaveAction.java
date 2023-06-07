package edu.monash.game.actions;

import edu.monash.game.*;
import edu.monash.game.player.Player;

/**
 * An action that saves the current game state.
 */
public class SaveAction implements Action {

    /**
     * Checks whether the save action is valid.
     *
     * @param game   The game object.
     * @param player The player who is performing the save action.
     * @return True if the action is valid, false otherwise.
     */
    @Override
    public boolean isValid(Game game, Player player) {
        return true;
    }

    /**
     * Executes the save action on the game.
     *
     * @param game The game object.
     */
    @Override
    public void executeOn(Game game) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

}
