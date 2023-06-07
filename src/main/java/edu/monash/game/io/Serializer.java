package edu.monash.game.io;

import edu.monash.game.Game;

/**
 * An interface for serializing game states.
 */
public interface Serializer {

    String getSupportedFileExtension();

    /**
     * Serializes a game state to a file.
     *
     * @param filepath The path to the file.
     */
    void serialize(Game game, String filepath);

}
