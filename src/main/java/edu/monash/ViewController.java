package edu.monash;

import edu.monash.game.Game;
import edu.monash.game.GameState;
import edu.monash.game.PieceColour;
import edu.monash.game.io.JsonDeserializer;
import edu.monash.game.io.JsonSerializer;
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

    @FXML
    private void initialize() {
        game = new Game();

        boardGrid.initialize(game, this);
        blackGrid.initialize(game, PieceColour.BLACK);
        whiteGrid.initialize(game, PieceColour.WHITE);
    }

    public void resetView(){
        blackGrid.initState();
        whiteGrid.initState();
        boardGrid.initState();
        initialize();
    }

    public void showGameWonDialog() {
        JsonSerializer jsonSerializer = new JsonSerializer(game);
        jsonSerializer.serialize("game.json");
        JsonDeserializer jsonDeserializer = new JsonDeserializer();
        GameState gameState = jsonDeserializer.deserialize("game.json");
        System.out.println(gameState.getBoard());
        System.out.println(gameState.getMoves());

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
        JsonSerializer jsonSerializer = new JsonSerializer(game);
        jsonSerializer.serialize("game.json");
        JsonDeserializer jsonDeserializer = new JsonDeserializer();
        GameState gameState = jsonDeserializer.deserialize("game.json");
        System.out.println(gameState.getBoard());
        System.out.println(gameState.getMoves());

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

        Stage primaryStage = new Stage(); // You can replace this with your main stage.
        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            String filePath = file.getPath();
            System.out.println("Selected file: " + filePath);
        }
    }

    public void saveAction() {

    }

    public void undoAction(){
        boolean isRemoveAction = boardGrid.undo();

        if (!isRemoveAction && game.getPlayer().getPiecesOnHand() != 0) {
            if (game.getPlayer().getPieceColour() == PieceColour.BLACK) {
                blackGrid.undo();
            } else {
                whiteGrid.undo();
            }
        }
    }

    public void quitAction(){
        Platform.exit();
    }
}


