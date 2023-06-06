package edu.monash.game.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.monash.game.Game;

import java.io.File;
import java.io.IOException;

public class Serializer {

    private final GameState gameState;

    public Serializer(Game game) {
        this.gameState = new GameState(game.getBoard(), game.getMoves());
    }

    public void serialize(String filepath) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            File file = new File(filepath);
            objectMapper.writeValue(file, gameState);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR SAVING FILE.");
        }
    }

}
