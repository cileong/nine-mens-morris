package edu.monash.game.io;

import edu.monash.game.GameState;

/**
 * An interface for deserializing game states from file.
 */
public interface Deserializer {

    /**
     * Gets the file extension that this deserializer supports.
     * @return The file extension.
     */
    String getSupportedFileExtension();

    /**
     * Deserializes a game state from a file.
     *
     * @param filepath The path to the file.
     * @return The deserialized game state.
     */
    GameState deserialize(String filepath);

}
