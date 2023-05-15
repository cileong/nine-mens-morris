package edu.monash;

import javafx.fxml.FXML;

public class ViewController {

    @FXML
    private GameBoardGridPane boardGrid;
    @FXML
    private PlayerHandGridPane blackGrid;
    @FXML
    private PlayerHandGridPane whiteGrid;

    @FXML
    private void initialize() {
        boardGrid.initialize();
        blackGrid.initialize();
        whiteGrid.initialize();
    }

}
