package edu.monash;
import edu.monash.game.Board;
import edu.monash.game.Game;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GUIController {

    private ObservableList<ImageView> boardGridChildren = FXCollections.observableArrayList();

    private Stage stage;
    private Board board;
    private Game game;
    @FXML
    private GridPane boardGrid;
    @FXML
    private GridPane leftTileGrid;
    @FXML
    private GridPane rightTileGrid;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @FXML
    private void newGame() {
        enableBlur();
        promptDialogAndRestartGame("New Game", null, "Start a new game?", 0);
        disableBlur();
    }

    @FXML
    public void quitGame() {
        enableBlur();
        promptDialogAndRestartGame("NINE MEN MORRIS", "Quit Game?", "Quit game?", 1);
        disableBlur();
    }

    private void promptDialogAndRestartGame(String title, String header, String contentText, int id) {
        ButtonType btnYes = new ButtonType("Yes", ButtonData.YES);
        ButtonType btnNo = new ButtonType("No", ButtonData.NO);
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contentText);
        alert.initOwner(stage);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(btnYes, btnNo);
        alert.showAndWait();
        if (id == 0) {
            if (alert.getResult() == btnYes) {
//                initWindow();
//                board.setNewGame(true);
            }
        } else if (id == 1) {
            if (alert.getResult() == btnYes) {
                Platform.exit();
            }
        }
    }

    @FXML
    private void handleAbout() {
        enableBlur();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("About NINE MEN MORRIS");
        alert.initOwner(stage);
        alert.showAndWait();
        disableBlur();
    }


    private void showInvalidMoveDialog(String contentText) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.initStyle(StageStyle.UTILITY);
        alert.initOwner(stage);
        alert.showAndWait();
    }



    private void enableBlur() {
        stage.getScene().getRoot().setEffect(new GaussianBlur(4));
    }

    private void disableBlur() {
        stage.getScene().getRoot().setEffect(null);
    }

    @FXML
    private void initialize() {
        board = Board.getInstance();

        for (Node i : boardGrid.getChildren()) {
            boardGridChildren.add((ImageView) i);
        }

//        initDrag(leftTileGrid);
//        initDrag(rightTileGrid);
//        initDrag(boardGrid);
//        initDrop(boardGrid);

    }

}
