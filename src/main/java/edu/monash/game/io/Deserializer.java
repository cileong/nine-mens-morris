package edu.monash.game.io;

import edu.monash.game.GameState;

public interface Deserializer {

    GameState deserialize(String filepath);

}
