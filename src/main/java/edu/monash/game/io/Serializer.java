package edu.monash.game.io;

import edu.monash.game.Game;

/**
 * An interface for serializing game states.
 */
public interface Serializer {

    /**
     * Gets the file extension that this serializer supports.
     *
     * @return The file extension.
     */
    String getSupportedFileExtension();

    /**
     * Serializes a game state to a file (to be stored at filepath).
     *
     * @param game The game to be serialized.
     * @param filepath The path to the file to be saved.
     */
    void serialize(Game game, String filepath);

}
