package edu.monash;

import edu.monash.game.Game;
import edu.monash.game.PieceColour;
import edu.monash.game.actions.LoadAction;
import edu.monash.game.actions.SaveAction;
import edu.monash.game.io.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * The controller for the main view.
 */
public class ViewController {

    /**
     * The view representing the game board.
     */
    @FXML
    private GameBoardGridPane boardGrid;

    /**
     * The views representing the black player's hand.
     */
    @FXML
    private PlayerHandGridPane blackGrid;

    /**
     * The views representing the white player's hand.
     */
    @FXML
    private PlayerHandGridPane whiteGrid;

    /**
     * The game model.
     */
    private Game game;

    /**
     * The serializer factory used to serialize the game to files.
     */
    private SerializerFactory serializerFactory;

    /**
     * The deserializer factory used to deserialize the game from files.
     */
    private DeserializerFactory deserializerFactory;

    /**
     * Initialize the view controller.
     */
    @FXML
    private void initialize() {
        game = new Game();

        boardGrid.initialize(game, this);
        blackGrid.initialize(game, PieceColour.BLACK);
        whiteGrid.initialize(game, PieceColour.WHITE);

        serializerFactory = new SerializerFactory();
        serializerFactory.registerSerializer(new JsonSerializer());

        deserializerFactory = new DeserializerFactory();
        deserializerFactory.registerDeserializer(new JsonDeserializer());
    }

    /**
     * Resets the view to initial state.
     */
    public void resetView() {
        blackGrid.initState();
        whiteGrid.initState();
        boardGrid.initState();
        initialize();
    }

    /**
     * In the case where a player has won, display the game end dialog.
     */
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

    /**
     * In the case where the game ends in a draw, display the game end dialog.
     */
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

    /**
     * Prompt the user for input with a dialog.
     * @param title The title of the dialog.
     * @param header The header of the dialog.
     * @param contentText The content text of the dialog.
     */
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

    /**
     * The handler called when the user clicks on the "Load" menu item.
     */
    public void loadAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load game...");

        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            Deserializer deserializer = deserializerFactory.getDeserializerFor(file.getPath());
            game.execute(new LoadAction(deserializer, file.getPath()));

            boardGrid.updateView();
            blackGrid.updateView();
            whiteGrid.updateView();
        }
    }

    /**
     * The handler called when the user clicks on the "Save" menu item.
     */
    public void saveAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save game...");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            Serializer serializer = serializerFactory.getSerializerFor(file.getPath());
            game.execute(new SaveAction(serializer, file.getPath()));
        }
    }

    /**
     * The handler called when the user clicks on the "Undo" menu item.
     */
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

    /**
     * The handler called when the user clicks on the "Quit" menu item.
     */
    public void quitAction() {
        Platform.exit();
    }
}


