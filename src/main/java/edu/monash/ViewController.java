package edu.monash;

import edu.monash.game.Game;
import edu.monash.game.GameState;
import edu.monash.game.PieceColour;
import edu.monash.game.io.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;

public class ViewController {

    @FXML
    private GameBoardGridPane boardGrid;
    @FXML
    private PlayerHandGridPane blackGrid;
    @FXML
    private PlayerHandGridPane whiteGrid;
    private Game game;

    private SerializerFactory serializerFactory;

    @FXML
    private void initialize() {
        game = new Game();

        boardGrid.initialize(game, this);
        blackGrid.initialize(game, PieceColour.BLACK);
        whiteGrid.initialize(game, PieceColour.WHITE);

        serializerFactory = new SerializerFactory();
        serializerFactory.registerSerializer(new JsonSerializer());
    }

    public void resetView(){
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

    public void loadAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load game...");

        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            Deserializer jsonDeserializer = new JsonDeserializer();
            GameState gameState = jsonDeserializer.deserialize(file.getPath());

            game.loadGameState(gameState);
            boardGrid.updateView();
            blackGrid.updateView();
            whiteGrid.updateView();
        }
    }

    public void saveAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save game...");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            Serializer jsonSerializer = new JsonSerializer();
            jsonSerializer.serialize(game, file.getPath());
        }
    }

    public void undoAction() {
        boolean isRemoveAction = boardGrid.undo();

        if (!isRemoveAction && game.getPlayer().getPiecesOnHand() != 0) {
            if (game.getPlayer().getPieceColour() == PieceColour.BLACK) {
                blackGrid.undo();
            } else {
                whiteGrid.undo();
            }
        }
    }

    public void quitAction() {
        Platform.exit();
    }
}


