package edu.monash;

import edu.monash.game.Game;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Optional;

public class ViewController {

    private Game game;

    public ViewController() {
        game = new Game();
    }

    private ObservableList<ImageView> boardGridChildren = FXCollections.observableArrayList();

    @FXML
    private Stage stage;
    @FXML
    private GridPane boardGrid;
    @FXML
    private GridPane blackGrid;
    @FXML
    private GridPane whiteGrid;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void newGame() {
        enableBlur();
        showPromptDialog("New Game", null, "Start a new game?", 0);
        disableBlur();
    }

    @FXML
    public void quitGame() {
        enableBlur();
        showPromptDialog("NINE MEN MORRIS", "Quit Game?", "Quit game?", 1);
        disableBlur();
    }

    public void showPromptDialog(String title, String header, String content, Runnable yesCallback, Runnable noCallback) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        // Remove default buttons and add custom "Yes" and "No" buttons
        alert.getButtonTypes().setAll(yesButton, noButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == yesButton) {
                yesCallback.run();
            } else if (buttonType == noButton) {
                noCallback.run();
            }
        });
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
        assert boardMapping[y][x] != null;
        return boardMapping[y][x];
    }

    private void initDrag(GridPane grid) {
        for (Node i : grid.getChildren()) {
            ImageView imageView = (ImageView) i;

            imageView.setOnDragDetected(event -> { // MouseEvent
                if (imageView.getImage() == null) {
                    return;
                }
                    game.getBoard().setAllowDropLocations(getLocationOfTile(imageView));

                    Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent content = new ClipboardContent();
                    content.putImage(imageView.getImage());
                    content.putString(imageView.getId());
                    db.setContent(content);

                event.consume();
            });

            imageView.setOnDragDone(event -> { // DragEvent
                if (event.getTransferMode() == TransferMode.MOVE) {
                    imageView.setImage(null);
                    if (grid.getId().equals(boardGrid.getId())) {
                        imageView.setId(null);
                    }
                }
                event.consume();
                resetAllowedDropLocationImages();
            });
        }
    }

    private void enableBlur() {
        stage.getScene().getRoot().setEffect(new GaussianBlur(4));
    }

    private void disableBlur() {
        stage.getScene().getRoot().setEffect(null);
    }

    @FXML
    private void initialize() {
        for (Node i : boardGrid.getChildren()) {
            boardGridChildren.add((ImageView) i);
        }

//        initDrag(leftTileGrid);
//        initDrag(rightTileGrid);
//        initDrag(boardGrid);
//        initDrop(boardGrid);

    }


}
