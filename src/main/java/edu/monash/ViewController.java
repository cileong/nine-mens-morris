package edu.monash;

import edu.monash.game.Game;
import edu.monash.game.actions.Action;
import edu.monash.game.actions.MoveAction;
import edu.monash.game.player.Player;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Optional;

public class ViewController {

    private static final DataFormat GRID_PANE_CELL_FORMAT =
            new DataFormat("application/x-gridpane-cell");

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
//        showPromptDialog("New Game", null, "Start a new game?", 0);
        disableBlur();
    }

    @FXML
    public void quitGame() {
        enableBlur();
//        showPromptDialog("NINE MEN MORRIS", "Quit Game?", "Quit game?", 1);
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

    private void setupDragAndDrop(GridPane gridPane) {
        for (Node child : gridPane.getChildren()) {
            child.setOnDragDetected(event -> onDragDetectedHandler(child, event));
            child.setOnDragOver(event -> onDragOverHandler(child, event));
            child.setOnDragDropped(event -> onDragDroppedHandler(child, event));
        }
    }

    private void onDragDetectedHandler(Node child, MouseEvent event) {
        int sourceX = GridPane.getColumnIndex(child),
            sourceY = GridPane.getRowIndex(child);
        System.out.println(sourceX + " " + sourceY);

        Dragboard dragboard = child.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();

        content.put(GRID_PANE_CELL_FORMAT, new int[] { sourceX, sourceY });
        dragboard.setContent(content);

        event.consume();
    }

    private void onDragOverHandler(Node child, DragEvent event) {
        if (event.getGestureSource() != child &&
                event.getDragboard().hasContent(GRID_PANE_CELL_FORMAT))
            event.acceptTransferModes(TransferMode.MOVE);

        event.consume();
    }

    private void onDragDroppedHandler(Node child, DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        boolean success = dragboard.hasContent(GRID_PANE_CELL_FORMAT);

        if (success) {
            int[] sourceIndices = (int[]) dragboard.getContent(GRID_PANE_CELL_FORMAT);
            int sourceX = sourceIndices[0];
            int sourceY = sourceIndices[1];

            int destinationX = GridPane.getColumnIndex(child);
            int destinationY = GridPane.getRowIndex(child);

            System.out.println("Source GridPane indices: (" + sourceX + ", " + sourceY + ")");
            System.out.println("Destination GridPane indices: (" + destinationX + ", " + destinationY + ")");
            System.out.println(boardMapping[destinationY][destinationX]);
        }

        event.setDropCompleted(success);
        event.consume();
    }

    private void initDrag(GridPane grid) {
        for (Node i : grid.getChildren()) {
            if (!(i instanceof ImageView imageView))
                continue;

            imageView.setOnDragDetected(event -> {
                if (imageView.getImage() == null) {
                    return;
                }

                Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(imageView.getImage());
                content.putString(imageView.getId());
                db.setContent(content);

                event.consume();
            });

            imageView.setOnDragDone(event -> {
                if (event.getTransferMode() == TransferMode.MOVE) {
                    imageView.setImage(null);
                    if (grid.getId().equals(boardGrid.getId())) {
                        imageView.setId(null);
                    }
                }

                event.consume();
            });

            imageView.setOnDragOver(event -> { // DragEvent

                Dragboard db = event.getDragboard();
                if (event.getGestureSource() != imageView && db.hasImage() && db.hasString() && imageView.getId() == null) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            });

            imageView.setOnDragDropped(event -> { // DragEvent
                Dragboard db = event.getDragboard();
                if (db.hasImage() && db.hasString()) {

                    imageView.setImage(db.getImage());
                    imageView.setId(db.getString());
                    event.setDropCompleted(true);
                }
                event.consume();
            });
        }
    }

//    private void initDrop(GridPane grid) {
//        for (Node i : grid.getChildren()) {
//            System.out.println(i);
//            ImageView iv = (ImageView) i;
//
//            iv.setOnDragOver(event -> { // DragEvent
//                Dragboard db = event.getDragboard();
//                if (event.getGestureSource() != iv && db.hasImage() && db.hasString() && iv.getId() == null) {
//                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
//                }
//                event.consume();
//            });
//
//            iv.setOnDragDropped(event -> { // DragEvent
//                Dragboard db = event.getDragboard();
//                if (db.hasImage() && db.hasString()) {
//
//                            iv.setImage(db.getImage());
//                            iv.setId(db.getString());
////                            game.getBoard().setPlacedTileLocation(dropLocation);
//                            event.setDropCompleted(true);
//                }
//                event.consume();
//            });
//        }
//    }

    private void enableBlur() {
        stage.getScene().getRoot().setEffect(new GaussianBlur(4));
    }

    private void disableBlur() {
        stage.getScene().getRoot().setEffect(null);
    }

    @FXML
    private void initialize() {
//        setupDragAndDrop(boardGrid);


//        for (Node child : blackGrid.getChildren()) {
//            child.setOnDragDetected(event -> onDragDetectedHandler(child, event));
//            child.setOnDragOver(event -> onDragOverHandler(child, event));
//        }

        initDrag(blackGrid);
        initDrag(whiteGrid);
        initDrag(boardGrid);
//        initDrop(boardGrid);
        System.out.println("HELLO WORLD");
    }


}
