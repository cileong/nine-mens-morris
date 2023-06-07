package edu.monash.game.io;

/**
 * An interface for serializing game states.
 */
public interface Serializer {

    /**
     * Serializes a game state to a file.
     *
     * @param filepath The path to the file.
     */
    void serialize(String filepath);

}
