package edu.monash.game.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.monash.game.GameState;

import java.io.File;
import java.io.IOException;

public class JsonDeserializer implements Deserializer {

    @Override
    public GameState deserialize(String filepath) {
        ObjectMapper objectMapper = new ObjectMapper();
        GameState gameState = null;

        try {
            File file = new File(filepath);
            gameState = objectMapper.readValue(file, GameState.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gameState;
    }

}
