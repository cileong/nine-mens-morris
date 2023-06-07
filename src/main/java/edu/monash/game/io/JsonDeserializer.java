package edu.monash.game.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.monash.game.GameState;

import java.io.File;
import java.io.IOException;

/**
 * A deserializer for JSON files.
 */
public class JsonDeserializer implements Deserializer {

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
        // Create an object mapper.
        ObjectMapper objectMapper = new ObjectMapper();
        // Create a game state object.
        GameState gameState = null;

        // Retrieve the file and deserialize it.
        try {
            File file = new File(filepath);
            gameState = objectMapper.readValue(file, GameState.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gameState;
    }

}
