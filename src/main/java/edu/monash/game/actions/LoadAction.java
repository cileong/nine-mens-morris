package edu.monash.game.actions;

import edu.monash.game.Game;
import edu.monash.game.player.Player;

/**
 * An action that loads a game state.
 */
public class LoadAction implements Action {

    /**
     * Checks whether the load action is valid.
     *
     * @param game   The game object.
     * @param player The player who is performing the load action.
     * @return True if the action is valid, false otherwise.
     */
    @Override
    public boolean isValid(Game game, Player player) {
        return true;
    }

    /**
     * Executes the load action on the game.
     *
     * @param game The game object.
     */
    @Override
    public void executeOn(Game game) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

}
