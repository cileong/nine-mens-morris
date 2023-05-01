package edu.monash.game;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class GameView {

    private Stage stage;

    public GameView() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/edu/monash/game/GUI.fxml"));

        stage = new Stage();
    }

    public void show() {
        stage.show();
    }

}
