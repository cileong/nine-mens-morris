package edu.monash;

import edu.monash.game.Game;

public class ViewController {

    private Game game;

    public ViewController() {
        game = new Game();
    }

    private final Integer[][] boardMapping = {
            {    0, null, null,    1, null, null,    2 },
            { null,    8, null,    9, null,   10, null },
            { null, null,   16,   17,   18, null, null },
            {    7,   15,   23, null,   19,   11,    3 },
            { null, null,   22,   21,   20, null, null },
            { null,   14, null,   13, null,   12, null },
            {    6, null, null,    5, null, null,    4 }
    };

    public Integer getPositionId(int x, int y) {
        assert x >= 0 && x < boardMapping.length;
        assert y >= 0 && y < boardMapping[0].length;
        return boardMapping[y][x];
    }

}
