package edu.monash;

import edu.monash.game.Game;
import edu.monash.game.PieceColour;
import javafx.fxml.FXML;

public class ViewController {

    @FXML
    private GameBoardGridPane boardGrid;
    @FXML
    private PlayerHandGridPane blackGrid;
    @FXML
    private PlayerHandGridPane whiteGrid;

    private Game game;

    @FXML
    private void initialize() {
        game = new Game();

        boardGrid.initialize(game);
        blackGrid.initialize(game, PieceColour.BLACK);
        whiteGrid.initialize(game, PieceColour.WHITE);
    }

}
