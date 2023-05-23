package edu.monash;

import edu.monash.game.Game;
import edu.monash.game.PieceColour;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;

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

        boardGrid.initialize(game, this);
        blackGrid.initialize(game, PieceColour.BLACK);
        whiteGrid.initialize(game, PieceColour.WHITE);
    }

    void resetView(){
        blackGrid.initState();
        whiteGrid.initState();
        boardGrid.initState();
        initialize();
    }

    public void showGameWonDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game won");
        alert.setHeaderText(null);
        if ((game.getPlayer().hasLost())) {
            alert.setContentText(game.getOpponent().getPieceColour() + " won the game!");
        } else {
            alert.setContentText(game.getPlayer().getPieceColour() + " won the game!");
        }
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            promptDialog("Game won", null, "Game is over. Do you want to start a new game?");
        }
    }

    public void showDrawDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Draw");
        alert.setHeaderText(null);
        alert.setContentText("No one won the game!");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            promptDialog("No valid moves left", null, "Game is over. Do you want to start a new game?");
        }
    }

    private void promptDialog(String title, String header, String contentText) {
        ButtonType btnYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contentText);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(btnYes, btnNo);
        alert.showAndWait();
        if (alert.getResult() == btnYes) {
                resetView();
        }
        else if (alert.getResult() == btnNo) {
                Platform.exit();
        }

    }
}


