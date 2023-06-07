package edu.monash.game.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.monash.game.GameState;

import java.io.File;
import java.io.IOException;

/**
 * A deserializer for JSON files.
 */
public class JsonDeserializer implements Deserializer {

    /**
     * Gets the file extension that this deserializer supports.
     * @return The file extension.
     */
    @Override
    public String getSupportedFileExtension() {
        return "json";
    }

    /**
     * Deserializes a game state from a JSON file.
     *
     * @param filepath The path to the file.
     * @return The deserialized game state.
     */
    @Override
    public GameState deserialize(String filepath) {
        ObjectMapper objectMapper = new ObjectMapper();
        GameState gameState = null;

        // Deserializes the file and bind the data to the game state object.
        try {
            File file = new File(filepath);
            gameState = objectMapper.readValue(file, GameState.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gameState;
    }

}
