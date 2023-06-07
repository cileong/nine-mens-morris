package edu.monash.game.io;

import edu.monash.game.GameState;

/**
 * An interface for deserializing game states.
 */
public interface Deserializer {

    String getSupportedFileExtension();

    /**
     * Deserializes a game state from a file.
     *
     * @param filepath The path to the file.
     * @return The deserialized game state.
     */
    GameState deserialize(String filepath);

}
